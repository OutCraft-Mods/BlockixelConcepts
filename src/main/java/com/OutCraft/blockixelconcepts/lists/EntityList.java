package com.OutCraft.blockixelconcepts.lists;

import com.OutCraft.blockixelconcepts.BlockixelConcepts;
import com.OutCraft.blockixelconcepts.entities.FancyCactusSpine;
import com.OutCraft.blockixelconcepts.entities.Tumblewheed;
import com.OutCraft.blockixelconcepts.entities.Vulture;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityList {
	public static DeferredRegister<EntityType<?>> EntityTypes = DeferredRegister.create(ForgeRegistries.ENTITIES,
			BlockixelConcepts.modid);

	public static final RegistryObject<EntityType<FancyCactusSpine>> FANCY_CACTUS_SPINE = EntityTypes.register(
			"fancy_cactus_spine",
			() -> EntityType.Builder.<FancyCactusSpine>of(FancyCactusSpine::new, MobCategory.MISC).sized(0.1F, 0.2F)
					.clientTrackingRange(6).fireImmune()
					.build(new ResourceLocation(BlockixelConcepts.modid, "fancy_cactus_spine").toString()));
	public static final RegistryObject<EntityType<Vulture>> VULTURE = EntityTypes.register("vulture",
			() -> EntityType.Builder.<Vulture>of(Vulture::new, MobCategory.CREATURE).sized(0.6F, 0.85F)
					.clientTrackingRange(10)
					.build(new ResourceLocation(BlockixelConcepts.modid, "vulture").toString()));
	public static final RegistryObject<EntityType<Tumblewheed>> TUMBLEWHEED = EntityTypes.register("tumblewheed",
			() -> EntityType.Builder.<Tumblewheed>of(Tumblewheed::new, MobCategory.MISC).sized(0.8F, 0.8F)
					.clientTrackingRange(5)
					.build(new ResourceLocation(BlockixelConcepts.modid, "tumblewheed").toString()));

	public static void addEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(EntityList.VULTURE.get(), Vulture.createAttributes());
		event.put(EntityList.TUMBLEWHEED.get(), Tumblewheed.createAttributes());
	}
}
