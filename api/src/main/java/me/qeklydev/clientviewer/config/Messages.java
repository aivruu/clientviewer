package me.qeklydev.clientviewer.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;

@ConfigSerializable
public final class Messages implements UsableConfiguration {
  public String onlyPlayer = "<prefix> <red>Only players can execute this command!";
  public String permission = "<prefix> <red>You don't have permission for this!";

  public String unknownCommand = "<prefix> <red>That sub-command doesn't exist!";

  public List<String> help = List.of(
      "<gradient:aqua:green>[⭐] ClientViewer [⭐] Available Commands List ->",
      "<gray> • <green><hover:show_text:''><click:run_command:'/cv help'>/clientviewer help</click></hover> <gray>Shows this messages.",
      "<gray> • <green><hover:show_text:''><click:run_command:'/cv reload'>/clientviewer reload</click></hover> <gray>Reloads the plugin configuration.",
      "<gray> • <green><hover:show_text:''><click:run_command:'/client'>/client [player]</click></hover> <gray>Shows your client information, or for another player.");

  public String reloadSuccess = "<prefix> The plugin configurations have been reloaded successful.";

  public String reloadFailed = "<prefix> <red>Something went wrong during reloading process, check the console.";

  public String nonClientModelInformation = "<prefix> <red>A client-model with that information was not founded.";

  public String offlinePlayer = "<prefix> <red>The specified player is not connected now.";

  public List<String> ownClientInformation = List.of(
      "<dark_gray>[<yellow>⭐</yellow>] <gray>Showing your client information",
      "<green> • <white>Brand name: <light_purple><brand-name>",
      "<green> • <white>Protocol Version Number: <light_purple><protocol-number>");

  public List<String> thirdPartyClientInformation = List.of(
      "<dark_gray>[<yellow>⭐</yellow>]  <gray>Showing client information of <yellow><player>",
      "<green> • <white>Brand name: <light_purple><brand-name>",
      "<green> • <white>Protocol Version Number: <light_purple><protocol-number>");

  public List<String> clientBrandUnknown = List.of(
      "<dark_gray>[<red>[❌]</red>] <red>The brand name provided by your client is null/unknown. <dark_gray>[<red>[❌]</red>]",
      "<gradient:red:gold>Please try again, or use another client to join!");

  public List<String> clientBrandDisallowed = List.of(
      "<dark_gray>[<red>[❌]</red>] <red>The client you're using has been blocked by the administrators. <dark_gray>[<red>[❌]</red>]",
      "<gradient:blue:red>Use another client to play on the server!");
}
