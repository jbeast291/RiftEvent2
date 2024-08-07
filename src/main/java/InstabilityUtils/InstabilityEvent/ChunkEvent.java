package InstabilityUtils.InstabilityEvent;

import GenericUtils.RandomUtils;
import GenericUtils.RegionUtils;
import RiftEvent2.RiftEvent2;
import org.bukkit.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class ChunkEvent {
    public static final List<Material> ReplacementBlocks = new ArrayList<Material>(){{
        add(Material.MAGENTA_CONCRETE);
        add(Material.BLACK_CONCRETE);
    }};
    public static final List<Material> BlackListedBlocks = new ArrayList<Material>(){{
        add(Material.CHEST);
        add(Material.BARREL);
        add(Material.DISPENSER);
        add(Material.DROPPER);
        add(Material.JUKEBOX);
        add(Material.BLAST_FURNACE);
        add(Material.BREWING_STAND);
        add(Material.CAMPFIRE);
        add(Material.CAULDRON);
        add(Material.CHISELED_BOOKSHELF);
        add(Material.ENDER_CHEST);
        add(Material.DECORATED_POT);
        add(Material.SUSPICIOUS_GRAVEL);
        add(Material.SUSPICIOUS_SAND);
        add(Material.FLOWER_POT);
        add(Material.FURNACE);
        add(Material.HOPPER);
        add(Material.LECTERN);
        add(Material.SHULKER_BOX);
        add(Material.WHITE_SHULKER_BOX);
        add(Material.LIGHT_GRAY_SHULKER_BOX);
        add(Material.GRAY_SHULKER_BOX);
        add(Material.BLACK_SHULKER_BOX);
        add(Material.BROWN_SHULKER_BOX);
        add(Material.RED_SHULKER_BOX);
        add(Material.ORANGE_SHULKER_BOX);
        add(Material.YELLOW_SHULKER_BOX);
        add(Material.LIME_SHULKER_BOX);
        add(Material.GREEN_SHULKER_BOX);
        add(Material.CYAN_SHULKER_BOX);
        add(Material.LIGHT_BLUE_SHULKER_BOX);
        add(Material.BLUE_SHULKER_BOX);
        add(Material.PURPLE_SHULKER_BOX);
        add(Material.MAGENTA_SHULKER_BOX);
        add(Material.PINK_SHULKER_BOX);
        add(Material.SMOKER);
        add(Material.SOUL_CAMPFIRE);
        add(Material.TRAPPED_CHEST);
        add(Material.BUBBLE_COLUMN);
        add(Material.KELP);
        add(Material.KELP_PLANT);
        add(Material.SEA_PICKLE);
        add(Material.SEAGRASS);
        add(Material.VINE);
        add(Material.CAVE_VINES);
        add(Material.WEEPING_VINES);
        add(Material.TWISTING_VINES);
        add(Material.CAVE_VINES_PLANT);
        add(Material.WEEPING_VINES_PLANT);
        add(Material.TWISTING_VINES_PLANT);
    }};
    public static void ChunkLooper(){
        try {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getWorld(RiftEvent2.getInstance().WorldName).getPlayers().forEach(player -> {
                        RegionUtils.RandomReplaceBlockInRegionWithBlackList((int) Math.round(player.getLocation().x() - 5),
                                (int) Math.round(player.getLocation().y() - 5),
                                (int) Math.round(player.getLocation().z() - 5),
                                (int) Math.round(player.getLocation().x() + 5),
                                (int) Math.round(player.getLocation().y() + 5),
                                (int) Math.round(player.getLocation().z() + 5), player.getWorld(), BlackListedBlocks, ReplacementBlocks);
                    });
                    ChunkLooper();
                }
            }.runTaskLater(RiftEvent2.getInstance(), 1);
            ChunkLoopTasks.add(task.getTaskId());
        } catch (UnsupportedOperationException e) {
            // Log a warning message
            Bukkit.getLogger().warning("[RiftEvent] Failed to schedule sound loop: " + e.getMessage());
        }
    }
    public static List<Integer> ChunkLoopTasks = new ArrayList<Integer>();

    public static void replaceAllAroundPlayersOnce(){
        Bukkit.getWorld(RiftEvent2.getInstance().WorldName).getPlayers().forEach(player -> {
            RegionUtils.RandomReplaceBlockInRegionWithBlackList((int) Math.round(player.getLocation().x() - 15),
                    (int) Math.round(player.getLocation().y() - 15),
                    (int) Math.round(player.getLocation().z() - 15),
                    (int) Math.round(player.getLocation().x() + 15),
                    (int) Math.round(player.getLocation().y() + 15),
                    (int) Math.round(player.getLocation().z() + 15), player.getWorld(), BlackListedBlocks, ReplacementBlocks);
        });
    }

    public static void CancelAllTasks() {
        ChunkLoopTasks.forEach(taskId -> {
            Bukkit.getScheduler().cancelTask(taskId);
        });
        ChunkLoopTasks.clear();
    }
    public static Material getRandomMaterial(List<Material> Materials){
        return Materials.get(RandomUtils.Randomint(Materials.size() - 1, 0));
    }
}
