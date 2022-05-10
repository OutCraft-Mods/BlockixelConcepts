package com.OutCraft.blockixelconcepts.world.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.OutCraft.blockixelconcepts.lists.BlockList;
import com.OutCraft.blockixelconcepts.world.gen.features.FeatureList;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
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
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class VegetationGen {
	static Random random = new Random();
	private static final List<Holder<PlacedFeature>> BADLANDS_VEGETATION = new ArrayList<>();

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void biomeLoading(BiomeLoadingEvent event) {
		if (event.getCategory() == BiomeCategory.MESA) {
			event.getGeneration().getFeatures(Decoration.VEGETAL_DECORATION).addAll(BADLANDS_VEGETATION);
		}
	}

	public static void registerVegetation() {
		final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_FANCY_CACTUS_BADLANDS_CONFIG = FeatureUtils
				.register("patch_mesa_cactus_config", Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(
						10,
						PlacementUtils.inlinePlaced(FeatureList.FANCY_CACTUS.get(),
								new SimpleBlockConfiguration(BlockStateProvider.simple(BlockList.FANCY_CACTUS.get())),
								BlockPredicateFilter
										.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE,
												BlockPredicate.wouldSurvive(
														BlockList.FANCY_CACTUS.get().defaultBlockState(),
														BlockPos.ZERO))))));

		final Holder<PlacedFeature> PATCH_FANCY_CACTUS_BADLANDS = PlacementUtils.register("patch_cactus_mesa",
				PATCH_FANCY_CACTUS_BADLANDS_CONFIG, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		BADLANDS_VEGETATION.add(PATCH_FANCY_CACTUS_BADLANDS);
	}
}
