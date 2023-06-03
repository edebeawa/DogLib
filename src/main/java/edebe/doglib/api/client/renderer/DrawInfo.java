package edebe.doglib.api.client.renderer;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.math.Vector3d;

public class DrawInfo {
    private static final Vector3d NO_OFFSET = new Vector3d(0, 0, 0);
    private final Vector3d vertex;
    private final Vector3d offset;

    private DrawInfo(Vector3d vertex, Vector3d offset) {
        this.vertex = vertex;
        this.offset = offset;
    }

    public static DrawInfo[] create(Vector3d... vertex) {
        DrawInfo[] cubeRenders = new DrawInfo[vertex.length];
        for (int i = 0;i < vertex.length;i++)
            cubeRenders[i] = new DrawInfo(vertex[i], NO_OFFSET);
        return cubeRenders;
    }

    public static DrawInfo[] create(Vector3d[] vertex, Vector3d[] offset) {
        DrawInfo[] cubeRenders = new DrawInfo[vertex.length];
        for (int i = 0;i < vertex.length;i++)
            cubeRenders[i] = new DrawInfo(vertex[i], offset[i]);
        return cubeRenders;
    }

    public void addVertex(BufferBuilder renderer, IVertexInfo vertexInfo) {
        vertexInfo.info(renderer.vertex(this.vertex.x - this.offset.x, this.vertex.y - this.offset.y, this.vertex.z - this.offset.z)).endVertex();
    }
}
