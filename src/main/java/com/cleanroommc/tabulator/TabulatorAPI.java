package com.cleanroommc.tabulator;

import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TabulatorAPI {

    public static final Set<CreativeTabs> removedTabs = new HashSet<>();
    private static final Set<ItemStack> removedItemsAll = new ObjectOpenCustomHashSet<>(Helper.ITEM_META_NBT_HASH_STRATEGY);
    private static final Map<CreativeTabs, Set<ItemStack>> removedItems = new HashMap<>();
    private static int removedVanillaTabs = 0;

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
        if (creativeTab == null || creativeTab == CreativeTabs.INVENTORY || creativeTab == CreativeTabs.SEARCH) {
            return;
        }
        int oldSize = CreativeTabs.CREATIVE_TAB_ARRAY.length;
        CreativeTabs.CREATIVE_TAB_ARRAY = ArrayUtils.removeElement(CreativeTabs.CREATIVE_TAB_ARRAY, creativeTab);
        if (oldSize != CreativeTabs.CREATIVE_TAB_ARRAY.length) {
            removedTabs.add(creativeTab);
            Helper.assignCreativeTabIndexes();
            if (((ModifiedCreativeTab) creativeTab).getOriginalIndex() < 12) {
                removedVanillaTabs++;
            }
        }
    }

    public static void removeItem(ItemStack item) {
        if (item == null || item.isEmpty()) return;
        removedItemsAll.add(item);
    }

    public static void removeItem(CreativeTabs tab, ItemStack item) {
        if (tab == null || item == null || item.isEmpty()) return;
        removedItems.computeIfAbsent(tab, key -> new ObjectOpenCustomHashSet<>(Helper.ITEM_META_NBT_HASH_STRATEGY)).add(item);
        if (tab != CreativeTabs.SEARCH) {
            removeItem(CreativeTabs.SEARCH, item);
        }
    }

    public static boolean shouldRemoveItem(CreativeTabs creativeTab, ItemStack item) {
        if (removedItemsAll.contains(item)) return true;
        Set<ItemStack> set = removedItems.get(creativeTab);
        return set != null && set.contains(item);
    }

    public static int getRemovedVanillaTabs() {
        return removedVanillaTabs;
    }
}
