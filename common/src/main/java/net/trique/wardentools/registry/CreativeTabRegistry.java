package net.trique.wardentools.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registration.RegistrationProvider;
import net.trique.wardentools.registration.RegistryObject;
import net.trique.wardentools.util.WTEnchantments;

import java.util.List;

import static net.trique.wardentools.registry.BlockRegistry.*;
import static net.trique.wardentools.registry.ItemRegistry.*;


public class CreativeTabRegistry {
    public static void init() {
        Constants.LOGGER.info("Adding creative tab for {}", Constants.MOD_NAME);
    }

    protected static final RegistrationProvider<CreativeModeTab> CREATIVE_MODE_TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);


    public static final RegistryObject<CreativeModeTab, CreativeModeTab> WARDEN_TOOLS_TAB = CREATIVE_MODE_TABS.register(Constants.MOD_ID + "_tab",
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .icon(() -> new ItemStack(WARDEN_CHESTPLATE.get()))
            .displayItems(
                    (itemDisplayParameters, output) -> {
                        output.accept(SCULKHYST_BLOCK.get());
                        output.accept(BUDDING_SCULKHYST.get());
                        output.accept(SMALL_SCULKHYST_BUD.get());
                        output.accept(MEDIUM_SCULKHYST_BUD.get());
                        output.accept(LARGE_SCULKHYST_BUD.get());
                        output.accept(SCULKHYST_CLUSTER.get());
                        output.accept(SCULKIFIED_SHOVEL.get());
                        output.accept(SCULKIFIED_PICKAXE.get());
                        output.accept(SCULKIFIED_AXE.get());
                        output.accept(SCULKIFIED_HOE.get());
                        output.accept(SCULKIFIED_SWORD.get());
                        output.accept(SCULKIFIED_HELMET.get());
                        output.accept(SCULKIFIED_CHESTPLATE.get());
                        output.accept(SCULKIFIED_LEGGINGS.get());
                        output.accept(SCULKIFIED_BOOTS.get());
                        output.accept(WARDEN_SHOVEL.get());
                        output.accept(WARDEN_PICKAXE.get());
                        output.accept(WARDEN_AXE.get());
                        output.accept(WARDEN_HOE.get());
                        output.accept(WARDEN_SWORD.get());
                        output.accept(WARDEN_HELMET.get());
                        output.accept(WARDEN_MASK.get());
                        output.accept(WARDEN_CHESTPLATE.get());
                        output.accept(WARDEN_LEGGINGS.get());
                        output.accept(WARDEN_BOOTS.get());
                        output.accept(ECHO_STAFF.get());
                        output.accept(ROSE_GOLD_UPGRADED_ECHO_STAFF.get());
                        output.accept(AMETHYST_UPGRADED_ECHO_STAFF.get());
                        output.accept(ENDER_UPGRADED_ECHO_STAFF.get());
                        output.accept(ECHO_APPLE.get());
                        output.accept(ECHO_INGOT.get());
                        output.accept(WARDEN_INGOT.get());
                        output.accept(ROSE_GOLD_INGOT.get());
                        output.accept(AMETHYST_INGOT.get());
                        output.accept(WARDEN_UPGRADE_SMITHING_TEMPLATE.get());
                        output.accept(ECHO_SHRIEKER.get());
                        output.accept(SCULKIFIED_BOW.get());
                        output.accept(WARDEN_SOUL.get());
                        output.accept(SCULK_SHELL.get());
                        output.accept(SHRIEKER_FANG.get());
                        output.accept(WARDEN_TENDRIL.get());
                        output.accept(SCULK_ARROW.get());
                        for (Holder<Potion> potionHolder: PotionRegistry.getEntries()) {
                            for (Item base: List.of(Items.POTION, Items.SPLASH_POTION,
                                    Items.LINGERING_POTION, Items.TIPPED_ARROW)) {
                                output.accept(PotionContents.createItemStack(base, potionHolder));
                            }
                        }
                        var echo_concentration = itemDisplayParameters.holders().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(WTEnchantments.ECHO_CONCENTRATION);
                        output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(echo_concentration, echo_concentration.value().getMaxLevel())));
                    }).title(Component.literal(Constants.MOD_NAME))
            .build());

}
