package com.OutCraft.blockixelconcepts.client.renderers;

import com.OutCraft.blockixelconcepts.BlockixelConcepts;
import com.OutCraft.blockixelconcepts.client.models.TumbleweedModel;
import com.OutCraft.blockixelconcepts.entities.Tumbleweed;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class TumbleweedRenderer extends LivingEntityRenderer<Tumbleweed, TumbleweedModel<Tumbleweed>> {

	public TumbleweedRenderer(Context context) {
		super(context, new TumbleweedModel<Tumbleweed>(context.bakeLayer(TumbleweedModel.LAYER_LOCATION)), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(Tumbleweed tumblewheed) {
		return new ResourceLocation(BlockixelConcepts.modid, "textures/entity/tumbleweed.png");
	}
}
