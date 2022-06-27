package com.cleanroommc.tabulator;

import it.unimi.dsi.fastutil.Hash;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class Helper {

    public static void assignCreativeTabIndexes() {
        int i = 0;
        for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
            try {
                indexField.set(tab, i++);
            } catch (IllegalAccessException e) {
                System.out.println("Error assigning indexes at index " + --i);
                e.printStackTrace();
            }
        }
    }

    private static final Field indexField;

    static {
        Field indexField1;
        try {
            indexField1 = CreativeTabs.class.getDeclaredField("index");
            indexField1.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(indexField1, indexField1.getModifiers() & ~Modifier.FINAL);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            indexField1 = null;
        }
        indexField = indexField1;
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
