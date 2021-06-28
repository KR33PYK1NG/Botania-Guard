package rmc.mixins.botania_guard.inject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import rmc.mixins.botania_guard.BotaniaGuard;
import vazkii.botania.common.block.subtile.functional.SubTileHyacidus;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = SubTileHyacidus.class)
public abstract class SubTileHyacidusMixin {

    @Inject(method = "Lvazkii/botania/common/block/subtile/functional/SubTileHyacidus;tickFlower()V",
            remap = false,
            cancellable = true,
            at = @At(value = "HEAD"))
    private void guardFlowerAction(CallbackInfo mixin) {
        if (!BotaniaGuard.validateTilePositioning((SubTileHyacidus)(Object) this, ((SubTileHyacidus)(Object) this).getEffectivePos(), BotaniaGuard.FUNCTIONAL_FAKE, 6, 6)) {
            mixin.cancel();
        }
    }

}