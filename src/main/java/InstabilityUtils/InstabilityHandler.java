package InstabilityUtils;

import GenericUtils.RandomUtils;
import InstabilityUtils.InstabilityEvent.AnimalEvent;
import RiftEvent2.RiftEvent2;
import WorldUtils.WorldUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class InstabilityHandler {
    public BossBar bossBar;
    int currentInstabilityPercent = RiftEvent2.getInstance().percentInstab;
    //    THESE ARE NOT FINAL ONLY WIP IDEA
    //50% one-off minor event
    //60% two-off minor event
    //70% 2 Minor Event Loop
    //80% 2 one-off major event, one Major Event loop
    //90%
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
                        if(currentInstabilityPercent == 10){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 10%");
                            RandomMinorEvent();
                        }
                        if(currentInstabilityPercent == 20){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 20%");
                            RandomMinorEvent();
                        }
                        if(currentInstabilityPercent == 30){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 30%");
                            RandomMinorEvent();
                        }
                        if(currentInstabilityPercent == 40){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 40%");
                            RandomMinorEvent();
                        }
                        if(currentInstabilityPercent == 50){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 50%");
                            LoopedRandomMinorEvent();
                            RandomMinorEvent();
                        }
                        if(currentInstabilityPercent == 60){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 60%");
                            LoopedRandomMinorEvent();
                        }
                        if(currentInstabilityPercent == 70){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 70%");
                            LoopedRandomMinorEvent();
                        }
                        if(currentInstabilityPercent == 80){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 80%");
                            LoopedRandomMajorEvent();
                        }
                        if(currentInstabilityPercent == 90){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 90%");
                            LoopedRandomMinorEvent();
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

    //single effect, cave noise, chat
    public void RandomMinorEvent() {
         int rand = RandomUtils.Randomint(3, 1);
         if (rand == 1) {
             RiftEvent2.getSoundEventInstance().PlayCaveSoundOnce();
         }
         if (rand == 2) {
            RiftEvent2.getAnimalEventInstance().AnimalSpawner();
         }
         if (rand == 3) {
             RiftEvent2.getEffectEventInstance().GiveRandomEffectToPlayers(500, 2);
         }

    }
    //Npc spawn, inventory fill, particle event
    public void RandomMajorEvent() {
        int rand = RandomUtils.Randomint(1, 1);
        if (rand == 1) {
            RiftEvent2.getNpcHandlerInstance().PlaceNpcsAroundPlayer();
        }
        if (rand == 2) {
            RiftEvent2.getChunkEventInstance().replaceAllAroundPlayersOnce();
        }
    }


    Boolean isSoundEventloopActive = false;
    Boolean isAnimalEventloopActive = false;
    //Each cannot be activated twice
    //Sky/weather changing constantly, Sound looper, inventory spam loop, animal spawn loop
    public void LoopedRandomMinorEvent() {
        int rand = RandomUtils.Randomint(2, 1);
        if (rand == 1 && !isSoundEventloopActive) {
            Bukkit.getLogger().info("Sound Looper Active");
            RiftEvent2.getSoundEventInstance().SoundLooper();
        }
        if (rand == 2 && !isAnimalEventloopActive) {
            Bukkit.getLogger().info("Animal Looper Active");
            RiftEvent2.getAnimalEventInstance().AnimalSpawnerLoop();
        }
        /*while(true) {
            if(isAnimalEventloopActive && isSoundEventloopActive){
                Bukkit.getLogger().info("Couldnt start minor event");
                break;
            }
            int rand = RandomUtils.Randomint(2, 1);
            if (rand == 1 && !isSoundEventloopActive) {
                Bukkit.getLogger().info("Sound Looper Active");
                RiftEvent2.getSoundEventInstance().SoundLooper();
                break;
            }
            if (rand == 2 && !isAnimalEventloopActive) {
                Bukkit.getLogger().info("Animal Looper Active");
                RiftEvent2.getAnimalEventInstance().AnimalSpawnerLoop();
                break;
            }

        }*/
    }


    Boolean isChunkEventloopActive = false;
    Boolean isBlockLoopActive = false;
    //Each cannot be activated twice
    //Chat Spam, Effect Loop, lighting spam, break block, chunk glitch
    public void LoopedRandomMajorEvent() {
        int rand = RandomUtils.Randomint(2, 1);
        if (rand == 1 && !isChunkEventloopActive) {
            Bukkit.getLogger().info("Chunk Looper Active");
            RiftEvent2.getChunkEventInstance().ChunkLooper();
        }
        if (rand == 2 && !isBlockLoopActive) {
            Bukkit.getLogger().info("Block Looper Active");
            RiftEvent2.getBlockEventInstance().EnableBlockEvent();
        }
        /*while(true) {
            if(isChunkEventloopActive && isBlockLoopActive){
                Bukkit.getLogger().info("Couldnt start major event");
                break;
            }
            int rand = RandomUtils.Randomint(2, 1);
            if (rand == 1 && !isChunkEventloopActive) {
                Bukkit.getLogger().info("Chunk Looper Active");
                RiftEvent2.getChunkEventInstance().ChunkLooper();
                break;
            }
            if (rand == 2 && !isBlockLoopActive) {
                Bukkit.getLogger().info("Block Looper Active");
                RiftEvent2.getBlockEventInstance().EnableBlockEvent();
                break;
            }

        }*/
    }

    public void SafelyCloseRift(Boolean reopenSafleyAfter) {
        //set game to disabling
        RiftEvent2.getInstance().gameState = 0;
        //send players back to regular world
        Bukkit.getWorld(RiftEvent2.getInstance().WorldName).getPlayers().forEach( player -> {
            bossBar.removePlayer(player);
            if(player.getRespawnLocation() != null) {
                player.teleport(player.getRespawnLocation());
            }
            else {
                FileConfiguration config = RiftEvent2.getInstance().getConfig();
                Location spawnLocation = new Location(Bukkit.getWorld(
                        config.getString("OverWorld-position.spawn-world")),
                        config.getDouble("OverWorld-position.spawn-x"),
                        config.getDouble("OverWorld-position.spawn-y"),
                        config.getDouble("OverWorld-position.spawn-z"));
                player.teleport(spawnLocation);
            }
            player.sendMessage("Rift Close Temp Text");
        });
        //cancel all current events
        RiftEvent2.getAnimalEventInstance().CancelAllTasks();
        RiftEvent2.getSoundEventInstance().CancelAllTasks();
        RiftEvent2.getChunkEventInstance().CancelAllTasks();
        RiftEvent2.getBlockEventInstance().ResetBlockEvent();
        RiftEvent2.getNpcHandlerInstance().CancelAllTasksAndRemoveNpcs();
        RiftEvent2.getEffectEventInstance().CancelAllTasks();

        //reset World variables
        RiftEvent2.getInstance().isNether = false;
        RiftEvent2.getInstance().isEnd = false;
        RiftEvent2.getInstance().isOcean = false;
        RiftEvent2.getInstance().percentInstab = 0;
        RiftEvent2.getInstance().RiftCenter = null;

        //reset event Vars
        isSoundEventloopActive = false;
        isAnimalEventloopActive = false;
        isChunkEventloopActive = false;
        isBlockLoopActive = false;

        //change game state to off
        RiftEvent2.getInstance().gameState = -1;

        //reopen, delay is just for safety
        if (reopenSafleyAfter) {
            try {
                BukkitTask task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        OpenRift();
                    }
                }.runTaskLater(RiftEvent2.getInstance(), 20);
            } catch (UnsupportedOperationException e) {
                // Log a warning message
                Bukkit.getLogger().warning("[RiftEvent] Failed to schedule Open Rift: " + e.getMessage());
            }
        }

    }
    public void OpenRift() {
        //more will be added here
        RiftEvent2.getWorldUtilsInstanceInstance().unloadWorldAndSheduleReset();

    }
}
