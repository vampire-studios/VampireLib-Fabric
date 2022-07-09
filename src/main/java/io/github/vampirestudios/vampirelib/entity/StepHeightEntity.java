package io.github.vampirestudios.vampirelib.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

import io.github.vampirestudios.vampirelib.init.VAttributes;

public interface StepHeightEntity {
	/**
	 * @return Return the height in blocks the Entity can step up without needing to jump
	 * This is the sum of vanilla's {@link Entity#maxUpStep} field and the current value
	 * of the {@link VAttributes#STEP_HEIGHT_ADDITION} attribute
	 * (if this Entity is a {@link LivingEntity} and has the attribute), clamped at 0.
	 */
	default float getStepHeight() {
		float vanillaStep = ((Entity)this).maxUpStep;
		if (this instanceof LivingEntity living) {
			AttributeInstance stepHeightAttribute = living.getAttribute(VAttributes.STEP_HEIGHT_ADDITION);
			if (stepHeightAttribute != null) {
				return (float) Math.max(0, vanillaStep + stepHeightAttribute.getValue());
			}
		}
		return vanillaStep;
	}
}