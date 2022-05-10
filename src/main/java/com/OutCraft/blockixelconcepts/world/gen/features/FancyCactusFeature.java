package com.OutCraft.blockixelconcepts.world.gen.features;

import com.OutCraft.blockixelconcepts.blocks.FancyCactus;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class FancyCactusFeature extends SimpleBlockFeature {

	public FancyCactusFeature(Codec<SimpleBlockConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> config) {
		SimpleBlockConfiguration simpleblockconfiguration = config.config();
		WorldGenLevel worldgenlevel = config.level();
		BlockPos blockpos = config.origin();
		BlockState blockstate = simpleblockconfiguration.toPlace().getState(config.random(), blockpos)
				.setValue(FancyCactus.CACTUS_TYPE, config.random().nextInt(3))
				.setValue(FancyCactus.AXIS, Direction.values()[config.random().nextInt(2, 6)]);
		if (blockstate.canSurvive(worldgenlevel, blockpos)) {
			if (blockstate.getBlock() instanceof DoublePlantBlock) {
				if (!worldgenlevel.isEmptyBlock(blockpos.above())) {
					return false;
				}

				DoublePlantBlock.placeAt(worldgenlevel, blockstate, blockpos, 2);
			} else {
				worldgenlevel.setBlock(blockpos, blockstate, 2);
			}

			return true;
		} else {
			return false;
		}
	}

}
