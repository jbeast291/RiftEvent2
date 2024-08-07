package WorldUtils;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.generator.ChunkGenerator;

import net.minecraft.server.level.ChunkMap;
import net.minecraft.world.level.chunk.ChunkGenerator;

import java.lang.reflect.Field;

public class worldEvents  implements Listener {



    @EventHandler(priority = EventPriority.LOW)
    public void onWorldLoad(WorldInitEvent event) {

        final World world = event.getWorld();

        try {

            // get the playerChunkMap where the ChunkGenerator is store, that we need to override
            ChunkMap
            ChunkMap chunkMap = world.getHandle().getChunkProvider().chunkMap;

            // get the ChunkGenerator from the PlayerChunkMap
            Field ChunkGeneratorField = ChunkMap.class.getDeclaredField("r");
            ChunkGeneratorField.setAccessible(true);
            Object chunkGeneratorObject = ChunkGeneratorField.get(chunkMap);

            // return, if the chunkGeneratorObject is not an instance of ChunkGenerator
            if (!(chunkGeneratorObject instanceof ChunkGenerator)) {
                return;
            }

            ChunkGenerator chunkGenerator = (ChunkGenerator) chunkGeneratorObject;
=            Map<StructureFeature<?>, StructureFeatureConfiguration> structureSettings = chunkGenerator.getSettings().structureConfig();

            if(structureSettings.containsKey(StructureFeature.NETHER_BRIDGE)) {
                structureSettings.put(StructureFeature.NETHER_BRIDGE, new StructureFeatureConfiguration(configuration.getSpacing(), configuration.getSeparation(), configuration.getSalt()));
            }

        } catch (final Exception e) {
            throw new RuntimeException("Unexpected error while hook into world " + world.getName(), e);
        }
    }
}
