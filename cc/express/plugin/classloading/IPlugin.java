package cc.express.plugin.classloading;

import cc.express.plugin.PluginDescription;

/**
 * 插件Plugin接口，插件主类
 */
public interface IPlugin {

	ThreadLocal<PluginDescription> descriptionFile = new ThreadLocal<>();
	/**
	 * onLoad方法，加载时调用
	 */
	abstract void onLoad();

	/**
	 * unLoad方法，卸载时调用
	 */
	abstract void onUnload();


	default void setDescriptionFile(PluginDescription descriptionFile) {
		this.descriptionFile.set(descriptionFile);
	}

	default PluginDescription getDescriptionFile() {
		return descriptionFile.get();
	}

}