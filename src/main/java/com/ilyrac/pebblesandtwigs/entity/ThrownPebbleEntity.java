package com.ilyrac.pebblesandtwigs.entity;

import com.ilyrac.pebblesandtwigs.block.ModBlocks;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.NonNull;

public class ThrownPebbleEntity extends ThrowableItemProjectile {

    public ThrownPebbleEntity(EntityType<? extends ThrownPebbleEntity> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownPebbleEntity(Level level, LivingEntity shooter) {
        super(ModEntities.THROWN_PEBBLE, level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
    }

    public ThrownPebbleEntity(Level level, double x, double y, double z) {
        super(ModEntities.THROWN_PEBBLE, level);
        this.setPos(x, y, z);
    }

    @Override
    protected @NonNull Item getDefaultItem() {
        return ModBlocks.ANDESITE_PEBBLE.asItem();
    }

    @Override
    protected void onHitEntity(@NonNull EntityHitResult result) {
        super.onHitEntity(result);
        Entity target = result.getEntity();
        //noinspection deprecation
        target.hurt(this.damageSources().thrown(this, this.getOwner()), 2.0F);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            // This is the logic that actually spawns the particles on the client
            ItemStack itemStack = this.getItem();
            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemStack),
                        this.getX(), this.getY(), this.getZ(),
                        ((double)this.random.nextFloat() - 0.5) * 0.08,
                        ((double)this.random.nextFloat() - 0.5) * 0.08,
                        ((double)this.random.nextFloat() - 0.5) * 0.08);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected void onHit(@NonNull HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.STONE_BREAK, SoundSource.NEUTRAL, 0.5F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.discard();
        }
    }
}