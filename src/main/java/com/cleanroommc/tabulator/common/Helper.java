package com.cleanroommc.tabulator.common;

import it.unimi.dsi.fastutil.Hash;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public class Helper {

    public static void assignCreativeTabIndexes() {
        int i = 0;
        for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
            ((ModifiedCreativeTab) tab).setIndex(i++);
        }
    }

    public static final Hash.Strategy<ItemStack> ITEM_META_NBT_HASH_STRATEGY = new Hash.Strategy<ItemStack>() {
        @Override
        public int hashCode(ItemStack o) {
            return Objects.hash(o.getItem(), o.getMetadata(), o.getTagCompound());
        }

        @Override
        public boolean equals(ItemStack a, ItemStack b) {
            if (a == b) return true;
            if (a == null || b == null) return false;
            return (a.isEmpty() && b.isEmpty()) ||
                    (a.getItem() == b.getItem() &&
                            a.getMetadata() == b.getMetadata() &&
                            Objects.equals(a.getTagCompound(), b.getTagCompound()));
        }
    };
}
