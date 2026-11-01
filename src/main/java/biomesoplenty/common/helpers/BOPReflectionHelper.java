package biomesoplenty.common.helpers;

import java.lang.reflect.Field;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class BOPReflectionHelper {

    public static <T> Field removeFinal(Class<? super T> classToAccess, T instance, String... fieldNames) {
        Field field = ReflectionHelper
            .findField(classToAccess, ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldNames));

        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & -17);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return field;
    }

    public static <T, E> void setPrivateFinalValue(Class<? super T> classToAccess, T instance, E value,
        String... fieldNames) {
        Field field = ReflectionHelper
            .findField(classToAccess, ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldNames));

        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & -17);
            field.set(instance, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
