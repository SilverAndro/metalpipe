package io.github.silverandro.metal_pipe;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetalPipeMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Metal Pipe");

	public static final Item METAL_PIPE = new MetalPipeItem(
		new QuiltItemSettings().maxCount(16)
	);

	public static final Identifier METAL_PIPE_SOUND_ID = new Identifier("metal_pipe:metal_pipe");
	public static final SoundEvent METAL_PIPE_SOUND = SoundEvent.createVariableRangeEvent(METAL_PIPE_SOUND_ID);
	public static final SoundCategory METAL_PIPE_CATEGORY = SoundCategory.valueOf("METAL_PIPE");

	public static final ClangEnchantment CLANG_ENCHANTMENT = new ClangEnchantment(
		Enchantment.Rarity.RARE,
		EnchantmentTarget.valueOf("METAL_PIPE"),
		new EquipmentSlot[]{EquipmentSlot.MAINHAND}
	);

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("kachchchchchching");
		Registry.register(Registries.ITEM, new Identifier("metal_pipe:metalpipe"), METAL_PIPE);
		Registry.register(Registries.SOUND_EVENT, METAL_PIPE_SOUND_ID, METAL_PIPE_SOUND);
		Registry.register(Registries.ENCHANTMENT, new Identifier("metal_pipe:clang"), CLANG_ENCHANTMENT);
	}
}
