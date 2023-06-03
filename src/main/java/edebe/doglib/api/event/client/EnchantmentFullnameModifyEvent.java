package edebe.doglib.api.event.client;

import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.eventbus.api.Event;

public class EnchantmentFullnameModifyEvent extends Event {
    private final String descriptionId;
    private final int level;
    private final int maxLevel;
    private final boolean isCurse;
    private MutableComponent fullname;

    public EnchantmentFullnameModifyEvent(String descriptionId, int level, int maxLevel, boolean isCurse, MutableComponent fullname) {
        this.descriptionId = descriptionId;
        this.level = level;
        this.maxLevel = maxLevel;
        this.isCurse = isCurse;
        this.fullname = fullname;
    }

    public String getDescriptionId() {
        return descriptionId;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public boolean isCurse() {
        return isCurse;
    }

    public MutableComponent getFullname() {
        return fullname;
    }

    public void setFullname(MutableComponent fullname) {
        this.fullname = fullname;
    }
}
