package net.trique.wardentools.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.gameevent.GameEvent;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registration.RegistrationProvider;
import net.trique.wardentools.registration.RegistryObject;

public class GameEventRegistry {
    private static final RegistrationProvider<GameEvent> GAME_EVENT_PROVIDER = RegistrationProvider.get(BuiltInRegistries.GAME_EVENT,
            Constants.MOD_ID);

    public static RegistryObject<GameEvent, GameEvent> ENTITY_SOUND = GAME_EVENT_PROVIDER.register("entity_sound",
            () -> new GameEvent(16));

    public static void init() {
        Constants.LOGGER.info("Adding {} Game Events", Constants.MOD_NAME);
    }
}
