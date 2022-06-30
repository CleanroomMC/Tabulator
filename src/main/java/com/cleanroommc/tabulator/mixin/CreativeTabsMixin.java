package com.cleanroommc.tabulator.mixin;

import com.cleanroommc.tabulator.Tabulator;
import com.cleanroommc.tabulator.common.ModifiedCreativeTab;
import com.cleanroommc.tabulator.common.TabManager;
import com.cleanroommc.tabulator.common.TabulatorAPI;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeTabs.class)
public class CreativeTabsMixin implements ModifiedCreativeTab {

    @Shadow
    @Final
    private int index;

    @Unique
    private int modifiedIndex = -1;

    /**
     * Hides specified items from this tab
     */
    @Inject(method = "displayAllRelevantItems", at = @At("TAIL"))
    public void buildItems(NonNullList<ItemStack> p_78018_1_, CallbackInfo ci) {
        p_78018_1_.removeIf(item -> TabulatorAPI.shouldRemoveItem((CreativeTabs) (Object) this, item));
    }

    @Inject(method = "getIndex", at = @At("HEAD"), cancellable = true)
    public void getIndex(CallbackInfoReturnable<Integer> cir) {
        if (modifiedIndex >= 0) {
            cir.setReturnValue(modifiedIndex);
        }
    }

    @Inject(method = "getColumn", at = @At("HEAD"), cancellable = true)
    public void getColumn(CallbackInfoReturnable<Integer> cir) {
        TabManager.TabPos pos = TabManager.getPos(getThis());
        if (pos != null) {
            cir.setReturnValue(pos.column);
        }
    }

    @Inject(method = "isOnTopRow", at = @At("HEAD"), cancellable = true)
    public void isOnTopRow(CallbackInfoReturnable<Boolean> cir) {
        TabManager.TabPos pos = TabManager.getPos(getThis());
        if (pos != null) {
            cir.setReturnValue(pos.topRow);
        }
    }

    @Inject(method = "getTabPage", at = @At("HEAD"), cancellable = true, remap = false)
    public void getTabPage(CallbackInfoReturnable<Integer> cir) {
        TabManager.TabPos pos = TabManager.getPos(getThis());
        if (pos != null) {
            cir.setReturnValue(pos.page);
        }
    }

    @Override
    public void setIndex(int index) {
        modifiedIndex = index;
    }

    @Override
    public int getOriginalIndex() {
        return index;
    }

    public CreativeTabs getThis() {
        return (CreativeTabs) (Object) this;
    }
}
