package Commands;

import GenericUtils.RandomUtils;
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
                RiftEvent2.getInstabilityUtilInstance().SafelyCloseRift(true);
                sender.sendMessage("The Rift has been reset.");
            }
            if (args[0].equalsIgnoreCase("npc")) {
                RiftEvent2.getNpcHandlerInstance().PlaceNpcsAroundPlayer();
            }
            if (args[0].equalsIgnoreCase("DEBUG")) {
                Player player = (Player) sender;
                RiftEvent2.getParticleEventInstance().SpawnParticlesInRegion((int) Math.round(player.getLocation().x() - 15),
                        (int) Math.round(player.getLocation().y() - 15),
                        (int) Math.round(player.getLocation().z() - 15),
                        (int) Math.round(player.getLocation().x() + 15),
                        (int) Math.round(player.getLocation().y() + 15),
                        (int) Math.round(player.getLocation().z() + 15), player.getWorld(), 70);
            }
            if (args[0].equalsIgnoreCase("DEBUG2")) {
                RiftEvent2.getChatEventInstance().ChatSpam();

            }


        }
        return true;
    }
}
