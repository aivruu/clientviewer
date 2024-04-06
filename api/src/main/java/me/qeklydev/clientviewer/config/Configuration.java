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
