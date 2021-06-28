package rmc.mixins.botania_guard.inject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import rmc.libs.event_factory.EventFactory;
import vazkii.botania.common.entity.EntityThornChakram;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = EntityThornChakram.class)
public abstract class EntityThornChakramMixin {

    @Inject(method = "Lvazkii/botania/common/entity/EntityThornChakram;onHit(Lnet/minecraft/util/math/RayTraceResult;)V",
            cancellable = true,
            at = @At(value = "HEAD"))
    private void guardChakramAttack(RayTraceResult pos, CallbackInfo mixin) {
        if (pos instanceof EntityRayTraceResult
         && !EventFactory.testEntityInteract(EventFactory.convert(((EntityThornChakram)(Object) this).getOwner()), ((EntityThornChakram)(Object) this).getOwner().level, ((EntityRayTraceResult) pos).getEntity())) {
            ((EntityThornChakram)(Object) this).remove();
            mixin.cancel();
        }
    }

}