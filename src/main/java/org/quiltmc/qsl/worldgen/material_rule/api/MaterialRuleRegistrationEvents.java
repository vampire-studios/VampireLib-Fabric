package org.quiltmc.qsl.worldgen.material_rule.api;

import java.util.List;

import org.quiltmc.qsl.base.api.event.Event;
import org.quiltmc.qsl.base.api.event.EventAwareListener;

import net.minecraft.world.level.levelgen.SurfaceRules;

public final class MaterialRuleRegistrationEvents {
	/**
	 * Event invoked when Overworld MaterialRules are getting initialized / registered.
	 */
	public static final Event<OverworldMaterialRuleRegistrationCallback> OVERWORLD_RULE_INIT = Event.create(OverworldMaterialRuleRegistrationCallback.class, callbacks -> (materialRules) -> {
		for (var callback : callbacks) {
			callback.registerRules(materialRules);
		}
	});

	/**
	 * Event invoked when Nether MaterialRules are getting initialized / registered.
	 */
	public static final Event<NetherMaterialRuleRegistrationCallback> NETHER_RULE_INIT = Event.create(NetherMaterialRuleRegistrationCallback.class, callbacks -> (materialrules) -> {
		for (var callback : callbacks) {
			callback.registerRules(materialrules);
		}
	});

	/**
	 * Event invoked when The End's MaterialRules are getting initialized / registered.
	 */
	public static final Event<TheEndMaterialRuleRegistrationCallback> THE_END_RULE_INIT = Event.create(TheEndMaterialRuleRegistrationCallback.class, callbacks -> (materialRules) -> {
		for (var callback : callbacks) {
			callback.registerRules(materialRules);
		}
	});


	/**
	 * Functional interface to be implemented on callbacks for {@link #OVERWORLD_RULE_INIT}.
	 *
	 * @see #OVERWORLD_RULE_INIT
	 */
	@FunctionalInterface
	public interface OverworldMaterialRuleRegistrationCallback extends EventAwareListener {
		/**
		 * Called as material rules get registered for the Overworld.
		 *
		 * To add a rule, simply add your rule(s) to {@code materialRules} inside the event callback.
		 *
		 * @param materialRules the list of surface rules to be added to the overworld, including vanilla ones.
		 */
		void registerRules(List<SurfaceRules.RuleSource> materialRules);
	}

	/**
	 * Functional interface to be implemented on callbacks for {@link #NETHER_RULE_INIT}.
	 *
	 * @see #NETHER_RULE_INIT
	 */
	@FunctionalInterface
	public interface NetherMaterialRuleRegistrationCallback extends EventAwareListener {
		/**
		 * Called as material rules get registered for the Nether.
		 *
		 * To add a rule, simply add your rule(s) to {@code materialRules} inside the event callback.
		 *
		 * @param materialRules the list of surface rules to be added to the Nether, including vanilla ones.
		 */
		void registerRules(List<SurfaceRules.RuleSource> materialRules);
	}

	/**
	 * Functional interface to be implemented on callbacks for {@link #THE_END_RULE_INIT}.
	 *
	 * @see #THE_END_RULE_INIT
	 */
	@FunctionalInterface
	public interface TheEndMaterialRuleRegistrationCallback extends EventAwareListener {
		/**
		 * Called as material rules get registered for The End.
		 *
		 * To add a rule, simply add your rule(s) to {@code materialRules} inside the event callback.
		 *
		 * @param materialRules the list of surface rules to be added to The End, including vanilla ones.
		 */
		void registerRules(List<SurfaceRules.RuleSource> materialRules);
	}
}