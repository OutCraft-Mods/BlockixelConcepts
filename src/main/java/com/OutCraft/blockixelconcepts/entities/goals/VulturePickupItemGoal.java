package com.OutCraft.blockixelconcepts.entities.goals;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.OutCraft.blockixelconcepts.entities.Vulture;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

public class VulturePickupItemGoal extends Goal {

	private int tryTicks;
	private Vulture vulture;
	private ItemEntity targetedItemEntity;
	public List<ItemEntity> blacklistedItemEntities = new ArrayList<ItemEntity>();
	private double speedModifier;

	private final double ITEM_SEARCH_DISTANCE = 20;

	public VulturePickupItemGoal(Vulture vulture, double speedModifier) {
		this.vulture = vulture;
		this.speedModifier = speedModifier;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.TARGET, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		for (ItemEntity itemEntity : this.vulture.getCommandSenderWorld().getEntitiesOfClass(ItemEntity.class,
				new AABB(this.vulture.blockPosition()).inflate(this.ITEM_SEARCH_DISTANCE))) {
			if (!this.blacklistedItemEntities.contains(itemEntity)
					&& this.vulture.wantsToPickUp(new ItemStack(itemEntity.getItem().getItem()))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void tick() {
		if (this.targetedItemEntity == null || !this.targetedItemEntity.isAlive()
				|| this.blacklistedItemEntities.contains(this.targetedItemEntity)) {
			for (ItemEntity itemEntity : this.vulture.getCommandSenderWorld().getEntitiesOfClass(ItemEntity.class,
					new AABB(this.vulture.blockPosition()).inflate(this.ITEM_SEARCH_DISTANCE))) {
				if (!this.blacklistedItemEntities.contains(itemEntity)
						&& this.vulture.wantsToPickUp(new ItemStack(itemEntity.getItem().getItem()))) {
					this.targetedItemEntity = itemEntity;
				}
			}
		} else {
			--this.tryTicks;
			this.vulture.getNavigation().moveTo(this.targetedItemEntity.position().x,
					this.targetedItemEntity.position().y, this.targetedItemEntity.position().z, this.speedModifier);
			if (this.tryTicks <= 0) {
				this.vulture.getNavigation().recomputePath();
				this.tryTicks = 50;
			}
			if ((this.vulture.getNavigation().isDone() || this.vulture.getNavigation().isStuck())
					&& this.targetedItemEntity != null && !(this.vulture.distanceTo(this.targetedItemEntity) < 2)) {
				this.blacklistedItemEntities.add(this.targetedItemEntity);
			}
		}

		if (this.targetedItemEntity != null) {
			for (ItemEntity itemEntity : this.vulture.getCommandSenderWorld().getEntitiesOfClass(ItemEntity.class,
					new AABB(this.vulture.blockPosition()).inflate(this.ITEM_SEARCH_DISTANCE))) {
				// Conditions for overriting targetedItemEntity
				if (itemEntity.getItem().getRarity().ordinal() > this.targetedItemEntity.getItem().getRarity().ordinal()
						&& this.vulture.wantsToPickUp(new ItemStack(itemEntity.getItem().getItem()))) {
					this.targetedItemEntity = itemEntity;
				}

			}
		}
		super.tick();
	}
}
