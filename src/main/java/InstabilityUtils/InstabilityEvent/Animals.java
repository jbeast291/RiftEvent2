package InstabilityUtils.InstabilityEvent;

import RiftEvent2.RiftEvent2;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class Animals {
    public static final List<EntityType> EntityList = new ArrayList<EntityType>(){{
        add(EntityType.LLAMA);
        add(EntityType.COW);
        add(EntityType.CHICKEN);
        add(EntityType.PIG);
        add(EntityType.SHEEP);
        add(EntityType.HORSE);
        add(EntityType.DONKEY);
        add(EntityType.CAMEL);
    }};
    public static void Animals() {
        List<Player> PlayerList = Bukkit.getWorld(RiftEvent2.getInstance().WorldName).getPlayers();
        PlayerList.forEach(player -> {
            Location location = player.getLocation().clone().add(0, 1, 0);
            EntityType SelectedEntityType = RandomEntity(EntityList);
            player.getWorld().spawnEntity(location, SelectedEntityType);
        });
    }
    public static EntityType RandomEntity(List<EntityType> EntityType){

        int max = EntityType.size() - 1;
        int min = 0;
        int range = max - min + 1;

        int rand = (int)(Math.random() * range) + min;
        return EntityType.get(rand);
    }
}
