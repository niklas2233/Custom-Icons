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
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.InputSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.io.InputStream;

/**
 * The implementation for mc [1.19.3, 1.20)
 * See:
 * - {@link MinecraftClientMixin} in subproject 1.15.2-fabric for implementation for mc (~, 1.19.3)
 * - {@link IconsMixin} in subproject 1.20.1-fabric for implementation for mc [1.20, ~)
 */
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin
{
	@ModifyArg(
			method = "<init>",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/util/Window;setIcon(Lnet/minecraft/resource/InputSupplier;Lnet/minecraft/resource/InputSupplier;)V"
			),
			index = 0
	)
	private InputSupplier<InputStream> bringTheClassicCraftingTableIconBack_general16x(InputSupplier<InputStream> icon16)
	{
		return CustomIconStorage.hasResource("icon_16x16.png") ? CustomIconStorage.getResource("icon_16x16.png") : icon16;
	}

	@ModifyArg(
			method = "<init>",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/util/Window;setIcon(Lnet/minecraft/resource/InputSupplier;Lnet/minecraft/resource/InputSupplier;)V"
			),
			index = 1
	)
	private InputSupplier<InputStream> bringTheClassicCraftingTableIconBack_general32x(InputSupplier<InputStream> icon32)
	{
		return CustomIconStorage.hasResource("icon_32x32.png") ? CustomIconStorage.getResource("icon_32x32.png") : icon32;
	}

	@ModifyArg(
			method = "<init>",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/util/MacWindowUtil;setApplicationIconImage(Lnet/minecraft/resource/InputSupplier;)V"
			)
	)
	private InputSupplier<InputStream> bringTheClassicCraftingTableIconBack_mac(InputSupplier<InputStream> iconMac)
	{
		return CustomIconStorage.hasMacIcon() ? CustomIconStorage.getResource("minecraft.icns") : iconMac;
	}
}
