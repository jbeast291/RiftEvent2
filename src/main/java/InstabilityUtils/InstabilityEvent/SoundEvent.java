package InstabilityUtils.InstabilityEvent;

import GenericUtils.RandomUtils;
import RiftEvent2.RiftEvent2;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.generator.structure.Structure;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;

public class SoundEvent {
    public static final List<Sound> sounds = new ArrayList<Sound>(){{
        add(Sound.AMBIENT_CAVE);
        add(Sound.AMBIENT_BASALT_DELTAS_ADDITIONS);//ocean monument
        add(Sound.AMBIENT_BASALT_DELTAS_MOOD);
        add(Sound.AMBIENT_CRIMSON_FOREST_MOOD);
        add(Sound.AMBIENT_SOUL_SAND_VALLEY_MOOD);
        add(Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE);
        add(Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE);
        add(Sound.ENTITY_GENERIC_EXPLODE);
        add(Sound.ENTITY_CREEPER_PRIMED);
        add(Sound.ENTITY_CAT_HISS);
        add(Sound.BLOCK_WOOD_PLACE);
        add(Sound.BLOCK_WOOD_BREAK);
        add(Sound.BLOCK_STONE_PLACE);
        add(Sound.BLOCK_STONE_BREAK);
        add(Sound.ENTITY_PILLAGER_HURT);
        add(Sound.ENTITY_PILLAGER_DEATH);
        add(Sound.ENTITY_WITHER_SKELETON_AMBIENT);
        add(Sound.ENTITY_RABBIT_AMBIENT);
        add(Sound.ENTITY_VEX_AMBIENT);
        add(Sound.ENTITY_VEX_CHARGE);
        add(Sound.ENTITY_LIGHTNING_BOLT_IMPACT);
        add(Sound.ENTITY_LIGHTNING_BOLT_THUNDER);
    }};

    public static void SoundLooper(){
        try {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getWorld(RiftEvent2.getInstance().WorldName).getPlayers().forEach(player -> {
                        player.playSound(player, getRandomSound(sounds), 100, RandomUtils.Randomint(2,0));
                    });
                    SoundLooper();
                }
            }.runTaskLater(RiftEvent2.getInstance(), 150 + RandomUtils.Randomint(100, 0));
            SoundLoopTasks.add(task.getTaskId());
        } catch (UnsupportedOperationException e) {
            // Log a warning message
            Bukkit.getLogger().warning("[RiftEvent] Failed to schedule sound loop: " + e.getMessage());
        }
    }
    public static List<Integer> SoundLoopTasks = new ArrayList<Integer>();

    public static void CancelAllTasks() {
        SoundLoopTasks.forEach(taskId -> {
            Bukkit.getScheduler().cancelTask(taskId);
        });
        SoundLoopTasks.clear();
    }
    public static void PlayCaveSoundOnce(){
        Bukkit.getWorld(RiftEvent2.getInstance().WorldName).getPlayers().forEach(player -> {
            player.playSound(player, Sound.AMBIENT_CAVE, 1, 1);
        });
    }
    public static Sound getRandomSound(List<Sound> sounds){
        return sounds.get(RandomUtils.Randomint(sounds.size() - 1, 0));
    }
}
