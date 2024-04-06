package me.qeklydev.clientviewer.command;

import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.bukkit.message.BukkitMessageKey;
import dev.triumphteam.cmd.core.message.MessageKey;
import dev.triumphteam.cmd.core.requirement.RequirementKey;
import me.qeklydev.clientviewer.ClientViewerPlugin;
import me.qeklydev.clientviewer.config.Configuration;
import me.qeklydev.clientviewer.config.ConfigurationProvider;
import me.qeklydev.clientviewer.config.Messages;
import me.qeklydev.clientviewer.registry.PlayableClientRegistry;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.qeklydev.clientviewer.utils.ComponentUtils.ofSingleWith;
import static me.qeklydev.clientviewer.utils.ComponentUtils.ofMany;

public final class CommandManager {
  private final BukkitCommandManager<CommandSender> commandManager;
  private final ConfigurationProvider<Configuration> configProvider;
  private final ConfigurationProvider<Messages> messagesProvider;
  private final PlayableClientRegistry clientRegistry;

  public CommandManager(final @NotNull ClientViewerPlugin plugin, final @NotNull ConfigurationProvider<@NotNull Configuration> configProvider,
                        final @NotNull ConfigurationProvider<@NotNull Messages> messagesProvider, final @NotNull PlayableClientRegistry clientRegistry) {
    this.commandManager = BukkitCommandManager.create(plugin);
    this.configProvider = configProvider;
    this.messagesProvider = messagesProvider;
    this.clientRegistry = clientRegistry;
  }

  private void prepareRequirements() {
    this.commandManager.registerRequirement(RequirementKey.of("player"), sender -> sender instanceof Player);
    this.commandManager.registerRequirement(RequirementKey.of("help-perm"), sender -> sender.hasPermission("clientviewer.help"));
    this.commandManager.registerRequirement(RequirementKey.of("reload-perm"), sender -> sender.hasPermission("clientviewer.reload"));
    this.commandManager.registerRequirement(RequirementKey.of("client-perm"), sender -> sender.hasPermission("clientviewer.usage"));
  }

  private void prepareMessages() {
    final var messages = this.messagesProvider.get();
    final var prefixTag = Placeholder.parsed("prefix", this.configProvider.get().prefix);
    this.commandManager.registerMessage(MessageKey.INVALID_ARGUMENT, (sender, ctx) ->
        sender.sendMessage(ofSingleWith(messages.unknownCommand, prefixTag)));
    this.commandManager.registerMessage(MessageKey.UNKNOWN_COMMAND, (sender, ctx) ->
        sender.sendMessage(ofSingleWith(messages.unknownCommand, prefixTag)));
    this.commandManager.registerMessage(MessageKey.NOT_ENOUGH_ARGUMENTS, (sender, ctx) -> sender.sendMessage(ofMany(messages.help)));
    this.commandManager.registerMessage(MessageKey.TOO_MANY_ARGUMENTS, (sender, ctx) -> sender.sendMessage(ofMany(messages.help)));
    this.commandManager.registerMessage(BukkitMessageKey.PLAYER_ONLY, (sender, ctx) ->
        sender.sendMessage(ofSingleWith(messages.onlyPlayer, prefixTag)));
    this.commandManager.registerMessage(BukkitMessageKey.NO_PERMISSION, (sender, ctx) ->
        sender.sendMessage(ofSingleWith(messages.permission, prefixTag)));
  }

  public void load() {
    this.prepareRequirements();
    this.prepareMessages();
    final var mainCommand = new MainCommand(this.configProvider, this.messagesProvider);
    final var clientCommand = new ClientCommand(this.configProvider, this.messagesProvider, this.clientRegistry);
    this.commandManager.registerCommand(mainCommand, clientCommand);
  }
}
