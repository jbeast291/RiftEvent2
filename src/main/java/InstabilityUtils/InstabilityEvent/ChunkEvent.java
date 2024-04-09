package InstabilityUtils.InstabilityEvent;

import GenericUtils.RandomUtils;
import RiftEvent2.RiftEvent2;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChunkEvent {
    public final List<Material> ReplacementBlocks = new ArrayList<Material>(){{
        add(Material.MAGENTA_CONCRETE);
        add(Material.BLACK_CONCRETE);
    }};
    public final List<Material> BlackListedBlocks = new ArrayList<Material>(){{
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
    }};
    public void ChunkLooper(){
        try {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getWorld(RiftEvent2.getInstance().WorldName).getPlayers().forEach(player -> {
                        ReplaceBlockInRegionWithBlackList((int) Math.round(player.getLocation().x() - 5),
                                (int) Math.round(player.getLocation().y() - 5),
                                (int) Math.round(player.getLocation().z() - 5),
                                (int) Math.round(player.getLocation().x() + 5),
                                (int) Math.round(player.getLocation().y() + 5),
                                (int) Math.round(player.getLocation().z() + 5), player.getWorld(), BlackListedBlocks, ReplacementBlocks);
                    });
                    ChunkLooper();
                }
            }.runTaskLater(RiftEvent2.getInstance(), 5);
            ChunkLoopTasks.add(task.getTaskId());
        } catch (UnsupportedOperationException e) {
            // Log a warning message
            Bukkit.getLogger().warning("[RiftEvent] Failed to schedule sound loop: " + e.getMessage());
        }
    }
    public List<Integer> ChunkLoopTasks = new ArrayList<Integer>();

    public void CancelAllTasks() {
        ChunkLoopTasks.forEach(taskId -> {
            Bukkit.getScheduler().cancelTask(taskId);
        });
        ChunkLoopTasks.clear();
    }
    public static void ReplaceBlockInRegionWithBlackList(int x, int y, int z, int x2, int y2, int z2, World world, List<Material> BlackListedBlocks, List<Material> ReplacementBLocks){
        int minX = Math.min(x, x2);
        int minY = Math.min(y, y2);
        int minZ = Math.min(z, z2);
        int maxX = Math.max(x, x2);
        int maxY = Math.max(y, y2);
        int maxZ = Math.max(z, z2);

        for (int currentX = minX; currentX <= maxX; currentX++) {//x
            for (int currentY = minY; currentY <= maxY; currentY++) {//y
                for (int currentZ = minZ; currentZ <= maxZ; currentZ++) {//z
                    if(RandomUtils.Randomint(10, 1) == 2){
                        Location blockpos = new Location(world, currentX, currentY, currentZ);
                        if (BlackListedBlocks.contains(blockpos.getBlock().getType()) || blockpos.getBlock().getType().equals(Material.AIR)) {
                            continue;
                        }
                        blockpos.getBlock().setType(getRandomMaterial(ReplacementBLocks));
                    }
                }
            }
        }
    }
    public static Material getRandomMaterial(List<Material> Materials){

        int max = Materials.size() - 1;
        int min = 0;
        int range = max - min + 1;

        int rand = (int)(Math.random() * range) + min;
        return Materials.get(rand);
    }
}
