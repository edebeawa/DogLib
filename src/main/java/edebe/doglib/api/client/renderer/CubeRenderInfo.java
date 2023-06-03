package edebe.doglib.api.client.renderer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.core.Direction;

public class CubeRenderInfo {
    private static final Vector3f NO_OFFSET = new Vector3f(0, 0, 0);
    private final Direction direction;
    private final Matrix4f matrix4f;
    private final Vector3f offset;

    private CubeRenderInfo(Direction direction, Vector3f vertex, Vector3f offset) {
        this.direction = direction;
        this.matrix4f = Matrix4f.createScaleMatrix(vertex.x(), vertex.y(), vertex.z());
        this.offset = offset;
    }

    public static CubeRenderInfo create(Direction direction, Vector3f vertex) {
        return new CubeRenderInfo(direction, vertex, NO_OFFSET);
    }

    public static CubeRenderInfo create(Direction direction, Vector3f vertex, Vector3f offset) {
        return new CubeRenderInfo(direction, vertex, offset);
    }

    public static CubeRenderInfo[] create() {
        CubeRenderInfo[] cubeRenders = new CubeRenderInfo[Direction.values().length];
        for (Direction direction : Direction.values())
            cubeRenders[direction.get3DDataValue()] = new CubeRenderInfo(direction, new Vector3f(1, 1, 1), NO_OFFSET);
        return cubeRenders;
    }

    public static CubeRenderInfo[] create(Direction[] direction, Vector3f vertex) {
        CubeRenderInfo[] cubeRenders = new CubeRenderInfo[direction.length];
        for (int i = 0;i < direction.length;i++)
            cubeRenders[i] = new CubeRenderInfo(direction[i], vertex, NO_OFFSET);
        return cubeRenders;
    }

    public static CubeRenderInfo[] create(Direction[] direction, Vector3f... vertex) {
        CubeRenderInfo[] cubeRenders = new CubeRenderInfo[direction.length];
        for (int i = 0;i < direction.length;i++)
            cubeRenders[i] = new CubeRenderInfo(direction[i], vertex[i], NO_OFFSET);
        return cubeRenders;
    }

    public static CubeRenderInfo[] create(Direction[] direction, Vector3f[] vertex, Vector3f[] offset) {
        CubeRenderInfo[] cubeRenders = new CubeRenderInfo[direction.length];
        for (int i = 0;i < direction.length;i++)
            cubeRenders[i] = new CubeRenderInfo(direction[i], vertex[i], offset[i]);
        return cubeRenders;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void render(VertexConsumer renderer, Matrix4f matrix4f, Vector3f scale, IVertexInfo vertexInfo1, IVertexInfo vertexInfo2, IVertexInfo vertexInfo3, IVertexInfo vertexInfo4) {
        this.matrix4f.multiply(this.matrix4f);

        Vector3f vertex1;
        Vector3f vertex2;
        Vector3f vertex3;
        Vector3f vertex4;

        switch (this.direction) {
            case WEST -> {
                vertex1 = getVertex(scale, 1, 1, 1);
                vertex2 = getVertex(scale, 1, 0, 1);
                vertex3 = getVertex(scale, 1, 0, 0);
                vertex4 = getVertex(scale, 1, 1, 0);
            }
            case EAST -> {
                vertex1 = getVertex(scale, 0, 1, 0);
                vertex2 = getVertex(scale, 0, 0, 0);
                vertex3 = getVertex(scale, 0, 0, 1);
                vertex4 = getVertex(scale, 0, 1, 1);
            }
            case NORTH -> {
                vertex1 = getVertex(scale, 0, 1, 1);
                vertex2 = getVertex(scale, 0, 0, 1);
                vertex3 = getVertex(scale, 1, 0, 1);
                vertex4 = getVertex(scale, 1, 1, 1);
            }
            case SOUTH -> {
                vertex1 = getVertex(scale, 1, 1, 0);
                vertex2 = getVertex(scale, 1, 0, 0);
                vertex3 = getVertex(scale, 0, 0, 0);
                vertex4 = getVertex(scale, 0, 1, 0);
            }
            case UP -> {
                vertex1 = getVertex(scale, 0, 1, 0);
                vertex2 = getVertex(scale, 0, 1, 1);
                vertex3 = getVertex(scale, 1, 1, 1);
                vertex4 = getVertex(scale, 1, 1, 0);
            }
            case DOWN -> {
                vertex1 = getVertex(scale, 1, 0, 1);
                vertex2 = getVertex(scale, 0, 0, 1);
                vertex3 = getVertex(scale, 0, 0, 0);
                vertex4 = getVertex(scale, 1, 0, 0);
            }
            default -> {
                vertex1 = getVertex(new Vector3f(), 0, 0, 0);
                vertex2 = getVertex(new Vector3f(), 0, 0, 0);
                vertex3 = getVertex(new Vector3f(), 0, 0, 0);
                vertex4 = getVertex(new Vector3f(), 0, 0, 0);
            }
        }

        addVertex(renderer, matrix4f, vertex1, vertexInfo1);
        addVertex(renderer, matrix4f, vertex2, vertexInfo2);
        addVertex(renderer, matrix4f, vertex3, vertexInfo3);
        addVertex(renderer, matrix4f, vertex4, vertexInfo4);
    }

    private Vector3f getVertex(Vector3f scale, float x, float y, float z) {
        return new Vector3f(getScale(x, scale.x()), getScale(y, scale.y()), getScale(z, scale.z()));
    }

    private float getScale(float v, float s) {
        return v == 1 ? s : 0;
    }

    private void addVertex(VertexConsumer renderer, Matrix4f matrixIn, Vector3f vertexIn, IVertexInfo vertexInfo) {
        vertexInfo.info(renderer.vertex(matrixIn, vertexIn.x() - this.offset.x(), vertexIn.y() - this.offset.y(), vertexIn.z() - this.offset.z())).endVertex();
    }
}
