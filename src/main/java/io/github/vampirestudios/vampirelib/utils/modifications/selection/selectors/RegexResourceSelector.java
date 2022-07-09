package io.github.vampirestudios.vampirelib.utils.modifications.selection.selectors;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import net.minecraft.resources.ResourceLocation;

import io.github.vampirestudios.vampirelib.utils.modifications.selection.ResourceSelector;
import io.github.vampirestudios.vampirelib.utils.modifications.selection.ResourceSelectorSerializers;
import io.github.vampirestudios.vampirelib.utils.modifications.selection.SelectionSpace;

/**
 * A {@link ResourceSelector} implementation that returns a list of target names picked using a configurable regular expression.
 *
 * @author SmellyModder (Luke Tonon)
 */
public final record RegexResourceSelector(Pattern pattern) implements ResourceSelector<RegexResourceSelector> {
	@Override
	public List<ResourceLocation> select(SelectionSpace space) {
		List<ResourceLocation> targetNames = new ArrayList<>();
		Matcher matcher = this.pattern.matcher("");
		space.forEach(key -> {
			if (matcher.reset(key.toString()).matches()) {
				targetNames.add(key);
			}
		});
		return targetNames;
	}

	@Override
	public Serializer getSerializer() {
		return ResourceSelectorSerializers.REGEX;
	}

	/**
	 * The serializer class for the {@link RegexResourceSelector}.
	 *
	 * @author SmellyModder (Luke Tonon)
	 */
	public static final class Serializer implements ResourceSelector.Serializer<RegexResourceSelector> {
		@Override
		public JsonElement serialize(RegexResourceSelector selector) {
			return new JsonPrimitive(selector.pattern().pattern());
		}

		@Override
		public RegexResourceSelector deserialize(JsonElement element) {
			return new RegexResourceSelector(Pattern.compile(element.getAsString()));
		}
	}
}