package InstabilityUtils.InstabilityEvent;

import GenericUtils.RandomUtils;
import RiftEvent2.RiftEvent2;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.generator.structure.Structure;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;

public class SoundEvent {
    public final List<Sound> sounds = new ArrayList<Sound>(){{
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
    public void SoundLooper(Player player, Sound sound){
        try {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    player.playSound(player, sound, 1, 1);
                    SoundLooper(player, getRandomSound(sounds));
                }
            }.runTaskLater(RiftEvent2.getInstance(), 400 + RandomUtils.Randomint(0, 200));
        } catch (UnsupportedOperationException e) {
            // Log a warning message
            Bukkit.getLogger().warning("[RiftEvent] Failed to schedule sound loop: " + e.getMessage());
        }
    }
    public void LoopRandomSound(Player player) {
        SoundLooper(player, getRandomSound(sounds));
    }
    public void PlayRandomSoundOnce(Player player) {
        player.playSound(player, getRandomSound(sounds), 1, 1);
    }
    public void PlayCaveSoundOnce(Player player){
        player.playSound(player, Sound.AMBIENT_CAVE, 1, 1);
    }
    public static Sound getRandomSound(List<Sound> sounds){

        int max = sounds.size() - 1;
        int min = 0;
        int range = max - min + 1;

        int rand = (int)(Math.random() * range) + min;
        return sounds.get(rand);
    }
}
