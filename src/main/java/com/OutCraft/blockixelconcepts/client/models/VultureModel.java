package com.OutCraft.blockixelconcepts.client.models;

import com.OutCraft.blockixelconcepts.BlockixelConcepts;
import com.OutCraft.blockixelconcepts.entities.Vulture;
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

public class VultureModel<T extends Vulture> extends EntityModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation(BlockixelConcepts.modid, "vulture"), "vulture");
	public final ModelPart head;
	public final ModelPart neck;
	public final ModelPart body;
	public final ModelPart leg0;
	public final ModelPart leg1;
	public final ModelPart wing0;
	public final ModelPart wing1;

	public VultureModel(ModelPart root) {
		this.head = root.getChild("head");
		this.neck = root.getChild("neck");
		this.body = root.getChild("body");
		this.leg0 = root.getChild("leg0");
		this.leg1 = root.getChild("leg1");
		this.wing0 = root.getChild("wing0");
		this.wing1 = root.getChild("wing1");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(21, 30)
						.addBox(-1.0F, -4.0F, 4.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(16, 0)
						.addBox(0.0F, -2.0F, 1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(8, 0)
						.addBox(0.0F, 0.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 5)
						.addBox(0.0F, -6.0F, 5.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 3)
						.addBox(1.0F, -6.0F, 5.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 1)
						.addBox(2.0F, -6.0F, 5.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-1.0F, 13.0F, -17.0F));

		PartDefinition neck = partdefinition.addOrReplaceChild("neck",
				CubeListBuilder.create().texOffs(32, 0)
						.addBox(-2.0F, -3.0F, 4.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(16, 5)
						.addBox(-1.0F, 0.0F, 1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 13.0F, -8.0F));

		PartDefinition neck2_r1 = neck
				.addOrReplaceChild("neck2_r1",
						CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6545F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 30).addBox(
				-2.0F, -7.0F, 1.0F, 6.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 20.0F, -1.0F));

		PartDefinition leg0 = partdefinition.addOrReplaceChild("leg0",
				CubeListBuilder.create().texOffs(8, 22)
						.addBox(-2.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(10, 8)
						.addBox(-1.5F, 2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(2, 0)
						.addBox(-1.0F, 2.0F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(22, 11)
						.addBox(-2.0F, 4.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(10, 6)
						.addBox(-2.0F, 4.0F, -2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 5)
						.addBox(-1.0F, 4.0F, -2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(8, 2)
						.addBox(0.0F, 4.0F, -2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-0.25F, 19.0F, 2.0F));

		PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1",
				CubeListBuilder.create().texOffs(0, 22)
						.addBox(-2.0F, -2.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(10, 2)
						.addBox(-1.5F, 2.0F, -1.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-1.0F, 2.0F, -1.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(16, 10)
						.addBox(-2.0F, 4.0F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(8, 1)
						.addBox(-2.0F, 4.0F, -3.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(2, 2)
						.addBox(-1.0F, 4.0F, -3.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 2)
						.addBox(0.0F, 4.0F, -3.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(2.25F, 19.0F, 3.0F));

		PartDefinition wing0 = partdefinition.addOrReplaceChild("wing0", CubeListBuilder.create().texOffs(16, 8)
				.addBox(-0.5F, -1.0F, -3.0F, 1.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-3.5F, 12.0F, -1.0F));

		PartDefinition wing1 = partdefinition.addOrReplaceChild("wing1", CubeListBuilder.create().texOffs(0, 0)
				.addBox(-0.5F, -1.0F, -3.0F, 1.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)),
				PartPose.offset(3.5F, 12.0F, -1.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		if (entity.isFlying()) {
			this.wing0.xRot = (float) (Math.cos(limbSwing) * 0.1 * limbSwingAmount);
			this.wing1.xRot = (float) (Math.cos(limbSwing) * 0.1 * limbSwingAmount);
			this.wing0.zRot = (float) (Math.cos(limbSwing * 0.5) * limbSwingAmount) + 45;
			this.wing1.zRot = -((float) (Math.cos(limbSwing * 0.5) * limbSwingAmount) + 45);
		} else {
			this.wing0.xRot = (float) (Math.cos(ageInTicks * 0.05) * 0.05);
			this.wing1.xRot = (float) (Math.cos(ageInTicks * 0.05) * 0.05);
			this.wing0.zRot = 0;
			this.wing1.zRot = 0;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.neck.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.leg0.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.wing0.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.wing1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}