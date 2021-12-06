package io.github.vampirestudios.vampirelib.api.datagen;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;

public class CustomFabricTagBuilder<T> extends TagsProvider.TagAppender<T> {
    private final TagsProvider.TagAppender<T> parent;

    public CustomFabricTagBuilder(TagsProvider.TagAppender<T> parent) {
        super(parent.builder, parent.registry, parent.source);
        this.parent = parent;
    }

    /**
     * Set the value of the `replace` flag in a Tag.
     *
     * <p>When set to true the tag will replace any existing tag entries.
     *
     * @return the {@link CustomFabricTagBuilder} instance
     */
    public CustomFabricTagBuilder<T> setReplace(boolean replace) {
        ((net.fabricmc.fabric.impl.datagen.FabricTagBuilder) builder).fabric_setReplace(replace);
        return this;
    }

    /**
     * Add a single element to the tag.
     *
     * @return the {@link CustomFabricTagBuilder} instance
     */
    @Override
    public CustomFabricTagBuilder<T> add(T element) {
        parent.add(element);
        return this;
    }

    /**
     * Add an optional {@link ResourceLocation} to the tag.
     *
     * @return the {@link CustomFabricTagBuilder} instance
     */
    @Override
    public CustomFabricTagBuilder<T> addOptional(ResourceLocation id) {
        parent.addOptional(id);
        return this;
    }

    /**
     * Add another tag to this tag.
     *
     * @return the {@link CustomFabricTagBuilder} instance
     */
    @Override
    public CustomFabricTagBuilder<T> addTag(Tag.Named<T> tag) {
        parent.addTag(tag);
        return this;
    }

    /**
     * Add another tag to this tag.
     *
     * @return the {@link FabricTagProvider.FabricTagBuilder} instance
     */
    public CustomFabricTagBuilder<T> addTags(Tag.Named<T>... values) {
        for (Tag.Named<T> value : values) {
            this.addTag(value);
        }
        return this;
    }

    /**
     * Add another optional tag to this tag.
     *
     * @return the {@link CustomFabricTagBuilder} instance
     */
    @Override
    public CustomFabricTagBuilder<T> addOptionalTag(ResourceLocation id) {
        parent.addOptionalTag(id);
        return this;
    }
}
