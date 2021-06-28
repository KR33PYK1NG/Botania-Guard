package rmc.mixins.botania_guard.inject;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rmc.libs.event_factory.EventFactory;
import rmc.libs.tile_ownership.TileOwnership;
import rmc.mixins.botania_guard.BotaniaGuard;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.common.block.mana.BlockForestDrum;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = BlockForestDrum.class)
public abstract class BlockForestDrumMixin {

    @Inject(method = "Lvazkii/botania/common/block/mana/BlockForestDrum;onBurstCollision(Lvazkii/botania/api/internal/IManaBurst;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE_ASSIGN",
                     target = "Lnet/minecraft/world/World;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/util/math/AxisAlignedBB;)Ljava/util/List;"))
    private void guardDrumAction(IManaBurst burst, World world, BlockPos pos, CallbackInfo mixin, int range, List<MobEntity> entities) {
        Entity src1 = burst.entity().getOwner();
        UUID src2 = null;
        if (src1 == null) {
            src2 = TileOwnership.loadOwner(TileOwnership.convert(world, burst.getBurstSourceBlockPos()));
        }
        Iterator<MobEntity> it = entities.iterator();
        while (it.hasNext()) {
            boolean cancelled;
            if (src1 != null) {
                cancelled = !EventFactory.testEntityInteract(EventFactory.convert(src1), world, it.next(), BotaniaGuard.DRUM_HORN_FAKE);
            }
            else {
                cancelled = !EventFactory.testEntityInteract(EventFactory.convertFake(world, src2), world, it.next(), BotaniaGuard.DRUM_HORN_FAKE);
            }
            if (cancelled) {
                entities.clear();
                break;
            }
        }
    }

}