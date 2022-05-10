package com.OutCraft.blockixelconcepts.client.layers;

import com.OutCraft.blockixelconcepts.client.models.VultureModel;
import com.OutCraft.blockixelconcepts.entities.Vulture;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VultureHeldItemLayer extends RenderLayer<Vulture, VultureModel<Vulture>> {
	public VultureHeldItemLayer(RenderLayerParent<Vulture, VultureModel<Vulture>> p_116994_) {
		super(p_116994_);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int p_117009_, Vulture vulture,
			float p_117011_, float p_117012_, float p_117013_, float p_117014_, float p_117015_, float p_117016_) {
		poseStack.pushPose();
		poseStack.scale(.7F, .7F, .7F);
		poseStack.translate(0D, 1.35D, -1.25D);
		poseStack.mulPose(Vector3f.XP.rotationDegrees(90));
		poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
		ItemStack itemstack = vulture.getMainHandItem();
		Minecraft.getInstance().getItemInHandRenderer().renderItem(vulture, itemstack,
				ItemTransforms.TransformType.GROUND, false, poseStack, multiBufferSource, p_117009_);
		poseStack.popPose();
	}
}