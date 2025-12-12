package dev.murqin.reifuelinfo.rei;

import dev.murqin.reifuelinfo.ReiFuelInfoMod;
import dev.murqin.reifuelinfo.util.FuelRegistryUtil;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FuelDisplay implements Display {
    public static final CategoryIdentifier<FuelDisplay> CATEGORY_ID = 
        CategoryIdentifier.of(ReiFuelInfoMod.MOD_ID, "fuel");
    
    private final FuelRegistryUtil.FuelEntry fuelEntry;
    private final EntryIngredient input;
    
    public FuelDisplay(FuelRegistryUtil.FuelEntry fuelEntry) {
        this.fuelEntry = fuelEntry;
        this.input = EntryIngredient.of(EntryStacks.of(fuelEntry.getStack()));
    }
    
    @Override
    public List<EntryIngredient> getInputEntries() {
        return Collections.singletonList(input);
    }
    
    @Override
    public List<EntryIngredient> getOutputEntries() {
        return Collections.emptyList();
    }
    
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CATEGORY_ID;
    }
    
    @Override
    public DisplaySerializer<? extends Display> getSerializer() {
        // No serialization needed for runtime-generated displays
        return null;
    }
    
    @Override
    public Optional<Identifier> getDisplayLocation() {
        // No specific location as this is a dynamically generated display
        return Optional.empty();
    }
    
    public FuelRegistryUtil.FuelEntry getFuelEntry() {
        return fuelEntry;
    }
    
    public int getBurnTime() {
        return fuelEntry.burnTime();
    }
    
    public int getSmeltCount() {
        return fuelEntry.getSmeltCount();
    }
    
    public double getSmeltCountDouble() {
        return fuelEntry.getSmeltCountDouble();
    }
    
    public double getBurnTimeSeconds() {
        return fuelEntry.getBurnTimeSeconds();
    }
    
    public ItemStack getItemStack() {
        return fuelEntry.getStack();
    }
}
