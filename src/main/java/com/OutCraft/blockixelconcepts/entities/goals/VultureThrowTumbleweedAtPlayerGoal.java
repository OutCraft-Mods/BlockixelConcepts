package com.OutCraft.blockixelconcepts.entities.goals;

import java.util.EnumSet;

import com.OutCraft.blockixelconcepts.entities.Tumbleweed;
import com.OutCraft.blockixelconcepts.entities.Vulture;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class VultureThrowTumbleweedAtPlayerGoal extends Goal {
	private Vulture vulture;
	private int cooldown;

	private Tumbleweed tumblewheed;
	private Player targetedPlayer;

	private double searchDistance = 100;

	public VultureThrowTumbleweedAtPlayerGoal(Vulture vulture) {
		this.vulture = vulture;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.TARGET, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		if (this.cooldown > 0)
			--this.cooldown;

		return (this.cooldown <= 0
				&& !this.vulture.getCommandSenderWorld()
						.getEntitiesOfClass(Tumbleweed.class,
								new AABB(this.vulture.blockPosition()).inflate(this.searchDistance))
						.isEmpty()
				&& this.vulture.getCommandSenderWorld().getNearestPlayer(this.vulture.getX(), this.vulture.getY(),
						this.vulture.getZ(), 300, true) != null
				&& this.vulture.getRandom().nextInt(reducedTickDelay(40)) != 0);
	}

	@Override
	public void tick() {
		if (this.tumblewheed == null || !this.tumblewheed.isAlive())
			this.tumblewheed = this.vulture.getCommandSenderWorld().getEntitiesOfClass(Tumbleweed.class,
					new AABB(this.vulture.blockPosition()).inflate(this.searchDistance)).get(0);

		if (this.vulture.distanceTo(this.tumblewheed) >= 1.5) {
			this.vulture.getNavigation().moveTo(this.tumblewheed, 1.3);
		} else {
			this.tumblewheed.setNoGravity(true);
			this.tumblewheed.setPos(this.vulture.getX(), this.vulture.getY() - 0.3, this.vulture.getZ());
			if (this.targetedPlayer == null || !this.targetedPlayer.isAlive()
					|| this.vulture.distanceTo(this.targetedPlayer) >= 400)
				this.targetedPlayer = this.vulture.getCommandSenderWorld().getNearestPlayer(this.vulture.getX(),
						this.vulture.getY(), this.vulture.getZ(), 300, true);
			this.vulture.getNavigation().moveTo(this.targetedPlayer.getX(), this.targetedPlayer.getY() + 4,
					this.targetedPlayer.getZ(), 1.2);
			if (this.vulture.distanceToSqr(this.targetedPlayer.getX(), this.targetedPlayer.getY() + 4,
					this.targetedPlayer.getZ()) <= .2) {
				this.cooldown = 1500;
				this.tumblewheed.setNoGravity(false);
				this.tumblewheed = null;
				this.targetedPlayer = null;
			}
		}

		super.tick();
	}

}
