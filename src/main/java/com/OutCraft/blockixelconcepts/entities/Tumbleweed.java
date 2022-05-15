package com.OutCraft.blockixelconcepts.entities;

import java.util.Collections;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

public class Tumbleweed extends LivingEntity {

	public Tumbleweed(EntityType<? extends Tumbleweed> entity, Level world) {
		super(entity, world);
	}

	public static AttributeSupplier createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.01).add(Attributes.MAX_HEALTH, 0.5D)
				.add(ForgeMod.ENTITY_GRAVITY.get(), 0.01).build();
	}

	@Override
	public void onAddedToWorld() {
		this.setYRot(this.random.nextFloat(256));
		super.onAddedToWorld();
	}

	@Override
	public void aiStep() {
		this.moveRelative((float) -this.getAttribute(Attributes.MOVEMENT_SPEED).getValue(), new Vec3(1, 0, 1));
		if (this.isOnGround())
			this.setDeltaMovement(this.getDeltaMovement().x, 0.2, this.getDeltaMovement().z);

		if (this.isInWaterOrBubble() || this.isInLava() || this.isInPowderSnow)
			this.kill();
		super.aiStep();
	}

	@Override
	public boolean hurt(DamageSource damageSource, float damage) {
		if (damageSource == DamageSource.OUT_OF_WORLD || damageSource == DamageSource.CRAMMING
				|| damageSource == DamageSource.GENERIC || damageSource == DamageSource.ANVIL
				|| damageSource == DamageSource.FALLING_BLOCK || damageSource == DamageSource.FALLING_STALACTITE
				|| damageSource == DamageSource.IN_FIRE || damageSource == DamageSource.IN_WALL
				|| damageSource == DamageSource.LIGHTNING_BOLT || damageSource instanceof EntityDamageSource)
			this.kill();
		return false;
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	public boolean canFreeze() {
		return false;
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean canSpawnSoulSpeedParticle() {
		return false;
	}

	@Override
	protected void createWitherRose(LivingEntity p_21269_) {
	}

	@Override
	protected void doPush(Entity p_20971_) {
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public void kill() {
		this.remove(Entity.RemovalReason.KILLED);
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.GRASS_BREAK;
	}

	@Override
	public boolean isCustomNameVisible() {
		return false;
	}

	@Override
	public Iterable<ItemStack> getArmorSlots() {
		return Collections.emptyList();
	}

	@Override
	public ItemStack getItemBySlot(EquipmentSlot p_21127_) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemSlot(EquipmentSlot p_21036_, ItemStack p_21037_) {
	}

	@Override
	public HumanoidArm getMainArm() {
		return HumanoidArm.RIGHT;
	}

}
