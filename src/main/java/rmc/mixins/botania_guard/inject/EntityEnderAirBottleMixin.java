package rmc.mixins.botania_guard.inject;

import java.util.Iterator;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.util.math.BlockPos;
import rmc.libs.event_factory.EventFactory;
import vazkii.botania.common.entity.EntityEnderAirBottle;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = EntityEnderAirBottle.class)
public abstract class EntityEnderAirBottleMixin {

    @Inject(method = "Lvazkii/botania/common/entity/EntityEnderAirBottle;convertStone(Lnet/minecraft/util/math/BlockPos;)V",
            remap = false,
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE_ASSIGN",
                     target = "Lvazkii/botania/common/entity/EntityEnderAirBottle;getCoordsToPut(Lnet/minecraft/util/math/BlockPos;)Ljava/util/List;"))
    private void guardStoneConversion(BlockPos pos, CallbackInfo mixin, List<BlockPos> coordsList) {
        Iterator<BlockPos> it = coordsList.iterator();
        while (it.hasNext()) {
            if (!EventFactory.testBlockBreak(EventFactory.convert(((EntityEnderAirBottle)(Object) this).getOwner()), ((EntityEnderAirBottle)(Object) this).level, it.next())) {
                coordsList.clear();
                break;
            }
        }
    }

}