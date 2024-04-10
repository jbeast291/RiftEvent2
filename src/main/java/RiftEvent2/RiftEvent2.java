package RiftEvent2;

import Commands.RiftEventCmd;
import InstabilityUtils.InstabilityEvent.*;
import InstabilityUtils.InstabilityHandler;
import WorldUtils.WorldUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.generator.structure.Structure;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class RiftEvent2 extends JavaPlugin {

    private static RiftEvent2 instance;
    private static WorldUtils WorldUtilsInstance;
    private static InstabilityHandler InstabilityInstance;
    private static NpcHandler NpcHandlerInstance;
    private static SoundEvent SoundEventInstance;
    private static AnimalEvent AnimalEventInstance;
    private static BlockEvent BlockEventInstance;
    private static ChunkEvent ChunkEventInstance;

    public String WorldName = "RiftEvent";

    public final List<Structure> Structures = new ArrayList<Structure>(){{
        add(Structure.ANCIENT_CITY);
        add(Structure.MONUMENT);//ocean monument
        add(Structure.MANSION);
        add(Structure.BASTION_REMNANT);
        add(Structure.END_CITY);
        add(Structure.TRAIL_RUINS);
    }};

    //How long the rift should stay open, once loaded, in minutes
    public static int TimeRiftOpenMin = 2;

    //Current Rift info
    //-----------

        // -1 Inactive
        // 0: starting up/shuting down
        // 1: Active
    public int gameState;
    public String structureName;
    public Location RiftCenter;
    public boolean isNether;
    public boolean isEnd;
    public boolean isOcean;
    public int percentInstab;

    //-----------

    @Override
    public void onEnable() {

        Bukkit.getLogger().info("§6pri§kv§r§6ate§6 §dvoid §r§9Gr§ke§r§9et§r§9Inte§kr§r§9loper§f() { §f§kUSER§r§f.mes§ks§r§fage.sen§kd§r§fDec§ko§r§fded(You Have Been Deemed To Be In Violation Of Cause 30 In The Interdimensional Harvesting Of Resources Act) §6ret§ku§r§6rn§f; §7}");
        saveDefaultConfig();

        //Instances
        instance = this;
        WorldUtilsInstance = new WorldUtils();
        InstabilityInstance = new InstabilityHandler();
        NpcHandlerInstance = new NpcHandler();
        SoundEventInstance = new SoundEvent();
        AnimalEventInstance = new AnimalEvent();
        BlockEventInstance = new BlockEvent(this);//implements listener
        ChunkEventInstance = new ChunkEvent();

        //
        InstabilityInstance.OpenRift();
        InstabilityInstance.setupBossBar();

        //TEMP

        //commands
        getCommand("riftevent").setExecutor(new RiftEventCmd());

    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        // Plugin shutdown logic
    }

    public static RiftEvent2 getInstance() {
        return instance;
    }

    public static InstabilityHandler getInstabilityUtilInstance() {
        return InstabilityInstance;
    }

    public static WorldUtils getWorldUtilsInstanceInstance() {
        return WorldUtilsInstance;
    }

    public static NpcHandler getNpcHandlerInstance() {
        return NpcHandlerInstance;
    }
    public static SoundEvent getSoundEventInstance() {
        return SoundEventInstance;
    }
    public static AnimalEvent getAnimalEventInstance() {
        return AnimalEventInstance;
    }
    public static BlockEvent getBlockEventInstance() {
        return BlockEventInstance;
    }
    public static ChunkEvent getChunkEventInstance() {
        return ChunkEventInstance;
    }
}
