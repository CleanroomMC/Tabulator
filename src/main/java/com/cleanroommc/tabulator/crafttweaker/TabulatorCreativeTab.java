package com.cleanroommc.tabulator.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nonnull;

@ZenClass("mods.tabulator.CreativeTab")
public class TabulatorCreativeTab extends CreativeTabs {

    private final ItemStack icon;
    private final NonNullList<ItemStack> content = NonNullList.create();

    public TabulatorCreativeTab(String label, ItemStack icon) {
        super(label);
        this.icon = icon;
    }

    @Override
    @Nonnull
    public ItemStack createIcon() {
        return icon;
    }

    public NonNullList<ItemStack> getContent() {
        return content;
    }

    @Override
    public void displayAllRelevantItems(NonNullList<ItemStack> p_78018_1_) {
        p_78018_1_.addAll(content);
    }

    public void addItemStack(IItemStack item) {
        if (item == null) {
            CraftTweakerAPI.logError("Can't add null item to creative tab " + getTabLabel());
            return;
        }
        content.add(CraftTweakerMC.getItemStack(item));
    }

    @ZenMethod
    public TabulatorCreativeTab addItem(IItemStack... items) {
        for (IItemStack item : items) {
            addItemStack(item);
        }
        return this;
    }

    @ZenMethod
    public TabulatorCreativeTab addIngredient(IIngredient... items) {
        for (IIngredient ingredient : items) {
            addItem(ingredient.getItemArray());
        }
        return this;
    }
}
