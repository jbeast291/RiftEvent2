package GenericUtils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

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
}
