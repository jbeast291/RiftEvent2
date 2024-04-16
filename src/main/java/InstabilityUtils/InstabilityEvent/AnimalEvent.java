package InstabilityUtils.InstabilityEvent;

import GenericUtils.RandomUtils;
import RiftEvent2.RiftEvent2;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class AnimalEvent {
    public final List<EntityType> OverworldList = new ArrayList<EntityType>(){{
        add(EntityType.LLAMA);
        add(EntityType.COW);
        add(EntityType.CHICKEN);
        add(EntityType.PIG);
        add(EntityType.SHEEP);
        add(EntityType.MULE);
        add(EntityType.MUSHROOM_COW);
        add(EntityType.OCELOT);
        add(EntityType.CAT);
        add(EntityType.CAMEL);
    }};
    public final List<EntityType> NetherList = new ArrayList<EntityType>(){{
        add(EntityType.STRIDER);
        add(EntityType.ZOMBIFIED_PIGLIN);
        add(EntityType.ZOMBIFIED_PIGLIN);
        add(EntityType.ENDERMAN);
    }};
    public final List<EntityType> EndList = new ArrayList<EntityType>(){{
        add(EntityType.ENDERMAN);
        add(EntityType.ENDERMAN);
        add(EntityType.ENDERMAN);
        add(EntityType.ENDERMAN);
        add(EntityType.ENDERMAN);
        add(EntityType.ENDERMITE);
    }};
    public final List<EntityType> OceanList = new ArrayList<EntityType>(){{
        add(EntityType.SALMON);
        add(EntityType.COD);
        add(EntityType.TROPICAL_FISH);
        add(EntityType.GLOW_SQUID);
        add(EntityType.SQUID);
        add(EntityType.AXOLOTL);
    }};


    public void AnimalSpawner() {
        List<EntityType> SelectedList = new ArrayList<EntityType>();
        if(RiftEvent2.getInstance().isNether) {
            SelectedList = NetherList;
        }
        else if(RiftEvent2.getInstance().isEnd) {
            SelectedList = EndList;
        }
        else if(RiftEvent2.getInstance().isOcean) {
            SelectedList = OceanList;
        }
        else {
            SelectedList = OverworldList;
        }
        List<EntityType> finalSelectedList = SelectedList;
        Bukkit.getWorld(RiftEvent2.getInstance().WorldName).getPlayers().forEach(player -> {
            int randomOffSet = RandomUtils.Randomint(3, 0);
            for (int i = 0; i < 2 + randomOffSet; i++) {
                player.getWorld().spawnEntity(player.getLocation(), RandomEntity(finalSelectedList));
            }
        });
    }
    public void AnimalSpawnerLoop() {
        try {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    AnimalSpawner();
                    AnimalSpawnerLoop();
                }
            }.runTaskLater(RiftEvent2.getInstance(), 200 + RandomUtils.Randomint(150, 0));
            AnimalLooperTasks.add(task.getTaskId());
        } catch (UnsupportedOperationException e) {
            // Log a warning message
            Bukkit.getLogger().warning("[RiftEvent] Failed to schedule sound loop: " + e.getMessage());
        }
    }

    public List<Integer> AnimalLooperTasks = new ArrayList<Integer>();

    public void CancelAllTasks() {
        AnimalLooperTasks.forEach(taskId -> {
            Bukkit.getScheduler().cancelTask(taskId);
        });
        AnimalLooperTasks.clear();
    }


    public EntityType RandomEntity(List<EntityType> EntityType){
        return EntityType.get(RandomUtils.Randomint(EntityType.size() - 1, 0));
    }
}
