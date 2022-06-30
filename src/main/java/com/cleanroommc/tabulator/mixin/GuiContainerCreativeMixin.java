package com.cleanroommc.tabulator.mixin;

import com.cleanroommc.tabulator.Tabulator;
import com.cleanroommc.tabulator.common.TabManager;
import com.cleanroommc.tabulator.common.TabulatorAPI;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.creativetab.CreativeTabs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainerCreative.class)
public class GuiContainerCreativeMixin {

    @Shadow
    private GuiTextField searchField;

    /**
     * Hides specified items in the search creative tab when the search bar is empty
     */
    @Inject(method = "updateCreativeSearch", at = @At("TAIL"))
    public void updateCreativeSearch(CallbackInfo ci) {
        if (searchField.getText().isEmpty()) {
            GuiContainerCreative.ContainerCreative container = (GuiContainerCreative.ContainerCreative) ((GuiContainerCreative) (Object) this).inventorySlots;
            container.itemList.removeIf(item -> TabulatorAPI.shouldRemoveItem(CreativeTabs.SEARCH, item));
        }
    }

    /**
     * @return the real page count
     */
    @Redirect(method = "initGui", at = @At(value = "INVOKE", target = "Ljava/lang/Math;ceil(D)D"))
    public double initGui(double v) {
        return TabManager.getPageCount() - 1;
    }

    @ModifyVariable(method = "initGui", at = @At(value = "STORE"), ordinal = 1)
    private int initGui2(int v) {
        return TabManager.getPageCount() + 11;
    }

    /**
     * @return the real tabs for the current page
     */
    @Redirect(method = "drawGuiContainerBackgroundLayer",
            at = @At(value = "INVOKE", target = "Ljava/util/Arrays;copyOfRange([Ljava/lang/Object;II)[Ljava/lang/Object;"))
    public <T> T[] drawGuiContainerBackgroundLayer(T[] ts, int start, int end) {
        int index = start / 10;
        return (T[]) TabManager.getTabs(index);
    }

    /**
     * @return the real tabs for the current page
     */
    @Redirect(method = "drawScreen",
            at = @At(value = "INVOKE", target = "Ljava/util/Arrays;copyOfRange([Ljava/lang/Object;II)[Ljava/lang/Object;"))
    public <T> T[] drawScreen(T[] ts, int start, int end) {
        return drawGuiContainerBackgroundLayer(ts, start, end);
    }
}
