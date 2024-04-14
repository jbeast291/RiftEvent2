package InstabilityUtils.InstabilityEvent;

import GenericUtils.RandomUtils;
import GenericUtils.RegionUtils;
import RiftEvent2.RiftEvent2;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.LookClose;
import net.citizensnpcs.trait.SkinTrait;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class NpcHandler {
    public void generateNPC(Location location, Player player, boolean isHostile) {

        //Saftey check
        if(player.getWorld() != Bukkit.getWorld(RiftEvent2.getInstance().WorldName))
            return;

        //create npc
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§5§kwdawdawdwaawdawd", location);

        //make npc randomly look
        LookClose lookClose = npc.getOrAddTrait(LookClose.class);
        lookClose.setRandomLook(true);
        lookClose.setRandomLookDelay(1);
        lookClose.setRandomLookPitchRange(-90, 90);

        //remove nametag
        //LivingEntity entity = (LivingEntity) npc.getEntity();
        //entity.setCustomNameVisible(!entity.isCustomNameVisible());
        //npc.data().setPersistent(NPC.Metadata.NAMEPLATE_VISIBLE, entity.isCustomNameVisible());

        //skin
        SkinTrait customSkin = npc.getOrAddTrait(SkinTrait.class);
        customSkin.setSkinPersistent("e1c8ad8d2a074085ab9a6a9620892cde",
                "kQgHAoc71QihKPyg8z82PRyT9hMjBvAARq6gkTTkJ6OKSL9aFNl6vg8" +
                        "AGyyVDftc57kXizfUZSAF+Mu+fUEcpc/kHIDkPJ9Ti/FOqvf/HrYrEJ" +
                        "HGkXHZmyF31IELHjT9JSYIWBFCxYhiJtXhl0OKS8hjxQMV9/KC+M73s1" +
                        "6yYEF/s7/T3XP5iTerHkD9FAfjBL4XGhu6SbZ0t4YlpF91z5yfnMLCw/" +
                        "V/+A70JW/SnrLwc1J5Qm2SjBNbJv0QISW+ADu3JFr+ot8uNr/lmV/qeM" +
                        "l/E6a227EblO6CSHouWMXWmxlJa8Ckmq0F/I3SzpVvp/dZkwaMUniRyP" +
                        "Aqho7qOwy+bawUPlcMHxml2xBKyJCjwpmYywwc9pmXqlywLQJOjj8D9O" +
                        "EW7i3ZbE1VDBruKSB7t9zyEd2MDejuch0l/a4P4CIzXjZaGo6aqBcZ7N" +
                        "Z1bwG5vENkMZlr242V0MwcuWhLI9y/90inEzG9ZBupGfG0oyfrZj4ECU" +
                        "gNgllguFoaY/ZLTkglbJ1bvI4NFLb36rcgBF1+Plc1d18N6znk/l47VL" +
                        "5XaGu6v2eOawIQza8HsdyY/2amlNT6kFjWoSdBjBuEyemohNtxIXcjFPq" +
                        "THw9JBMJNzt42cLvvJkcn8/oh0Cz4hRM1YIbiEw5flSM5eI2HWx+lxA1" +
                        "dE5KfUrwFOf0=", "ewogICJ0aW1lc3RhbXAiIDogMTcxMTQ4MT" +
                        "M3NDQ4MiwKICAicHJvZmlsZUlkIiA6ICJmYzFhOTdlNTgxM2Y0NDI2YTN" +
                        "mZTI4ZjJiNDc1ZjA4ZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJnZXRPbmxp" +
                        "bmVQbGF5ZXJzIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKI" +
                        "CAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIi" +
                        "A6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzI" +
                        "wMGFlZmQ5ZGFmOGIwMWI0NzhhMDkwMDg5YjMwNDI4NDk0MDFmNWU4YTc5" +
                        "ZDQyNDRlYmU3NDRiZmUzODEwOTIiCiAgICB9CiAgfQp9");
        //https://mineskin.org/e6129e39b40443f2973515239a957bd0

        //add npc to list
        NpcIds.add(npc.getId());

        //move npc
        scheduleNpcTargetToPlayer(npc, player, isHostile);
    }
    public void scheduleNpcTargetToPlayer(NPC npc, Player player, boolean isHostile){
        try {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    if(!player.isOnline()) {
                        scheduleNpcTargetToPlayer(npc, player, isHostile);
                        return;
                    }
                    if(player.getWorld() != Bukkit.getWorld(RiftEvent2.getInstance().WorldName)) {
                        scheduleNpcTargetToPlayer(npc, player, isHostile);
                        return;
                    }

                    if(!npc.isSpawned() || !npc.getNavigator().canNavigateTo(player.getLocation())) {
                        scheduleNpcTargetToPlayer(npc, player, isHostile);
                        return;
                    }
                    npc.getNavigator().setTarget(player, isHostile);
                    scheduleNpcTargetToPlayer(npc, player, isHostile);
                }
            }.runTaskLater(RiftEvent2.getInstance(), 200);
            MoveTasks.add(task.getTaskId());
        } catch (UnsupportedOperationException e) {
            // Log a warning message
            Bukkit.getLogger().warning("[RiftEvent] Failed to schedule Npc Move, This does not mean they couldn't find a path, rather an issue somewhere else: " + e.getMessage());
        }
    }

    public List<Integer> MoveTasks = new ArrayList<Integer>();
    public List<Integer> NpcIds = new ArrayList<Integer>();
    public void CancelAllTasksAndRemoveNpcs() {
        MoveTasks.forEach(taskId -> {
            Bukkit.getScheduler().cancelTask(taskId);
        });
        NpcIds.forEach(npcId -> {
            CitizensAPI.getNPCRegistry().deregister(CitizensAPI.getNPCRegistry().getById(npcId));
        });
        MoveTasks.clear();
        NpcIds.clear();
    }
    public void PlaceNpcsAroundPlayer() {


        boolean isHostileToPlayer = false;
        if (RandomUtils.Randomint(1,0) == 0){
            isHostileToPlayer = true;
        }

        //Don't really understand java but need this her for some reason :/
        boolean finalIsHostileToPlayer = isHostileToPlayer;

        Bukkit.getWorld(RiftEvent2.getInstance().WorldName).getPlayers().forEach(player -> {

            //Darkness
            player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 120, 1, false, false));

            //remove blocks around player and
            try {
                BukkitTask task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.playSound(player, Sound.ENTITY_WARDEN_HEARTBEAT, 100, 0);


                        RegionUtils.ReplaceBlockInRegionWithBlackList((int) Math.round(player.getLocation().x() - 3),
                                (int) Math.round(player.getLocation().y()),
                                (int) Math.round(player.getLocation().z() - 3),
                                (int) Math.round(player.getLocation().x() + 3),
                                (int) Math.round(player.getLocation().y() + 3),
                                (int) Math.round(player.getLocation().z() + 3), player.getWorld(), ChunkEvent.BlackListedBlocks, Material.AIR);


                        player.sendMessage("§0[§5Null Enti§kt§r§5y§0]§f You Have Been Deemed To Be In Violation Of Clause:" + RandomUtils.Randomint(420, 69) + " From The Interdimensional Harvesting Of Resources Act");
                    }
                }.runTaskLater(RiftEvent2.getInstance(), 30);
            } catch (UnsupportedOperationException e) {
                // Log a warning message
                Bukkit.getLogger().warning("[RiftEvent] Failed to schedule NPC sound"  + e.getMessage());
            }

            //spawn nps
            try {
                BukkitTask task = new BukkitRunnable() {
                    @Override
                    public void run() {

                        generateNPC(new Location(player.getWorld(), player.getLocation().x() + 3, player.getLocation().y(), player.getLocation().z()), player, finalIsHostileToPlayer);
                        generateNPC(new Location(player.getWorld(), player.getLocation().x() - 3, player.getLocation().y(), player.getLocation().z()), player, finalIsHostileToPlayer);
                        generateNPC(new Location(player.getWorld(), player.getLocation().x(), player.getLocation().y(), player.getLocation().z() + 3), player, finalIsHostileToPlayer);
                        generateNPC(new Location(player.getWorld(), player.getLocation().x(), player.getLocation().y(), player.getLocation().z() - 3), player, finalIsHostileToPlayer);
                        player.sendMessage("§0[§5Null Enti§kt§r§5y§0]§f DETERMINING THREAT LEVEL... §cno need to run ;)");

                    }
                }.runTaskLater(RiftEvent2.getInstance(), 60);
            } catch (UnsupportedOperationException e) {
                // Log a warning message
                Bukkit.getLogger().warning("[RiftEvent] Failed to schedule NPC generation"  + e.getMessage());
            }

            try {
                BukkitTask task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (finalIsHostileToPlayer) {
                            player.sendMessage("§0[§5Null Enti§kt§r§5y§0] §0{§aSuccess§0} §fTHREAT LEVEL: §4Severe");
                            player.sendMessage("§0[§5Null Enti§kt§r§5y§0]§f Hostile FORCE: §aAuthorized");
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§4 ALERT: §fYou are being hunted"));
                        }
                        if (!finalIsHostileToPlayer) {
                            player.sendMessage("§0[§5Null Enti§kt§r§5y§0] §0{§4Failure§0} §fTHREAT LEVEL: §5Unknown");
                            player.sendMessage("§0[§5Null Enti§kt§r§5y§0]§f Hostile FORCE: §4Denied");
                            player.sendMessage("§0[§5Null Enti§kt§r§5y§0]§f Continuing To Monitor §4Trespasser");
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§4 ALERT: §fYou are being §ofollowed"));
                        }
                    }
                }.runTaskLater(RiftEvent2.getInstance(), 140);
            } catch (UnsupportedOperationException e) {
                // Log a warning message
                Bukkit.getLogger().warning("[RiftEvent] Failed to schedule NPC generation"  + e.getMessage());
            }
        });
    }
}
