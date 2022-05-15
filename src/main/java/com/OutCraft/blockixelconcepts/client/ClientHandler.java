package com.OutCraft.blockixelconcepts.client;

import com.OutCraft.blockixelconcepts.BlockixelConcepts;
import com.OutCraft.blockixelconcepts.client.models.FancyCactusSpineModel;
import com.OutCraft.blockixelconcepts.client.models.TumbleweedModel;
import com.OutCraft.blockixelconcepts.client.models.VultureModel;
import com.OutCraft.blockixelconcepts.client.renderers.FancyCactusSpineRenderer;
import com.OutCraft.blockixelconcepts.client.renderers.TumbleweedRenderer;
import com.OutCraft.blockixelconcepts.client.renderers.VultureRenderer;
import com.OutCraft.blockixelconcepts.lists.EntityList;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = BlockixelConcepts.modid, value = Dist.CLIENT, bus = Bus.MOD)
public class ClientHandler {

	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(EntityList.FANCY_CACTUS_SPINE.get(), FancyCactusSpineRenderer::new);
		event.registerEntityRenderer(EntityList.VULTURE.get(), VultureRenderer::new);
		event.registerEntityRenderer(EntityList.TUMBLEWEED.get(), TumbleweedRenderer::new);
	}

	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(FancyCactusSpineModel.LAYER_LOCATION, FancyCactusSpineModel::createBodyLayer);
		event.registerLayerDefinition(VultureModel.LAYER_LOCATION, VultureModel::createBodyLayer);
		event.registerLayerDefinition(TumbleweedModel.LAYER_LOCATION, TumbleweedModel::createBodyLayer);
	}

	public static void init() {
	}

}
