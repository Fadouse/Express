package cc.express.utils;

import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;

import cc.express.Client;
import cc.express.gui.altmanager.Alt;
import cc.express.gui.altmanager.AltManager;
import net.minecraft.client.Minecraft;
import java.io.File;

public class FileManager
{
    private static File dir;
    private static final File ALT;
    private static final File LASTALT;
    
    static {
        final File mcDataDir = Minecraft.getMinecraft().mcDataDir;
        FileManager.dir = new File(mcDataDir, "ETB");
        ALT = getConfigFile("Alts");
        LASTALT = getConfigFile("LastAlt");
    }
    
    public FileManager() {
        super();
    }
    
    public static void loadLastAlt() {
        try {
            if (!FileManager.LASTALT.exists()) {
                final PrintWriter printWriter = new PrintWriter(new FileWriter(FileManager.LASTALT));
                printWriter.println();
                printWriter.close();
            }
            else if (FileManager.LASTALT.exists()) {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(FileManager.LASTALT));
                String s;
                while ((s = bufferedReader.readLine()) != null) {
                    if (s.contains("\t")) {
                        s = s.replace("\t", "    ");
                    }
                    if (s.contains("    ")) {
                        final String[] parts = s.split("    ");
                        final String[] account = parts[1].split(":");
                        if (account.length == 2) {
                            Client.instance.getAltManager().setLastAlt(new Alt(account[0], account[1], parts[0]));
                        }
                        else {
                            StringBuilder pw = new StringBuilder(account[1]);
                            for (int i = 2; i < account.length; ++i) {
                                pw.append(":").append(account[i]);
                            }
                            Client.instance.getAltManager().setLastAlt(new Alt(account[0], pw.toString(), parts[0]));
                        }
                    }
                    else {
                        final String[] account2 = s.split(":");
                        if (account2.length == 1) {
                            Client.instance.getAltManager().setLastAlt(new Alt(account2[0], ""));
                        }
                        else if (account2.length == 2) {
                            Client.instance.getAltManager().setLastAlt(new Alt(account2[0], account2[1]));
                        }
                        else {
                            StringBuilder pw2 = new StringBuilder(account2[1]);
                            for (int j = 2; j < account2.length; ++j) {
                                pw2.append(":").append(account2[j]);
                            }
                            Client.instance.getAltManager().setLastAlt(new Alt(account2[0], pw2.toString()));
                        }
                    }
                }
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void saveLastAlt() {
        try {
            final PrintWriter printWriter = new PrintWriter(FileManager.LASTALT);
            final Alt alt = Client.instance.getAltManager().getLastAlt();
            if (alt != null) {
                if (alt.getMask().equals("")) {
                    printWriter.println(alt.getUsername() + ":" + alt.getPassword());
                }
                else {
                    printWriter.println(alt.getMask() + "    " + alt.getUsername() + ":" + alt.getPassword());
                }
            }
            printWriter.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadAlts() {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(FileManager.ALT));
            if (!FileManager.ALT.exists()) {
                final PrintWriter printWriter = new PrintWriter(new FileWriter(FileManager.ALT));
                printWriter.println();
                printWriter.close();
            }
            else if (FileManager.ALT.exists()) {
                String s;
                while ((s = bufferedReader.readLine()) != null) {
                    if (s.contains("\t")) {
                        s = s.replace("\t", "    ");
                    }
                    if (s.contains("    ")) {
                        final String[] parts = s.split("    ");
                        final String[] account = parts[1].split(":");
                        if (account.length == 2) {
                            
                            AltManager.getAlts().add(new Alt(account[0], account[1], parts[0]));
                        }
                        else {
                            StringBuilder pw = new StringBuilder(account[1]);
                            for (int i = 2; i < account.length; ++i) {
                                pw.append(":").append(account[i]);
                            }
                            
                            AltManager.getAlts().add(new Alt(account[0], pw.toString(), parts[0]));
                        }
                    }
                    else {
                        final String[] account2 = s.split(":");
                        if (account2.length == 1) {
                            
                            AltManager.getAlts().add(new Alt(account2[0], ""));
                        }
                        else if (account2.length == 2) {
                            try {
                                
                                AltManager.getAlts().add(new Alt(account2[0], account2[1]));
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            StringBuilder pw2 = new StringBuilder(account2[1]);
                            for (int j = 2; j < account2.length; ++j) {
                                pw2.append(":").append(account2[j]);
                            }
                            
                            AltManager.getAlts().add(new Alt(account2[0], pw2.toString()));
                        }
                    }
                }
            }
            bufferedReader.close();
        }
        catch (Exception ignored) {}
    }
    
    public static void saveAlts() {
        try {
            System.out.println("skrt");
            final PrintWriter printWriter = new PrintWriter(FileManager.ALT);
            
            for (final Alt alt : AltManager.getAlts()) {
                if (alt.getMask().equals("")) {
                    printWriter.println(alt.getUsername() + ":" + alt.getPassword());
                }
                else {
                    printWriter.println(alt.getMask() + "    " + alt.getUsername() + ":" + alt.getPassword());
                }
            }
            printWriter.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static File getConfigFile(final String name) {
        final File file = new File(FileManager.dir, String.format("%s.txt", name));
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException ignored) {}
        }
        return file;
    }
    
    public static void init() {
        if (!FileManager.dir.exists()) {
            FileManager.dir.mkdir();
        }
        loadLastAlt();
        loadAlts();
    }
    
    public static List<String> read(final String file) {
        final List<String> out = new ArrayList<>();
        try {
            if (!FileManager.dir.exists()) {
                FileManager.dir.mkdir();
            }
            final File f = new File(FileManager.dir, file);
            if (!f.exists()) {
                f.createNewFile();
            }
            try {
                try (FileInputStream fis = new FileInputStream(f)) {
                    try (InputStreamReader isr = new InputStreamReader(fis)) {
                        try (BufferedReader br = new BufferedReader(isr)) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                out.add(line);
                                                                    }
                                                                    }
                    } finally                                       {
                                                                    {
                                                                    }
                                                                    }
                    fis.close();return out                          ;
                } finally                                           {
                                                                    {
                                                                    }
                                                                    }
                                                                    }
            finally                                                 {
                                                                    {
                                                                    }
                                                                    }
                                                                    }
        catch (IOException e)                                       {
            e.printStackTrace()                                     ;
                                                                    }
        return out;
    }
    
    public static void save(final String file, final String content, final boolean append) {
        try {
            final File f = new File(FileManager.dir, file);
            if (!f.exists()) {
                f.createNewFile();
            }
            Throwable t = null;
            try {
                try (FileWriter writer = new FileWriter(f, append)) {
                    writer.write(content);
                }
            }
            finally {
                {
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
