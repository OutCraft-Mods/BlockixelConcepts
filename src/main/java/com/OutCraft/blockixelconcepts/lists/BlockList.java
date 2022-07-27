package com.OutCraft.blockixelconcepts.lists;

import com.OutCraft.blockixelconcepts.BlockixelConcepts;
import com.OutCraft.blockixelconcepts.blocks.FancyCactus;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockList {
	public static DeferredRegister<Block> Blocks = DeferredRegister.create(ForgeRegistries.BLOCKS,
			BlockixelConcepts.modid);

	public static final RegistryObject<Block> FANCY_CACTUS = Blocks.register("fancy_cactus", FancyCactus::new);
}
