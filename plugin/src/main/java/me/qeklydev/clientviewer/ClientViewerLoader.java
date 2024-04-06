package me.qeklydev.clientviewer;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public final class ClientViewerLoader implements PluginLoader {
  @Override
  public void classloader(final @NotNull PluginClasspathBuilder classpathBuilder) {
    final var libraryResolver = new MavenLibraryResolver();
    libraryResolver.addRepository(new RemoteRepository.Builder("triumphteam", "default", "https://repo.triumphteam.dev/snapshots/").build());
    libraryResolver.addRepository(new RemoteRepository.Builder("central", "default", "https://repo1.maven.org/maven2/").build());
    libraryResolver.addDependency(new Dependency(new DefaultArtifact("org.spongepowered:configurate-hocon:4.1.2"), null));
    libraryResolver.addDependency(new Dependency(new DefaultArtifact("dev.triumphteam:triumph-cmd-bukkit:2.0.0-SNAPSHOT"), null));
    classpathBuilder.addLibrary(libraryResolver);
  }
}
