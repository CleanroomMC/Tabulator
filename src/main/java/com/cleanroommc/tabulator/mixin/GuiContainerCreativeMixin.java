package com.cleanroommc.tabulator.mixin;

import com.cleanroommc.tabulator.TabulatorAPI;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.creativetab.CreativeTabs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Hides specified items in the search creative tab when the search bar is empty
 */
@Mixin(GuiContainerCreative.class)
public class GuiContainerCreativeMixin {

    @Shadow
    private GuiTextField searchField;

    @Inject(method = "updateCreativeSearch", at = @At("TAIL"))
    public void updateCreativeSearch(CallbackInfo ci) {
        if (searchField.getText().isEmpty()) {
            GuiContainerCreative.ContainerCreative container = (GuiContainerCreative.ContainerCreative) ((GuiContainerCreative) (Object) this).inventorySlots;
            container.itemList.removeIf(item -> TabulatorAPI.shouldRemoveItem(CreativeTabs.SEARCH, item));
        }
    }
}
