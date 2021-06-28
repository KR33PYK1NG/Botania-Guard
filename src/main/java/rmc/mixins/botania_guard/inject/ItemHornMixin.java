package rmc.mixins.botania_guard.inject;

import java.util.Iterator;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rmc.libs.event_factory.EventFactory;
import rmc.mixins.botania_guard.BotaniaGuard;
import vazkii.botania.api.item.IHornHarvestable.EnumHornType;
import vazkii.botania.common.item.ItemHorn;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = ItemHorn.class)
public abstract class ItemHornMixin {

    @Inject(method = "Lvazkii/botania/common/item/ItemHorn;breakGrass(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/math/BlockPos;)V",
            remap = false,
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE",
                     target = "Ljava/util/Collections;shuffle(Ljava/util/List;Ljava/util/Random;)V"))
    private static void guardHornAction(World world, ItemStack stack, BlockPos srcPos, CallbackInfo mixin, EnumHornType type, int range, int rangeY, List<BlockPos> coords) {
        Iterator<BlockPos> it = coords.iterator();
        while (it.hasNext()) {
            if (!EventFactory.testBlockBreak((ServerPlayerEntity) null, world, it.next(), BotaniaGuard.DRUM_HORN_FAKE)) {
                coords.clear();
                break;
            }
        }
    }

}