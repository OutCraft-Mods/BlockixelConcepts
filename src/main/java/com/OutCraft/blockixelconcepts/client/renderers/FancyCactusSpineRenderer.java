package com.OutCraft.blockixelconcepts.client.renderers;

import com.OutCraft.blockixelconcepts.BlockixelConcepts;
import com.OutCraft.blockixelconcepts.client.models.FancyCactusSpineModel;
import com.OutCraft.blockixelconcepts.entities.FancyCactusSpine;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FancyCactusSpineRenderer extends EntityRenderer<FancyCactusSpine> {
	private final FancyCactusSpineModel<FancyCactusSpine> model;

	public FancyCactusSpineRenderer(Context context) {
		super(context);
		this.model = new FancyCactusSpineModel<>(context.bakeLayer(FancyCactusSpineModel.LAYER_LOCATION));
	}

	@Override
	public void render(FancyCactusSpine spine, float p_115374_, float p_115375_, PoseStack poseStack,
			MultiBufferSource multiBufferSource, int p_115378_) {
		poseStack.pushPose();
		poseStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(p_115375_, spine.yRotO, spine.getYRot()) - 90.0F));
		poseStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(p_115375_, spine.xRotO, spine.getXRot())));
		VertexConsumer vertexconsumer = multiBufferSource
				.getBuffer(this.model.renderType(this.getTextureLocation(spine)));
		this.model.renderToBuffer(poseStack, vertexconsumer, p_115378_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F,
				1.0F);
		poseStack.popPose();
		super.render(spine, p_115374_, p_115375_, poseStack, multiBufferSource, p_115378_);
	}

	@Override
	public ResourceLocation getTextureLocation(FancyCactusSpine spine) {
		return new ResourceLocation(BlockixelConcepts.modid, "textures/entity/fancy_cactus_spine.png");
	}
}
