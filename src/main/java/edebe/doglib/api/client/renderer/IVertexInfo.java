package edebe.doglib.api.client.renderer;

import com.mojang.blaze3d.vertex.VertexConsumer;

public interface IVertexInfo {
    VertexConsumer info(VertexConsumer vertex);
}