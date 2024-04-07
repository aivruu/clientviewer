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
package me.qeklydev.clientviewer.registry;

import java.util.HashMap;
import java.util.Map;
import me.qeklydev.clientviewer.client.CachedClientModel;
import me.qeklydev.clientviewer.event.ClientDetectionEvent;
import me.qeklydev.clientviewer.event.ClientRegistryDeletionEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class is used for have a registry of
 * the clients types that are being used on
 * this server.
 *
 * @since 0.0.1
 */
public final class PlayableClientRegistry {
  private final Map<String, CachedClientModel> recognizedClients;

  public PlayableClientRegistry() {
    this.recognizedClients = new HashMap<>();
  }

  /**
   * Returns, or not, the client model based on the
   * key provided.
   *
   * @param id the player uuid.
   * @return The {@link CachedClientModel} or {@code null}
   *     if the key isn't cached.
   * @since 0.0.1
   */
  public @Nullable CachedClientModel findOrNull(final @NotNull String id) {
    return this.recognizedClients.get(id);
  }

  /**
   * Registers a new client model for the key provided
   * with the given information.
   *
   * @param player the player.
   * @param clientBrand the player client brand.
   * @param protocol the protocol number for the client
   *                 version.
   * @since 0.0.1
   */
  public void register(final @NotNull Player player, final @NotNull String clientBrand, final short protocol) {
    /*
     * Fires the client detection event with the given
     * information early before registration.
     */
    final var clientDetectionEvent = new ClientDetectionEvent(player, clientBrand, protocol);
    Bukkit.getPluginManager().callEvent(clientDetectionEvent);
    /*
     * Builds client model with the information and
     * register it into the cache.
     */
    final var clientModel = new CachedClientModel(clientBrand, protocol);
    this.recognizedClients.put(player.getUniqueId().toString(), clientModel);
  }

  /**
   * Removes the client model from cache for the specified
   * key, if key doesn't exist, will do nothing.
   *
   * @param player the player for get their client model.
   * @since 0.0.1
   */
  public void remove(final @NotNull Player player) {
    /*
     * Checks if there's client model registered
     * for this player.
     */
    final var clientModel = this.recognizedClients.remove(player.getUniqueId().toString());
    if (clientModel == null) {
      return;
    }
    /*
     * Fires the client registry deletion event with the
     * client model information.
     */
    final var clientRegistryDeletionEvent = new ClientRegistryDeletionEvent(player, clientModel.brand());
    Bukkit.getPluginManager().callEvent(clientRegistryDeletionEvent);
  }

  public void clearRegistry() {
    this.recognizedClients.clear();
  }
}
