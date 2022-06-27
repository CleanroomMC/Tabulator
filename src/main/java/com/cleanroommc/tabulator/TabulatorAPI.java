package com.cleanroommc.tabulator;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class TabulatorAPI {

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

    public static final Set<CreativeTabs> removedTabs = new HashSet<>();
    private static final Set<ItemStack> removedItemsAll = new ObjectOpenCustomHashSet<>(ITEM_META_NBT_HASH_STRATEGY);
    private static final Map<CreativeTabs, Set<ItemStack>> removedItems = new HashMap<>();

    @Nullable
    public static CreativeTabs getCreativeTab(String creativeTab) {
        for (CreativeTabs tabs : CreativeTabs.CREATIVE_TAB_ARRAY) {
            if (tabs.getTabLabel().equals(creativeTab)) {
                return tabs;
            }
        }
        return null;
    }

    public static void removeTab(String creativeTab) {
        for (CreativeTabs tabs : CreativeTabs.CREATIVE_TAB_ARRAY) {
            if (tabs.getTabLabel().equals(creativeTab)) {
                removeTab(tabs);
                return;
            }
        }
    }

    public static void removeTab(CreativeTabs creativeTab) {
        if (creativeTab == null || creativeTab == CreativeTabs.INVENTORY) {
            return;
        }
        int oldSize = CreativeTabs.CREATIVE_TAB_ARRAY.length;
        CreativeTabs.CREATIVE_TAB_ARRAY = ArrayUtils.removeElement(CreativeTabs.CREATIVE_TAB_ARRAY, creativeTab);
        if (oldSize != CreativeTabs.CREATIVE_TAB_ARRAY.length) {
            removedTabs.add(creativeTab);
            assignIndexes();
        }
    }

    public static void removeItem(ItemStack item) {
        if (item == null || item.isEmpty()) return;
        removedItemsAll.add(item);
    }

    public static void removeItem(CreativeTabs tab, ItemStack item) {
        if (tab == null || item == null || item.isEmpty()) return;
        removedItems.computeIfAbsent(tab, key -> new ObjectOpenCustomHashSet<>(ITEM_META_NBT_HASH_STRATEGY)).add(item);
        if (tab != CreativeTabs.SEARCH) {
            removeItem(CreativeTabs.SEARCH, item);
        }
    }

    public static boolean shouldRemoveItem(CreativeTabs creativeTab, ItemStack item) {
        if (removedItemsAll.contains(item)) return true;
        Set<ItemStack> set = removedItems.get(creativeTab);
        return set != null && set.contains(item);
    }

    private static void assignIndexes() {
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
}
