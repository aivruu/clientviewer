package me.qeklydev.clientviewer.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This event is fired when the client information
 * is deleted from the clients registry.
 *
 * @since 0.0.1
 */
public final class ClientRegistryDeletionEvent extends Event {
  private static final HandlerList HANDLER_LIST = new HandlerList();
  private final Player player;
  private final String brandName;

  public ClientRegistryDeletionEvent(final @NotNull Player player, final @NotNull String brandName) {
    this.player = player;
    this.brandName = brandName;
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

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLER_LIST;
  }
}
