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
    public void ChatSpammer() {
        Bukkit.getWorld(RiftEvent2.getInstance().WorldName).getPlayers().forEach(player -> {
            int randomOffSet = RandomUtils.Randomint(3, 0);
            for (int i = 0; i < 2 + randomOffSet; i++) {
                player.sendMessage(RandomMessage(ChatMessages));
            }
    });
}
    public String RandomMessage(List<String> String){

        int max = String.size() - 1;
        int min = 0;
        int range = max - min + 1;

        int rand = (int)(Math.random() * range) + min;
        return String.get(rand);
    }
}
