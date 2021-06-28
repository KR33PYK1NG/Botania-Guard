package rmc.mixins.botania_guard.inject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import rmc.mixins.botania_guard.BotaniaGuard;
import vazkii.botania.common.block.subtile.functional.SubTileBellethorn;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = SubTileBellethorn.class)
public abstract class SubTileBellethornMixin {

    @Inject(method = "Lvazkii/botania/common/block/subtile/functional/SubTileBellethorn;tickFlower()V",
            remap = false,
            cancellable = true,
            at = @At(value = "HEAD"))
    private void guardFlowerAction(CallbackInfo mixin) {
        if (!BotaniaGuard.validateTilePositioning((SubTileBellethorn)(Object) this, ((SubTileBellethorn)(Object) this).getEffectivePos(), BotaniaGuard.FUNCTIONAL_FAKE, ((SubTileBellethorn)(Object) this).getRange(), ((SubTileBellethorn)(Object) this).getRange())) {
            mixin.cancel();
        }
    }

}