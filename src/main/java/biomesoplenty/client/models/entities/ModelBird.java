package biomesoplenty.client.models.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBird extends ModelBase {

    ModelRenderer Body;
    ModelRenderer Stomach;
    ModelRenderer Head;
    ModelRenderer Beak;
    ModelRenderer TuftBottom;
    ModelRenderer TuftTop;
    ModelRenderer Tail;
    ModelRenderer WingRight;
    ModelRenderer WingLeft;
    ModelRenderer LegLeft;
    ModelRenderer LegRight;

    public ModelBird() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(0.0F, 0.0F, 0.0F, 6, 3, 10);
        this.Body.setRotationPoint(-3.0F, 20.0F, -5.0F);
        this.Body.setTextureSize(64, 32);
        this.Body.mirror = true;
        this.setRotation(this.Body, 0.0F, 0.0F, 0.0F);
        this.Stomach = new ModelRenderer(this, 0, 13);
        this.Stomach.addBox(0.0F, 0.0F, 0.0F, 4, 1, 8);
        this.Stomach.setRotationPoint(-2.0F, 23.0F, -4.0F);
        this.Stomach.setTextureSize(64, 32);
        this.Stomach.mirror = true;
        this.setRotation(this.Stomach, 0.0F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 22);
        this.Head.addBox(0.0F, 0.0F, 0.0F, 4, 3, 4);
        this.Head.setRotationPoint(-2.0F, 19.0F, -9.0F);
        this.Head.setTextureSize(64, 32);
        this.Head.mirror = true;
        this.setRotation(this.Head, 0.0F, 0.0F, 0.0F);
        this.Beak = new ModelRenderer(this, 0, 29);
        this.Beak.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
        this.Beak.setRotationPoint(-1.0F, 21.0F, -10.0F);
        this.Beak.setTextureSize(64, 32);
        this.Beak.mirror = true;
        this.setRotation(this.Beak, 0.0F, 0.0F, 0.0F);
        this.TuftBottom = new ModelRenderer(this, 28, 23);
        this.TuftBottom.addBox(0.0F, 0.0F, 0.0F, 2, 1, 3);
        this.TuftBottom.setRotationPoint(-1.0F, 18.0F, -7.0F);
        this.TuftBottom.setTextureSize(64, 32);
        this.TuftBottom.mirror = true;
        this.setRotation(this.TuftBottom, 0.0F, 0.0F, 0.0F);
        this.TuftTop = new ModelRenderer(this, 20, 27);
        this.TuftTop.addBox(0.0F, 0.0F, 0.0F, 4, 0, 5);
        this.TuftTop.setRotationPoint(-2.0F, 18.0F, -5.0F);
        this.TuftTop.setTextureSize(64, 32);
        this.TuftTop.mirror = true;
        this.setRotation(this.TuftTop, 0.0F, 0.0F, 0.0F);
        this.Tail = new ModelRenderer(this, 42, 0);
        this.Tail.addBox(0.0F, 0.0F, 0.0F, 4, 0, 7);
        this.Tail.setRotationPoint(-2.0F, 21.0F, 5.0F);
        this.Tail.setTextureSize(64, 32);
        this.Tail.mirror = true;
        this.setRotation(this.Tail, 0.0F, 0.0F, 0.0F);
        this.WingLeft = new ModelRenderer(this, 36, 13);
        this.WingLeft.addBox(0.0F, 0.0F, 0.0F, 8, 0, 6);
        this.WingLeft.setRotationPoint(3.0F, 20.0F, -4.0F);
        this.WingLeft.setTextureSize(64, 32);
        this.WingRight = new ModelRenderer(this, 36, 7);
        this.WingRight.addBox(-8.0F, 0.0F, 0.0F, 8, 0, 6);
        this.WingRight.setRotationPoint(-3.0F, 20.0F, -4.0F);
        this.WingRight.setTextureSize(64, 32);
        this.LegLeft = new ModelRenderer(this, 33, 0);
        this.LegLeft.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
        this.LegLeft.setRotationPoint(2.0F, 23.0F, 0.0F);
        this.LegLeft.setTextureSize(64, 32);
        this.LegLeft.mirror = true;
        this.setRotation(this.LegLeft, 0.0F, 0.0F, 0.0F);
        this.LegRight = new ModelRenderer(this, 33, 0);
        this.LegRight.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
        this.LegRight.setRotationPoint(-3.0F, 23.0F, 0.0F);
        this.LegRight.setTextureSize(64, 32);
        this.LegRight.mirror = true;
        this.setRotation(this.LegRight, 0.0F, 0.0F, 0.0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.Body.render(f5);
        this.Stomach.render(f5);
        this.Head.render(f5);
        this.Beak.render(f5);
        this.TuftBottom.render(f5);
        this.TuftTop.render(f5);
        this.Tail.render(f5);
        this.WingRight.render(f5);
        this.WingLeft.render(f5);
        this.LegLeft.render(f5);
        this.LegRight.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        int x = MathHelper.floor_double(entity.posX);
        int y = MathHelper.floor_double(entity.posY);
        int z = MathHelper.floor_double(entity.posZ);
        if (entity.posY <= entity.prevPosY && entity.worldObj.isAirBlock(x, y - 1, z)) {
            this.WingRight.rotateAngleZ = 0.0F;
            this.WingLeft.rotateAngleZ = 0.0F;
        } else {
            this.WingRight.rotateAngleZ = MathHelper.cos(f2 * 1.7F) * (float) Math.PI * 0.25F;
            this.WingLeft.rotateAngleZ = -this.WingRight.rotateAngleZ;
        }
    }
}
