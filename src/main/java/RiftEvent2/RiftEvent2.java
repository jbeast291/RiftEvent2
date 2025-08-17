package RiftEvent2;

import Commands.RiftEventCmd;
import Events.PlayerEvents;
import InstabilityUtils.InstabilityEvent.*;
import InstabilityUtils.InstabilityHandler;
import WorldUtils.WorldUtils;
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
    private static PlayerEvents PlayerEventInstance;
    private static ChunkEvent ChunkEventInstance;
    private static EffectEvent EffectEventInstance;
    private static ChatEvent ChatEventInstance;
    private static ParticleEvent ParticleEventInstance;

    private static TimeEvent TimeEventInstance;

    public String WorldName = "RiftEvent";

    public final List<Structure> Structures = new ArrayList<Structure>(){{
        add(Structure.ANCIENT_CITY);
        add(Structure.MONUMENT);//ocean monument
        add(Structure.MANSION);
        add(Structure.BASTION_REMNANT);
        add(Structure.END_CITY);
        add(Structure.TRAIL_RUINS);
        add(Structure.TRIAL_CHAMBERS);
    }};

    //How long the rift should stay open, once loaded, in minutes
    public static int TimeRiftOpenMin = 20;

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
        saveDefaultConfig();

        //Instances
        instance = this;
        WorldUtilsInstance = new WorldUtils();
        InstabilityInstance = new InstabilityHandler();

        //
        //InstabilityInstance.OpenRift();
        //InstabilityInstance.setupBossBar();

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
}
