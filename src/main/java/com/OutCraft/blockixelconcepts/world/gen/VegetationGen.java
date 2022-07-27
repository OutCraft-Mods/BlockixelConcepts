package com.OutCraft.blockixelconcepts.world.gen;

import com.OutCraft.blockixelconcepts.BlockixelConcepts;
import com.OutCraft.blockixelconcepts.lists.BlockList;
import com.OutCraft.blockixelconcepts.world.gen.features.FeatureList;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class VegetationGen {
	public static void registerVegetation() {
		final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_FANCY_CACTUS_BADLANDS_CONFIG = FeatureUtils
				.register(BlockixelConcepts.modid + "patch_badlands_fancy_cactus_config", Feature.RANDOM_PATCH,
						FeatureUtils.simpleRandomPatchConfiguration(10, PlacementUtils.inlinePlaced(
								FeatureList.FANCY_CACTUS.get(),
								new SimpleBlockConfiguration(BlockStateProvider.simple(BlockList.FANCY_CACTUS.get())),
								BlockPredicateFilter
										.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE,
												BlockPredicate.wouldSurvive(
														BlockList.FANCY_CACTUS.get().defaultBlockState(),
														BlockPos.ZERO))))));

		final Holder<PlacedFeature> PATCH_FANCY_CACTUS_BADLANDS = PlacementUtils.register(
				new ResourceLocation(BlockixelConcepts.modid, "patch_fancy_cactus_badlands").toString(),
				PATCH_FANCY_CACTUS_BADLANDS_CONFIG, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
	}
}
