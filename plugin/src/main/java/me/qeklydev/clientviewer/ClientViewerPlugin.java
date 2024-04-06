package me.qeklydev.clientviewer;

import me.qeklydev.clientviewer.command.CommandManager;
import me.qeklydev.clientviewer.config.Configuration;
import me.qeklydev.clientviewer.config.ConfigurationProvider;
import me.qeklydev.clientviewer.config.Messages;
import me.qeklydev.clientviewer.listener.ClientInformationHandleListener;
import me.qeklydev.clientviewer.registry.PlayableClientRegistry;
import me.qeklydev.clientviewer.utils.ComponentUtils;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class ClientViewerPlugin extends JavaPlugin {
  private ComponentLogger logger;
  private ConfigurationProvider<Configuration> configProvider;
  private ConfigurationProvider<Messages> messagesProvider;
  private PlayableClientRegistry clientRegistry;
  private CommandManager commandManager;

  @Override
  public void onLoad() {
    final var pluginDirectory = super.getDataFolder().toPath();
    this.logger = super.getComponentLogger();
    this.configProvider = ConfigurationProvider.of(pluginDirectory, "config", Configuration.class);
    this.messagesProvider = ConfigurationProvider.of(pluginDirectory, "messages", Messages.class);
    this.clientRegistry = new PlayableClientRegistry();
  }

  @Override
  public void onEnable() {
    if (this.configProvider == null || this.messagesProvider == null) {
      this.logger.error(ComponentUtils.ofSingle("<red>The configuration could not be loaded correctly!"));
      return;
    }
    this.commandManager = new CommandManager(this, this.configProvider, this.messagesProvider, this.clientRegistry);
    this.commandManager.load();
    this.logger.info(ComponentUtils.ofSingle("<yellow>Plugin commands registered."));
    super.getServer().getPluginManager().registerEvents(
        new ClientInformationHandleListener(this.configProvider, this.messagesProvider, this.clientRegistry),
        this);
    this.logger.info(ComponentUtils.ofSingle("<yellow>Clients information/registry listener registered."));
    this.logger.info(ComponentUtils.ofMany(List.of(
        "<green>The plugin have been enabled! You're using the version <yellow>" + Constants.VERSION,
        "<gradient:blue:aqua>Developed by Qekly, running on Paper software.")));
  }

  @Override
  public void onDisable() {
    this.logger.info(ComponentUtils.ofMany(List.of(
        "<green>The plugin is being disabled, thanks for using!",
        "<gradient:blue:aqua>Developed by Qekly, designed for the Paper software.")));
    if (this.clientRegistry != null) {
      this.clientRegistry.clearRegistry();
    }
  }
}
