package net.trique.wardentools.item.misc;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;


public class WardenTemplateItem extends SmithingTemplateItem {
    private static final ChatFormatting TITLE_FORMATTING;
    private static final ChatFormatting DESCRIPTION_FORMATTING;
    private static final Component WARDEN_UPGRADE_TEXT;
    private static final Component WARDEN_UPGRADE_APPLIES_TO_TEXT;
    private static final Component WARDEN_UPGRADE_INGREDIENTS_TEXT;
    private static final Component WARDEN_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT;
    private static final Component WARDEN_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT;

    public WardenTemplateItem(Component appliesToText, Component ingredientsText, Component titleText, Component baseSlotDescriptionText, Component additionsSlotDescriptionText, List<ResourceLocation> emptyBaseSlotTextures, List<ResourceLocation> emptyAdditionsSlotTextures) {
        super(appliesToText, ingredientsText, titleText, baseSlotDescriptionText, additionsSlotDescriptionText, emptyBaseSlotTextures, emptyAdditionsSlotTextures);
    }

    public static WardenTemplateItem createWardenUpgrade() {
        return new WardenTemplateItem(WARDEN_UPGRADE_APPLIES_TO_TEXT, WARDEN_UPGRADE_INGREDIENTS_TEXT, WARDEN_UPGRADE_TEXT, WARDEN_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT, WARDEN_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT, getWardenUpgradeEmptyBaseSlotTextures(), getWardenUpgradeEmptyAdditionsSlotTextures());
    }

    private static List<ResourceLocation> getWardenUpgradeEmptyBaseSlotTextures() {
        return List.of(EMPTY_SLOT_HELMET, EMPTY_SLOT_SWORD, EMPTY_SLOT_CHESTPLATE, EMPTY_SLOT_PICKAXE,
                EMPTY_SLOT_LEGGINGS, EMPTY_SLOT_AXE, EMPTY_SLOT_BOOTS, EMPTY_SLOT_HOE, EMPTY_SLOT_SHOVEL);
    }

    private static List<ResourceLocation> getWardenUpgradeEmptyAdditionsSlotTextures() {
        return List.of(EMPTY_SLOT_INGOT);
    }

    static {
        TITLE_FORMATTING = ChatFormatting.GRAY;
        DESCRIPTION_FORMATTING = ChatFormatting.BLUE;
        WARDEN_UPGRADE_TEXT = Component.translatable(Util.makeDescriptionId("upgrade", ResourceLocation.parse("warden_upgrade"))).withStyle(TITLE_FORMATTING);
        WARDEN_UPGRADE_APPLIES_TO_TEXT = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("smithing_template.warden_upgrade.applies_to"))).withStyle(DESCRIPTION_FORMATTING);
        WARDEN_UPGRADE_INGREDIENTS_TEXT = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("smithing_template.warden_upgrade.ingredients"))).withStyle(DESCRIPTION_FORMATTING);
        WARDEN_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("smithing_template.warden_upgrade.base_slot_description")));
        WARDEN_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("smithing_template.warden_upgrade.additions_slot_description")));
    }
}