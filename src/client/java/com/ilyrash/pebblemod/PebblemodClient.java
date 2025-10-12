package com.ilyrash.pebblemod;

import com.ilyrash.pebblemod.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class PebblemodClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.THROWN_PEBBLE, FlyingItemEntityRenderer::new);
	}
}