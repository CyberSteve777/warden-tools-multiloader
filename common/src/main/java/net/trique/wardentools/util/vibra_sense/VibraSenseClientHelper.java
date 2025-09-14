package net.trique.wardentools.util.vibra_sense;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class VibraSenseClientHelper {
    private static final HashMap<Integer, EntityGlowTicker> TO_RENDER_GLOWING = new HashMap<>();


    public static void tickClientGlowingEntities() {
        Iterator<Integer> iterator = TO_RENDER_GLOWING.keySet().iterator();
        try {
            while(iterator.hasNext()) {
                int entityId = iterator.next();
                EntityGlowTicker ticker = TO_RENDER_GLOWING.get(entityId);
                if (!ticker.tick()) {
                    iterator.remove();
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    public static Set<Integer> getEntitiesToRenderGlowing() {
        return TO_RENDER_GLOWING.keySet();
    }

    public static void addEntity(int id, int ticks) {
        TO_RENDER_GLOWING.put(id, new EntityGlowTicker(ticks));
    }
}
