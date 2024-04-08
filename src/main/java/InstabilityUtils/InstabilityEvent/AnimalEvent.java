package InstabilityUtils.InstabilityEvent;

import GenericUtils.RandomUtils;
import RiftEvent2.RiftEvent2;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class AnimalEvent {
    public static final List<EntityType> OverworldList = new ArrayList<EntityType>(){{
        add(EntityType.LLAMA);
        add(EntityType.COW);
        add(EntityType.CHICKEN);
        add(EntityType.PIG);
        add(EntityType.SHEEP);
        add(EntityType.MULE);
        add(EntityType.MUSHROOM_COW);
        add(EntityType.OCELOT);
        add(EntityType.CAT);
        add(EntityType.TROPICAL_FISH);
        add(EntityType.AXOLOTL);
        add(EntityType.CAMEL);
        add(EntityType.SALMON);
        add(EntityType.COD);
    }};
    public static final List<EntityType> NetherList = new ArrayList<EntityType>(){{
        add(EntityType.STRIDER);
        add(EntityType.ZOMBIFIED_PIGLIN);
        add(EntityType.ENDERMAN);
        add(EntityType.ENDERMAN);
    }};
    public static final List<EntityType> EndList = new ArrayList<EntityType>(){{
        add(EntityType.LLAMA);
        add(EntityType.COW);
        add(EntityType.CHICKEN);
        add(EntityType.PIG);
        add(EntityType.SHEEP);
        add(EntityType.MULE);
        add(EntityType.MUSHROOM_COW);
        add(EntityType.OCELOT);
        add(EntityType.CAT);
        add(EntityType.TROPICAL_FISH);
        add(EntityType.AXOLOTL);
        add(EntityType.CAMEL);
        add(EntityType.STRIDER);
        add(EntityType.ZOMBIFIED_PIGLIN);
        add(EntityType.SALMON);
        add(EntityType.COD);
    }};


    public static void AnimalSpawner() {
        Bukkit.getWorld(RiftEvent2.getInstance().WorldName).getPlayers().forEach(player -> {
            if(RiftEvent2.getInstance().isNether) {

            }
            int randomOffSet = RandomUtils.Randomint(0, 3);
            for (int i = 0; i < 2 + randomOffSet; i++) {
                player.getWorld().spawnEntity(player.getLocation(), RandomEntity(EntityList));
            }
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
