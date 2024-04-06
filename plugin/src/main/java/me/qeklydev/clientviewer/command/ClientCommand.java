package me.qeklydev.clientviewer.command;

import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.Optional;
import dev.triumphteam.cmd.core.annotation.Requirement;
import dev.triumphteam.cmd.core.annotation.Requirements;
import me.qeklydev.clientviewer.config.Configuration;
import me.qeklydev.clientviewer.config.ConfigurationProvider;
import me.qeklydev.clientviewer.config.Messages;
import me.qeklydev.clientviewer.registry.PlayableClientRegistry;
import me.qeklydev.clientviewer.utils.ComponentUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ClientCommand extends BaseCommand {
  private final ConfigurationProvider<Configuration> configProvider;
  private final ConfigurationProvider<Messages> messagesProvider;
  private final PlayableClientRegistry clientRegistry;

  public ClientCommand(final @NotNull ConfigurationProvider<@NotNull Configuration> configProvider,
                       final @NotNull ConfigurationProvider<@NotNull Messages> messagesProvider,
                       final @NotNull PlayableClientRegistry clientRegistry) {
    this.configProvider = configProvider;
    this.messagesProvider = messagesProvider;
    this.clientRegistry = clientRegistry;
  }

  @Default
  @Requirements({
      @Requirement("player"),
      @Requirement("client-perm")
  })
  void single(final @NotNull Player player, final @Optional String targetName) {
    final var messages = this.messagesProvider.get();
    final var prefixTag = Placeholder.parsed("prefix", this.configProvider.get().prefix);
    /*
     * Another player have been specified for this
     * command?
     */
    if (targetName == null) {
      /*
       * This player have their client-model available in cache
       * for accessing?
       */
      final var ownClientModel = this.clientRegistry.findOrNull(player.getUniqueId().toString());
      if (ownClientModel == null) {
        player.sendMessage(ComponentUtils.ofSingleWith(messages.nonClientModelInformation, prefixTag));
        return;
      }
      player.sendMessage(ComponentUtils.ofManyWith( // Send to the player their client information.
          messages.ownClientInformation,
          Placeholder.parsed("brand-name", ownClientModel.brand()),
          Placeholder.parsed("protocol-number", Short.toString(ownClientModel.protocol()))));
      return;
    }
    /*
     * We need to check if the specified player is currently
     * connected to the server.
     */
    final var target = Bukkit.getPlayer(targetName);
    if (target == null) {
      player.sendMessage(ComponentUtils.ofSingleWith(messages.offlinePlayer, prefixTag));
      return;
    }
    /*
     * We obtain the client-model for the targeted player, and
     * we check if the model exists.
     */
    final var targetClientModel = this.clientRegistry.findOrNull(target.getUniqueId().toString());
    if (targetClientModel == null) {
      player.sendMessage(ComponentUtils.ofSingleWith(messages.nonClientModelInformation, prefixTag));
      return;
    }
    player.sendMessage(ComponentUtils.ofManyWith(
        messages.thirdPartyClientInformation,
        Placeholder.parsed("player", target.getName()),
        Placeholder.parsed("brand-name", targetClientModel.brand()),
        Placeholder.parsed("protocol-number", Short.toString(targetClientModel.protocol()))));
  }
}
