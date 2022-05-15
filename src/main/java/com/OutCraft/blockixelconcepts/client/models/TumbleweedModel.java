package com.OutCraft.blockixelconcepts.client.models;

import com.OutCraft.blockixelconcepts.BlockixelConcepts;
import com.OutCraft.blockixelconcepts.entities.Tumbleweed;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class TumbleweedModel<T extends Tumbleweed> extends EntityModel<T> {
	private static final double speed = 0.02;
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation(BlockixelConcepts.modid, "tumbleweed"), "tumbleweed");
	private final ModelPart main;

	public TumbleweedModel(ModelPart root) {
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, -16)
						.addBox(-6.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(-16, 0)
						.addBox(-8.0F, -8.0F, 6.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(-16, -16)
						.addBox(6.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(-16, 0)
						.addBox(-8.0F, -6.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(-16, 0)
						.addBox(-8.0F, 6.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 18.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(Tumbleweed tumblewheed, float p_102619_, float p_102620_, float p_102621_, float p_102622_,
			float p_102623_) {
		this.main.xRot += -speed;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		this.main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}