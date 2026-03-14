package com.ilyrac.pebblesandtwigs;

import com.ilyrac.pebblesandtwigs.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class PebblesAndTwigsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        //noinspection deprecation
        EntityRendererRegistry.register(ModEntities.THROWN_PEBBLE, ThrownItemRenderer::new);
	}
}