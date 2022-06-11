package com.OutCraft.blockixelconcepts.entities.goals;

import java.util.EnumSet;
import java.util.UUID;

import com.OutCraft.blockixelconcepts.entities.Vulture;
import com.OutCraft.blockixelconcepts.lists.ItemList;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

public class VultureTravelGoal extends Goal {
	private Vulture vulture;
	private Player travelPlayer;

	private int tryTicks;

	private Direction travelDirection;
	private BlockPos travelPos;

	private UUID modifierUUID;

	public VultureTravelGoal(Vulture vulture) {
		this.setFlags(EnumSet.allOf(Goal.Flag.class));
		this.vulture = vulture;
		this.modifierUUID = UUID.randomUUID();
	}

	@Override
	public boolean canUse() {
		return this.vulture.getMainHandItem().getItem().equals(ItemList.TREASURE_GLOVE.get());
	}

	@Override
	public void start() {

		this.travelPlayer = this.vulture.travelPlayer;

		if (this.travelPlayer == null && !this.vulture.getPassengers().isEmpty())
			this.travelPlayer = (Player) this.vulture.getPassengers().get(0);

		if (this.travelPlayer == null || this.travelPlayer.isDeadOrDying()) {
			this.vulture.discard();
			return;
		}

		this.vulture.getMainHandItem().setDamageValue(this.vulture.getMainHandItem().getDamageValue() + 1);

		this.travelDirection = this.travelPlayer.getDirection();
		int travelDistance = (this.vulture.getMainHandItem().getOrCreateTag().getInt("treasureType") ^ 2) * 100;
		this.travelPos = this.vulture.blockPosition().relative(this.travelDirection, travelDistance).atY(128);
		//int travelSpeed = Math.max(1, this.vulture.getMainHandItem().getOrCreateTag().getInt("treasureType") / 2);

		this.vulture.setYRot(this.travelDirection.toYRot());
		this.travelPlayer.startRiding(this.vulture);

		this.vulture.setMoveControl(new FlyingMoveControl(this.vulture, 20, true));
		this.vulture.getNavigation().moveTo(this.travelPos.getX(), this.travelPos.getY(), this.travelPos.getZ(), 3);

		super.start();
	}

	@Override
	public void tick() {
		if (this.travelPlayer == null && !this.vulture.getPassengers().isEmpty())
			this.travelPlayer = (Player) this.vulture.getPassengers().get(0);

		if (this.travelPlayer == null || this.travelPlayer.isDeadOrDying())
			this.vulture.discard();

		--this.tryTicks;
		if (this.tryTicks <= 0 || this.vulture.getNavigation().isDone()) {
			this.vulture.getNavigation().recomputePath();
			this.tryTicks = 200;
		}

		if (this.vulture.distanceToSqr(this.travelPos.getX(), this.travelPos.getY(), this.travelPos.getZ()) <= 10) {
			this.vulture.spitOutItem(this.vulture.getMainHandItem());
			this.vulture.remove(RemovalReason.KILLED);
		}

		super.tick();
	}

}