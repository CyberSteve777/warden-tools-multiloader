package net.trique.wardentools.item.misc;

import com.eliotlash.mclib.math.functions.classic.Mod;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.SmithingTemplateItem;
import net.trique.wardentools.Constants;
import net.trique.wardentools.util.ModHelper;

import java.util.List;

public class StaffTemplateItem extends SmithingTemplateItem {
    protected static final ChatFormatting TITLE_FORMATTING;
    protected static final ChatFormatting DESCRIPTION_FORMATTING;
    protected static final Component STAFF_UPGRADE_TEXT;
    protected static final Component STAFF_UPGRADE_APPLIES_TO_TEXT;
    protected static final Component STAFF_UPGRADE_INGREDIENTS_TEXT;
    protected static final Component STAFF_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT;
    protected static final Component STAFF_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT;
    protected static final ResourceLocation EMPTY_SLOT_STAFF;
    protected static final ResourceLocation EMPTY_SLOT_ENDER_EYE;


    public StaffTemplateItem(Component appliesTo, Component ingredients, Component upgradeDescription, Component baseSlotDescription, Component additionsSlotDescription, List<ResourceLocation> baseSlotEmptyIcons, List<ResourceLocation> additionalSlotEmptyIcons, FeatureFlag... requiredFeatures) {
        super(appliesTo, ingredients, upgradeDescription, baseSlotDescription, additionsSlotDescription, baseSlotEmptyIcons, additionalSlotEmptyIcons, requiredFeatures);
    }

    public static StaffTemplateItem createStaffUpgrade() {
        return new StaffTemplateItem(STAFF_UPGRADE_APPLIES_TO_TEXT, STAFF_UPGRADE_INGREDIENTS_TEXT, STAFF_UPGRADE_TEXT,
                STAFF_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT, STAFF_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT,
                getStaffUpgradeEmptyBaseSlotTextures(), getStaffUpgradeEmptyAdditionsSlotTextures());
    }

    protected static List<ResourceLocation> getStaffUpgradeEmptyBaseSlotTextures() {
        return List.of(EMPTY_SLOT_STAFF);
    }

    protected static List<ResourceLocation> getStaffUpgradeEmptyAdditionsSlotTextures() {
        return List.of(EMPTY_SLOT_INGOT, EMPTY_SLOT_ENDER_EYE);
    }

    static {
        TITLE_FORMATTING = ChatFormatting.GRAY;
        DESCRIPTION_FORMATTING = ChatFormatting.BLUE;
        STAFF_UPGRADE_TEXT = Component.translatable(Util.makeDescriptionId("upgrade", ResourceLocation.tryParse("staff_upgrade"))).withStyle(TITLE_FORMATTING);
        STAFF_UPGRADE_APPLIES_TO_TEXT = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.tryParse("smithing_template.staff_upgrade.applies_to"))).withStyle(DESCRIPTION_FORMATTING);
        STAFF_UPGRADE_INGREDIENTS_TEXT = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.tryParse("smithing_template.staff_upgrade.ingredients"))).withStyle(DESCRIPTION_FORMATTING);
        STAFF_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.tryParse("smithing_template.staff_upgrade.base_slot_description")));
        STAFF_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.tryParse("smithing_template.staff_upgrade.additions_slot_description")));
        EMPTY_SLOT_STAFF = ModHelper.getLoc("item/empty_slot_echo_staff");
        EMPTY_SLOT_ENDER_EYE = ModHelper.getLoc("item/empty_slot_ender_eye");
    }
}
