package Events;

import GenericUtils.RandomUtils;
import RiftEvent2.RiftEvent2;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents implements Listener {
    public PlayerEvents(RiftEvent2 plugin) { Bukkit.getPluginManager().registerEvents(this, plugin); }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        if(!event.getPlayer().getWorld().equals(Bukkit.getWorld(RiftEvent2.getInstance().WorldName))) {
            return;
        }
        RiftEvent2.getInstabilityUtilInstance().bossBar.addPlayer(event.getPlayer());
    }
}
