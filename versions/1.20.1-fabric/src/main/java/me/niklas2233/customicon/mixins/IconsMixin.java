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

package me.niklas2233.customicon.mixins;

import me.niklas2233.customicon.CustomIconStorage;
import net.minecraft.client.util.Icons;
import net.minecraft.resource.InputSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.InputStream;
import java.util.List;

/**
 * The implementation for mc [1.20, ~)
 * See:
 * - {@link MinecraftClientMixin} in subproject 1.15.2-fabric for implementation for mc (~, 1.19.3)
 * - {@link MinecraftClientMixin} in subproject 1.19.3-fabric for implementation for mc [1.19.3, 1.20)
 */
@Mixin(Icons.class)
public abstract class IconsMixin
{
	@Inject(method = "getIcons", at = @At("HEAD"), cancellable = true)
	private void bringTheClassicCraftingTableIconBack_general(CallbackInfoReturnable<List<InputSupplier<InputStream>>> cir)
	{
		if (CustomIconStorage.hasCustomPngs())
		{
			List<InputSupplier<InputStream>> list = CustomIconStorage.getAllPngResources();
			if (list != null && !list.isEmpty())
			{
				cir.setReturnValue(list);
			}
		}
	}

	@Inject(method = "getMacIcon", at = @At("HEAD"), cancellable = true)
	private void bringTheClassicCraftingTableIconBack_mac( CallbackInfoReturnable<InputSupplier<InputStream>> cir)
	{
		if (CustomIconStorage.hasMacIcon())
		{
			cir.setReturnValue(CustomIconStorage.getResource("minecraft.icns"));
		}
	}
}
