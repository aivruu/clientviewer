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
package me.qeklydev.clientviewer;

import java.util.List;
import me.qeklydev.clientviewer.command.CommandManager;
import me.qeklydev.clientviewer.config.Configuration;
import me.qeklydev.clientviewer.config.ConfigurationProvider;
import me.qeklydev.clientviewer.config.Messages;
import me.qeklydev.clientviewer.listener.ClientInformationHandleListener;
import me.qeklydev.clientviewer.registry.PlayableClientRegistry;
import me.qeklydev.clientviewer.utils.ComponentUtils;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.plugin.java.JavaPlugin;

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
