package WorldUtils;

import RiftEvent2.RiftEvent2;
import org.bukkit.*;
import org.bukkit.generator.structure.Structure;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.StructureSearchResult;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class WorldUtils {

    public void resetRiftEvent(String worldName, List<Structure> Allstructures){
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

        //world type
        WorldType worldType = getRandomWorldType();

        //reset world and create it
        removeAndRecreateWorld(worldName, SelectedEnviroment, worldType);

        //Find Structure
        Location StructureLoc = getStructureLocation(SelectedStructure, Bukkit.getWorld(worldName));
        RiftEvent2.getInstance().RiftCenter = StructureLoc;

        //make platform
        RiftEvent2.getInstabilityUtilInstance().makeSafePlatform(StructureLoc);

        RiftEvent2.getInstabilityUtilInstance().instabilityTicker();

        RiftEvent2.getInstance().gameState = 1;

        Bukkit.getLogger().info("" + SelectedStructure.getKey());
        Bukkit.getLogger().info("" + worldType);
        Bukkit.getLogger().info("" + StructureLoc);

    }

    //use to safely unload world to ensure the world is fully unloaded before scheduling a reset
    public void unloadworldandshedulereset() {

        Bukkit.unloadWorld(RiftEvent2.getInstance().WorldName, false);//no need to save chunks if the world is being reset

        //have to wait to delete folder as unloading the world is not instant, 5 seconds is a generous ammount
        try {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    resetRiftEvent(RiftEvent2.getInstance().WorldName, RiftEvent2.getInstance().Structures);
                }
            }.runTaskLater(RiftEvent2.getInstance(), 5 * 20);
        } catch (UnsupportedOperationException e) {
            // Log a warning message
            Bukkit.getLogger().warning("[RiftEvent] Failed to schedule Instability Ticker: " + e.getMessage());
        }
    }



    //Environment: overworld, nether, end
    //WorldType: Amplified, Flat, Large Biomes, Normal
    //ensure world is already **FULLY** unloaded before use
    public void removeAndRecreateWorld(String worldName, World.Environment environment, WorldType worldType){

        //delete old world
        //Might want to look into making this async as this operation could cause the server to hang for a moment
        deleteWorld(Paths.get(Bukkit.getWorldContainer().getPath() + File.separator + worldName).toFile());

        //Set up the new worlds settings
        WorldCreator NewWorld = new WorldCreator(worldName);
        NewWorld.environment(environment);//overworld, nether, end
        if(environment == World.Environment.NORMAL){
            NewWorld.type(worldType);//Amplified or normal
        }

        //Create the new world based off the settings above
        Bukkit.createWorld(NewWorld);

    }

    public static boolean deleteWorld(File path) {
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
        return(path.delete());
    }
    public static void centerWorldBorderAndWorldSpawn(Location centerLoc, int WBWidth) {
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

    public static Structure getRandomStructure(List<Structure> Structures){

        int max = Structures.size() - 1;
        int min = 0;
        int range = max - min + 1;

        int rand = (int)(Math.random() * range) + min;
        return Structures.get(rand);
    }

    public WorldType getRandomWorldType(){

        int max = 5;
        int min = 0;
        int range = max - min + 1;

        int rand = (int)(Math.random() * range) + min;
        if(rand <= 3)
            return WorldType.NORMAL;
        else
            return WorldType.AMPLIFIED;
    }
}
