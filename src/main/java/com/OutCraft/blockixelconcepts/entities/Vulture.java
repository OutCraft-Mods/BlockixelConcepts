package com.OutCraft.blockixelconcepts.entities;

import javax.annotation.Nullable;

import com.OutCraft.blockixelconcepts.entities.goals.VulturePickupItemGoal;
import com.OutCraft.blockixelconcepts.entities.goals.VultureSitOnCactusGoal;
import com.OutCraft.blockixelconcepts.entities.goals.VultureThrowTumblewheedAtPlayerGoal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class Vulture extends Animal implements FlyingAnimal {

	public Vulture(EntityType<? extends Vulture> entity, Level world) {
		super(entity, world);
		this.setCanPickUpLoot(true);
		this.moveControl = new FlyingMoveControl(this, 10, false);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
	}

	@Override
	public boolean isFlying() {
		return !this.isOnGround();
	}

	public static AttributeSupplier createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.2F).add(Attributes.MAX_HEALTH, 6.0D)
				.add(Attributes.FLYING_SPEED, 0.6F).build();
	}

	@Override
	public void tick() {
		if (!this.goalSelector.getRunningGoals().toList().isEmpty())
			System.out.println(this.goalSelector.getRunningGoals().toList().get(0).getGoal());
		super.tick();
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new VulturePickupItemGoal(this, 1));
		this.goalSelector.addGoal(2, new VultureThrowTumblewheedAtPlayerGoal(this));
		this.goalSelector.addGoal(3, new VultureSitOnCactusGoal(this, 1, 20, 20));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, level);
		flyingpathnavigation.setCanOpenDoors(false);
		flyingpathnavigation.setCanFloat(true);
		flyingpathnavigation.setCanPassDoors(false);
		return flyingpathnavigation;
	}

	@Override
	public boolean wantsToPickUp(ItemStack stack) {
		return this.getMainHandItem().isEmpty();
	}

	@Override
	protected void pickUpItem(ItemEntity itemEntity) {
		if (!this.getMainHandItem().isEmpty())
			return;
		ItemStack itemstack = itemEntity.getItem();
		this.spitOutItem(this.getMainHandItem());
		this.onItemPickup(itemEntity);
		this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
		this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 1.0F;

	}

	private void spitOutItem(ItemStack p_28602_) {
		if (!p_28602_.isEmpty() && !this.level.isClientSide) {
			ItemEntity itementity = new ItemEntity(this.level, this.getX() + this.getLookAngle().x, this.getY() + 1.0D,
					this.getZ() + this.getLookAngle().z, p_28602_);
			itementity.setPickUpDelay(40);
			itementity.setThrower(this.getUUID());
			this.playSound(SoundEvents.FOX_SPIT, 1.0F, 1.0F);
			this.level.addFreshEntity(itementity);
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float damage) {
		if (damageSource == DamageSource.CACTUS)
			return false;
		else
			return super.hurt(damageSource, damage);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.PARROT_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.PARROT_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.PARROT_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.PARROT_STEP, 0.15F, 1.0F);
	}

	@Override
	public boolean isBaby() {
		return false;
	}

	@Override
	public boolean causeFallDamage(float p_148989_, float p_148990_, DamageSource p_148991_) {
		return false;
	}

	@Override
	protected void checkFallDamage(double p_29370_, boolean p_29371_, BlockState p_29372_, BlockPos p_29373_) {
	}

	@Override
	public boolean canMate(Animal p_29381_) {
		return false;
	}

	@Override
	@Nullable
	public AgeableMob getBreedOffspring(ServerLevel p_148993_, AgeableMob p_148994_) {
		return null;
	}
}
