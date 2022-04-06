package io.github.vampirestudios.vampirelib.client;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;

public class AnimationCodecs {
    public static final Codec<AnimationChannel.Target> TARGET_CODEC = Codec.unit(() -> (modelPart, vec3f) ->{});
    public static final Codec<AnimationChannel.Interpolation> INTERPOLATION_CODEC = Codec.unit(() -> (vector3f, f, keyframes, i, j, g) -> null);
    public static final Codec<Keyframe> KEYFRAME_CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.FLOAT.fieldOf("timestamp").forGetter(Keyframe::timestamp),
            Vector3f.CODEC.fieldOf("target").forGetter(Keyframe::target),
            INTERPOLATION_CODEC.fieldOf("interpolation").forGetter(Keyframe::interpolation)
        ).apply(instance, Keyframe::new)
    );
    public static final Codec<AnimationChannel> ANIMATION_CHANNEL_CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            TARGET_CODEC.fieldOf("target").forGetter(AnimationChannel::target),
            KEYFRAME_CODEC.listOf().fieldOf("target").forGetter(o -> {
                Keyframe[] keyframes = o.keyframes();
                return Arrays.stream(keyframes).collect(Collectors.toList());
            })
        ).apply(instance, (target, keyframes) -> new AnimationChannel(target, (Keyframe[]) keyframes.toArray()))
    );
    public static final Codec<AnimationDefinition> ANIMATION_DEFINITION_CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.FLOAT.fieldOf("lengthInSeconds").forGetter(AnimationDefinition::lengthInSeconds),
            Codec.BOOL.fieldOf("looping").forGetter(AnimationDefinition::looping),
            Codec.unboundedMap(Codec.STRING, ANIMATION_CHANNEL_CODEC.listOf()).fieldOf("boneAnimations")
                .forGetter(AnimationDefinition::boneAnimations)
        ).apply(instance, AnimationDefinition::new)
    );
}
