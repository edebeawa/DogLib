package edebe.doglib.api.register;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class RegisterManager<T> {
    private final String modid;

    public RegisterManager(String modid) {
        this.modid = modid;
    }

    private final Map<ResourceLocation, RegisterObject<?>> registry = new LinkedHashMap<>();

    public RegisterObject<T> add(RegisterObject<T> object) {
        this.registry.put(object.getResourceLocation(), object);
        return object;
    }

    public RegisterObject<T> add(ResourceLocation key, T object) {
        return this.add(new RegisterObject<>(key, object));
    }

    public RegisterObject<T> add(String name, T object) {
        return this.add(new ResourceLocation(this.modid, name), object);
    }

    public RegisterObject<T> add(RegisterObject<Block> blockObject, Item item) {
        return this.add(blockObject.getResourceLocation(), (T) item);
    }

    public RegisterObject<T> add(RegisterObject<Block> blockObject, Item.Properties properties) {
        return this.add(blockObject.getResourceLocation(), (T) new BlockItem(blockObject.getObject(), properties));
    }

    public <V extends BlockEntity> RegisterObject<BlockEntityType<V>> add(String name, BlockEntityType.BlockEntitySupplier<V> type, Block... blocks) {
        RegisterObject<BlockEntityType<V>> object = new RegisterObject<>(new ResourceLocation(this.modid, name), BlockEntityType.Builder.of(type, blocks).build(null));
        this.registry.put(object.getResourceLocation(), object);
        return object;
    }

    public <V extends Entity> RegisterObject<EntityType<V>> add(String name, EntityType.Builder<V> type) {
        RegisterObject<EntityType<V>> object = new RegisterObject<>(new ResourceLocation(this.modid, name), type.build(name));
        this.registry.put(object.getResourceLocation(), object);
        return object;
    }

    public RegisterObject<T> getValue(ResourceLocation key) {
        return (RegisterObject<T>) this.registry.get(key);
    }

    public Collection<RegisterObject<T>> getValues() {
        Map<ResourceLocation, RegisterObject<T>> map = new LinkedHashMap<>();
        for (ResourceLocation key : this.registry.keySet()) map.put(key, getValue(key));
        return map.values();
    }
}