package rmc.mixins.botania_guard.inject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import rmc.mixins.botania_guard.BotaniaGuard;
import vazkii.botania.common.block.tile.TileTinyPlanet;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = TileTinyPlanet.class)
public abstract class TileTinyPlanetMixin {

    @Inject(method = "Lvazkii/botania/common/block/tile/TileTinyPlanet;tick()V",
            cancellable = true,
            at = @At(value = "HEAD"))
    private void guardPlanetPull(CallbackInfo mixin) {
        if (!BotaniaGuard.validateTilePositioning((TileTinyPlanet)(Object) this, ((TileTinyPlanet)(Object) this).getBlockPos(), BotaniaGuard.TINY_PLANET_FAKE, 8, 8)) {
            mixin.cancel();
        }
    }

}