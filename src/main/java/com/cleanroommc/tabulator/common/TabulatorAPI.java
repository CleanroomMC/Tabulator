package com.cleanroommc.tabulator.common;

import crafttweaker.CraftTweakerAPI;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TabulatorAPI {

    private static final Set<CreativeTabs> removedTabs = new HashSet<>();
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
        CreativeTabs tab = getCreativeTab(creativeTab);
        if (tab == null) {
            CraftTweakerAPI.logError("Could not find creative tab with name " + creativeTab);
        } else {
            removeTab(tab);
        }
    }

    public static void removeTab(CreativeTabs creativeTab) {
        if (creativeTab == CreativeTabs.INVENTORY || creativeTab == CreativeTabs.SEARCH) {
            CraftTweakerAPI.logError("Creative tabs 'search' and 'inventory' can not be removed!");
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
            TabManager.markDirty();

            NonNullList<ItemStack> items = NonNullList.create();
            for (Item item : ForgeRegistries.ITEMS) {
                if (item.getCreativeTab() != creativeTab) {
                    item.getSubItems(creativeTab, items);
                }
            }
            for (ItemStack item : items) {
                removeItem(CreativeTabs.SEARCH, item);
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
        CreativeTabs tab = item.getItem().getCreativeTab();
        if (tab != null && removedTabs.contains(tab)) return true;
        Set<ItemStack> set = removedItems.get(creativeTab);
        return set != null && set.contains(item);
    }

    public static int getRemovedVanillaTabs() {
        return removedVanillaTabs;
    }
}
