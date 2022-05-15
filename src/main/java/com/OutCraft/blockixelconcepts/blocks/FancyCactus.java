package com.OutCraft.blockixelconcepts.blocks;

import java.util.List;
import java.util.Random;

import com.OutCraft.blockixelconcepts.entities.FancyCactusSpine;
import com.OutCraft.blockixelconcepts.lists.EntityList;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class FancyCactus extends Block implements IPlantable {
	public static final IntegerProperty CACTUS_TYPE = IntegerProperty.create("cactus_type", 0, 2);
	public static final DirectionProperty AXIS = BlockStateProperties.HORIZONTAL_FACING;

	public static final VoxelShape CACTUS_SMALL_SHAPE = Shapes.or(Block.box(6, 0, 4, 12, 6, 10),
			Block.box(3, 5, 9, 7, 11, 13)); // Just for testing
	public static final VoxelShape CACTUS_MIDDLE_SHAPE = Shapes.or(Block.box(6, 0, 4, 12, 6, 10),
			Block.box(3, 5, 9, 7, 11, 13)); // Just for testing
	public static final VoxelShape CACTUS_BIG_SHAPE = Shapes.or(Shapes.box(-0.25, 0.5, 0.3125, 0.3125, 1.0625, 0.875),
			Shapes.box(0, 1, 0, 0.6875, 1.6875, 0.6875), Shapes.box(0.125, 0, 0, 0.8125, 0.6875, 0.6875),
			Shapes.box(0.75, 0.625, 0.1875, 1.0625, 1.0625, 0.5), Shapes.box(1, 1, 0.125, 1.3125, 1.3125, 0.4375),
			Shapes.box(1.0625, 0.5, 0.25, 1.25, 0.6875, 0.4375));

	public FancyCactus() {
		super(BlockBehaviour.Properties.of(Material.CACTUS).dynamicShape().randomTicks().strength(0.4F)
				.sound(SoundType.WOOL));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext) {
		Random random = new Random();
		return this.defaultBlockState().setValue(CACTUS_TYPE, random.nextInt(3)).setValue(AXIS,
				Direction.values()[random.nextInt(2, 6)]);
	}

	@Override
	public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
		pEntity.hurt(DamageSource.CACTUS, 1.5F);
		super.entityInside(pState, pLevel, pPos, pEntity);
	}

	@Override
	public PlantType getPlantType(BlockGetter level, BlockPos pos) {
		return PlantType.DESERT;
	}

	@Override
	public BlockState getPlant(BlockGetter level, BlockPos pos) {
		return this.defaultBlockState();
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
		pBuilder.add(CACTUS_TYPE, AXIS);
		super.createBlockStateDefinition(pBuilder);
	}

	public static VoxelShape rotateShape(int times, VoxelShape shape) {
		VoxelShape[] buffer = new VoxelShape[] { shape, Shapes.empty() };
		for (int i = 0; i < times; i++) {
			buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1],
					Shapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
			buffer[0] = buffer[1];
			buffer[1] = Shapes.empty();
		}
		return buffer[0];
	}

	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		// What a mess...
		switch (pState.getValue(CACTUS_TYPE)) {
			case 0:
				switch (pState.getValue(AXIS)) {
					case NORTH:
						return CACTUS_SMALL_SHAPE;
					case EAST:
						return rotateShape(1, CACTUS_SMALL_SHAPE);
					case SOUTH:
						return rotateShape(2, CACTUS_SMALL_SHAPE);
					case WEST:
						return rotateShape(3, CACTUS_SMALL_SHAPE);
				}
			case 1:
				switch (pState.getValue(AXIS)) {
					case NORTH:
						return CACTUS_MIDDLE_SHAPE;
					case EAST:
						return rotateShape(1, CACTUS_MIDDLE_SHAPE);
					case SOUTH:
						return rotateShape(2, CACTUS_MIDDLE_SHAPE);
					case WEST:
						return rotateShape(3, CACTUS_MIDDLE_SHAPE);
				}
			case 2:
				switch (pState.getValue(AXIS)) {
					case NORTH:
						return CACTUS_BIG_SHAPE;
					case EAST:
						return rotateShape(1, CACTUS_BIG_SHAPE);
					case SOUTH:
						return rotateShape(2, CACTUS_BIG_SHAPE);
					case WEST:
						return rotateShape(3, CACTUS_BIG_SHAPE);
				}
			default:
				throw new IllegalStateException("cactus_type cannot be" + pState.getValue(CACTUS_TYPE));
		}
	}

	@Override
	public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
		if (!pLevel.isAreaLoaded(pPos, 1))
			return;
		if (!pState.canSurvive(pLevel, pPos)) {
			pLevel.destroyBlock(pPos, true);
		}
		super.tick(pState, pLevel, pPos, pRandom);
	}

	@Override
	public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState,
			LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
		if (!pState.canSurvive(pLevel, pCurrentPos)) {
			pLevel.scheduleTick(pCurrentPos, this, 1);
		}
		return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
	}

	@Override
	public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			BlockState blockstate = pLevel.getBlockState(pPos.relative(direction));
			Material material = blockstate.getMaterial();
			if (material.isSolid() || pLevel.getFluidState(pPos.relative(direction)).is(FluidTags.LAVA)) {
				return false;
			}
		}
		BlockState stateBelow = pLevel.getBlockState(pPos.below());
		return stateBelow.is(Blocks.CACTUS) || stateBelow.is(Blocks.SAND) || stateBelow.is(Blocks.RED_SAND)
				|| stateBelow.is(Blocks.TERRACOTTA) && !pLevel.getBlockState(pPos.above()).getMaterial().isLiquid();
	}

	@Override
	public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
		if (pType == PathComputationType.AIR)
			return true;
		return false;
	}

	public void shootSpines(Level world, BlockPos pos) {
		List<LivingEntity> entitiesInRange = world.getEntitiesOfClass(LivingEntity.class, new AABB(pos.getX() - 2,
				pos.getY() - 2, pos.getZ() - 2, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3));

		entitiesInRange.forEach(entry -> {
			if (entry instanceof Parrot)
				entitiesInRange.remove(entry);
		});

		for (LivingEntity livingEntity : entitiesInRange) {
			FancyCactusSpine spine = new FancyCactusSpine(EntityList.FANCY_CACTUS_SPINE.get(), world);
			spine.setPos(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5);
			world.addFreshEntity(spine);
		}
	}

	@Override
	public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
		this.shootSpines(pLevel, pPos);
		super.stepOn(pLevel, pPos, pState, pEntity);
	}

	@Override
	public void attack(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
		this.shootSpines(pLevel, pPos);
		super.attack(pState, pLevel, pPos, pPlayer);
	}

	@Override
	public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pHit, Projectile pProjectile) {
		if (!(pProjectile instanceof FancyCactusSpine))
			this.shootSpines(pLevel, pHit.getBlockPos());
		super.onProjectileHit(pLevel, pState, pHit, pProjectile);
	}

}
