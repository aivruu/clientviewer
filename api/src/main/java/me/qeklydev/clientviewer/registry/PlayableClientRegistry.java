package me.qeklydev.clientviewer.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import me.qeklydev.clientviewer.client.CachedClientModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class is used for have a registry of
 * the clients types that are being used on
 * this server.
 *
 * @since 0.0.1
 */
public final class PlayableClientRegistry {
  private final Map<String, CachedClientModel> recognizedClients;

  public PlayableClientRegistry() {
    this.recognizedClients = new HashMap<>();
  }

  /**
   * Returns a collection with all the client models
   * actually in cache.
   *
   * @return A {@link Collection} of {@link CachedClientModel}.
   * @since 0.0.1
   */
  public @NotNull Collection<CachedClientModel> findAll() {
    return this.recognizedClients.values();
  }

  /**
   * Returns, or not, the client model based on the
   * key provided.
   *
   * @param id the player uuid.
   * @return The {@link CachedClientModel} or {@code null}
   *     if the key isn't cached.
   * @since 0.0.1
   */
  public @Nullable CachedClientModel findOrNull(final @NotNull String id) {
    return this.recognizedClients.get(id);
  }

  /**
   * Registers a new client model for the key provided
   * with the given information.
   *
   * @param id the player uuid.
   * @param clientBrand the player client brand.
   * @param protocol the protocol number for the client
   *                 version.
   * @since 0.0.1
   */
  public void register(final @NotNull String id, final @NotNull String clientBrand, final short protocol) {
    final var clientModel = new CachedClientModel(clientBrand, protocol);
    this.recognizedClients.put(id, clientModel);
  }

  /**
   * Removes the client model from cache for the specified
   * key, if key doesn't exist, will do nothing.
   *
   * @param id the player uuid.
   * @since 0.0.1
   */
  public void remove(final @NotNull String id) {
    this.recognizedClients.remove(id);
  }

  public void clearRegistry() {
    this.recognizedClients.clear();
  }
}
