package com.github.nyuppo.mixin;

import com.github.nyuppo.MoreMobVariants;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieBaseEntityRenderer.class)
public class ZombieRendererMixin {
    private static final Identifier DEFAULT = new Identifier("textures/entity/zombie/zombie.png");

    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    private void onGetTexture(ZombieEntity zombieEntity, CallbackInfoReturnable<Identifier> ci) {
        NbtCompound nbt = new NbtCompound();
        zombieEntity.writeNbt(nbt);

        if (nbt.contains("Variant")) {
            String variant = nbt.getString("Variant");
            if (variant.equals("default")) {
                ci.setReturnValue(DEFAULT);
            } else {
                ci.setReturnValue(new Identifier(MoreMobVariants.MOD_ID, "textures/entity/zombie/" + variant + ".png"));
            }
        }
    }
}
