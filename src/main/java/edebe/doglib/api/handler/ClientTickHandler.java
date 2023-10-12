package edebe.doglib.api.handler;

public class ClientTickHandler {
    private static int tick = 0;
    private static float partialTick = 0;

    public static void setTick(int tick) {
        ClientTickHandler.tick = tick;
    }

    public static void setPartialTick(float partialTick) {
        ClientTickHandler.partialTick = partialTick;
    }

    public static void addTick() {
        ClientTickHandler.tick++;
    }

    public static void addPartialTick() {
        ClientTickHandler.partialTick++;
    }

    public static int getTick() {
        return ClientTickHandler.tick;
    }

    public static float getPartialTick() {
        return ClientTickHandler.partialTick;
    }
}