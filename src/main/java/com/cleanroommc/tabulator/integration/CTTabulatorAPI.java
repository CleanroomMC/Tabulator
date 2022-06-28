package com.cleanroommc.tabulator.integration;

import com.cleanroommc.tabulator.common.TabulatorAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.tabulator.Tabs")
@ZenRegister
public class CTTabulatorAPI {

    /**
     * Removes a creative tab from the menu
     *
     * @param tab tab to remove
     */
    @ZenMethod
    public static void removeTab(String tab) {
        TabulatorAPI.removeTab(tab);
    }

    /**
     * Remove an item from all creative tabs
     *
     * @param itemStack item to remove
     */
    @ZenMethod
    public static void remove(IItemStack itemStack) {
        TabulatorAPI.removeItem(CraftTweakerMC.getItemStack(itemStack));
    }

    /**
     * Remove an ingredient (f.e. ore dict) from all creative tabs
     *
     * @param ingredient ingredient to remove
     */
    @ZenMethod
    public static void remove(IIngredient ingredient) {
        for (IItemStack itemStack : ingredient.getItemArray()) {
            remove(itemStack);
        }
    }

    /**
     * Remove an item from a specific creative tab. Also removes the item from the search tab.
     *
     * @param tab       tab to remove from
     * @param itemStack item to remove
     */
    @ZenMethod
    public static void remove(String tab, IItemStack itemStack) {
        TabulatorAPI.removeItem(TabulatorAPI.getCreativeTab(tab), CraftTweakerMC.getItemStack(itemStack));
    }
}
