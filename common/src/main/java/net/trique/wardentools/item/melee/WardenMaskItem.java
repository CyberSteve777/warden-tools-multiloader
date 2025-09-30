package net.trique.wardentools.item.melee;

import me.cybersteve.equiplib.armorset.base.EffectArmorSet;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.trique.wardentools.client.renderer.WardenMaskRenderer;
import net.trique.wardentools.platform.Services;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class WardenMaskItem extends WardenArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation TENDRILS_CLICK = RawAnimation.begin().thenPlay("mask.tendrils.click");

    public WardenMaskItem(Holder<ArmorMaterial> material, Type type, Properties settings, EffectArmorSet set) {
        super(material, type, settings, set);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private WardenMaskRenderer renderer;

            @Override
            public <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable T livingEntity, ItemStack itemStack, @Nullable EquipmentSlot equipmentSlot, @Nullable HumanoidModel<T> original) {
                if (this.renderer == null)
                    this.renderer = new WardenMaskRenderer();
                // Defer creation of our renderer then cache it so that it doesn't get instantiated too early

                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<GeoAnimatable>(this,
                "warden_mask",
                state -> {
                    if (Services.PLATFORM.isDevelopmentEnvironment()) {
                        RawAnimation TENDRILS_CLICK_LOOPING = RawAnimation.begin().thenLoop("mask.tendrils.click");
                        Entity entity = state.getData(DataTickets.ENTITY);
                        if (entity instanceof ArmorStand) return state.setAndContinue(TENDRILS_CLICK_LOOPING);
                    }
                    return PlayState.STOP;
                })
                .triggerableAnim("tendrils_click", TENDRILS_CLICK));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
