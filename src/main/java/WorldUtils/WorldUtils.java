package WorldUtils;

import GenericUtils.RandomUtils;
import RiftEvent2.RiftEvent2;
import net.kyori.adventure.util.TriState;
import org.bukkit.*;
import org.bukkit.generator.structure.Structure;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.StructureSearchResult;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class WorldUtils{

    //use to safely unload world to ensure the world is fully unloaded before scheduling a reset
    public void unloadWorldAndSheduleReset() {

        Bukkit.getLogger().info("Unloading Rift Event World... (if exists)");

        //only unload if the world exists
        if(Bukkit.getWorld(RiftEvent2.getInstance().WorldName) != null) {
            Bukkit.unloadWorld(RiftEvent2.getInstance().WorldName, false);//no need to save chunks if the world is being reset
        }

        //have to wait to delete folder as unloading the world is not instant, 2 ticks should be enough
        try {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    resetRiftEvent(RiftEvent2.getInstance().WorldName, RiftEvent2.getInstance().Structures);
                }
            }.runTaskLater(RiftEvent2.getInstance(), 2);
        } catch (UnsupportedOperationException e) {
            // Log a warning message
            Bukkit.getLogger().warning("[RiftEvent] Failed to schedule Open Rift: " + e.getMessage());
        }
    }

    private static Structure getRandomStructure(List<Structure> Structures){
        int rand = RandomUtils.Randomint(Structures.size() - 1,0);
        return Structures.get(rand);
    }

    private WorldType getRandomWorldType(){
        int rand = RandomUtils.Randomint(5,0);
        if(rand <= 3)
            return WorldType.NORMAL;
        else
            return WorldType.AMPLIFIED;
    }

    private void resetRiftEvent(String worldName, List<Structure> Allstructures){
        RiftEvent2.getInstance().gameState = 0;

        //get the random structure
        World.Environment SelectedEnviroment = World.Environment.NORMAL;
        Structure SelectedStructure = getRandomStructure(Allstructures);
        if(SelectedStructure == Structure.BASTION_REMNANT){
            RiftEvent2.getInstance().isNether = true;
            SelectedEnviroment = World.Environment.NETHER;
        }

        if(SelectedStructure == Structure.END_CITY){
            RiftEvent2.getInstance().isEnd = true;
            SelectedEnviroment = World.Environment.THE_END;
        }

        if(SelectedStructure == Structure.MONUMENT){
            RiftEvent2.getInstance().isOcean = true;
        }


        //world type
        WorldType worldType = getRandomWorldType();

        //reset world and create it
        deleteWorldAsyncAndRecreateSync(Paths.get(Bukkit.getWorldContainer().getPath() + File.separator + worldName).toFile(), worldName, SelectedEnviroment, worldType, SelectedStructure);
    }



    //Environment: overworld, nether, end
    //WorldType: Amplified, Flat, Large Biomes, Normal
    //ensure world is already **FULLY** unloaded before use
    private static void CreateWorld(String worldName, World.Environment environment, WorldType worldType, Structure SelectedStructure){

        //Set up the new worlds settings
        WorldCreator NewWorld = new WorldCreator(worldName);
        NewWorld.environment(environment);//overworld, nether, end
        NewWorld.keepSpawnLoaded(TriState.FALSE);//needed to stop server hang as the entire spawn of new world will try to generate without it
        NewWorld.type(worldType);

        //Create the new world based off the settings above
        Bukkit.createWorld(NewWorld);

        //Find Structure
        Location StructureLoc = getStructureLocation(SelectedStructure, Bukkit.getWorld(worldName));
        RiftEvent2.getInstance().RiftCenter = StructureLoc;

        //make platform
        RiftEvent2.getInstabilityUtilInstance().makeSafePlatform(StructureLoc);

        RiftEvent2.getInstabilityUtilInstance().instabilityTicker();

        RiftEvent2.getInstabilityUtilInstance().setupBossBar();

        centerWorldBorderAndWorldSpawn(RiftEvent2.getInstance().RiftCenter, 500);

        RiftEvent2.getInstance().gameState = 1;

        Bukkit.getLogger().info("---NEW RIFT IS ACTIVE---");

        Bukkit.getLogger().info("Structure Name:" + SelectedStructure.getKey());
        Bukkit.getLogger().info("World Type:" + worldType);
        Bukkit.getLogger().info("Structure Location: " + StructureLoc);


    }

    private static void deleteWorldAsyncAndRecreateSync(File path, String worldName, World.Environment environment, WorldType worldType, Structure SelectedStructure) {
        try {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getLogger().info("Deleting Old Rift Event Files (if existent)");
                    deleteWorld(path);
                    //then create the world after files deleted
                    try {
                        BukkitTask task = new BukkitRunnable() {
                            @Override
                            public void run() {
                                CreateWorld(worldName, environment, worldType, SelectedStructure);
                            }
                        }.runTask(RiftEvent2.getInstance());
                    } catch (UnsupportedOperationException e) {
                        // Log a warning message
                        Bukkit.getLogger().warning("[RiftEvent] Failed to schedule world creation: " + e.getMessage());
                    }
                }
            }.runTaskAsynchronously(RiftEvent2.getInstance());
        } catch (UnsupportedOperationException e) {
            // Log a warning message
            Bukkit.getLogger().warning("[RiftEvent] Failed to schedule old world file deletion: " + e.getMessage());
        }
    }

    private static void deleteWorld(File path) {

        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
    }
    private static void centerWorldBorderAndWorldSpawn(Location centerLoc, int WBWidth) {
        World world = centerLoc.getWorld();
        world.getWorldBorder().setCenter(centerLoc);
        world.getWorldBorder().setSize(WBWidth, 0);


        centerLoc.setY(world.getHighestBlockYAt(centerLoc));
        world.setSpawnLocation(centerLoc);
    }

    public static Location getStructureLocation(Structure selectedStructure, World world) {
        Location location = world.getBlockAt(0,0,0).getLocation();//block to search around

        StructureSearchResult result = world.locateNearestStructure(location, selectedStructure, 100000, true);
        if (result != null) {
            return result.getLocation().toHighestLocation();
        }
        return null;
    }
}