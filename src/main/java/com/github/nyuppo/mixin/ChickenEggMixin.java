package com.github.nyuppo.mixin;

import com.github.nyuppo.config.Variants;
import com.github.nyuppo.variant.MobVariant;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EggEntity.class)
public class ChickenEggMixin {
    @ModifyVariable(
            method = "onCollision(Lnet/minecraft/util/hit/HitResult;)V",
            at = @At("STORE")
    )
    private ChickenEntity mixin(ChickenEntity chickenEntity) {
        MobVariant variant = Variants.getRandomVariant(Variants.Mob.CHICKEN, chickenEntity.getWorld().getRandom(), chickenEntity.getWorld().getBiome(chickenEntity.getBlockPos()), null);

        NbtCompound newNbt = new NbtCompound();
        chickenEntity.writeNbt(newNbt);
        newNbt.putString("Variant", variant.getIdentifier().toString());
        chickenEntity.readCustomDataFromNbt(newNbt);

        return chickenEntity;
    }
}
