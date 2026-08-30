package io.github.vampirestudios.vampirelib.api.datagen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSources;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

public abstract class SpriteSourceProvider extends JsonCodecProvider<List<SpriteSource>> {
	protected static final Identifier BLOCKS_ATLAS = Identifier.withDefaultNamespace("blocks");
	protected static final Identifier ITEMS_ATLAS = Identifier.withDefaultNamespace("items");
	protected static final Identifier BANNER_PATTERNS_ATLAS = Identifier.withDefaultNamespace("banner_patterns");
	protected static final Identifier CHESTS_ATLAS = Identifier.withDefaultNamespace("chests");
	protected static final Identifier SHIELD_PATTERNS_ATLAS = Identifier.withDefaultNamespace("shield_patterns");
	protected static final Identifier SHULKER_BOXES_ATLAS = Identifier.withDefaultNamespace("shulker_boxes");
	protected static final Identifier MOB_EFFECTS_ATLAS = Identifier.withDefaultNamespace("mob_effects");
	protected static final Identifier PAINTINGS_ATLAS = Identifier.withDefaultNamespace("paintings");
	protected static final Identifier PARTICLES_ATLAS = Identifier.withDefaultNamespace("particles");

	private final Map<Identifier, SourceList> atlases = new HashMap<>();

	public SpriteSourceProvider(PackOutput output) {
		super(output, PackOutput.Target.RESOURCE_PACK, "atlases", SpriteSources.FILE_CODEC);
	}

	@Override
	public String getName() {
		return "Sprite Source Provider";
	}

	@Override
	protected void configure(BiConsumer<Identifier, List<SpriteSource>> provider) {
		addSources();
		atlases.forEach((atlas, srcList) -> provider.accept(atlas, srcList.sources));
	}

	protected abstract void addSources();

	/**
	 * Get or create a {@link SourceList} for the given atlas
	 *
	 * @param atlas The texture atlas the sources should be added to, see constants at the top for the format
	 *              and the vanilla atlases
	 * @return an existing {@code SourceList} for the given atlas or a new one if not present yet
	 */
	protected final SourceList atlas(Identifier atlas) {
		return atlases.computeIfAbsent(atlas, $ -> new SourceList());
	}

	protected static final class SourceList {
		private final List<SpriteSource> sources = new ArrayList<>();

		/**
		 * Add the given {@link SpriteSource} to this atlas configuration
		 *
		 * @param source The {@code SpriteSource} to be added
		 */
		public SourceList addSource(SpriteSource source) {
			sources.add(source);
			return this;
		}
	}
}
