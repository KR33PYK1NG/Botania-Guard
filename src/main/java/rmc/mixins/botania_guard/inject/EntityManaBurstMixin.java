package rmc.mixins.botania_guard.inject;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import rmc.libs.event_factory.EventFactory;
import rmc.libs.tile_ownership.TileOwnership;
import rmc.mixins.botania_guard.BotaniaGuard;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.api.mana.ILensEffect;
import vazkii.botania.common.entity.EntityManaBurst;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = EntityManaBurst.class)
public abstract class EntityManaBurstMixin {

    @Redirect(method = "Lvazkii/botania/common/entity/EntityManaBurst;onHit(Lnet/minecraft/util/math/RayTraceResult;)V",
              at = @At(value = "INVOKE",
                       target = "Lvazkii/botania/api/mana/ILensEffect;collideBurst(Lvazkii/botania/api/internal/IManaBurst;Lnet/minecraft/util/math/RayTraceResult;ZZLnet/minecraft/item/ItemStack;)Z"))
    private boolean guardBurstCollision(ILensEffect lens, IManaBurst burst, RayTraceResult pos, boolean isManaBlock, boolean dead, ItemStack stack) {
        World world = ((EntityManaBurst)(Object) this).level;
        Entity src1 = burst.entity().getOwner();
        UUID src2 = null;
        if (src1 == null) {
            src2 = TileOwnership.loadOwner(TileOwnership.convert(world, burst.getBurstSourceBlockPos()));
        }
        boolean cancelled;
        if (src1 != null) {
            cancelled = !EventFactory.testBlockBreak(EventFactory.convert(src1), world, new BlockPos(pos.getLocation()), BotaniaGuard.MANA_BURST_FAKE);
        }
        else {
            cancelled = !EventFactory.testBlockBreak(EventFactory.convertFake(world, src2), world, new BlockPos(pos.getLocation()), BotaniaGuard.MANA_BURST_FAKE);
        }
        if (cancelled) {
            return true;
        }
        return lens.collideBurst(burst, pos, isManaBlock, dead, stack);
    }

}