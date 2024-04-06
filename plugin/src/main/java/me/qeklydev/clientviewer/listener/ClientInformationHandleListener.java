package me.qeklydev.clientviewer.listener;

import me.qeklydev.clientviewer.config.Configuration;
import me.qeklydev.clientviewer.config.ConfigurationProvider;
import me.qeklydev.clientviewer.config.Messages;
import me.qeklydev.clientviewer.registry.PlayableClientRegistry;
import me.qeklydev.clientviewer.utils.ComponentUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public final class ClientInformationHandleListener implements Listener {
  private final ConfigurationProvider<Configuration> configProvider;
  private final ConfigurationProvider<Messages> messagesProvider;
  private final PlayableClientRegistry clientRegistry;

  public ClientInformationHandleListener(final @NotNull ConfigurationProvider<@NotNull Configuration> configProvider,
                                         final @NotNull ConfigurationProvider<@NotNull Messages> messagesProvider,
                                         final @NotNull PlayableClientRegistry clientRegistry) {
    this.configProvider = configProvider;
    this.messagesProvider = messagesProvider;
    this.clientRegistry = clientRegistry;
  }

  @EventHandler
  void onPreLogin(final @NotNull PlayerLoginEvent event) {
    /*
     * Another plugin has listened and interacted with this
     * event early?
     */
    if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
      return;
    }
    final var config = configProvider.get();
    final var player = event.getPlayer();
    final var playerClientBrand = player.getClientBrandName();
    /*
     * Check if non-null-client-brand option is enabled, and
     * check if the player client brand is null.
     */
    if (config.denyClientBrandNull && playerClientBrand == null) {
      /*
       * The player can't join to the server using a client
       * that provide a null brand name.
       */
      event.disallow(
          PlayerLoginEvent.Result.KICK_OTHER,
          ComponentUtils.ofMany(this.messagesProvider.get().clientBrandUnknown));
      return;
    }
    assert playerClientBrand != null; // Client brand is non-null.
    if (!config.blockClients) {
      return;
    }
    /*
     * Iterate over the blocked-clients list
     * and check if player client brand is.
     */
    final var clients = config.blockedClients;
    for (final var client : clients) {
      /*
       * Check if client brand is on blocked-clients list
       * and removing additional spaces.
       */
      if (playerClientBrand.equals(client.trim())) {
        /*
         * The player can't join to the server using
         * this client.
         */
        event.disallow(
            PlayerLoginEvent.Result.KICK_OTHER,
            ComponentUtils.ofMany(this.messagesProvider.get().clientBrandDisallowed));
      }
    }
    /*
     * The client brand is non-null, and also, isn't blocked.
     * So, we register it into the clients registry for manipulate
     * that information later as required.
     */
    this.clientRegistry.register(player.getUniqueId().toString(), playerClientBrand, (short) player.getProtocolVersion());
  }

  @EventHandler
  void onQuit(final @NotNull PlayerQuitEvent event) {
    final var player = event.getPlayer();
    final var playerUid = player.getUniqueId().toString();
    /*
     * Remove cached client information for this player
     * to avoid memory leaks.
     */
    this.clientRegistry.remove(playerUid);
  }
}
