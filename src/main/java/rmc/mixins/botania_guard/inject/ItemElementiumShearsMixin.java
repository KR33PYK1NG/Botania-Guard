package rmc.mixins.botania_guard.inject;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rmc.libs.event_factory.EventFactory;
import vazkii.botania.common.item.equipment.tool.elementium.ItemElementiumShears;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = ItemElementiumShears.class)
public abstract class ItemElementiumShearsMixin {

    @Inject(method = "Lvazkii/botania/common/item/equipment/tool/elementium/ItemElementiumShears;onUseTick(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;I)V",
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE_ASSIGN",
                     target = "Lnet/minecraft/world/World;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/function/Predicate;)Ljava/util/List;"))
    private void guardShearsUsage(World world, LivingEntity living, ItemStack stack, int count, CallbackInfo ci, int range, Predicate<Entity> shearablePred, List<Entity> shearable) {
        Iterator<Entity> it = shearable.iterator();
        while (it.hasNext()) {
            if (!EventFactory.testEntityInteract(EventFactory.convert(living), world, it.next())) {
                shearable.clear();
                break;
            }
        }
    }

}