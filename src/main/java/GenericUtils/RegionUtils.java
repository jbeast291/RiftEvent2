package GenericUtils;

import InstabilityUtils.InstabilityEvent.ChunkEvent;
import RiftEvent2.RiftEvent2;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.List;

public class RegionUtils {
    public static void fillRegionBetweenPoints(int x, int y, int z, int x2, int y2, int z2, World world, Material mat){
        int minX = Math.min(x, x2);
        int minY = Math.min(y, y2);
        int minZ = Math.min(z, z2);
        int maxX = Math.max(x, x2);
        int maxY = Math.max(y, y2);
        int maxZ = Math.max(z, z2);

        for (int currentX = minX; currentX <= maxX; currentX++) {//x
            for (int currentY = minY; currentY <= maxY; currentY++) {//y
                for (int currentZ = minZ; currentZ <= maxZ; currentZ++) {//z
                    Location blockpos = new Location(world, currentX, currentY, currentZ);
                    blockpos.getBlock().setType(mat);
                }
            }
        }
    }
    public static void ReplaceBlockInRegionBetweenPoints(int x, int y, int z, int x2, int y2, int z2, World world, Material toReplace, Material Replacement){
        int minX = Math.min(x, x2);
        int minY = Math.min(y, y2);
        int minZ = Math.min(z, z2);
        int maxX = Math.max(x, x2);
        int maxY = Math.max(y, y2);
        int maxZ = Math.max(z, z2);

        for (int currentX = minX; currentX <= maxX; currentX++) {//x
            for (int currentY = minY; currentY <= maxY; currentY++) {//y
                for (int currentZ = minZ; currentZ <= maxZ; currentZ++) {//z
                    Location blockpos = new Location(world, currentX, currentY, currentZ);
                    if (blockpos.getBlock().getType() == toReplace) {
                        blockpos.getBlock().setType(Replacement);
                    }
                }
            }
        }
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
                    if(RandomUtils.Randomint(50, 1) == 2){
                        Location blockpos = new Location(world, currentX, currentY, currentZ);
                        if (BlackListedBlocks.contains(blockpos.getBlock().getType()) ||
                                blockpos.getBlock().getType().equals(Material.AIR) ||
                                blockpos.getBlock().getType().equals(Material.WATER)) {
                            continue;
                        }
                        blockpos.getBlock().setType(ChunkEvent.getRandomMaterial(ReplacementBLocks));
                    }
                }
            }
        }
    }
    public static void ReplaceBlockInRegionWithBlackList(int x, int y, int z, int x2, int y2, int z2, World world, List<Material> BlackListedBlocks, Material ReplacementBLock){
        int minX = Math.min(x, x2);
        int minY = Math.min(y, y2);
        int minZ = Math.min(z, z2);
        int maxX = Math.max(x, x2);
        int maxY = Math.max(y, y2);
        int maxZ = Math.max(z, z2);

        for (int currentX = minX; currentX <= maxX; currentX++) {//x
            for (int currentY = minY; currentY <= maxY; currentY++) {//y
                for (int currentZ = minZ; currentZ <= maxZ; currentZ++) {//z
                    if(RandomUtils.Randomint(50, 1) == 2){
                        Location blockpos = new Location(world, currentX, currentY, currentZ);
                        if (BlackListedBlocks.contains(blockpos.getBlock().getType()) ||
                                blockpos.getBlock().getType().equals(Material.AIR) ||
                                blockpos.getBlock().getType().equals(Material.WATER)) {
                            continue;
                        }
                        blockpos.getBlock().setType(ReplacementBLock);
                    }
                }
            }
        }
    }
}
