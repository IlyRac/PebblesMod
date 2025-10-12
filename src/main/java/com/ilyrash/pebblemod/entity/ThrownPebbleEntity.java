package com.ilyrash.pebblemod.entity;

import com.ilyrash.pebblemod.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ThrownPebbleEntity extends ThrownItemEntity {

    public ThrownPebbleEntity(EntityType<? extends ThrownPebbleEntity> entityType, World world) {
        super(entityType, world);
    }

    public ThrownPebbleEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.THROWN_PEBBLE, owner, world, stack);
    }

    public ThrownPebbleEntity(World world, double x, double y, double z, ItemStack stack) {
        super(ModEntities.THROWN_PEBBLE, x, y, z, world, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.STONE_PEBBLE;
    }

    @Override
    protected void onEntityHit(EntityHitResult hit) {
        super.onEntityHit(hit);
        Entity entity = hit.getEntity();
        float damage = 2;
        entity.serverDamage(this.getDamageSources().thrown(this, this.getOwner()), damage);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getEntityWorld().isClient()) {
            this.getEntityWorld().sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }

    @Override
    public void handleStatus(byte status) {
    }
}