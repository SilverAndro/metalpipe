package io.github.silverandro.metal_pipe.mixin;

import net.minecraft.enchantment.EnchantmentTarget;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.quiltmc.loader.api.QuiltLoader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentTarget.class)
public class EnchantmentTargetMixin {
	@Mutable
	@Shadow
	@Final
	private static EnchantmentTarget[] field_9077;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void metal_pipe$injectNewTarget(CallbackInfo ci) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		var mapper = QuiltLoader.getMappingResolver();
		var enchTargetName = mapper.mapClassName("intermediary", "net.minecraft.class_1886").replace(".", "/");
		var itemName = mapper.mapClassName("intermediary", "net.minecraft.class_1792").replace(".", "/");
		var acceptableItemName = mapper.mapMethodName(
			"intermediary",
			"net.minecraft.class_1886",
			"method_8177",
			"(Lnet/minecraft/class_1792;)Z"
		).replace(".", "/");

		System.out.println(enchTargetName);
		System.out.println(itemName);
		System.out.println(acceptableItemName);

		var node = new ClassNode();
		// Class shell
		node.name = enchTargetName + "$MetalPipeTarget";
		node.access = Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL;
		node.version = 61;
		node.superName = enchTargetName;

		// Constructor
		var init = new MethodNode();
		init.name = "<init>";
		init.access = Opcodes.ACC_PUBLIC;
		init.desc = "(Ljava/lang/String;I)V";
		var initInstructions = new InsnList();
		initInstructions.add(new IntInsnNode(Opcodes.ALOAD, 0));
		initInstructions.add(new LdcInsnNode("METAL_PIPE"));
		initInstructions.add(new LdcInsnNode(field_9077.length));
		initInstructions.add(new MethodInsnNode(
			Opcodes.INVOKESPECIAL,
			enchTargetName,
			"<init>",
			"(Ljava/lang/String;I)V"
		));
		initInstructions.add(new InsnNode(Opcodes.RETURN));
		init.instructions.add(initInstructions);
		node.methods.add(init);

		// isAcceptableItem
		var isAcceptableItem = new MethodNode();
		isAcceptableItem.name = acceptableItemName;
		isAcceptableItem.access = Opcodes.ACC_PUBLIC;
		isAcceptableItem.desc = "(L" + itemName + ";)Z";

		var acceptableItemInstructions = new InsnList();
		acceptableItemInstructions.add(new LdcInsnNode(1));
		acceptableItemInstructions.add(new InsnNode(Opcodes.IRETURN));

		isAcceptableItem.instructions.add(acceptableItemInstructions);
		node.methods.add(isAcceptableItem);

		var writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		node.accept(writer);

		var lookup = MethodHandles.lookup();
		var clazz = lookup.defineClass(writer.toByteArray());
		var instance = clazz.getConstructor(String.class, int.class).newInstance("METAL_PIPE", field_9077.length);

		var list = new ArrayList<>(List.of(field_9077));
		list.add((EnchantmentTarget)instance);
		field_9077 = list.toArray(EnchantmentTarget[]::new);
	}
}
