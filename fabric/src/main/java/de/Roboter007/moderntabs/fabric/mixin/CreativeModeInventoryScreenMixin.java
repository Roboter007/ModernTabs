package de.Roboter007.moderntabs.fabric.mixin;

import de.Roboter007.moderntabs.background.config.TabIconBackgroundImage.Column;
import de.Roboter007.moderntabs.platform.CreativeModeInventoryScreenPlatform;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Row;
import org.spongepowered.asm.mixin.Mixin;

import static de.Roboter007.moderntabs.background.config.TabIconBackgroundImage.Column.*;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> implements CreativeModeInventoryScreenPlatform {

    public CreativeModeInventoryScreenMixin(CreativeModeInventoryScreen.ItemPickerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public Row moderntabs$row(CreativeModeTab creativeModeTab) {
        return creativeModeTab.row();
    }

    @Override
    public Column moderntabs$column(CreativeModeTab creativeModeTab) {
        int column = creativeModeTab.column();
        if(column == 0) {
            return LEFT;
        } else {
            return MIDDLE;
        }
    }
}
