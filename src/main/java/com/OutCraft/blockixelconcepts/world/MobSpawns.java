package com.OutCraft.blockixelconcepts.world;

import com.OutCraft.blockixelconcepts.lists.EntityList;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MobSpawns {

	@SubscribeEvent
	public static void onBiomesLoad(BiomeLoadingEvent event) {
		if (event.getCategory().equals(BiomeCategory.MESA)) {
			event.getSpawns().getSpawner(MobCategory.CREATURE).add(new SpawnerData(EntityList.VULTURE.get(), 15, 1, 3));
			event.getSpawns().getSpawner(MobCategory.MISC).add(new SpawnerData(EntityList.TUMBLEWEED.get(), 200, 1, 1));
		}
	}
}
