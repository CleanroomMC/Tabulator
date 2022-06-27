package com.cleanroommc.tabulator;

import it.unimi.dsi.fastutil.Hash;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Field;
import java.util.Objects;

public class Helper {

    private static Field indexField;

    public static void assignCreativeTabIndexes() {
        int i = 0;
        for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
            ((ModifiedCreativeTab) tab).setIndex(i++);
        }
        /*if (indexField == null) {
            Field indexField1;
            try {
                indexField1 = CreativeTabs.class.getDeclaredField("index");
                indexField1.setAccessible(true);

                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(indexField1, indexField1.getModifiers() & ~Modifier.FINAL);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
            indexField = indexField1;
        }
        int i = 0;
        for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
            try {
                indexField.set(tab, i++);
            } catch (IllegalAccessException e) {
                System.out.println("Error assigning indexes at index " + (i - 1));
                e.printStackTrace();
            }
        }*/
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
