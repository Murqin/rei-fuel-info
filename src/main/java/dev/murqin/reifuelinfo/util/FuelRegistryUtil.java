package dev.murqin.reifuelinfo.util;

import dev.murqin.reifuelinfo.ReiFuelInfoMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.resource.featuretoggle.FeatureSet;

import java.util.*;

/**
 * Fuel registry utility - optimized for memory efficiency.
 * Uses lazy loading and efficient caching.
 */
public class FuelRegistryUtil {
    
    public record FuelEntry(Item item, int burnTime) {
        public int getSmeltCount() {
            return burnTime / 200;
        }
        
        public double getSmeltCountDouble() {
            return burnTime / 200.0;
        }
        
        public double getBurnTimeSeconds() {
            return burnTime / 20.0;
        }
        
        public ItemStack getStack() {
            return new ItemStack(item);
        }
    }
    
    // Lazy-loaded cache - cleared when no longer needed
    private static volatile List<FuelEntry> cachedFuels = null;
    
    /**
     * Get all fuel items dynamically.
     * Results are cached but can be cleared to free memory.
     */
    public static List<FuelEntry> getAllFuels() {
        // Double-checked locking for thread safety
        List<FuelEntry> result = cachedFuels;
        if (result != null) {
            return result;
        }
        
        synchronized (FuelRegistryUtil.class) {
            result = cachedFuels;
            if (result != null) {
                return result;
            }
            
            result = collectFuels();
            cachedFuels = result;
            return result;
        }
    }
    
    /**
     * Collect all fuel items from the vanilla FuelRegistry
     */
    private static List<FuelEntry> collectFuels() {
        List<FuelEntry> fuels = new ArrayList<>();
        
        try {
            // Get registry lookup and feature set from client
            MinecraftClient client = MinecraftClient.getInstance();
            if (client == null || client.world == null) {
                ReiFuelInfoMod.LOGGER.warn("Client or world is null, cannot load fuels");
                return fuels;
            }
            
            var registryManager = client.world.getRegistryManager();
            FeatureSet featureSet = client.world.getEnabledFeatures();
            
            // Create the vanilla fuel registry with proper parameters
            net.minecraft.item.FuelRegistry vanillaFuelRegistry = 
                net.minecraft.item.FuelRegistry.createDefault(registryManager, featureSet, 200);
            
            // Iterate all registered items
            for (Item item : Registries.ITEM) {
                ItemStack stack = new ItemStack(item);
                
                // Check if item is a fuel using vanilla registry
                if (vanillaFuelRegistry.isFuel(stack)) {
                    int burnTime = vanillaFuelRegistry.getFuelTicks(stack);
                    if (burnTime > 0) {
                        fuels.add(new FuelEntry(item, burnTime));
                    }
                }
            }
            
            // Sort by burn time (highest first)
            fuels.sort(Comparator.comparingInt(FuelEntry::burnTime).reversed());
            
            ReiFuelInfoMod.LOGGER.info("Collected {} fuel items dynamically", fuels.size());
            
        } catch (Exception e) {
            ReiFuelInfoMod.LOGGER.error("Failed to collect fuels: {}", e.getMessage());
        }
        
        return fuels;
    }
    
    /**
     * Clear the fuel cache to free memory.
     * Call this when leaving the REI screen or on world unload.
     */
    public static void clearCache() {
        cachedFuels = null;
    }
    
    /**
     * Get the count of cached fuels without triggering a load.
     * Returns -1 if not cached.
     */
    public static int getCachedCount() {
        List<FuelEntry> cache = cachedFuels;
        return cache != null ? cache.size() : -1;
    }
}
