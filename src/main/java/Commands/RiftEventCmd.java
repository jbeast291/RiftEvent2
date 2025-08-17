package Commands;

import GenericUtils.RandomUtils;
import InstabilityUtils.InstabilityEvent.AnimalEvent;
import InstabilityUtils.InstabilityEvent.NpcHandler;
import InstabilityUtils.InstabilityEvent.ParticleEvent;
import InstabilityUtils.InstabilityEvent.TimeEvent;
import RiftEvent2.RiftEvent2;
import WorldUtils.WorldUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;
import java.util.Random;

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
                NpcHandler.PlaceNpcsAroundPlayer();
            }
            if (args[0].equalsIgnoreCase("DEBUG")) {
                Player player = (Player) sender;
                ParticleEvent.SpawnParticlesInRegion((int) Math.round(player.getLocation().x() - 15),
                        (int) Math.round(player.getLocation().y() - 15),
                        (int) Math.round(player.getLocation().z() - 15),
                        (int) Math.round(player.getLocation().x() + 15),
                        (int) Math.round(player.getLocation().y() + 15),
                        (int) Math.round(player.getLocation().z() + 15), player.getWorld(), 70);
            }
            if (args[0].equalsIgnoreCase("DEBUG2")) {
                org.bukkit.structure.Structure s = Bukkit.getStructureManager().loadStructure(Objects.requireNonNull(NamespacedKey.fromString("minecraft:woodland_mansion/2x2_b4")));

                Player player = (Player) sender;
                try {
                    s.place(player.getLocation(), true, StructureRotation.NONE, Mirror.NONE, 0, 1, Random.class.newInstance());
                } catch (InstantiationException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        return true;
    }
}
