package biomesoplenty.api.biome;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.world.gen.feature.WorldGenerator;

import biomesoplenty.api.exception.FeatureExistsException;
import biomesoplenty.api.exception.NoSuchFeatureException;

public class BiomeFeatures {

    public HashMap<WorldGenerator, Double> weightedGrassGen = new HashMap<>();
    public HashMap<WorldGenerator, Integer> weightedFlowerGen = new HashMap<>();
    private ArrayList<String> features = new ArrayList<>();
    @BiomeFeatures.BiomeFeature
    public int bopFlowersPerChunk = 0;
    @BiomeFeatures.BiomeFeature
    public int bopGrassPerChunk = 0;

    public BiomeFeatures() {
        for (Field field : this.getClass()
            .getFields()) {
            if (field.isAnnotationPresent(BiomeFeatures.BiomeFeature.class)) {
                if (this.features.contains(field.getName())) {
                    throw new FeatureExistsException(field.getName());
                }

                this.features.add(field.getName());
            }
        }
    }

    public Object getFeature(String featureName) {
        try {
            return this.getClass()
                .getField(featureName)
                .get(this);
        } catch (Exception e) {
            throw new NoSuchFeatureException(featureName);
        }
    }

    public ArrayList<String> getFeatureNames() {
        return this.features;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    protected @interface BiomeFeature {}
}
