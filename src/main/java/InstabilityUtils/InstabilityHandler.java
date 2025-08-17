package InstabilityUtils;

import GenericUtils.RandomUtils;
import InstabilityUtils.InstabilityEvent.*;
import RiftEvent2.RiftEvent2;
import WorldUtils.WorldUtils;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.sql.Time;

public class InstabilityHandler {
    public BossBar bossBar;
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
                    if(RiftEvent2.getInstance().percentInstab != 100){
                        RiftEvent2.getInstance().percentInstab++;

                        bossBar.setProgress((double) RiftEvent2.getInstance().percentInstab / 100);
                        bossBar.setTitle("§4⚠§d Instability: §b%" + RiftEvent2.getInstance().percentInstab + " §4⚠");


                        //Bukkit.getLogger().info("[RiftEvent] instab:" + RiftEvent2.getInstance().percentInstab);
                        if(RiftEvent2.getInstance().percentInstab == 10){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 10%");
                            RandomMinorEvent();
                        }
                        if(RiftEvent2.getInstance().percentInstab == 20){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 20%");
                            RandomMinorEvent();
                        }
                        if(RiftEvent2.getInstance().percentInstab == 30){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 30%");
                            RandomMinorEvent();
                        }
                        if(RiftEvent2.getInstance().percentInstab == 40){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 40%");
                            RandomMinorEvent();
                        }
                        if(RiftEvent2.getInstance().percentInstab == 50){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 50%");
                            RandomMajorEvent();
                            RandomMinorEvent();
                        }
                        if(RiftEvent2.getInstance().percentInstab == 60){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 60%");
                            LoopedRandomMinorEvent();
                        }
                        if(RiftEvent2.getInstance().percentInstab == 70){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 70%");
                            LoopedRandomMinorEvent();
                        }
                        if(RiftEvent2.getInstance().percentInstab == 80){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 80%");
                            RandomMajorEvent();
                        }
                        if(RiftEvent2.getInstance().percentInstab == 90){
                            BroadcastAllInRift("§4⚠ WARNING:§d Instability§7 at 90%");
                            LoopedRandomMajorEvent();
                        }
                        if(RiftEvent2.getInstance().percentInstab == 95){
                            BroadcastAllInRift("§4⚠ §d§kdWARNING§r§:§d Inst§d§kab§r§ility§7 at 95§d§k%§r§");
                            //
                            bossBar.setStyle(BarStyle.SEGMENTED_10);
                        }
                        if(RiftEvent2.getInstance().percentInstab == 100){
                            BroadcastAllInRift("§4⚠ §d§kdWARNING§r§:§d Inst§d§kab§r§ility§7 at 95§d§k%§r§");
                            SafelyCloseRift(true);
                        }
                        instabilityTicker();
                    }
                    else {
                        Bukkit.getLogger().info("[RiftEvent] DONE:" + RiftEvent2.getInstance().percentInstab);
                    }
                }
            }.runTaskLater(RiftEvent2.getInstance(), ((long) RiftEvent2.TimeRiftOpenMin * 60 * 20)/ 100);
        } catch (UnsupportedOperationException e) {
            // Log a warning message
            Bukkit.getLogger().warning("[RiftEvent] Failed to schedule Instability Ticker: " + e.getMessage());
        }
    }
    public void setupBossBar(){
        bossBar = RiftEvent2.getInstance().getServer().createBossBar("§4⚠§5 Instability: % " + RiftEvent2.getInstance().percentInstab + " §4⚠" , BarColor.PURPLE, BarStyle.SEGMENTED_6);
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

    //single effect, cave noise, chat, change weather
    public void RandomMinorEvent() {
         int rand = RandomUtils.Randomint(4, 1);
         if (rand == 1) {
            SoundEvent.PlayCaveSoundOnce();
         }
         if (rand == 2) {
            AnimalEvent.AnimalSpawner();
         }
         if (rand == 3) {
             EffectEvent.GiveRandomEffectToPlayers(500, 2);
         }
         if (rand == 4) {
             WeatherEvent.Weather();
         }

    }
    //Npc spawn, inventory fill, particle event
    public void RandomMajorEvent() {
        int rand = RandomUtils.Randomint(1, 1);
        if (rand == 1) {
            NpcHandler.PlaceNpcsAroundPlayer();
        }
        if (rand == 2) {
            ChunkEvent.replaceAllAroundPlayersOnce();
        }
        if (rand == 3) {
            ParticleEvent.SpawnParticlesOnce();
        }
    }


    Boolean isSoundEventloopActive = false;
    Boolean isAnimalEventloopActive = false;
    //Each cannot be activated twice
    //Sky/weather changing constantly, Sound looper, animal spawn loop
    public void LoopedRandomMinorEvent() {
        /*int rand = RandomUtils.Randomint(2, 1);
        if (rand == 1 && !isSoundEventloopActive) {
            Bukkit.getLogger().info("Sound Looper Active");
            RiftEvent2.getSoundEventInstance().SoundLooper();
        }
        if (rand == 2 && !isAnimalEventloopActive) {
            Bukkit.getLogger().info("Animal Looper Active");
            RiftEvent2.getAnimalEventInstance().AnimalSpawnerLoop();
        }*/
        while(true) {
            if(isAnimalEventloopActive && isSoundEventloopActive){
                Bukkit.getLogger().info("Couldnt start minor event");
                break;
            }
            int rand = RandomUtils.Randomint(2, 1);
            if (rand == 1 && !isSoundEventloopActive) {
                Bukkit.getLogger().info("Sound Looper Active");
                SoundEvent.SoundLooper();
                break;
            }
            if (rand == 2 && !isAnimalEventloopActive) {
                Bukkit.getLogger().info("Animal Looper Active");
                AnimalEvent.AnimalSpawnerLoop();
                break;
            }

        }
    }


    Boolean isChunkEventloopActive = false;
    Boolean isBlockLoopActive = false;
    Boolean isDayCycleSpeedUpActive = false;
    Boolean isEffectLoopActive = false;
    //Each cannot be activated twice
    //Chat Spam, Effect Loop, lighting spam, break block, chunk glitch, Day Cycle Speed up
    public void LoopedRandomMajorEvent() {
        /*int rand = RandomUtils.Randomint(2, 1);
        if (rand == 1 && !isChunkEventloopActive) {
            Bukkit.getLogger().info("Chunk Looper Active");
            RiftEvent2.getChunkEventInstance().ChunkLooper();
        }
        if (rand == 2 && !isBlockLoopActive) {
            Bukkit.getLogger().info("Block Looper Active");
            RiftEvent2.getBlockEventInstance().EnableBlockEvent();
        }
        if (rand == 3 && !isDayCycleSpeedUpActive){

        }*/

        while(true) {
            if(isChunkEventloopActive && isBlockLoopActive && isDayCycleSpeedUpActive && isEffectLoopActive){
                Bukkit.getLogger().info("Couldnt start major event");
                break;
            }
            int rand = RandomUtils.Randomint(3, 1);
            if (rand == 1 && !isChunkEventloopActive) {
                Bukkit.getLogger().info("Chunk Looper Active");
                ChunkEvent.ChunkLooper();
                break;
            }
            if (rand == 2 && !isBlockLoopActive) {
                Bukkit.getLogger().info("Block Looper Active");
                BlockEvent.EnableBlockEvent();
                break;
            }
            if (rand == 3 && !isDayCycleSpeedUpActive) {
                Bukkit.getLogger().info("Day Cycle Speed Up Looper Active");
                TimeEvent.TimeLooper();
                break;
            }
            if (rand == 4 && isEffectLoopActive) {
                Bukkit.getLogger().info("Effect Looper Active");
                EffectEvent.EffectLooper();
                break;
            }
        }
    }

    public void SafelyCloseRift(Boolean reopenSafleyAfter) {
        //set game to disabling
        RiftEvent2.getInstance().gameState = 0;

        //send players back to regular world
        if (Bukkit.getWorld(RiftEvent2.getInstance().WorldName) != null) {
            Bukkit.getWorld(RiftEvent2.getInstance().WorldName).getPlayers().forEach( player -> {
                bossBar.removePlayer(player);
                if(player.getRespawnLocation() != null) {
                    player.teleport(player.getRespawnLocation());
                }
                else {
                    FileConfiguration config = RiftEvent2.getInstance().getConfig();
                    //Location spawnLocation = Bukkit.getWorld("world").getSpawnLocation();
                    Location spawnLocation = new Location(Bukkit.getWorld(
                            config.getString("OverWorld-position.spawn-world")),
                            config.getDouble("OverWorld-position.spawn-x"),
                            config.getDouble("OverWorld-position.spawn-y"),
                            config.getDouble("OverWorld-position.spawn-z"));
                    player.teleport(spawnLocation);
                }
                player.sendMessage("Rift Close Temp Text");
            });
        }

        //cancel all current events
        AnimalEvent.CancelAllTasks();
        SoundEvent.CancelAllTasks();
        ChunkEvent.CancelAllTasks();
        BlockEvent.ResetBlockEvent();
        NpcHandler.CancelAllTasksAndRemoveNpcs();
        EffectEvent.CancelAllTasks();
        TimeEvent.CancelAllTasks();

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
        isDayCycleSpeedUpActive = false;
        isEffectLoopActive = false;

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
