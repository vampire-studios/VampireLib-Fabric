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

import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class BlendingFunctions {

	public static double easeInOutCirc(double x) {
		return x < 0.5
				? (1 - sqrt(1 - pow(2 * x, 2))) / 2
				: (sqrt(1 - pow(-2 * x + 2, 2)) + 1) / 2;

	}

	public static double easeOutCubic(double x) {
		return 1 - pow(1 - x, 3);
	}

	public static double easeOutBounce(double x) {
		double n1 = 7.5625;
		double d1 = 2.75;

		if (x < 1 / d1) {
			return n1 * x * x;
		} else if (x < 2 / d1) {
			return n1 * (x -= 1.5 / d1) * x + 0.75;
		} else if (x < 2.5 / d1) {
			return n1 * (x -= 2.25 / d1) * x + 0.9375;
		} else {
			return n1 * (x -= 2.625 / d1) * x + 0.984375;
		}
	}

	public static double easeOutElastic(double x) {
		double c4 = (2 * Math.PI) / 3;

		return x == 0
				? 0
				: x == 1
				? 1
				: pow(2, -10 * x) * sin((x * 10 - 0.75) * c4) + 1;
	}

	public static double easeInCirc(double x) {
		return 1 - sqrt(1 - pow(x, 2));
	}

	public static double easeOutQuint(double x) {
		return 1 - pow(1 - x, 5);
	}
}
