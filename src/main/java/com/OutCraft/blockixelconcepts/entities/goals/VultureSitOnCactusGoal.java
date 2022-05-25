package com.OutCraft.blockixelconcepts.entities.goals;

import com.OutCraft.blockixelconcepts.blocks.FancyCactus;
import com.OutCraft.blockixelconcepts.entities.Vulture;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

public class VultureSitOnCactusGoal extends MoveToBlockGoal {
	private Vulture vulture;

	public VultureSitOnCactusGoal(Vulture vulture, double speedModifier, int searchRange, int verticalSearchRange) {
		super(vulture, speedModifier, searchRange, verticalSearchRange);
		this.vulture = vulture;
	}

	@Override
	public boolean canUse() {
		if (this.vulture.getCommandSenderWorld().getBlockState(this.vulture.blockPosition())
				.getBlock() instanceof FancyCactus
				|| this.vulture.getCommandSenderWorld().getBlockState(this.vulture.blockPosition().below())
						.getBlock() instanceof FancyCactus)
			return false;
		else
			return super.canUse();
	}

	@Override
	public double acceptedDistance() {
		return 0.8D;
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	protected boolean isValidTarget(LevelReader reader, BlockPos pos) {
		return (reader.getBlockState(pos.below()).getBlock() instanceof FancyCactus
				&& reader.getBlockState(pos.below()).getValue(FancyCactus.CACTUS_TYPE) == 2);
	}

}
