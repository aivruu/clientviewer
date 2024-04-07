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
package me.qeklydev.clientviewer.client;

import org.jetbrains.annotations.NotNull;

/**
 * This record is used to store and provide handling
 * about this player client information.
 *
 * @param brand the client brand, e.g. vanilla.
 * @param protocol the pvn (protocol version number)
 *                 for the minecraft version of client.
 * @since 0.0.1
 */
public record CachedClientModel(@NotNull String brand, short protocol) {
  /**
   * Uses the operator type provided to perform the requested
   * operation with given protocol number.
   *
   * @param operatorType the operator type to use for this
   *                     operation.
   * @param targetedProtocol the needed protocol version number.
   * @return The boolean status for this operation, {@code true}
   *     if the required operation was as expected, e.g.
   *     {@code 758 > 68 = true}. Otherwise {@code false}.
   * @since 0.0.1
   */
  public boolean protocolIs(final @NotNull OperatorType operatorType, final int targetedProtocol) {
    return switch (operatorType) {
      case EQUAL -> this.protocol == targetedProtocol;
      case MINOR -> this.protocol < targetedProtocol;
      case HIGHER -> this.protocol > targetedProtocol;
      case MINOR_OR_EQUAL -> this.protocol <= targetedProtocol;
      case HIGHER_OR_EQUAL -> this.protocol >= targetedProtocol;
      case DIFFERENT -> this.protocol != targetedProtocol;
    };
  }

  public enum OperatorType {
    /**
     * This enum use the equality '==' operator.
     *
     * @since 0.0.1
     */
    EQUAL,
    /**
     * This enum use the relational operator for minor type.
     *
     * @since 0.0.1
     */
    MINOR,
    /**
     * This enum use the relational operator for major type.
     *
     * @since 0.0.1
     */
    HIGHER,
    /**
     * This enum use the relational operator for minor or
     * equal types.
     *
     * @since 0.0.1
     */
    MINOR_OR_EQUAL,
    /**
     * This enum use the relational operator for major or
     * equal types.
     *
     * @since 0.0.1
     */
    HIGHER_OR_EQUAL,
    /**
     * This enum use the equality '!=' operator.
     *
     * @since 0.0.1
     */
    DIFFERENT
  }
}
