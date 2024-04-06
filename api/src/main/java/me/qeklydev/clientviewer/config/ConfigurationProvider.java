package me.qeklydev.clientviewer.config;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

/**
 * This model is used for the configuration models
 * handling.
 *
 * @param config an atomic reference with the config
 *               model.
 * @param loader the configuration loader.
 * @param configClass the config model class.
 * @param <C> the serializable model.
 * @since 0.0.1
 */
public record ConfigurationProvider<C>(@NotNull AtomicReference<@NotNull C> config, @NotNull HoconConfigurationLoader loader,
                                       @NotNull Class<@NotNull C> configClass) {
  /**
   * Reloads the configuration model content.
   *
   * @return The {@link CompletableFuture} with a boolean
   *     status, {@code true} if the operation was successful.
   *     Otherwise {@code false}.
   * @since 0.0.1
   */
  public @NotNull CompletableFuture<@NotNull Boolean> reload() {
    return CompletableFuture.supplyAsync(() -> {
      try {
        final var node = this.loader.load();
        final var newConfig = node.get(this.configClass);
        node.set(this.configClass, newConfig);
        this.loader.save(node);
        this.config.set(newConfig);
        return true;
      } catch (final ConfigurateException exception) {
        exception.printStackTrace();
        return false;
      }
    });
  }

  /**
   * Returns the reference since the {@link AtomicReference}
   * for this provider.
   *
   * @return The config model.
   * @since 0.0.1
   */
  public @NotNull C get() {
    return this.config.get();
  }

  /**
   * Creates a new configuration provider with the information
   * provided.
   *
   * @param directory the directory for the file.
   * @param fileName the name of the file.
   * @param clazz the configuration model.
   * @param <C> the serializable model.
   * @return The {@link ConfigurationProvider} or {@code null}
   *     if an exception is triggered.
   * @since 0.0.1
   */
  public static <C> @Nullable ConfigurationProvider<@NotNull C> of(final @NotNull Path directory, final @NotNull String fileName,
                                                                   final @NotNull Class<C> clazz) {
    final var loader = HoconConfigurationLoader.builder()
        .defaultOptions(opts -> opts
            .header("""
                ClientViewer | Check what clients use thee players to play!
                               This plugin use MiniMessage format for the messages.""")
            .shouldCopyDefaults(true))
        .path(directory.resolve(fileName + ".conf"))
        .build();
    try {
      final var node = loader.load();
      final var config = node.get(clazz);
      node.set(clazz, config);
      loader.save(node);
      return new ConfigurationProvider<>(new AtomicReference<>(config), loader, clazz);
    } catch (final ConfigurateException exception) {
      exception.printStackTrace();
      return null;
    }
  }
}
