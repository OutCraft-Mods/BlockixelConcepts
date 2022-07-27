package com.OutCraft.blockixelconcepts;

import com.OutCraft.blockixelconcepts.lists.BlockList;
import com.OutCraft.blockixelconcepts.lists.EntityList;
import com.OutCraft.blockixelconcepts.lists.ItemList;
import com.OutCraft.blockixelconcepts.world.gen.VegetationGen;
import com.OutCraft.blockixelconcepts.world.gen.features.FeatureList;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BlockixelConcepts.modid)
public class BlockixelConcepts {

	public static final String modid = "blockixelconcepts";

	final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

	public BlockixelConcepts() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

		this.modEventBus.addListener(EntityList::addEntityAttributes);

		BlockList.Blocks.register(this.modEventBus);
		ItemList.Items.register(this.modEventBus);
		EntityList.EntityTypes.register(this.modEventBus);
		FeatureList.Features.register(this.modEventBus);
	}

	public void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			VegetationGen.registerVegetation();
		});
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(BlockList.FANCY_CACTUS.get(), RenderType.translucent());
	}
}
