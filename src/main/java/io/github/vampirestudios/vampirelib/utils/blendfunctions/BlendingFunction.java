/*
 * Copyright (c) 2022 OliviaTheVampire
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.vampirestudios.vampirelib.utils.blendfunctions;

import com.mojang.serialization.Codec;

import io.github.vampirestudios.vampirelib.VampireLib;
import io.github.vampirestudios.vampirelib.utils.registry.ResourceLocationUtils;

public interface BlendingFunction {
//	Codec<BlendingFunction> CODEC = VRegistries.BLENDING_FUNCTION.byNameCodec().dispatchStable(BlendingFunction::codec,
//			Function.identity());

	Codec<? extends BlendingFunction> codec();

	double apply(double factor);

	default double apply(double factor, double min, double max) {
		double range = max - min;
		return min + (range * apply(factor));
	}

	static void init() {
		ResourceLocationUtils.setModInstance(VampireLib.INSTANCE);
		/*Registry.register(VRegistries.BLENDING_FUNCTION, ResourceLocationUtils.modId("ease_in_out_circ"),
				EaseInOutCirc.CODEC);
		Registry.register(VRegistries.BLENDING_FUNCTION, ResourceLocationUtils.modId("ease_out_bounce"),
				EaseOutBounce.CODEC);
		Registry.register(VRegistries.BLENDING_FUNCTION, ResourceLocationUtils.modId("ease_out_cubic"),
				EaseOutCubic.CODEC);
		Registry.register(VRegistries.BLENDING_FUNCTION, ResourceLocationUtils.modId("ease_out_elastic"),
				EaseOutElastic.CODEC);
		Registry.register(VRegistries.BLENDING_FUNCTION, ResourceLocationUtils.modId("ease_in_circ"), EaseInCirc.CODEC);
		Registry.register(VRegistries.BLENDING_FUNCTION, ResourceLocationUtils.modId("ease_out_quint"),
				EaseOutQuint.CODEC);*/
	}

	record EaseInOutCirc() implements BlendingFunction {
		public static final EaseInOutCirc INSTANCE = new EaseInOutCirc();
		public static final Codec<EaseInOutCirc> CODEC = Codec.unit(() -> INSTANCE);

		@Override
		public double apply(double factor) {
			return BlendingFunctions.easeInOutCirc(factor);
		}

		@Override
		public Codec<? extends BlendingFunction> codec() {
			return CODEC;
		}
	}

	record EaseOutCubic() implements BlendingFunction {
		public static final EaseOutCubic INSTANCE = new EaseOutCubic();
		public static final Codec<EaseOutCubic> CODEC = Codec.unit(() -> INSTANCE);

		@Override
		public double apply(double factor) {
			return BlendingFunctions.easeOutCubic(factor);
		}

		@Override
		public Codec<? extends BlendingFunction> codec() {
			return CODEC;
		}

	}

	record EaseOutBounce() implements BlendingFunction {
		public static final EaseOutBounce INSTANCE = new EaseOutBounce();
		public static final Codec<EaseOutBounce> CODEC = Codec.unit(() -> INSTANCE);

		@Override
		public double apply(double factor) {
			return BlendingFunctions.easeOutBounce(factor);
		}

		@Override
		public Codec<? extends BlendingFunction> codec() {
			return CODEC;
		}
	}

	record EaseOutElastic() implements BlendingFunction {
		public static final EaseOutElastic INSTANCE = new EaseOutElastic();
		public static final Codec<EaseOutElastic> CODEC = Codec.unit(() -> INSTANCE);

		@Override
		public double apply(double factor) {
			return BlendingFunctions.easeOutElastic(factor);
		}

		@Override
		public Codec<? extends BlendingFunction> codec() {
			return CODEC;
		}
	}

	record EaseInCirc() implements BlendingFunction {
		public static final EaseInCirc INSTANCE = new EaseInCirc();
		public static final Codec<EaseInCirc> CODEC = Codec.unit(() -> INSTANCE);

		@Override
		public double apply(double factor) {
			return BlendingFunctions.easeInCirc(factor);
		}

		@Override
		public Codec<? extends BlendingFunction> codec() {
			return CODEC;
		}
	}

	record EaseOutQuint() implements BlendingFunction {
		public static final EaseOutQuint INSTANCE = new EaseOutQuint();
		public static final Codec<EaseOutQuint> CODEC = Codec.unit(() -> INSTANCE);

		@Override
		public double apply(double factor) {
			return BlendingFunctions.easeOutQuint(factor);
		}

		@Override
		public Codec<? extends BlendingFunction> codec() {
			return CODEC;
		}
	}

}
