package net.trique.wardentools.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registration.RegistrationProvider;
import net.trique.wardentools.registration.RegistryObject;

public class DataComponentRegistry {
    private static final RegistrationProvider<DataComponentType<?>> DATA_COMPONENT_PROVIDER = RegistrationProvider.get(
            BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.MOD_ID
    );

    public static RegistryObject<DataComponentType<?>, DataComponentType<Integer>> CHARGE_COUNT =
            DATA_COMPONENT_PROVIDER.register("charge_count", () -> DataComponentType.<Integer>builder().
                    persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());

    public static void init() {
        Constants.LOGGER.info("Registering warden data components...");
    }
}
