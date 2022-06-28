package com.cleanroommc.tabulator.common;

import com.cleanroommc.tabulator.Tabulator;
import net.minecraft.creativetab.CreativeTabs;

import java.util.*;

public class TabManager {

    private static final List<CreativeTabs> vanillaTabs = new ArrayList<>();
    private static final List<CreativeTabs> modTabs = new ArrayList<>();
    private static final List<List<CreativeTabs>> pages = new ArrayList<>();
    private static final Map<CreativeTabs, TabPos> positions = new HashMap<>();
    private static boolean dirty = true;

    private static void buildPages() {
        vanillaTabs.clear();
        modTabs.clear();

        for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
            if (((ModifiedCreativeTab) tab).getOriginalIndex() > 11) {
                modTabs.add(tab);
            } else {
                vanillaTabs.add(tab);
            }
        }
        vanillaTabs.sort(Comparator.comparingInt(CreativeTabs::getIndex));
        modTabs.sort(Comparator.comparingInt(CreativeTabs::getIndex));

        pages.clear();
        positions.clear();
        List<CreativeTabs> tabs1 = new ArrayList<>();
        tabs1.add(CreativeTabs.SEARCH);
        tabs1.add(CreativeTabs.INVENTORY);
        positions.put(CreativeTabs.SEARCH, new TabPos(true, 5, 0));
        positions.put(CreativeTabs.INVENTORY, new TabPos(false, 5, 0));
        boolean addedVanillaTabs = false, addedHotbarTab = false;
        int column = -1, page = 0;
        boolean topRow = true;
        if (!vanillaTabs.isEmpty()) {
            for (CreativeTabs tab : vanillaTabs) {
                if (tab == CreativeTabs.HOTBAR) {
                    tabs1.add(tab);
                    positions.put(tab, new TabPos(true, 4, 0));
                    addedHotbarTab = true;
                    addedVanillaTabs = true;
                    continue;
                }
                if (tab != CreativeTabs.SEARCH && tab != CreativeTabs.INVENTORY) {
                    if (++column == (addedHotbarTab && topRow ? 4 : 5)) {
                        if (!topRow) break;
                        topRow = false;
                        column = 0;
                    }
                    TabPos pos = new TabPos(topRow, column, page);
                    tabs1.add(tab);
                    positions.put(tab, pos);
                    addedVanillaTabs = true;
                }
            }
        }

        if (addedVanillaTabs) {
            pages.add(tabs1);
            tabs1 = new ArrayList<>();
            page++;
        }
        topRow = true;
        column = -1;

        for (CreativeTabs tab : modTabs) {
            if (++column == 5) {
                if (!topRow) {
                    pages.add(tabs1);
                    tabs1 = new ArrayList<>();
                    topRow = true;
                    page++;
                } else {
                    topRow = false;
                }
                column = 0;
            }
            TabPos pos = new TabPos(topRow, column, page);
            tabs1.add(tab);
            positions.put(tab, pos);
        }

        if (!tabs1.isEmpty()) {
            pages.add(tabs1);
        }
    }

    public static CreativeTabs[] getTabs(int page) {
        if (dirty) {
            buildPages();
            dirty = false;
        }
        return pages.get(page).toArray(new CreativeTabs[0]);
    }

    public static TabPos getPos(CreativeTabs tab) {
        if (dirty) {
            buildPages();
            dirty = false;
        }
        return positions.get(tab);
    }

    public static int getPageCount() {
        if (dirty) {
            buildPages();
            dirty = false;
        }
        return pages.size();
    }

    public static void markDirty() {
        dirty = true;
    }

    public static class TabPos {
        public final boolean topRow;
        public final int column;
        public final int page;

        private TabPos(boolean topRow, int column, int page) {
            this.topRow = topRow;
            this.column = column;
            this.page = page;
        }
    }
}
