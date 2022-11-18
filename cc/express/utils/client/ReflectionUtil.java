package cc.express.utils.client;


import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collections;

public class ReflectionUtil {
    public static Object getFieldValue(Object obj, String... fields) {
        Class<?> clazz = obj.getClass();
        for (String string : fields) {
            try {
                Field f = clazz.getDeclaredField(string);
                f.setAccessible(true);
                try {
                    return f.get(obj);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return null;
                }
            } catch (NoSuchFieldException ignored) {
            }
        }
        return null;
    }

    public static Field findField(Class<?> clazz, String... fieldNames) {
        Exception failed = null;
        String[] var3 = fieldNames;
        int var4 = fieldNames.length;
        int var5 = 0;

        while(var5 < var4) {
            String fieldName = var3[var5];

            try {
                Field f = clazz.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f;
            } catch (Exception var8) {
                failed = var8;
                ++var5;
            }
        }
        return null;
    }

    public static Object getField(Field f, Object o) {
        try {
            return f.get(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setField(Field f, Object v, Object o) {
        try {
            f.set(o, v);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object invoke(Method m, Object o, Object... args) {
        try {
            return m.invoke(o, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    public static Object construction(Class<?> classIn, Object... args) {
        Class<?>[] arg = new Class<?>[args.length];
        int i = 0;
        for (Object o : args) {
            arg[i] = o.getClass();
            i++;
        }
        try {
            Constructor<?> constructor = classIn.getConstructor(arg);
            return constructor.newInstance(args);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object callMethod(String className, String methodName, Object object, Object... args) {
        try {
            Class clazz = Class.forName(className);
            Method method = null;
            for (Method declaredMethod : clazz.getDeclaredMethods()) {
                if (declaredMethod.getName().equals(methodName)) method = declaredMethod;
            }
            if (method == null) {
                throw new NoSuchMethodException();
            }
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getField(String className, String fieldName, Object object) {
        try {
            Class clazz = Class.forName(className);
            Field field = null;
            for (Field declaredField : clazz.getDeclaredFields()) {
                if (declaredField.getName().equals(fieldName)) field = declaredField;
            }
            if (field == null) {
                throw new NoSuchFieldException();
            }
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object construction(Constructor<?> constructor, Object... args) {
        try {
            return constructor.newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFieldValue(Class<?> clazz, String... fields) {
        for (String string : fields) {
            try {
                Field f = clazz.getDeclaredField(string);
                if (f == null)
                    continue;
                f.setAccessible(true);
                try {
                    return f.get(null);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return null;
                }
            } catch (NoSuchFieldException e) {
                continue;
            }
        }
        return null;
    }



    public static void setFieldValue(Object obj, Object value, String... fields) {
        Class<?> clazz = obj.getClass();
        for (String string : fields) {
            try {
                Field f = clazz.getDeclaredField(string);
                if (f == null)
                    continue;
                f.setAccessible(true);
                try {
                    f.set(obj, value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return;
                }
            } catch (NoSuchFieldException e) {
                continue;
            }
        }
    }

    public static void setFieldValue(Class<?> clazz, Object value, String... fields) {
        for (String string : fields) {
            try {
                Field f = clazz.getDeclaredField(string);
                if (f == null)
                    continue;
                f.setAccessible(true);
                try {
                    f.set(null, value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (NoSuchFieldException e) {
                continue;
            }
        }
    }

    public static <T> T copy(T src, T dst) {
        for (Field f : getAllFields(src.getClass())) {
            if (Modifier.isFinal(f.getModifiers()) || Modifier.isStatic(f.getModifiers()))
                continue;
            f.setAccessible(true);
            try {
                f.set(dst, f.get(src));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return dst;
    }

    public static Field[] getAllFields(Class<?> clazz) {
        ArrayList<Field> fields = new ArrayList<>();
        do {
            Collections.addAll(fields, clazz.getDeclaredFields());
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class && clazz != null);
        return fields.toArray(new Field[0]);
    }
}
