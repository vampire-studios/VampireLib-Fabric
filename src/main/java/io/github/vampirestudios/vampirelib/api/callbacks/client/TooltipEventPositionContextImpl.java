/*
 * Copyright (c) 2022-2023 OliviaTheVampire
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

package io.github.vampirestudios.vampirelib.api.callbacks.client;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class TooltipEventPositionContextImpl implements RenderTooltipCallback.PositionContext {
	private int tooltipX;
	private int tooltipY;

	public TooltipEventPositionContextImpl reset(int tooltipX, int tooltipY) {
		this.tooltipX = tooltipX;
		this.tooltipY = tooltipY;

		return this;
	}

	@Override
	public int getTooltipX() {
		return this.tooltipX;
	}

	@Override
	public void setTooltipX(int x) {
		this.tooltipX = x;
	}

	@Override
	public int getTooltipY() {
		return this.tooltipY;
	}

	@Override
	public void setTooltipY(int y) {
		this.tooltipY = y;
	}
}