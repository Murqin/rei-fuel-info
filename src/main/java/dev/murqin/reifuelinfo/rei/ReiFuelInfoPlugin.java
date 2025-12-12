package dev.murqin.reifuelinfo.rei;

import dev.murqin.reifuelinfo.ReiFuelInfoMod;
import dev.murqin.reifuelinfo.util.FuelRegistryUtil;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.Items;

public class ReiFuelInfoPlugin implements REIClientPlugin {
    
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new FuelCategory());
        
        // Add furnace as workstation for this category
        registry.addWorkstations(FuelDisplay.CATEGORY_ID, 
            EntryStacks.of(Items.FURNACE));
        
        ReiFuelInfoMod.LOGGER.info("Registered Fuel category");
    }
    
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        // Register all fuel items as displays
        for (FuelRegistryUtil.FuelEntry fuel : FuelRegistryUtil.getAllFuels()) {
            registry.add(new FuelDisplay(fuel));
        }
        
        ReiFuelInfoMod.LOGGER.info("Registered {} fuel displays", FuelRegistryUtil.getAllFuels().size());
    }
}
