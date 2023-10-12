package edebe.doglib.api.event.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraftforge.eventbus.api.Event;

import java.util.Set;

public class ModelLayersCreationEvent extends Event {
    private final Set<ModelLayerLocation> set;

    public ModelLayersCreationEvent(Set<ModelLayerLocation> set) {
        this.set = set;
    }

    public Set<ModelLayerLocation> getModelLayerLocations() {
        return this.set;
    }
}