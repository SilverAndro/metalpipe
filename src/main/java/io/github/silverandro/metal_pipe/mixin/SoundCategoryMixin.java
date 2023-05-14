package io.github.silverandro.metal_pipe.mixin;

import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(SoundCategory.class)
public class SoundCategoryMixin {
	@Mutable
	@Shadow
	@Final
	private static SoundCategory[] field_15255;

	@SuppressWarnings({"unused", "SameParameterValue"})
	@Invoker("<init>")
	private static SoundCategory metal_pipe$createNew(String name, int ordinal, String display) {
		throw new AbstractMethodError("Mixin method");
	}

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void metal_pipe$injectNewCategory(CallbackInfo ci) {
		var list = new ArrayList<>(List.of(field_15255));
		list.add(metal_pipe$createNew("METAL_PIPE", field_15255.length, "metal_pipe"));
		field_15255 = list.toArray(SoundCategory[]::new);
	}
}
