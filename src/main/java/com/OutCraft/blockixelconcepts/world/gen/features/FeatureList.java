package com.OutCraft.blockixelconcepts.world.gen.features;

import com.OutCraft.blockixelconcepts.BlockixelConcepts;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FeatureList {
	public static DeferredRegister<Feature<?>> Features = DeferredRegister.create(ForgeRegistries.FEATURES,
			BlockixelConcepts.modid);

	public static final RegistryObject<Feature<SimpleBlockConfiguration>> FANCY_CACTUS = Features
			.register("fancy_cactus", () -> new FancyCactusFeature(SimpleBlockConfiguration.CODEC));
}
