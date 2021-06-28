package rmc.mixins.botania_guard.inject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import rmc.mixins.botania_guard.BotaniaGuard;
import vazkii.botania.common.block.subtile.functional.SubTileClayconia;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = SubTileClayconia.class)
public abstract class SubTileClayconiaMixin {

    @Inject(method = "Lvazkii/botania/common/block/subtile/functional/SubTileClayconia;tickFlower()V",
            remap = false,
            cancellable = true,
            at = @At(value = "HEAD"))
    private void guardFlowerAction(CallbackInfo mixin) {
        if (!BotaniaGuard.validateTilePositioning((SubTileClayconia)(Object) this, ((SubTileClayconia)(Object) this).getEffectivePos(), BotaniaGuard.FUNCTIONAL_FAKE, ((SubTileClayconia)(Object) this).getRange(), ((SubTileClayconia)(Object) this).getRangeY())) {
            mixin.cancel();
        }
    }

}