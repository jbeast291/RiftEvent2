package WorldUtils;

import GenericUtils.RandomUtils;
import RiftEvent2.RiftEvent2;
import net.kyori.adventure.util.TriState;
import org.bukkit.*;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.structure.Structure;
import org.bukkit.util.StructureSearchResult;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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

        if(SelectedStructure == Structure.MONUMENT){
            RiftEvent2.getInstance().isOcean = true;
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

        centerWorldBorderAndWorldSpawn(RiftEvent2.getInstance().RiftCenter, 500);

        RiftEvent2.getInstance().gameState = 1;

        Bukkit.getLogger().info("---NEW RIFT IS ACTIVE---");

        Bukkit.getLogger().info("Structure Name:" + SelectedStructure.getKey());
        Bukkit.getLogger().info("World Type:" + worldType);
        Bukkit.getLogger().info("Structure Location: " + StructureLoc);

    }

    //use to safely unload world to ensure the world is fully unloaded before scheduling a reset
    public void unloadWorldAndSheduleReset() {

        Bukkit.getLogger().info("Unloading Rift Event World...");
        Bukkit.unloadWorld(RiftEvent2.getInstance().WorldName, false);//no need to save chunks if the world is being reset

        //have to wait to delete folder as unloading the world is not instant, 5 seconds is a generous ammount
        Bukkit.getLogger().info("Creating new Rift Event World...");
        resetRiftEvent(RiftEvent2.getInstance().WorldName, RiftEvent2.getInstance().Structures);
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
        NewWorld.keepSpawnLoaded(TriState.FALSE);

        if(environment == World.Environment.NORMAL){
            NewWorld.type(worldType);//Amplified or normal
        }

        //Create the new world based off the settings above
        Bukkit.createWorld(NewWorld);
    }
    public static void createStructure(Location location, NamespacedKey structureKey) {
        org.bukkit.structure.@Nullable Structure structure = Bukkit.getStructureManager().getStructure(structureKey);

        if (structure == null) {
            structure = Bukkit.getStructureManager().loadStructure(structureKey); // Try and load it if it isn't already
            if (structure == null) {
                return; // The structure doesn't exist
            }
        }

        structure.place(
                location,
                true, // include entities
                StructureRotation.NONE,
                Mirror.NONE,
                -1, // a pallet index. If you don't know what this does, -1 is probably fine. It will pick a random one
                1.0F, // integrity of the structure. 1.0 means it will place all the blocks in the structure
                ThreadLocalRandom.current() // or whatever Random instance you want
        );
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
        int rand = RandomUtils.Randomint(Structures.size() - 1,0);
        return Structures.get(rand);
    }

    public WorldType getRandomWorldType(){
        int rand = RandomUtils.Randomint(5,0);
        if(rand <= 3)
            return WorldType.NORMAL;
        else
            return WorldType.AMPLIFIED;
    }
}
