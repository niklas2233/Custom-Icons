/*
 * This file is part of the Custom Icon project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2025  niklas2233 and contributors
 *
 * Custom Icon is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Custom Icon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Custom Icon.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.niklas2233.customicon;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

//#if MC >= 11903
//$$ import net.minecraft.resource.InputSupplier;
//#endif

public class CustomIconStorage
{
	private static final String RESOURCES_ROOT = "assets/CustomIcon/icons/";
	private static final String CUSTOM_ICONS_DIR = "config/custom icons/";
	private static final Map<String, byte[]> STORAGE = Maps.newLinkedHashMap();
	private static final List<String> PNG_PATHS = Lists.newArrayList();
	private static boolean inited = false;

	public synchronized static void init()
	{
		if (inited)
		{
			return;
		}

		ensureConfigScaffold();

		// Only load custom icons from config folder; do not load bundled defaults
		loadResource("icon_16x16.png", true);
		loadResource("icon_32x32.png", true);
		loadResource("icon_48x48.png", true);
		loadResource("icon_128x128.png", true);
		loadResource("icon_256x256.png", true);
		loadResource("minecraft.icns", false);

		CustomIconMod.LOGGER.info("{} initialized", CustomIconStorage.class.getSimpleName());
		inited = true;
	}

	private static void ensureConfigScaffold()
	{
		try
		{
			Path iconsDir = Paths.get(CUSTOM_ICONS_DIR);
			if (Files.notExists(iconsDir))
			{
				Files.createDirectories(iconsDir);
				CustomIconMod.LOGGER.info("Created directory: {}", iconsDir.toAbsolutePath());
			}

			Path configDir = Paths.get("config"); // top-level config folder
			Path configFile = configDir.resolve("custom_icon.json");
			if (Files.notExists(configFile))
			{
				String defaultJson = "{\n" +
					"  \"version\": 1,\n" +
					"  \"info\": \"Place PNGs in 'config/custom icons/'. If missing, Minecraft uses its default icons.\",\n" +
					"  \"icons\": [\n" +
					"    \"icon_16x16.png\",\n" +
					"    \"icon_32x32.png\",\n" +
					"    \"icon_48x48.png\",\n" +
					"    \"icon_128x128.png\",\n" +
					"    \"icon_256x256.png\",\n" +
					"    \"minecraft.icns\"\n" +
					"  ]\n" +
					"}\n";
				Files.createDirectories(configDir);
				Files.write(configFile, defaultJson.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
				CustomIconMod.LOGGER.info("Created default config: {}", configFile.toAbsolutePath());
			}
		}
		catch (Exception e)
		{
			CustomIconMod.LOGGER.warn("Failed to create config scaffold: {}", e.toString());
		}
	}

	private static void loadResource(String path, boolean isPng)
	{
		// Only try to load custom icon from config directory. If absent, do not override.
		Path customIconPath = Paths.get(CUSTOM_ICONS_DIR + path);
		if (Files.exists(customIconPath))
		{
			try (InputStream stream = Files.newInputStream(customIconPath))
			{
				byte[] data = IOUtils.toByteArray(stream);
				STORAGE.put(path, data);
				if (isPng)
				{
					PNG_PATHS.add(path);
				}
				CustomIconMod.LOGGER.info("Loaded custom icon: {}", customIconPath);
			}
			catch (IOException e)
			{
				CustomIconMod.LOGGER.warn("Failed to load custom icon from {}: {}", customIconPath, e.getMessage());
			}
		}
	}

	//#if MC >= 11903
	//$$ public static InputSupplier<InputStream> getResource(String path)
	//#else
	public static InputStream getResource(String path)
	//#endif
	{
		init();
		byte[] data = STORAGE.get(path);
		if (data == null)
		{
			// Resource not provided; caller should handle and fall back to Minecraft defaults
			return
				//#if MC >= 11903
				//$$ () ->
				//#endif
				new ByteArrayInputStream(new byte[0]);
		}
		return
				//#if MC >= 11903
				//$$ () ->
				//#endif
				new ByteArrayInputStream(data);
	}

	//#if MC >= 12000
	//$$ public static List<InputSupplier<InputStream>> getAllPngResources()
	//$$ {
	//$$ 	init();
	//$$ 	return PNG_PATHS.stream().
	//$$ 			map(CustomIconStorage::getResource).
	//$$ 			toList();
	//$$ }
	//#endif

	public static boolean hasCustomPngs()
	{
		init();
		return !PNG_PATHS.isEmpty();
	}

	public static boolean hasMacIcon()
	{
		init();
		return STORAGE.containsKey("minecraft.icns");
	}

	public static boolean hasResource(String path)
	{
		init();
		return STORAGE.containsKey(path);
	}
}
