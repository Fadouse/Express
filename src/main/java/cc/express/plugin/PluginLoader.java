package cc.express.plugin;

import cc.express.plugin.classloading.IPlugin;
import cc.express.plugin.utils.DescriptionFileExtractor;
import cc.express.plugin.utils.PluginException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

public class PluginLoader {
	private final HashMap<File, IPlugin> map = new HashMap<>();
	public IPlugin load(File file) {
		if (!(file.getName().endsWith(".jar"))) throw new PluginException("File have to be a Jar! " + file.getName());
		try {
			if(map.containsKey(file)) {
				throw new PluginException(file.getName() + " " + "Plugin already loaded.");
			}
			PluginDescription pluginDescriptionFile = new DescriptionFileExtractor(file).getObject();
			ClassLoader loader = URLClassLoader.newInstance( new URL[] { file.toURI().toURL() }, getClass().getClassLoader() );
			Class<?> clazz = Class.forName(pluginDescriptionFile.getMain(), true, loader);
			Class<? extends IPlugin> instanceClass = clazz.asSubclass(IPlugin.class);
			Constructor<? extends IPlugin> instanceClassConstructor = instanceClass.getConstructor();
			IPlugin IPlugin = instanceClassConstructor.newInstance();
			IPlugin.setDescriptionFile(pluginDescriptionFile);
			map.put(file, IPlugin);
			IPlugin.onLoad();
			return IPlugin;
		}
		catch(MalformedURLException e) {
			throw new PluginException("Failed to convert the file path to a URL.", e);
		}
		catch(ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new PluginException("Failed to create a new instance of the me.plugin.", e);
		}
	}

	public IPlugin update(File file) {
		if (!(file.getName().endsWith(".jar"))) throw new PluginException("File have to be a Jar! " + file.getName());
		try {
			if(!map.containsKey(file)) {
				throw new PluginException(file.getName() + " " + "dev.tarico.api.pluginapi.Plugin isn´t loaded!");
			}
			PluginDescription pluginDescriptionFile = new DescriptionFileExtractor(file).getObject();
			ClassLoader loader = URLClassLoader.newInstance( new URL[] { file.toURI().toURL() }, getClass().getClassLoader() );
			Class<?> clazz = Class.forName(pluginDescriptionFile.getMain(), true, loader);
			Class<? extends IPlugin> instanceClass = clazz.asSubclass(IPlugin.class);
			Constructor<? extends IPlugin> instanceClassConstructor = instanceClass.getConstructor();
			IPlugin IPlugin = instanceClassConstructor.newInstance();
			IPlugin.setDescriptionFile(pluginDescriptionFile);
			return IPlugin;
		}
		catch(MalformedURLException e) {
			throw new PluginException("Failed to convert the file path to a URL.", e);
		}
		catch(ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new PluginException("Failed to create a new instance of the me.plugin.", e);
		}
	}

	public IPlugin loadFromURL(final String url, final File filePath) {
		InputStream in;
		try {
			in = new URL(url).openStream();
			Files.copy(in, Paths.get(filePath.getPath()), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		File file = Paths.get(filePath.getPath()).toFile();
		if (!(file.getName().endsWith(".jar"))) throw new PluginException("File have to be a Jar! " + file.getName());
		try {
			if(map.containsKey(file)) {
				throw new PluginException(file.getName() + " " + "dev.tarico.api.pluginapi.Plugin already loaded.");
			}
			PluginDescription pluginDescriptionFile = new DescriptionFileExtractor(file).getObject();
			ClassLoader loader = URLClassLoader.newInstance( new URL[] { file.toURI().toURL() }, getClass().getClassLoader() );
			Class<?> clazz = Class.forName(pluginDescriptionFile.getMain(), true, loader);
			Class<? extends IPlugin> instanceClass = clazz.asSubclass(IPlugin.class);
			Constructor<? extends IPlugin> instanceClassConstructor = instanceClass.getConstructor();
			IPlugin IPlugin = instanceClassConstructor.newInstance();
			IPlugin.setDescriptionFile(pluginDescriptionFile);
			map.put(file, IPlugin);
			IPlugin.onLoad();
			return IPlugin;
		}
		catch(MalformedURLException e) {
			throw new PluginException("Failed to convert the file path to a URL.", e);
		}
		catch(ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new PluginException("Failed to create a new instance of the me.plugin.", e);
		}
	}

	public IPlugin unload(File file) {
		if (!(file.getName().endsWith(".jar"))) throw new PluginException("File have to be a Jar! " + file.getName());
		if(!map.containsKey(file)) {
			throw new PluginException("Can't unload a Plugin that wasn't loaded in the first place.");
		}
		IPlugin IPlugin = map.get(file);
		IPlugin.onUnload();
		map.remove(file);
		return IPlugin;
	}

	public void reload(File file) {
		unload(file);
		load(file);
	}
}
