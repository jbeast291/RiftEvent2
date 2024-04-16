package InstabilityUtils.InstabilityEvent;

import GenericUtils.RandomUtils;
import RiftEvent2.RiftEvent2;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import java.util.List;

public class ChatEvent {
    public final List<String> ChatMessages = new ArrayList<String>(){{
        add("§6§kRoses are red, Diamonds are blue, Creepers are scary, There's one behind you!");
        add("http://discord.com/invite/S2F4TJhVcV");
        add("");
         /*
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
         */
    }};
    public void ChatSpam() {
        Bukkit.getWorld(RiftEvent2.getInstance().WorldName).getPlayers().forEach(player -> {
            int randomOffSet = RandomUtils.Randomint(3, 0);
                player.sendMessage(RandomMessage(ChatMessages));
    });
}
    public String RandomMessage(List<String> String){
        return String.get(RandomUtils.Randomint(String.size() - 1, 0));
    }
}
