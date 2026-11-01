package biomesoplenty.client.models.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelPixie extends ModelBase {

    ModelRenderer Body;
    ModelRenderer LeftWing;
    ModelRenderer RightWing;

    public ModelPixie() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
        this.Body.setRotationPoint(-2.0F, 16.0F, -2.0F);
        this.Body.setTextureSize(64, 32);
        this.Body.mirror = true;
        this.setRotation(this.Body, 0.0F, 0.0F, 0.0F);
        this.LeftWing = new ModelRenderer(this, 32, 0);
        this.LeftWing.addBox(0.0F, 0.0F, -1.0F, 0, 4, 7);
        this.LeftWing.setRotationPoint(2.0F, 15.0F, 2.0F);
        this.LeftWing.setTextureSize(64, 32);
        this.LeftWing.mirror = true;
        this.setRotation(this.LeftWing, 0.0F, 0.0F, 0.0F);
        this.RightWing = new ModelRenderer(this, 50, 0);
        this.RightWing.addBox(0.0F, 0.0F, -1.0F, 0, 4, 7);
        this.RightWing.setRotationPoint(-2.0F, 15.0F, 2.0F);
        this.RightWing.setTextureSize(64, 32);
        this.RightWing.mirror = true;
        this.setRotation(this.RightWing, 0.0F, 0.0F, 0.0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.Body.render(f5);
        this.LeftWing.render(f5);
        this.RightWing.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.RightWing.rotateAngleY = -(MathHelper.cos(f2 * 1.7F) * (float) Math.PI * 0.5F);
        this.LeftWing.rotateAngleY = MathHelper.cos(f2 * 1.7F) * (float) Math.PI * 0.5F;
    }
}
