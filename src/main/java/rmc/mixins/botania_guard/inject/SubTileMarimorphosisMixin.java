package rmc.mixins.botania_guard.inject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import rmc.mixins.botania_guard.BotaniaGuard;
import vazkii.botania.common.block.subtile.functional.SubTileMarimorphosis;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = SubTileMarimorphosis.class)
public abstract class SubTileMarimorphosisMixin {

    @Inject(method = "Lvazkii/botania/common/block/subtile/functional/SubTileMarimorphosis;tickFlower()V",
            remap = false,
            cancellable = true,
            at = @At(value = "HEAD"))
    private void guardFlowerAction(CallbackInfo mixin) {
        if (!BotaniaGuard.validateTilePositioning((SubTileMarimorphosis)(Object) this, ((SubTileMarimorphosis)(Object) this).getEffectivePos(), BotaniaGuard.FUNCTIONAL_FAKE, ((SubTileMarimorphosis)(Object) this).getRange(), ((SubTileMarimorphosis)(Object) this).getRangeY())) {
            mixin.cancel();
        }
    }

}