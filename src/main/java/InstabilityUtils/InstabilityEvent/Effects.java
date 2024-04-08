package InstabilityUtils.InstabilityEvent;

import RiftEvent2.RiftEvent2;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.List;

public class Effects {
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
    }};
    public static void Effects() {
        List<Player> PlayerList = Bukkit.getWorld(RiftEvent2.getInstance().WorldName).getPlayers();
        PlayerList.forEach(player -> {
            PotionEffectType SelectedEffectType = RandomEffect(EffectList);
            player.addPotionEffect(new PotionEffect(SelectedEffectType, 100, 1, false, false));
        });
    }
    public static PotionEffectType RandomEffect(List<PotionEffectType> PotionType){

        int max = PotionType.size() - 1;
        int min = 0;
        int range = max - min + 1;

        int rand = (int)(Math.random() * range) + min;
        return PotionType.get(rand);
    }
}
