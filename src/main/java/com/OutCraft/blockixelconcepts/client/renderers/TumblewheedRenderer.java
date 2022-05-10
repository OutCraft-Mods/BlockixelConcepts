package com.OutCraft.blockixelconcepts.client.renderers;

import com.OutCraft.blockixelconcepts.BlockixelConcepts;
import com.OutCraft.blockixelconcepts.client.models.TumblewheedModel;
import com.OutCraft.blockixelconcepts.entities.Tumblewheed;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class TumblewheedRenderer extends LivingEntityRenderer<Tumblewheed, TumblewheedModel<Tumblewheed>> {

	public TumblewheedRenderer(Context context) {
		super(context, new TumblewheedModel<Tumblewheed>(context.bakeLayer(TumblewheedModel.LAYER_LOCATION)), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(Tumblewheed tumblewheed) {
		return new ResourceLocation(BlockixelConcepts.modid, "textures/entity/tumblewheed.png");
	}
}
