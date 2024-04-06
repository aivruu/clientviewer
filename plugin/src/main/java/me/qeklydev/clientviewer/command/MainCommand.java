package me.qeklydev.clientviewer.command;

import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.Requirement;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import java.util.List;
import me.qeklydev.clientviewer.config.Configuration;
import me.qeklydev.clientviewer.config.ConfigurationProvider;
import me.qeklydev.clientviewer.config.Messages;
import me.qeklydev.clientviewer.utils.ComponentUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class MainCommand extends BaseCommand {
  private final ConfigurationProvider<Configuration> configProvider;
  private final ConfigurationProvider<Messages> messagesProvider;

  public MainCommand(final @NotNull ConfigurationProvider<@NotNull Configuration> configProvider,
                     final @NotNull ConfigurationProvider<@NotNull Messages> messagesProvider) {
    super("clientviewer", List.of("cv"));
    this.configProvider = configProvider;
    this.messagesProvider = messagesProvider;
  }

  @Default
  void withoutArguments(final @NotNull CommandSender sender) {
    sender.sendMessage(ComponentUtils.ofSingle("<gradient:aqua:green>ClientViewer</gradient> <gray>> <green>Thanks for using my plugin! Use <yellow>/cv help</yellow>to see commands."));
  }

  @SubCommand("help")
  @Requirement("help-perm")
  void withHelpType(final @NotNull CommandSender sender) {
    sender.sendMessage(ComponentUtils.ofMany(this.messagesProvider.get().help));
  }

  @SubCommand("reload")
  @Requirement("reload-perm")
  void withReloadType(final @NotNull CommandSender sender) {
    final var start = System.currentTimeMillis();
    final var messages = this.messagesProvider.get();
    final var prefixTag = Placeholder.parsed("prefix", this.configProvider.get().prefix);
    /*
     * Reloads the configuration and messages models and send operation
     * information to the sender.
     */
    this.configProvider.reload()
        .thenCombine(this.messagesProvider.reload(), (configSuccess, messagesSuccess) ->
            configSuccess && messagesSuccess)
        .thenAccept(success -> {
          if (success) {
            sender.sendMessage(ComponentUtils.ofSingleWith(
                messages.reloadSuccess,
                prefixTag,
                Placeholder.parsed("time", Long.toString(System.currentTimeMillis() - start))));
            return;
          }
          sender.sendMessage(ComponentUtils.ofSingleWith(messages.reloadFailed, prefixTag));
        });
  }
}
