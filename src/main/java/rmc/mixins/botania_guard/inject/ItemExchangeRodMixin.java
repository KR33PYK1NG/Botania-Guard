package rmc.mixins.botania_guard.inject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rmc.libs.event_factory.EventFactory;
import vazkii.botania.common.item.rod.ItemExchangeRod;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = ItemExchangeRod.class)
public abstract class ItemExchangeRodMixin {

    @Inject(method = "Lvazkii/botania/common/item/rod/ItemExchangeRod;exchange(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/Item;)I",
            remap = false,
            cancellable = true,
            at = @At(value = "HEAD"))
    private void guardBlockExchange(World world, PlayerEntity player, BlockPos pos, ItemStack rod, Item replacement, CallbackInfoReturnable<Integer> mixin) {
        if (!EventFactory.testBlockBreak(EventFactory.convert(player), world, pos)) {
            mixin.setReturnValue(0);
        }
    }

}