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
            }
            if (args[0].equalsIgnoreCase("npc")) {
                RiftEvent2.getNpcHandlerInstance().PlaceNpcsAroundPlayer();
            }
            if (args[0].equalsIgnoreCase("DEBUG")) {
                RiftEvent2.getChunkEventInstance().CancelAllTasks();
            }
            if (args[0].equalsIgnoreCase("DEBUG2")) {
                RiftEvent2.getChunkEventInstance().ChunkLooper();

            }
            if (args[0].equalsIgnoreCase("TESTMSG")) {
                Player player = (Player) sender;
                player.sendMessage("§6pri§kv§r§6ate§6 §dvoid §r§9Gr§ke§r§9et§r§9Inte§kr§r§9loper§7w() { §f§kUSER§r§f.mes§ks§r§fage.sen§kd§r§fDec§ko§r§fded(You Have Been Deemed To Be In Violation Of Clause: " + RandomUtils.Randomint(420, 69) +" In The Interdimensional Harvesting Of Resources Act) §6ret§ku§r§6rn§f; §7}");

            }


        }
        return true;
    }
}
