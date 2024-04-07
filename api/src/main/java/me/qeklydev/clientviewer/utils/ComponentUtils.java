/*
 * This file is part of clientviewer - https://github.com/aivruu/clientviewer
 * Copyright (C) 2020-2024 aivruu (https://github.com/aivruu)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package me.qeklydev.clientviewer.utils;

import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

/**
 * This class is used to proportionate fast utilities
 * for work with native Paper components.
 *
 * @since 0.0.1
 */
public final class ComponentUtils {
  /**
   * This is used to strings/messages formatting.
   *
   * @since 0.0.1
   */
  public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
  /**
   * This {@link Component} type is used when a new is
   * being created since a list provider.
   *
   * @since 0.0.1
   */
  private static final Component NEW_LINE = Component.newline();

  private ComponentUtils() {
    throw new UnsupportedOperationException("This class is for utility and cannot be instantiated.");
  }

  /**
   * Creates a new component since the given string.
   *
   * @param text the text string.
   * @return The built {@link Component}.
   * @since 0.0.1
   */
  public static @NotNull Component ofSingle(final @NotNull String text) {
    return MINI_MESSAGE.deserialize(text);
  }

  /**
   * Creates a new component since the given string, and apply the tag
   * resolvers provided.
   *
   * @param text the text string.
   * @param resolvers the placeholders for this component.
   * @return The built {@link Component} with the {@link TagResolver}
   *     array applied.
   * @since 0.0.1
   */
  public static @NotNull Component ofSingleWith(final @NotNull String text, final TagResolver @NotNull... resolvers) {
    return MINI_MESSAGE.deserialize(text, resolvers);
  }

  /**
   * Uses all strings in the list and creates a new component
   * using this values.
   *
   * @param text the provider list.
   * @return The built {@link Component}.
   * @since 0.0.1
   */
  public static @NotNull Component ofMany(final @NotNull List<@NotNull String> text) {
    final var componentBuilder = Component.text();
    for (final var iterated : text) {
      componentBuilder.append(MINI_MESSAGE.deserialize(iterated)).append(NEW_LINE);
    }
    return componentBuilder.build();
  }

  /**
   * Uses all strings in the list and creates a new component
   * using this values, and apply the tag resolvers provided.
   *
   * @param text the provider list.
   * @param resolvers the placeholders for this component.
   * @return The built {@link Component} with the {@link TagResolver}
   *     array applied.
   * @since 0.0.1
   */
  public static @NotNull Component ofManyWith(final @NotNull List<@NotNull String> text,
                                              final TagResolver @NotNull... resolvers) {
    final var componentBuilder = Component.text();
    for (final var iterated : text) {
      componentBuilder.append(MINI_MESSAGE.deserialize(iterated, resolvers)).append(NEW_LINE);
    }
    return componentBuilder.build();
  }
}
