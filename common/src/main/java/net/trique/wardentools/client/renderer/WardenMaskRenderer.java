package net.trique.wardentools.client.renderer;

import net.trique.wardentools.item.armor.WardenMaskItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import net.trique.wardentools.util.ModHelper;

public class WardenMaskRenderer extends GeoArmorRenderer<WardenMaskItem> {
    public WardenMaskRenderer() {
        super(new DefaultedItemGeoModel<>(ModHelper.getLoc("armor/warden_mask")));
    }
}
