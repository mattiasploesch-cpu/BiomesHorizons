package biomesoplenty.client.render.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderRosester extends RenderChicken {

    public RenderRosester(ModelBase par1ModelBase, float par2) {
        super(par1ModelBase, par2);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return new ResourceLocation("biomesoplenty:textures/mobs/rosester.png");
    }
}
