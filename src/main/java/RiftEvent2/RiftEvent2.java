package RiftEvent2;

import Commands.RiftEventCmd;
import InstabilityUtils.InstabilityUtils;
import WorldUtils.WorldUtils;
import org.bukkit.Location;
import org.bukkit.generator.structure.Structure;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class RiftEvent2 extends JavaPlugin {

    private static RiftEvent2 instance;
    private static WorldUtils WorldUtilsInstance;
    private static InstabilityUtils InstabilityInstance;

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
    public static int TimeRiftOpenMin = 1;

    public Location RiftCenter;

    //Current Rift info
    //-----------

    public int time;

        // -1 Inactive
        // 0: starting up/shuting down
        // 1: Active
    public int gameState;

    public String structureName;

    public int spawnX;
    public int spawnY;
    public int spawnZ;
    public boolean isNether;
    public boolean isEnd;
    public int percentInstab;

    //-----------

    @Override
    public void onEnable() {
        //Instances
        instance = this;
        WorldUtilsInstance = new WorldUtils();
        InstabilityInstance = new InstabilityUtils();

        WorldUtilsInstance.resetRiftEvent(WorldName, Structures);
        InstabilityInstance.setupBossBar();

        //commands
        getCommand("riftevent").setExecutor(new RiftEventCmd());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static RiftEvent2 getInstance() {
        return instance;
    }

    public static InstabilityUtils getInstabilityUtilInstance() {
        return InstabilityInstance;
    }

    public static WorldUtils getWorldUtilsInstanceInstance() {
        return WorldUtilsInstance;
    }
}
