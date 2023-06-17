package io.github.silverandro.metal_pipe;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class ClangEnchantment extends Enchantment {
	protected ClangEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
		super(weight, type, slotTypes);
	}

	@Override
	public int getMaxLevel() {
		return 4;
	}

	@Override
	public int getMinPower(int level) {
		return 15 + ((level) * 6);
	}

	@Override
	public int getMaxPower(int level) {
		return getMinPower(level) + 15;
	}

	@Override
	public float getAttackDamage(int level, EntityGroup group) {
		return (level * 3.5f);
	}

	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		return stack.getItem() instanceof MetalPipeItem;
	}

	@Override
	public void onTargetDamaged(LivingEntity user, Entity target, int level) {
		if (target instanceof LivingEntity le && le.isDead()) {
			if (EnchantmentHelper.getLevel(MetalPipeMod.CLANG_ENCHANTMENT, user.getMainHandStack()) > 0) {
				user.getWorld().playSound(
					null,
					target.getX(),
					target.getEyeY(),
					target.getZ(),
					MetalPipeMod.METAL_PIPE_SOUND,
					MetalPipeMod.METAL_PIPE_CATEGORY,
					0.25f,
					1.01f + (float) (user.getRandom().nextGaussian() / 10)
				);
			}
		}
	}
}
