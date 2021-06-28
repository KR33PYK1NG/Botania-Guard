package rmc.mixins.botania_guard.inject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import rmc.libs.event_factory.EventFactory;
import vazkii.botania.common.entity.EntityVineBall;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = EntityVineBall.class)
public abstract class EntityVineBallMixin {

    @Inject(method = "Lvazkii/botania/common/entity/EntityVineBall;onHit(Lnet/minecraft/util/math/RayTraceResult;)V",
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"))
    private void guardVineSpread(RayTraceResult rtr, CallbackInfo mixin, Direction dir, BlockPos pos) {
        if (!EventFactory.testBlockBreak(EventFactory.convert(((EntityVineBall)(Object) this).getOwner()), ((EntityVineBall)(Object) this).level, pos)) {
            ((EntityVineBall)(Object) this).remove();
            mixin.cancel();
        }
    }

}