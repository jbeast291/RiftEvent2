package InstabilityUtils.InstabilityEvent;

import GenericUtils.RandomUtils;
import RiftEvent2.RiftEvent2;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


public class BlockEvent implements Listener {
    public BlockEvent(RiftEvent2 plugin) { Bukkit.getPluginManager().registerEvents(this, plugin); }

    public boolean active = false;

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        if(!event.getPlayer().getWorld().equals(Bukkit.getWorld(RiftEvent2.getInstance().WorldName))) {
            return;
        }
        if(active) {
            int rand = RandomUtils.Randomint(10, 0);
            if(rand == 8) {
                event.getBlock().setType(Material.MAGENTA_CONCRETE);
                event.setCancelled(true);
            }
            if(rand == 9) {
                event.getBlock().setType(Material.BLACK_CONCRETE);
                event.setCancelled(true);
            }
            if(rand < 8 && rand > 4) {

                Location BlockCenter = new Location(event.getBlock().getWorld(),
                        event.getBlock().getX() + 0.5,
                        event.getBlock().getY() + 0.5,
                        event.getBlock().getZ() + 0.5);
                Bukkit.getWorld(RiftEvent2.getInstance().WorldName).spawnParticle(Particle.BLOCK_MARKER, BlockCenter, 1, Material.AIR.createBlockData().clone());

            }
        }
    }

    public void EnableBlockEvent(){
        active = true;
    }

    public void ResetBlockEvent(){
        active = false;
    }
}
