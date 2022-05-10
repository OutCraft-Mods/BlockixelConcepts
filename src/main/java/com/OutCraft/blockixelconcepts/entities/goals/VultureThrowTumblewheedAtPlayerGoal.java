package com.OutCraft.blockixelconcepts.entities.goals;

import java.util.EnumSet;

import com.OutCraft.blockixelconcepts.entities.Tumblewheed;
import com.OutCraft.blockixelconcepts.entities.Vulture;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;

public class VultureThrowTumblewheedAtPlayerGoal extends Goal {
	private Vulture vulture;
	private int cooldown;
	private Tumblewheed tumblewheed;

	private double searchDistance = 10;

	public VultureThrowTumblewheedAtPlayerGoal(Vulture vulture) {
		this.vulture = vulture;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.TARGET, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		return true;
		// TODO return
	}

	@Override
	public void tick() {
		if (this.cooldown > 0) {
			--this.cooldown;
		} else {
			this.tumblewheed = this.vulture.getCommandSenderWorld().getEntitiesOfClass(Tumblewheed.class,
					new AABB(this.vulture.blockPosition()).inflate(this.searchDistance)).get(0);
		}
		super.tick();
	}

}
