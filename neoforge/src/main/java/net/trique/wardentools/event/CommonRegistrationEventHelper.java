package net.trique.wardentools.event;


import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registry.ItemRegistry;
import net.trique.wardentools.registry.PotionRegistry;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class CommonRegistrationEventHelper {
    @SubscribeEvent
    private static void registerPotions(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();
        builder.addMix(
                Potions.AWKWARD,
                ItemRegistry.WARDEN_SOUL.get(),
                PotionRegistry.SCULK_ADAPTION_POTION
        );
    }
}
