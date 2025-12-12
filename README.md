# REI Fuel Info

A Fabric mod plugin for [Roughly Enough Items (REI)](https://github.com/shedaniel/RoughlyEnoughItems) that displays fuel information in Minecraft.

![Minecraft](https://img.shields.io/badge/Minecraft-1.21.10-green)
![Fabric API](https://img.shields.io/badge/Fabric%20API-Required-blue)
![REI](https://img.shields.io/badge/REI-Required-orange)

## Features

- 🔥 **Fuel Category**: Adds a dedicated "Fuel" category to REI
- ⏱ **Burn Time Display**: Shows burn time in ticks and seconds
- 📊 **Smelt Count**: Shows how many items each fuel can smelt (with decimals)
- 🎨 **Visual Progress Bar**: Color-coded bar comparing fuel efficiency
- 🔄 **Dynamic Detection**: Automatically detects all vanilla and modded fuels

## Screenshots

The mod adds a "Fuel" category accessible from the REI interface showing:
- Fuel item
- Animated flame icon
- Burn time (ticks & seconds)
- Items that can be smelted
- Visual efficiency bar

## Requirements

- Minecraft 1.21.10
- Fabric Loader 0.18.2+
- Fabric API
- Roughly Enough Items (REI)

## Installation

1. Download the latest release from [Releases](https://github.com/yourusername/rei-fuel-info/releases)
2. Place the JAR file in your `.minecraft/mods/` folder
3. Make sure you have Fabric API and REI installed
4. Launch Minecraft with Fabric

## Usage

1. Open REI in-game (default: `R` key)
2. Look for the **Fuel** category (🔥 furnace icon)
3. Browse all available fuels and their statistics

## Building from Source

```bash
./gradlew build
```

The compiled JAR will be in `build/libs/`.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Credits

- [shedaniel](https://github.com/shedaniel) for REI
- [FabricMC](https://fabricmc.net/) for the Fabric modding platform
