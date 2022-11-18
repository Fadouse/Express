package cc.express.plugin;


import java.io.File;
import java.util.Objects;
import java.util.function.Consumer;

public class PluginAPI {

    private static PluginLoader loader;
    private String outName;

    public static PluginAPI getApi(Consumer<PluginLoader> consumer, String outName) {
        consumer.accept(loader = new PluginLoader());
        PluginAPI api = new PluginAPI(loader);
        api.outName = outName;
        return api;
    }

    public PluginLoader getLoader() {
        return loader;
    }

    private PluginAPI(PluginLoader loader) {
        PluginAPI.loader = loader;
    }

    public void loadAll(File pluginFolder) {
        try {
        if(!pluginFolder.exists())
            pluginFolder.mkdir();

        for (File fileIndex : Objects.requireNonNull(pluginFolder.listFiles())) {
                if (fileIndex.getName().endsWith(".jar")) {
                    loader.load(fileIndex);
                    System.out.println(outName + " " + "Loaded: " + fileIndex.getName());
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void loadAllThread( File pluginFolder) {
        Thread loadThread = new Thread("loading Thread") {
            @Override
            public void run() {
                try {
                    for (File fileIndex : Objects.requireNonNull(pluginFolder.listFiles())) {
                        if (fileIndex.getName().endsWith(".jar")) {
                            loader.load(fileIndex);
                            System.out.println(outName + " " + "Loaded: " + fileIndex.getName());
                        }
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        };
        loadThread.start();
    }

    public void unloadAll(File pluginFolder) {
        try {
            for (File fileIndex : Objects.requireNonNull(pluginFolder.listFiles())) {
                loader.unload(fileIndex);
                System.out.println(outName + " " + "Unloaded: " + fileIndex.getName());
            }
        }catch (NullPointerException e){e.printStackTrace();}
    }

    public void reloadAll( File pluginFolder) {
        Thread loadThread = new Thread("reloading Thread") {
            @Override
            public void run() {
                try {
                    for (File fileIndex : Objects.requireNonNull(pluginFolder.listFiles())) {
                        if (fileIndex.getName().endsWith(".jar")) {
                            loader.reload(fileIndex);
                            System.out.println(outName + " " + "Reloaded: " + fileIndex.getName());
                        }
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        };
        loadThread.start();
    }

}
