package com.OutCraft.blockixelconcepts.lists;

import com.OutCraft.blockixelconcepts.BlockixelConcepts;
import com.OutCraft.blockixelconcepts.items.BlockixelConceptsCreativeModeTab;
import com.OutCraft.blockixelconcepts.items.TreasureGlove;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemList {
	public static CreativeModeTab BlockixelConceptsCreativeModeTab = new BlockixelConceptsCreativeModeTab();

	public static DeferredRegister<Item> Items = DeferredRegister.create(ForgeRegistries.ITEMS,
			BlockixelConcepts.modid);

	public static final RegistryObject<Item> TREASURE_GLOVE = Items.register("treasure_glove", TreasureGlove::new);

	public static final RegistryObject<Item> FANCY_CACTUS = Items.register("fancy_cactus",
			() -> new BlockItem(BlockList.FANCY_CACTUS.get(),
					new Item.Properties().tab(BlockixelConceptsCreativeModeTab)));
}
