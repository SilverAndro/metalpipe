package io.github.silverandro.metal_pipe.mixin;

import io.github.silverandro.metal_pipe.MetalPipeMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
	@Shadow
	public abstract ItemStack getStack();

	private boolean wasOnGround;

	public ItemEntityMixin(EntityType<?> variant, World world) {
		super(variant, world);
	}

	@Inject(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/ItemEntity;move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V"
		)
	)
	public void metalpipe$captureOnGround(CallbackInfo ci) {
		wasOnGround = onGround;
	}

	@Inject(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;",
			shift = At.Shift.BEFORE
		)
	)
	public void metalpipe$onHitGround(CallbackInfo ci) {
		if (!world.isClient && !wasOnGround && onGround && getStack().getItem() == MetalPipeMod.METAL_PIPE) {
			world.playSound(
				null,
				getX(),
				getY(),
				getZ(),
				MetalPipeMod.METAL_PIPE_SOUND,
				MetalPipeMod.METAL_PIPE_CATEGORY,
				0.25f,
				1.01f + (float)(random.nextGaussian()/10)
			);
		}
	}
}
