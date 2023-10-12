package edebe.doglib.api.register;

import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import edebe.doglib.api.client.renderer.item.ItemStackRenderer;
import edebe.doglib.api.helper.ResourceLocationHelper;
import edebe.doglib.register.RegisteredObjects;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.IItemDecorator;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.HashMap;
import java.util.Map;

public class ItemBuilderTransformers {
    public static final Map<ResourceLocation, ItemColor> COLORS = new HashMap<>();
    public static final Map<ResourceLocation, Object[]> PROPERTIES = new HashMap<>();
    public static final Map<ResourceLocation, IItemDecorator> DECORATIONS = new HashMap<>();
    public static final Map<ResourceLocation, ItemStackRenderer> ITEM_RENDERS = new HashMap<>();
    public static final Map<ResourceLocation, ICurioRenderer> CURIOS_RENDERS = new HashMap<>();

    public static boolean hasItemColor(Item item) {
        return COLORS.containsKey(RegisteredObjects.getKeyOrThrow(item));
    }

    public static boolean hasItemProperty(Item item) {
        return PROPERTIES.containsKey(RegisteredObjects.getKeyOrThrow(item));
    }

    public static boolean hasItemDecorator(Item item) {
        return DECORATIONS.containsKey(RegisteredObjects.getKeyOrThrow(item));
    }

    public static boolean hasItemStackRenderer(Item item) {
        return ITEM_RENDERS.containsKey(RegisteredObjects.getKeyOrThrow(item));
    }

    public static boolean hasCuriosItemRenderer(Item item) {
        return CURIOS_RENDERS.containsKey(RegisteredObjects.getKeyOrThrow(item));
    }

    public static ItemColor getItemColor(Item item) {
        return COLORS.get(RegisteredObjects.getKeyOrThrow(item));
    }

    public static Object[] getItemProperty(Item item) {
        return PROPERTIES.get(RegisteredObjects.getKeyOrThrow(item));
    }

    public static IItemDecorator getItemDecorator(Item item) {
        return DECORATIONS.get(RegisteredObjects.getKeyOrThrow(item));
    }

    public static ItemStackRenderer getItemStackRenderer(Item item) {
        return ITEM_RENDERS.get(RegisteredObjects.getKeyOrThrow(item));
    }

    public static ICurioRenderer getCuriosItemRenderer(Item item) {
        return CURIOS_RENDERS.get(RegisteredObjects.getKeyOrThrow(item));
    }

    public static void setItemColor(ResourceLocation location, ItemColor color) {
        COLORS.put(location, color);
    }

    public static void setItemProperty(ResourceLocation location, ResourceLocation name, ItemPropertyFunction function) {
        PROPERTIES.put(location, new Object[]{name, function});
    }

    public static void setItemDecorator(ResourceLocation location, IItemDecorator decorator) {
        DECORATIONS.put(location, decorator);
    }

    public static void setItemStackRenderer(ResourceLocation location, ItemStackRenderer renderer) {
        ITEM_RENDERS.put(location, renderer);
    }

    public static void setCuriosItemRenderer(ResourceLocation location, ICurioRenderer renderer) {
        CURIOS_RENDERS.put(location, renderer);
    }

    public static <T extends Item, P> NonNullUnaryOperator<ItemBuilder<T, P>> setColor(ItemColor color) {
        return builder -> {
            setItemColor(ResourceLocationHelper.getResource(builder), color);
            return builder;
        };
    }

    public static <T extends Item, P> NonNullUnaryOperator<ItemBuilder<T, P>> setProperty(ResourceLocation name, ItemPropertyFunction function) {
        return builder -> {
            setItemProperty(ResourceLocationHelper.getResource(builder), name, function);
            return builder;
        };
    }

    public static <T extends Item, P> NonNullUnaryOperator<ItemBuilder<T, P>> setItemDecorator(NonNullSupplier<IItemDecorator> decorator) {
        return builder -> {
            setItemDecorator(ResourceLocationHelper.getResource(builder), decorator.get());
            return builder;
        };
    }

    public static <T extends Item, P> NonNullUnaryOperator<ItemBuilder<T, P>> setItemRenderer(NonNullSupplier<ItemStackRenderer> renderer) {
        return builder -> {
            setItemStackRenderer(ResourceLocationHelper.getResource(builder), renderer.get());
            return builder;
        };
    }

    public static <T extends Item, P> NonNullUnaryOperator<ItemBuilder<T, P>> setCuriosRenderer(NonNullSupplier<ICurioRenderer> renderer) {
        return builder -> {
            setCuriosItemRenderer(ResourceLocationHelper.getResource(builder), renderer.get());
            return builder;
        };
    }
}
