package cc.express.event;

import net.minecraft.client.Minecraft;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventBus {
    private final static EventBus theEventBus = new EventBus();

    public static EventBus getInstance(){
        return theEventBus;
    }

    public EventBus(){

    }

    public Map<Method, Class<?>> METHODS = new HashMap<>();
    public Map<Method, Object> OBJECTS = new HashMap<>();
    private ArrayList<Class<?>> UNCHECKE_CLASS = new ArrayList<>();

    public void register(Object obj) {
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        getMethods:
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();

            for (Annotation annotation : annotations) {
                if (annotation.annotationType() != EventTarget.class) {
                    continue;
                }

                if (method.getParameterTypes().length == 0) {
                    continue;
                }
                METHODS.put(method, method.getParameterTypes()[0]);
                OBJECTS.put(method, obj);
                continue getMethods;
            }
        }
    }

    public void unregister(Object obj) {
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            METHODS.remove(method);
            OBJECTS.remove(method);
        }
    }

    public Event call(Event event) {
        if(UNCHECKE_CLASS.contains(event) || (Minecraft.getMinecraft().thePlayer != null || Minecraft.getMinecraft().theWorld != null))
        {
            try {
                METHODS.forEach((method, eventClazz) -> {
                    if (event.getClass() != eventClazz) {
                        return;
                    }
                    Object obj = OBJECTS.get(method);
                    method.setAccessible(true);
                    try {
                        method.invoke(obj, event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                // L ConcurrentModificationException
            }
        }
        return event;
    }
}
