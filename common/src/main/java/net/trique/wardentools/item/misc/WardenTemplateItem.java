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
    private static final String TRANSLATION_KEY;
    private static final Component INGREDIENTS_TEXT;
    private static final Component APPLIES_TO_TEXT;
    private static final Component WARDEN_UPGRADE_TEXT;
    private static final Component WARDEN_UPGRADE_APPLIES_TO_TEXT;
    private static final Component WARDEN_UPGRADE_INGREDIENTS_TEXT;
    private static final Component WARDEN_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT;
    private static final Component WARDEN_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT;
    private static final ResourceLocation EMPTY_ARMOR_SLOT_HELMET_TEXTURE;
    private static final ResourceLocation EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE;
    private static final ResourceLocation EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE;
    private static final ResourceLocation EMPTY_ARMOR_SLOT_BOOTS_TEXTURE;
    private static final ResourceLocation EMPTY_SLOT_HOE_TEXTURE;
    private static final ResourceLocation EMPTY_SLOT_AXE_TEXTURE;
    private static final ResourceLocation EMPTY_SLOT_SWORD_TEXTURE;
    private static final ResourceLocation EMPTY_SLOT_SHOVEL_TEXTURE;
    private static final ResourceLocation EMPTY_SLOT_PICKAXE_TEXTURE;
    private static final ResourceLocation EMPTY_SLOT_ECHO_INGOT_TEXTURE;
    private final Component baseSlotDescriptionText;
    private final Component additionsSlotDescriptionText;
    private final List<ResourceLocation> emptyBaseSlotTextures;
    private final List<ResourceLocation> emptyAdditionsSlotTextures;

    public WardenTemplateItem(Component appliesToText, Component ingredientsText, Component titleText, Component baseSlotDescriptionText, Component additionsSlotDescriptionText, List<ResourceLocation> emptyBaseSlotTextures, List<ResourceLocation> emptyAdditionsSlotTextures) {
        super(appliesToText, ingredientsText, titleText, baseSlotDescriptionText, additionsSlotDescriptionText, emptyBaseSlotTextures, emptyAdditionsSlotTextures);
        this.baseSlotDescriptionText = baseSlotDescriptionText;
        this.additionsSlotDescriptionText = additionsSlotDescriptionText;
        this.emptyBaseSlotTextures = emptyBaseSlotTextures;
        this.emptyAdditionsSlotTextures = emptyAdditionsSlotTextures;
    }

    public static WardenTemplateItem createWardenUpgrade() {
        return new WardenTemplateItem(WARDEN_UPGRADE_APPLIES_TO_TEXT, WARDEN_UPGRADE_INGREDIENTS_TEXT, WARDEN_UPGRADE_TEXT, WARDEN_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT, WARDEN_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT, getWardenUpgradeEmptyBaseSlotTextures(), getWardenUpgradeEmptyAdditionsSlotTextures());
    }

    private static List<ResourceLocation> getWardenUpgradeEmptyBaseSlotTextures() {
        return List.of(EMPTY_ARMOR_SLOT_HELMET_TEXTURE, EMPTY_SLOT_SWORD_TEXTURE, EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE, EMPTY_SLOT_PICKAXE_TEXTURE, EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE, EMPTY_SLOT_AXE_TEXTURE, EMPTY_ARMOR_SLOT_BOOTS_TEXTURE, EMPTY_SLOT_HOE_TEXTURE, EMPTY_SLOT_SHOVEL_TEXTURE);
    }

    private static List<ResourceLocation> getWardenUpgradeEmptyAdditionsSlotTextures() {
        return List.of(EMPTY_SLOT_ECHO_INGOT_TEXTURE);
    }

    public Component getBaseSlotDescription() {
        return this.baseSlotDescriptionText;
    }

    public Component getAdditionSlotDescription() {
        return this.additionsSlotDescriptionText;
    }

    public List<ResourceLocation> getBaseSlotEmptyIcons() {
        return this.emptyBaseSlotTextures;
    }

    public List<ResourceLocation> getAdditionalSlotEmptyIcons() {
        return this.emptyAdditionsSlotTextures;
    }

    public String getDescriptionId() {
        return TRANSLATION_KEY;
    }

    static {
        TITLE_FORMATTING = ChatFormatting.GRAY;
        DESCRIPTION_FORMATTING = ChatFormatting.BLUE;
        TRANSLATION_KEY = Util.makeDescriptionId("item", ResourceLocation.parse("smithing_template"));
        INGREDIENTS_TEXT = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("smithing_template.ingredients"))).withStyle(TITLE_FORMATTING);
        APPLIES_TO_TEXT = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("smithing_template.applies_to"))).withStyle(TITLE_FORMATTING);
        WARDEN_UPGRADE_TEXT = Component.translatable(Util.makeDescriptionId("upgrade", ResourceLocation.parse("warden_upgrade"))).withStyle(TITLE_FORMATTING);
        WARDEN_UPGRADE_APPLIES_TO_TEXT = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("smithing_template.warden_upgrade.applies_to"))).withStyle(DESCRIPTION_FORMATTING);
        WARDEN_UPGRADE_INGREDIENTS_TEXT = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("smithing_template.warden_upgrade.ingredients"))).withStyle(DESCRIPTION_FORMATTING);
        WARDEN_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("smithing_template.warden_upgrade.base_slot_description")));
        WARDEN_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("smithing_template.warden_upgrade.additions_slot_description")));
        EMPTY_ARMOR_SLOT_HELMET_TEXTURE = ResourceLocation.parse("minecraft:item/empty_armor_slot_helmet");
        EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE = ResourceLocation.parse("minecraft:item/empty_armor_slot_chestplate");
        EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE = ResourceLocation.parse("minecraft:item/empty_armor_slot_leggings");
        EMPTY_ARMOR_SLOT_BOOTS_TEXTURE = ResourceLocation.parse("minecraft:item/empty_armor_slot_boots");
        EMPTY_SLOT_HOE_TEXTURE = ResourceLocation.parse("minecraft:item/empty_slot_hoe");
        EMPTY_SLOT_AXE_TEXTURE = ResourceLocation.parse("minecraft:item/empty_slot_axe");
        EMPTY_SLOT_SWORD_TEXTURE = ResourceLocation.parse("minecraft:item/empty_slot_sword");
        EMPTY_SLOT_SHOVEL_TEXTURE = ResourceLocation.parse("minecraft:item/empty_slot_shovel");
        EMPTY_SLOT_PICKAXE_TEXTURE = ResourceLocation.parse("minecraft:item/empty_slot_pickaxe");
        EMPTY_SLOT_ECHO_INGOT_TEXTURE = ResourceLocation.parse("wardentools:item/empty_slot_echo_ingot");
    }
}