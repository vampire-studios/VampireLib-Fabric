/*
 * Copyright (c) 2023 OliviaTheVampire
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

package io.github.vampirestudios.vampirelib.api.datagen;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class ResourcePackLoader {
	private static Map<ModContainer, PathPackResources> modResourcePacks;

	public static Optional<PathPackResources> getPackFor(String modId) {
		return Optional.ofNullable(FabricLoader.getInstance().getModContainer(modId).orElse(null)).
				map(mf->modResourcePacks.get(mf));
	}

	@SuppressWarnings("deprecation")
	@NotNull
	public static PathPackResources createPackForMod(ModContainer mf) {
		return new PathPackResources(mf.getMetadata().getName(), false, mf.getRootPath()){
			@NotNull
			@Override
			protected Path resolve(@NotNull String... paths) {
				if (paths.length < 1) {
					throw new IllegalArgumentException("Missing path");
				}
				String path = String.join("/", paths);
				for (ModContainer modContainer : FabricLoader.getInstance().getAllMods()) {
					if (modContainer.findPath(path).isPresent())
						return modContainer.findPath(path).get();
				}

				return mf.findPath(path).orElse(new RefPath(mf.getRootPath().getFileSystem(), makeKey(mf.getRootPath().getRoot()), path));
			}
		};
	}

	private static int index = 0;

	private static synchronized String makeKey(Path path) {
		var key= path.toAbsolutePath().normalize().toUri().getPath();
		return key.replace('!', '_') + "#" + index++;
	}

	public static List<String> getPackNames() {
		return FabricLoader.getInstance().getAllMods().stream().map(mf->"mod:"+mf.getMetadata().getId()).filter(n->!n.equals("mod:minecraft")).collect(Collectors.toList());
	}

	public static <V> Comparator<Map.Entry<String,V>> getSorter() {
		List<String> order = new ArrayList<>();
		order.add("vanilla");
		order.add("mod_resources");

		FabricLoader.getInstance().getAllMods().stream()
				.map(e -> e.getMetadata().getId())
				.map(e -> "mod:" + e)
				.forEach(order::add);

		final Object2IntMap<String> order_f = new Object2IntOpenHashMap<>(order.size());
		for (int x = 0; x < order.size(); x++)
			order_f.put(order.get(x), x);

		return (e1, e2) -> {
			final String s1 = e1.getKey();
			final String s2 = e2.getKey();
			final int i1 = order_f.getOrDefault(s1, -1);
			final int i2 = order_f.getOrDefault(s2, -1);

			if (i1 == i2 && i1 == -1)
				return s1.compareTo(s2);
			if (i1 == -1) return 1;
			if (i2 == -1) return -1;
			return i2 - i1;
		};
	}

}
