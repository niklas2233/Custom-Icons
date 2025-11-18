
# Custom Icon

[![License](https://img.shields.io/github/license/Fallen-Breath/classic-minecraft-icon.svg)](http://www.gnu.org/licenses/lgpl-3.0.html)

This is a Fork/copy from [Classic minecraft icons](https://github.com/Fallen-Breath/classic-minecraft-icon)

Here's a simple client-side mod to customize the window icon. Use your own icons or let Minecraft use its defaults!

This mod is client-side only and requires no extra dependency. It supports all Minecraft versions since 1.14 (fabric) or since 1.15 (forge)

## Custom Icons

You can now use your own custom icons instead of the default crafting table icons! Simply place your custom icon files in the following directory:

```
.minecraft/config/custom icons/
```

The mod will only use icons you place there. If a file is missing, Minecraft will use its own default icons.

### Required Icon Files

To use custom icons, create the following files in the directory above:

- `icon_16x16.png` - 16x16 pixels (Windows/Linux small icon)
- `icon_32x32.png` - 32x32 pixels (Windows/Linux large icon)
- `icon_48x48.png` - 48x48 pixels (additional size for better scaling)
- `icon_128x128.png` - 128x128 pixels (high-res icon)
- `icon_256x256.png` - 256x256 pixels (very high-res icon)
- `minecraft.icns` - macOS icon file (optional, for macOS users)

**Note:** You only need to provide the icon files you want to customize. Any missing sizes will fall back to Minecraft's default icons.

### Example

To use a custom 32x32 icon while keeping other sizes default:
1. Create the directory: `.minecraft/config/custom icons/`
2. Place your `icon_32x32.png` file in that directory
3. Launch Minecraft - the mod will use your custom 32x32 icon and default icons for other sizes

Additionally, a default config file is created on first run at:

```
.minecraft/config/custom_icon.json
```
It lists the expected file names and explains where to place your PNGs.

