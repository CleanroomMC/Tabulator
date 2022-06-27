package com.cleanroommc.tabulator.mixin;

import com.cleanroommc.tabulator.TabulatorAPI;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Hides specified items from this tab
 */
@Mixin(CreativeTabs.class)
public class CreativeTabsMixin {

    @Inject(method = "displayAllRelevantItems", at = @At("TAIL"))
    public void buildItems(NonNullList<ItemStack> p_78018_1_, CallbackInfo ci) {
        p_78018_1_.removeIf(item -> TabulatorAPI.shouldRemoveItem((CreativeTabs) (Object) this, item));
    }
}
