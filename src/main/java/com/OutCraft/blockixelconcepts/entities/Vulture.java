package com.OutCraft.blockixelconcepts.entities;

import javax.annotation.Nullable;

import com.OutCraft.blockixelconcepts.entities.goals.VulturePickupItemGoal;
import com.OutCraft.blockixelconcepts.entities.goals.VultureSitOnCactusGoal;
import com.OutCraft.blockixelconcepts.entities.goals.VultureThrowTumbleweedAtPlayerGoal;
import com.OutCraft.blockixelconcepts.entities.goals.VultureTravelGoal;
import com.OutCraft.blockixelconcepts.lists.ItemList;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class Vulture extends Animal implements FlyingAnimal {
	public Player travelPlayer;

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
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new VultureTravelGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new VulturePickupItemGoal(this, 1));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.2, Ingredient.of(ItemList.TREASURE_GLOVE.get()), false));
		this.goalSelector.addGoal(4, new VultureThrowTumbleweedAtPlayerGoal(this));
		this.goalSelector.addGoal(5, new VultureSitOnCactusGoal(this, 1.1, 50, 20));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomFlyingGoal(this, 1));
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
		return this.getMainHandItem().isEmpty() && stack.getItem() != ItemList.TREASURE_GLOVE.get();
	}

	@Override
	protected void pickUpItem(ItemEntity pItemEntity) {
		ItemStack itemstack = pItemEntity.getItem();
		this.spitOutItem(this.getItemBySlot(EquipmentSlot.MAINHAND));
		this.onItemPickup(pItemEntity);
		this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
		this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0F;
		this.take(pItemEntity, itemstack.getCount());
		pItemEntity.discard();

	}

	public void spitOutItem(ItemStack pItemEntity) {
		if (!pItemEntity.isEmpty() && !this.level.isClientSide) {
			ItemEntity itemEntity = new ItemEntity(this.level, this.getX() + this.getLookAngle().x, this.getY() + 1.0D,
					this.getZ() + this.getLookAngle().z, pItemEntity.split(pItemEntity.getCount()));
			itemEntity.setPickUpDelay(40);
			itemEntity.setThrower(this.getUUID());
			this.playSound(SoundEvents.FOX_SPIT, 1.0F, 1.0F);
			this.level.addFreshEntity(itemEntity);
		}
	}

	@Override
	public boolean hurt(DamageSource pSource, float pAmount) {
		if (pSource == DamageSource.CACTUS) {
			return false;
		} else if (pSource instanceof IndirectEntityDamageSource
				&& ((IndirectEntityDamageSource) pSource).getEntity() instanceof Snowball) {
			this.spitOutItem(this.getMainHandItem());
			return super.hurt(pSource, pAmount);
		} else {
			return super.hurt(pSource, pAmount);
		}
	}

	@Override
	public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
		if (pPlayer.getItemInHand(pHand).getItem() != ItemList.TREASURE_GLOVE.get()
				|| !pPlayer.getItemInHand(pHand).getOrCreateTag().contains("treasureType")
				|| (pPlayer.getItemInHand(pHand).getDamageValue() == pPlayer.getItemInHand(pHand).getMaxDamage()))
			return InteractionResult.PASS;

		this.travelPlayer = pPlayer;
		this.spitOutItem(this.getMainHandItem());
		this.setItemInHand(InteractionHand.MAIN_HAND, pPlayer.getItemInHand(pHand).copy());
		this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0F;
		this.usePlayerItem(pPlayer, pHand, pPlayer.getItemInHand(pHand));

		return InteractionResult.SUCCESS;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance pDifficulty) {
		if (random.nextFloat() < 0.2F) {
			float f = random.nextFloat();
			ItemStack itemstack = ItemStack.EMPTY;
			if (f < 0.45) {
				itemstack = new ItemStack(Items.DEAD_BUSH);
			} else if (f < 0.9) {
				itemstack = new ItemStack(Items.CACTUS);
			} else if (f < 1) {
				itemstack = random.nextBoolean() ? new ItemStack(Items.GOLD_ORE) : new ItemStack(Items.GOLD_INGOT);
			}
			this.setItemSlot(EquipmentSlot.MAINHAND, itemstack);
		}

	}

	public void setMoveControl(MoveControl moveControl) {
		this.moveControl = moveControl;
	}

	@Override
	protected boolean canRide(Entity pEntity) {
		return true;
	}

	@Override
	public boolean shouldRiderSit() {
		return false;
	}

	@Override
	public double getPassengersRidingOffset() {
		return -1.5;
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
