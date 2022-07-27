package com.OutCraft.blockixelconcepts.client.renderers;

import com.OutCraft.blockixelconcepts.BlockixelConcepts;
import com.OutCraft.blockixelconcepts.client.layers.VultureHeldItemLayer;
import com.OutCraft.blockixelconcepts.client.models.VultureModel;
import com.OutCraft.blockixelconcepts.entities.Vulture;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VultureRenderer extends MobRenderer<Vulture, VultureModel<Vulture>> {

	public VultureRenderer(EntityRendererProvider.Context context) {
		super(context, new VultureModel<Vulture>(context.bakeLayer(VultureModel.LAYER_LOCATION)), 0.4F);
		this.addLayer(new VultureHeldItemLayer(this, context.getItemInHandRenderer()));
	}

	@Override
	public ResourceLocation getTextureLocation(Vulture entity) {
		return new ResourceLocation(BlockixelConcepts.modid, "textures/entity/vulture.png");
	}
}
