package rmc.mixins.botania_guard.inject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import rmc.libs.event_factory.EventFactory;
import vazkii.botania.common.item.rod.ItemGravityRod;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = ItemGravityRod.class)
public abstract class ItemGravityRodMixin {

    @Inject(method = "Lvazkii/botania/common/item/rod/ItemGravityRod;use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;",
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE",
                     target = "Lvazkii/botania/api/mana/ManaItemHandler;instance()Lvazkii/botania/api/mana/ManaItemHandler;"))
    private void guardEntityControl(World world, PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult<ItemStack>> mixin, ItemStack stack, int targetID, int ticksCooldown, double length, Entity target) {
        if (!EventFactory.testEntityInteract(EventFactory.convert(player), world, target)) {
            mixin.setReturnValue(ActionResult.fail(stack));
        }
    }

    @Inject(method = "Lvazkii/botania/common/item/rod/ItemGravityRod;leftClick(Lnet/minecraft/entity/player/PlayerEntity;)V",
            remap = false,
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE",
                     ordinal = 0,
                     target = "Lvazkii/botania/common/core/helper/ItemNBTHelper;setInt(Lnet/minecraft/item/ItemStack;Ljava/lang/String;I)V"))
    private static void guardEntityLaunch(PlayerEntity player, CallbackInfo mixin, ItemStack stack, int targetID, Entity item) {
        if (!EventFactory.testEntityInteract(EventFactory.convert(player), player.level, item)) {
            mixin.cancel();
        }
    }

}