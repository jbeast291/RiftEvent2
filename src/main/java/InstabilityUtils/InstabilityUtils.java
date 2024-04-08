package InstabilityUtils;

import RiftEvent2.RiftEvent2;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class InstabilityUtils {
    public BossBar bossBar;
    int currentInstabilityPercent = RiftEvent2.getInstance().percentInstab;
    public void instabilityTicker(){
        try {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    if(currentInstabilityPercent != 100){
                        currentInstabilityPercent++;

                        bossBar.setProgress((double) currentInstabilityPercent / 100);
                        bossBar.setTitle("§4⚠§d Instability: §b%" + currentInstabilityPercent + " §4⚠");

                        //Bukkit.getLogger().info("[RiftEvent] instab:" + currentInstabilityPercent);
                        if(currentInstabilityPercent == 50){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 50%");
                        }
                        if(currentInstabilityPercent == 60){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 60%");
                        }
                        instabilityTicker();

                    }
                    else {
                        Bukkit.getLogger().info("[RiftEvent] DONE:" + currentInstabilityPercent);
                    }
                }
            }.runTaskLater(RiftEvent2.getInstance(), ((long) RiftEvent2.TimeRiftOpenMin * 60 * 20)/ 100);
        } catch (UnsupportedOperationException e) {
            // Log a warning message
            Bukkit.getLogger().warning("[RiftEvent] Failed to schedule Instability Ticker: " + e.getMessage());
        }
    }
    public void setupBossBar(){
        bossBar = RiftEvent2.getInstance().getServer().createBossBar("§4⚠§5 Instability: % " + currentInstabilityPercent + " §4⚠" , BarColor.PURPLE, BarStyle.SEGMENTED_6);
        bossBar.setVisible(true);
    }
    public void makeSafePlatform(Location startloc){
        Material platformMaterial = Material.OBSIDIAN;

        Material netherPlatformMaterial = Material.COBBLESTONE;
        GenericUtils.RegionUtils.fillRegionBetweenPoints(startloc.getBlockX() - 3, startloc.getBlockY() - 1, startloc.getBlockZ() - 3,
                startloc.getBlockX() + 3, startloc.getBlockY() + 2, startloc.getBlockZ() + 3,
                startloc.getWorld(), Material.AIR);


        if (!RiftEvent2.getInstance().isNether){
            GenericUtils.RegionUtils.fillRegionBetweenPoints(startloc.getBlockX() - 3, startloc.getBlockY() - 1, startloc.getBlockZ() - 3,
                    startloc.getBlockX() + 3, startloc.getBlockY() - 1, startloc.getBlockZ() + 3,
                    startloc.getWorld(), platformMaterial);
        }
        else {
            GenericUtils.RegionUtils.fillRegionBetweenPoints(startloc.getBlockX() - 3, startloc.getBlockY() - 1, startloc.getBlockZ() - 3,
                    startloc.getBlockX() + 3, startloc.getBlockY() - 4, startloc.getBlockZ() + 3,
                    startloc.getWorld(), netherPlatformMaterial);
        }

        //remove water/lava if around platform
        GenericUtils.RegionUtils.ReplaceBlockInRegionBetweenPoints(startloc.getBlockX() - 4, startloc.getBlockY() - 2, startloc.getBlockZ() - 4,
                startloc.getBlockX() + 4, startloc.getBlockY() + 3, startloc.getBlockZ() + 4,
                startloc.getWorld(), Material.LAVA, Material.AIR);
        GenericUtils.RegionUtils.ReplaceBlockInRegionBetweenPoints(startloc.getBlockX() - 4, startloc.getBlockY() - 2, startloc.getBlockZ() - 4,
                startloc.getBlockX() + 4, startloc.getBlockY() + 3, startloc.getBlockZ() + 4,
                startloc.getWorld(), Material.WATER, Material.AIR);
    }

    public void BroadcastAllInRift(String message) {
        World Riftworld = Bukkit.getWorld(RiftEvent2.getInstance().WorldName);
        Riftworld.getPlayers().forEach(player -> {
            player.sendMessage(message);
        });
    }
    public void startRandomMinorEvent() {

    }
    public void StartRandomMajorEvent() {

    }
}
