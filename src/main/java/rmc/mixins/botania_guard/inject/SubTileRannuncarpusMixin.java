package rmc.mixins.botania_guard.inject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import rmc.mixins.botania_guard.BotaniaGuard;
import vazkii.botania.common.block.subtile.functional.SubTileRannuncarpus;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = SubTileRannuncarpus.class)
public abstract class SubTileRannuncarpusMixin {

    @Inject(method = "Lvazkii/botania/common/block/subtile/functional/SubTileRannuncarpus;tickFlower()V",
            remap = false,
            cancellable = true,
            at = @At(value = "HEAD"))
    private void guardFlowerAction(CallbackInfo mixin) {
        if (!BotaniaGuard.validateTilePositioning((SubTileRannuncarpus)(Object) this, ((SubTileRannuncarpus)(Object) this).getEffectivePos(), BotaniaGuard.FUNCTIONAL_FAKE, ((SubTileRannuncarpus)(Object) this).getPlaceRange(), ((SubTileRannuncarpus)(Object) this).getVerticalPlaceRange())) {
            mixin.cancel();
        }
    }

}