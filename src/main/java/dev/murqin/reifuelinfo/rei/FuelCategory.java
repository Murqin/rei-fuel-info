package dev.murqin.reifuelinfo.rei;

import dev.murqin.reifuelinfo.ReiFuelInfoMod;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FuelCategory implements DisplayCategory<FuelDisplay> {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#");
    
    @Override
    public CategoryIdentifier<? extends FuelDisplay> getCategoryIdentifier() {
        return FuelDisplay.CATEGORY_ID;
    }
    
    @Override
    public Text getTitle() {
        return Text.translatable("category.reifuelinfo.fuel");
    }
    
    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Items.FURNACE);
    }
    
    @Override
    public int getDisplayHeight() {
        return 60;
    }
    
    @Override
    public int getDisplayWidth(FuelDisplay display) {
        return 180;
    }
    
    @Override
    public List<Widget> setupDisplay(FuelDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        Point startPoint = new Point(bounds.getCenterX() - 85, bounds.getCenterY() - 25);
        
        // Background panel
        widgets.add(Widgets.createRecipeBase(bounds));
        
        // Fuel item slot
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 5, startPoint.y + 15))
            .entries(display.getInputEntries().get(0))
            .markInput());
        
        // Fire/flame icon (animated)
        widgets.add(Widgets.createBurningFire(new Point(startPoint.x + 30, startPoint.y + 17))
            .animationDurationTicks(display.getBurnTime()));
        
        // Burn time text
        String burnTimeText = String.format("%d ticks (%.1fs)", 
            display.getBurnTime(), 
            display.getBurnTimeSeconds());
        widgets.add(Widgets.createLabel(new Point(startPoint.x + 55, startPoint.y + 8), 
                Text.literal("⏱ " + burnTimeText).formatted(Formatting.GRAY))
            .leftAligned()
            .noShadow());
        
        // Smelt count text (now with decimals)
        double smeltCount = display.getSmeltCountDouble();
        String smeltText = DECIMAL_FORMAT.format(smeltCount) + " item" + (smeltCount != 1 ? "s" : "");
        widgets.add(Widgets.createLabel(new Point(startPoint.x + 55, startPoint.y + 22), 
                Text.literal("🔥 Smelts: " + smeltText).formatted(Formatting.YELLOW))
            .leftAligned()
            .noShadow());
        
        // Progress bar showing relative burn time (compared to coal = 1600 ticks)
        int maxBurnTime = 24000; // Lava bucket burn time as max
        double progress = Math.min(1.0, (double) display.getBurnTime() / maxBurnTime);
        int barWidth = (int) (100 * progress);
        
        widgets.add(Widgets.createDrawableWidget((drawContext, mouseX, mouseY, delta) -> {
            // Background bar
            drawContext.fill(startPoint.x + 55, startPoint.y + 36, 
                startPoint.x + 155, startPoint.y + 42, 0xFF333333);
            // Progress bar
            int color = getProgressColor(progress);
            drawContext.fill(startPoint.x + 55, startPoint.y + 36, 
                startPoint.x + 55 + barWidth, startPoint.y + 42, color);
        }));
        
        return widgets;
    }
    
    private int getProgressColor(double progress) {
        if (progress < 0.25) return 0xFF55FF55; // Green (low burn time)
        if (progress < 0.5) return 0xFFFFFF55;  // Yellow
        if (progress < 0.75) return 0xFFFFAA00; // Orange
        return 0xFFFF5555; // Red (high burn time)
    }
}
