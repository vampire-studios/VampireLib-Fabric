package org.quiltmc.qsl.worldgen.material_rule.mixin;

import java.util.ArrayList;
import java.util.List;

import org.quiltmc.qsl.worldgen.material_rule.api.MaterialRuleRegistrationEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.world.level.levelgen.SurfaceRules;

@Mixin(SurfaceRuleData.class)
public abstract class VanillaSurfaceRulesMixin {
	@Inject(method = "overworld", at = @At("RETURN"), cancellable = true)
	private static void quilt$injectOverworldRules(CallbackInfoReturnable<SurfaceRules.RuleSource> cir) {
		List<SurfaceRules.RuleSource> overworldMaterialRules = new ArrayList<>();

		MaterialRuleRegistrationEvents.OVERWORLD_RULE_INIT.invoker().registerRules(overworldMaterialRules);

		overworldMaterialRules.add(cir.getReturnValue());
		cir.setReturnValue(SurfaceRules.sequence(overworldMaterialRules.toArray(new SurfaceRules.RuleSource[0])));
	}

	@Inject(method = "nether", at = @At("RETURN"), cancellable = true)
	private static void quilt$injectNetherRules(CallbackInfoReturnable<SurfaceRules.RuleSource> cir) {
		List<SurfaceRules.RuleSource> netherMaterialRules = new ArrayList<>();

		MaterialRuleRegistrationEvents.NETHER_RULE_INIT.invoker().registerRules(netherMaterialRules);

		netherMaterialRules.add(cir.getReturnValue());
		cir.setReturnValue(SurfaceRules.sequence(netherMaterialRules.toArray(new SurfaceRules.RuleSource[0])));
	}

	@Inject(method = "end", at = @At("RETURN"), cancellable = true)
	private static void quiltInjectEndRules(CallbackInfoReturnable<SurfaceRules.RuleSource> cir) {
		List<SurfaceRules.RuleSource> theEndMaterialRules = new ArrayList<>();

		MaterialRuleRegistrationEvents.THE_END_RULE_INIT.invoker().registerRules(theEndMaterialRules);

		theEndMaterialRules.add(cir.getReturnValue());
		cir.setReturnValue(SurfaceRules.sequence(theEndMaterialRules.toArray(new SurfaceRules.RuleSource[0])));
	}
}