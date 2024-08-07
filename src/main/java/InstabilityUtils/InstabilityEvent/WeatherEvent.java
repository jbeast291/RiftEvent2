package InstabilityUtils.InstabilityEvent;
import GenericUtils.RandomUtils;
import RiftEvent2.RiftEvent2;
import org.bukkit.Bukkit;
public class WeatherEvent {
    public static void Weather() {
        //Selects and sends a random weather event
        int RandInt = RandomUtils.Randomint(2,0);
        if(RandInt == 0) {
            Bukkit.getWorld(RiftEvent2.getInstance().WorldName).setStorm(false);
        };
        if(RandInt == 1) {
            Bukkit.getWorld(RiftEvent2.getInstance().WorldName).setStorm(true);
        };
        if(RandInt == 2) {
            Bukkit.getWorld(RiftEvent2.getInstance().WorldName).setThundering(true);
        };
    }
}
