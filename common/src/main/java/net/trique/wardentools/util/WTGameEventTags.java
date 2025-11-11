package net.trique.wardentools.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.gameevent.GameEvent;

import static net.trique.wardentools.util.ModHelper.getLoc;

public class WTGameEventTags {
    public static final TagKey<GameEvent> WARDEN_CURSE_USER_CAN_LISTEN =
            create("warden_curse_user_can_listen");


    private static TagKey<GameEvent> create(String id) {
        return TagKey.create(Registries.GAME_EVENT, getLoc(id));
    }
}
