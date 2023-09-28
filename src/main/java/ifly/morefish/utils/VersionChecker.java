package ifly.morefish.utils;

import ifly.morefish.fishpack.Config;
import ifly.morefish.main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class VersionChecker implements Listener {

    String reqversion;
    Plugin plugin;
    int pluginid;

    public VersionChecker(Plugin plugin, int pluginid) {
        this.plugin = plugin;
        this.pluginid = pluginid;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.spigotmc.org/legacy/update.php?resource=" + pluginid))
                .build();

        try {
            HttpResponse version = client.send(request, HttpResponse.BodyHandlers.ofString());
            setReqversion((String) version.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getReqversion() {
        return reqversion;
    }

    public void setReqversion(String reqversion) {
        this.reqversion = reqversion;
    }

    public boolean isUpgrade() {
        return !reqversion.equalsIgnoreCase(main.mainPlugin.getDescription().getVersion());
    }

    @EventHandler
    public void joinAdmin(PlayerJoinEvent e) {
        if (e.getPlayer().hasPermission("*")) {
            e.getPlayer().sendMessage(Config.getMessage("A new plugin update is available. Download link: https://www.spigotmc.org/resources/" + getPluginid() + "/"));
        }
    }

    public int getPluginid() {
        return pluginid;
    }

}
