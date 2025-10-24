package net.trique.wardentools.event;


import net.minecraft.world.item.Items;
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
                Items.ECHO_SHARD,
                PotionRegistry.SCULK_ADAPTION
        );
        builder.addMix(
                PotionRegistry.SCULK_ADAPTION,
                ItemRegistry.WARDEN_SOUL.get(),
                PotionRegistry.SCULK_BLESS
        );
        builder.addMix(
                PotionRegistry.SCULK_ADAPTION,
                ItemRegistry.WARDEN_TENDRIL.get(),
                PotionRegistry.WARDEN
        );
        builder.addMix(
                PotionRegistry.SCULK_ADAPTION,
                Items.REDSTONE,
                PotionRegistry.LONG_SCULK_ADAPTION
        );
        builder.addMix(
                PotionRegistry.SCULK_BLESS,
                Items.REDSTONE,
                PotionRegistry.LONG_SCULK_BLESS
        );
        builder.addMix(
                PotionRegistry.SCULK_BLESS,
                Items.GLOWSTONE_DUST,
                PotionRegistry.STRONG_SCULK_BLESS
        );
        builder.addMix(
                PotionRegistry.WARDEN,
                Items.REDSTONE,
                PotionRegistry.LONG_WARDEN
        );
        builder.addMix(
                PotionRegistry.WARDEN,
                Items.GLOWSTONE_DUST,
                PotionRegistry.STRONG_WARDEN
        );
    }
}
