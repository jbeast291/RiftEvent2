package InstabilityUtils.InstabilityEvent;

import GenericUtils.RandomUtils;
import RiftEvent2.RiftEvent2;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class EffectEvent {
    public static final List<PotionEffectType> EffectList = new ArrayList<PotionEffectType>(){{
        add(PotionEffectType.NIGHT_VISION);
        add(PotionEffectType.GLOWING);
        add(PotionEffectType.INVISIBILITY);
        add(PotionEffectType.BLINDNESS);
        add(PotionEffectType.DARKNESS);
        add(PotionEffectType.CONDUIT_POWER);
        add(PotionEffectType.DOLPHINS_GRACE);
        add(PotionEffectType.HERO_OF_THE_VILLAGE);
        add(PotionEffectType.SLOW_FALLING);
        add(PotionEffectType.ABSORPTION);
        add(PotionEffectType.BAD_OMEN);
        add(PotionEffectType.HASTE);
        add(PotionEffectType.HUNGER);
        add(PotionEffectType.JUMP_BOOST);
        add(PotionEffectType.LEVITATION);
        add(PotionEffectType.RESISTANCE);
        add(PotionEffectType.SLOWNESS);
        add(PotionEffectType.WATER_BREATHING);
        add(PotionEffectType.WEAKNESS);
    }};
    public static void GiveRandomEffectToPlayers(int duration, int amplifier) {
        List<Player> PlayerList = Bukkit.getWorld(RiftEvent2.getInstance().WorldName).getPlayers();
        PlayerList.forEach(player -> {
            player.addPotionEffect(new PotionEffect(getRandomEffect(EffectList), duration, amplifier, false, false));
        });
    }
    public static void EffectLooper(){
        try {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    GiveRandomEffectToPlayers(199, RandomUtils.Randomint(2,1));
                    EffectLooper();
                }
            }.runTaskLater(RiftEvent2.getInstance(), 200);
            EffectLoopTasks.add(task.getTaskId());
        } catch (UnsupportedOperationException e) {
            // Log a warning message
            Bukkit.getLogger().warning("[RiftEvent] Failed to schedule effect loop: " + e.getMessage());
        }
    }
    public static List<Integer> EffectLoopTasks = new ArrayList<Integer>();

    public static void CancelAllTasks() {
        EffectLoopTasks.forEach(taskId -> {
            Bukkit.getScheduler().cancelTask(taskId);
        });
        EffectLoopTasks.clear();
    }
    public static PotionEffectType getRandomEffect(List<PotionEffectType> PotionType){
        return PotionType.get(RandomUtils.Randomint(PotionType.size() - 1, 0));
    }
}
