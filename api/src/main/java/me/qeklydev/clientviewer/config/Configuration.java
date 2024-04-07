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
package me.qeklydev.clientviewer.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public final class Configuration implements UsableConfiguration {
  @Comment("""
      The format used for the prefix.
      This prefix is used on all plugin messages.""")
  public String prefix = "<aqua>[⭐]";

  @Comment("""
      If the player client provide a null brand name,
      the player will be kicked from the server.""")
  public boolean denyClientBrandNull = true;

  @Comment("""
      Do you want to unable that specific clients can't
      join to the server?""")
  public boolean blockClients = true;

  @Comment("""
      If the player client brand name equals to some
      specified brand name, will be kicked.""")
  public String[] blockedClients = new String[] {
      "forge"
  };
}
