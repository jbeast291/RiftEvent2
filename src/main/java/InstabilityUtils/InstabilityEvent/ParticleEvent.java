package InstabilityUtils.InstabilityEvent;

import GenericUtils.RandomUtils;
import RiftEvent2.RiftEvent2;
import org.bukkit.*;

import java.util.List;

public class ParticleEvent {
    public void SpawnParticlesInRegion(int x, int y, int z, int x2, int y2, int z2, World world, int percentParticles){
        int minX = Math.min(x, x2);
        int minY = Math.min(y, y2);
        int minZ = Math.min(z, z2);
        int maxX = Math.max(x, x2);
        int maxY = Math.max(y, y2);
        int maxZ = Math.max(z, z2);

        for (int currentX = minX; currentX <= maxX; currentX++) {//x
            for (int currentY = minY; currentY <= maxY; currentY++) {//y
                for (int currentZ = minZ; currentZ <= maxZ; currentZ++) {//z
                    //not every block should be a particle otherwise we will reach the limit FAST
                    if(RandomUtils.RandomTrueFromPercent(percentParticles)) {
                        continue;
                    }

                    //check if air, no need to spawn particles in blocks
                    Location blockPos = new Location(world, currentX, currentY, currentZ);

                    if (blockPos.getBlock().getType().equals(Material.AIR) || blockPos.getBlock().getType().equals(Material.WATER)) {//particles
                        Location blockCenter = new Location(blockPos.getBlock().getWorld(),
                                blockPos.getBlock().getX() + 0.5,
                                blockPos.getBlock().getY() + 0.5,
                                blockPos.getBlock().getZ() + 0.5);
                        Bukkit.getWorld(RiftEvent2.getInstance().WorldName).spawnParticle(Particle.BLOCK_MARKER, blockCenter, 1, Material.AIR.createBlockData().clone());
                    }

                }
            }
        }
    }
}
