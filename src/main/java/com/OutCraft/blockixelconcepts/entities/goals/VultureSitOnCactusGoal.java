package com.OutCraft.blockixelconcepts.entities.goals;

import java.util.EnumSet;

import com.OutCraft.blockixelconcepts.blocks.FancyCactus;
import com.OutCraft.blockixelconcepts.entities.Vulture;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

public class VultureSitOnCactusGoal extends MoveToBlockGoal {
	private Vulture vulture;

	public VultureSitOnCactusGoal(Vulture vulture, double speedModifier, int searchRange, int verticalSearchRange) {
		super(vulture, speedModifier, searchRange, verticalSearchRange);
		this.vulture = vulture;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.TARGET));
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
		return 0.1D;
	}

	@Override
	public void tick() {
		System.out.println(this.blockPos);
		super.tick();
	}

	@Override
	protected boolean isValidTarget(LevelReader reader, BlockPos pos) {
		return (reader.getBlockState(pos).getBlock() instanceof FancyCactus
				&& reader.getBlockState(pos).getValue(FancyCactus.CACTUS_TYPE) == 3);
	}

}
