package InstabilityUtils.InstabilityEvent;

import GenericUtils.RandomUtils;
import GenericUtils.RegionUtils;
import RiftEvent2.RiftEvent2;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.LookClose;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NpcHandler {
    public void generateNPC(Location location, Player player) {

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
        npc.getNavigator().setTarget(player, false);
        //scheduleNpcMoveToPlayer(npc, player);
    }
    public void scheduleNpcMoveToPlayer(NPC npc, Player player){
        try {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    if(!player.isOnline()) {
                        scheduleNpcMoveToPlayer(npc, player);
                        return;
                    }
                    if(player.getWorld() != Bukkit.getWorld(RiftEvent2.getInstance().WorldName)) {
                        scheduleNpcMoveToPlayer(npc, player);
                        return;
                    }

                    if(!npc.isSpawned() || !npc.getNavigator().canNavigateTo(player.getLocation())) {
                        scheduleNpcMoveToPlayer(npc, player);
                        return;
                    }
                    npc.getNavigator().setTarget(player, false);
                    scheduleNpcMoveToPlayer(npc, player);
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
        Bukkit.getWorld(RiftEvent2.getInstance().WorldName).getPlayers().forEach(player -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 60, 1, false, false));

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
                    }
                }.runTaskLater(RiftEvent2.getInstance(), 25);
            } catch (UnsupportedOperationException e) {
                // Log a warning message
                Bukkit.getLogger().warning("[RiftEvent] Failed to schedule NPC sound"  + e.getMessage());
            }
            try {
                BukkitTask task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        generateNPC(new Location(player.getWorld(), player.getLocation().x() + 3, player.getLocation().y(), player.getLocation().z()), player);
                        generateNPC(new Location(player.getWorld(), player.getLocation().x() - 3, player.getLocation().y(), player.getLocation().z()), player);
                        generateNPC(new Location(player.getWorld(), player.getLocation().x(), player.getLocation().y(), player.getLocation().z() + 3), player);
                        generateNPC(new Location(player.getWorld(), player.getLocation().x(), player.getLocation().y(), player.getLocation().z() - 3), player);

                    }
                }.runTaskLater(RiftEvent2.getInstance(), 30);
            } catch (UnsupportedOperationException e) {
                // Log a warning message
                Bukkit.getLogger().warning("[RiftEvent] Failed to schedule NPC generation"  + e.getMessage());
            }
        });
    }
}
