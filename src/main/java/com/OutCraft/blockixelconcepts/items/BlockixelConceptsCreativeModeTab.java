package com.OutCraft.blockixelconcepts.items;

import com.OutCraft.blockixelconcepts.lists.ItemList;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class BlockixelConceptsCreativeModeTab extends CreativeModeTab {

	public BlockixelConceptsCreativeModeTab() {
		super("blockixelconcepts");
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ItemList.FANCY_CACTUS.get());
	}

}
