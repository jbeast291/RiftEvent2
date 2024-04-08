package Commands;

import InstabilityUtils.InstabilityEvent.AnimalEvent;
import InstabilityUtils.InstabilityEvent.NpcHandler;
import RiftEvent2.RiftEvent2;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RiftEventCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("riftevent")) {
            if (args.length == 0) {
                sender.sendMessage("§7[§dRiftEvent§7]§c Please specify a sub command!");
                return false;
                // could probably have this show like a help page that shows all the available arguments for this command
            }
            if (args[0].equalsIgnoreCase("enter")) {
                Player player = (Player) sender;
                RiftEvent2.getInstabilityUtilInstance().bossBar.addPlayer(player);
                player.teleport(RiftEvent2.getInstance().RiftCenter);
            }
            if (args[0].equalsIgnoreCase("ResetRift")) {
                Player player = (Player) sender;
                RiftEvent2.getWorldUtilsInstanceInstance().unloadWorldAndSheduleReset();
            }
            if (args[0].equalsIgnoreCase("DEBUG")) {
                AnimalEvent.AnimalSpawner();
            }
            if (args[0].equalsIgnoreCase("DEBUG2")) {
                Player player = (Player) sender;
                RiftEvent2.getSoundEventInstance().PlayRandomSoundOnce(player);
            }


        }
        return true;
    }
}
