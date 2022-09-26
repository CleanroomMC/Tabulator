package com.cleanroommc.tabulator.mixin;

import com.cleanroommc.tabulator.common.TabulatorAPI;
import net.minecraft.client.util.SearchTree;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SearchTree.class)
public class SearchTreeMixin {

    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private <T> void onAdd(T element, CallbackInfo ci) {
        if (element instanceof ItemStack) {
            if (TabulatorAPI.shouldRemoveItem(CreativeTabs.SEARCH, (ItemStack) element)) {
                ci.cancel();
            }
        }
    }

}
