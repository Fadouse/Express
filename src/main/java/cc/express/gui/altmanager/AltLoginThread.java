
package cc.express.gui.altmanager;

import cc.express.Client;
import cc.express.utils.FileManager;
import me.ratsiel.auth.abstracts.exception.AuthenticationException;
import me.ratsiel.auth.model.mojang.MinecraftAuthenticator;
import me.ratsiel.auth.model.mojang.MinecraftToken;
import me.ratsiel.auth.model.mojang.profile.MinecraftProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class AltLoginThread
extends Thread {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final String password;
    private String status;
    private final String username;

    public AltLoginThread(String username, String password) {
        super("Alt Login Thread");
        this.username = username;
        this.password = password;
        this.status = "\u00a7eWaiting...";
    }

    private final Session createSession(String username, String password) {
        try {
            MinecraftAuthenticator minecraftAuthenticator = new MinecraftAuthenticator();
            MinecraftToken minecraftToken = minecraftAuthenticator.loginWithXbox(username, password);
            MinecraftProfile minecraftProfile = minecraftAuthenticator.checkOwnership(minecraftToken);
            return new Session(minecraftProfile.getUsername(), minecraftProfile.getUuid().toString(), minecraftToken.getAccessToken(), "mojang");
        }
        catch (AuthenticationException authenticationException) {
            return null;
        }
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public void run() {
        if (this.password.equals("")) {
            this.mc.session = new Session(this.username, "", "", "mojang");
            this.status = "\u00a7aLogged in. (" + this.username + " - offline name)";
            return;
        }
        this.status = "\u00a7eLogging in...";
        Session auth = this.createSession(this.username, this.password);
        if (auth == null) {
            this.status = "\u00a7cLogin failed!";
        } else {
            Client.instance.getAltManager().setLastAlt(new Alt(this.username, this.password));
            FileManager.saveLastAlt();
            this.status = "\u00a7aLogged in. (" + auth.getUsername() + ")";
            this.mc.session = auth;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

