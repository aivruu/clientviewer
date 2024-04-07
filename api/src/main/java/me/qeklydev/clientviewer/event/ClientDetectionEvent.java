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
package me.qeklydev.clientviewer.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This event is fired when the player is on the
 * login process and the client information is stored.
 *
 * @since 0.0.1
 */
public final class ClientDetectionEvent extends Event {
  private static final HandlerList HANDLER_LIST = new HandlerList();
  private final Player player;
  private final String brandName;
  private final short protocol;

  public ClientDetectionEvent(final @NotNull Player player, final @NotNull String brandName, final short protocol) {
    this.player = player;
    this.brandName = brandName;
    this.protocol = protocol;
  }

  /**
   * Returns the player involved in this
   * event.
   *
   * @return The involved player.
   * @since 0.0.1
   */
  public @NotNull Player player() {
    return this.player;
  }

  /**
   * Returns the brand name of the client
   * involved in this event.
   *
   * @return The client brand name.
   * @since 0.0.1
   */
  public @NotNull String brandName() {
    return this.brandName;
  }

  /**
   * Returns the protocol version number for the
   * Minecraft version running on the client.
   *
   * @return The protocol version number.
   * @since 0.0.1
   */
  public short protocol() {
    return this.protocol;
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLER_LIST;
  }
}
