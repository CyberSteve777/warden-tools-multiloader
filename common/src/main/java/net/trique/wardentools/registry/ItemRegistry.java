package net.trique.wardentools.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.trique.wardentools.Constants;
import net.trique.wardentools.item.archery.EchoShriekerItem;
import net.trique.wardentools.item.archery.SculkArrowItem;
import net.trique.wardentools.item.archery.SculkifiedBow;
import net.trique.wardentools.item.material.WardenArmorMaterials;
import net.trique.wardentools.item.material.WardenToolMaterials;
import net.trique.wardentools.item.melee.DarknessAxeItem;
import net.trique.wardentools.item.melee.DarknessSwordItem;
import net.trique.wardentools.item.melee.WardenArmorItem;
import net.trique.wardentools.item.melee.WardenMaskItem;
import net.trique.wardentools.item.misc.WardenFoodItem;
import net.trique.wardentools.item.misc.WardenTemplateItem;
import net.trique.wardentools.item.staff.AmethystEchoStaff;
import net.trique.wardentools.item.staff.EchoStaff;
import net.trique.wardentools.item.staff.EnderEchoStaff;
import net.trique.wardentools.item.staff.RoseGoldEchoStaff;
import net.trique.wardentools.registration.RegistrationProvider;
import net.trique.wardentools.registration.RegistryObject;

public class ItemRegistry {
    protected static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, Constants.MOD_ID);

    public static final RegistryObject<Item, EchoStaff> ECHO_STAFF = ITEMS.register("echo_staff",
            () -> new EchoStaff(getItemProperties().rarity(Rarity.EPIC).durability(76),
                    80, 20, 20, 7, 10.0f, 5.0, 1.0));

    public static final RegistryObject<Item, AmethystEchoStaff> AMETHYST_UPGRADED_ECHO_STAFF = ITEMS.register("amethyst_upgraded_echo_staff",
            () -> new AmethystEchoStaff(getItemProperties().rarity(Rarity.EPIC).durability(76),
                    60, 15, 20, 7, 10.0f, 7.5, 1.5));

    public static final RegistryObject<Item, EnderEchoStaff> ENDER_UPGRADED_ECHO_STAFF = ITEMS.register("ender_upgraded_echo_staff",
            () -> new EnderEchoStaff(getItemProperties().rarity(Rarity.EPIC).durability(76),
                    80, 20, 20, 7, 10.0f, 5.0, 1.0));

    public static final RegistryObject<Item, RoseGoldEchoStaff> ROSE_GOLD_UPGRADED_ECHO_STAFF = ITEMS.register("rose_gold_upgraded_echo_staff",
            () -> new RoseGoldEchoStaff(getItemProperties().rarity(Rarity.EPIC).durability(76),
                    80, 20, 40, 7, 20.0f, 2.5, 0.5));

    public static final RegistryObject<Item, EchoShriekerItem> ECHO_SHRIEKER = ITEMS.register("echo_shrieker",
            () -> new EchoShriekerItem(getFireResistantProperties().rarity(Rarity.EPIC).durability(50)));

    public static final RegistryObject<Item, SculkifiedBow> SCULKIFIED_BOW = ITEMS.register("sculkified_bow", () ->
            new SculkifiedBow(getFireResistantProperties().rarity(Rarity.RARE).attributes(SculkifiedBow.createAttributeModifiers())));

    public static final RegistryObject<Item, ShovelItem> SCULKIFIED_SHOVEL = ITEMS.register("sculkified_shovel", () ->
            new ShovelItem(WardenToolMaterials.SCULKIFIED, getItemProperties().attributes(ShovelItem.createAttributes(WardenToolMaterials.SCULKIFIED, 1.5f, -3.0f))));

    public static final RegistryObject<Item, PickaxeItem> SCULKIFIED_PICKAXE = ITEMS.register("sculkified_pickaxe", () ->
            new PickaxeItem(WardenToolMaterials.SCULKIFIED, getItemProperties().attributes(PickaxeItem.createAttributes(WardenToolMaterials.SCULKIFIED, 1f, -2.8f))));

    public static final RegistryObject<Item, AxeItem> SCULKIFIED_AXE = ITEMS.register("sculkified_axe", () ->
            new AxeItem(WardenToolMaterials.SCULKIFIED, getItemProperties().attributes(DarknessAxeItem.createAttributes(WardenToolMaterials.SCULKIFIED, 5, -2.9f))));

    public static final RegistryObject<Item, HoeItem> SCULKIFIED_HOE = ITEMS.register("sculkified_hoe", () ->
            new HoeItem(WardenToolMaterials.SCULKIFIED, getItemProperties().attributes(HoeItem.createAttributes(WardenToolMaterials.SCULKIFIED, -4, 0.0f))));

    public static final RegistryObject<Item, SwordItem> SCULKIFIED_SWORD = ITEMS.register("sculkified_sword", () ->
            new SwordItem(WardenToolMaterials.SCULKIFIED, getItemProperties().attributes(DarknessSwordItem.createAttributes(WardenToolMaterials.SCULKIFIED, 3, -2.4f))));

    public static final RegistryObject<Item, WardenArmorItem> SCULKIFIED_HELMET = ITEMS.register("sculkified_helmet", () ->
            new WardenArmorItem(WardenArmorMaterials.SCULKIFIED, ArmorItem.Type.HELMET, getItemProperties().durability(ArmorItem.Type.HELMET.getDurability(40)),
                    WTArmorSets.SCULKIFIED_SET));

    public static final RegistryObject<Item, WardenArmorItem> SCULKIFIED_CHESTPLATE = ITEMS.register("sculkified_chestplate", () ->
            new WardenArmorItem(WardenArmorMaterials.SCULKIFIED, ArmorItem.Type.CHESTPLATE, getItemProperties().durability(ArmorItem.Type.CHESTPLATE.getDurability(40)),
                    WTArmorSets.SCULKIFIED_SET));

    public static final RegistryObject<Item, WardenArmorItem> SCULKIFIED_LEGGINGS = ITEMS.register("sculkified_leggings", () ->
            new WardenArmorItem(WardenArmorMaterials.SCULKIFIED, ArmorItem.Type.LEGGINGS, getItemProperties().durability(ArmorItem.Type.LEGGINGS.getDurability(40)),
                    WTArmorSets.SCULKIFIED_SET));

    public static final RegistryObject<Item, WardenArmorItem> SCULKIFIED_BOOTS = ITEMS.register("sculkified_boots", () ->
            new WardenArmorItem(WardenArmorMaterials.SCULKIFIED, ArmorItem.Type.BOOTS, getItemProperties().durability(ArmorItem.Type.BOOTS.getDurability(40)),
                    WTArmorSets.SCULKIFIED_SET));

    public static final RegistryObject<Item, ShovelItem> WARDEN_SHOVEL = ITEMS.register("warden_shovel", () ->
            new ShovelItem(WardenToolMaterials.WARDEN, getFireResistantProperties().attributes(ShovelItem.createAttributes(WardenToolMaterials.WARDEN, 1.5f, -3.0f))));

    public static final RegistryObject<Item, PickaxeItem> WARDEN_PICKAXE = ITEMS.register("warden_pickaxe", () ->
            new PickaxeItem(WardenToolMaterials.WARDEN, getFireResistantProperties().attributes(PickaxeItem.createAttributes(WardenToolMaterials.WARDEN, 1f, -2.8f))));

    public static final RegistryObject<Item, DarknessAxeItem> WARDEN_AXE = ITEMS.register("warden_axe", () ->
            new DarknessAxeItem(WardenToolMaterials.WARDEN, getFireResistantProperties().attributes(DarknessAxeItem.createAttributes(WardenToolMaterials.WARDEN, 5, -2.9f))));

    public static final RegistryObject<Item, HoeItem> WARDEN_HOE = ITEMS.register("warden_hoe", () ->
            new HoeItem(WardenToolMaterials.WARDEN, getFireResistantProperties().attributes(HoeItem.createAttributes(WardenToolMaterials.WARDEN, -4, 0.0f))));

    public static final RegistryObject<Item, DarknessSwordItem> WARDEN_SWORD = ITEMS.register("warden_sword", () ->
            new DarknessSwordItem(WardenToolMaterials.WARDEN, getFireResistantProperties().attributes(DarknessSwordItem.createAttributes(WardenToolMaterials.WARDEN, 3, -2.4f))));

    public static final RegistryObject<Item, WardenArmorItem> WARDEN_HELMET = ITEMS.register("warden_helmet", () ->
            new WardenArmorItem(WardenArmorMaterials.WARDEN, ArmorItem.Type.HELMET, getFireResistantProperties().durability(ArmorItem.Type.HELMET.getDurability(45)),
                    WTArmorSets.WARDEN_SET));

    public static final RegistryObject<Item, WardenMaskItem> WARDEN_MASK = ITEMS.register("warden_mask", () ->
            new WardenMaskItem(WardenArmorMaterials.WARDEN, ArmorItem.Type.HELMET, getFireResistantProperties().durability(ArmorItem.Type.HELMET.getDurability(45)).rarity(Rarity.EPIC),
                    WTArmorSets.WARDEN_SET));

    public static final RegistryObject<Item, WardenArmorItem> WARDEN_CHESTPLATE = ITEMS.register("warden_chestplate", () ->
            new WardenArmorItem(WardenArmorMaterials.WARDEN, ArmorItem.Type.CHESTPLATE, getFireResistantProperties().durability(ArmorItem.Type.CHESTPLATE.getDurability(45)),
                    WTArmorSets.WARDEN_SET));

    public static final RegistryObject<Item, WardenArmorItem> WARDEN_LEGGINGS = ITEMS.register("warden_leggings", () ->
            new WardenArmorItem(WardenArmorMaterials.WARDEN, ArmorItem.Type.LEGGINGS, getFireResistantProperties().durability(ArmorItem.Type.LEGGINGS.getDurability(45)),
                    WTArmorSets.WARDEN_SET));

    public static final RegistryObject<Item, WardenArmorItem> WARDEN_BOOTS = ITEMS.register("warden_boots", () ->
            new WardenArmorItem(WardenArmorMaterials.WARDEN, ArmorItem.Type.BOOTS, getFireResistantProperties().durability(ArmorItem.Type.BOOTS.getDurability(45)),
                    WTArmorSets.WARDEN_SET));

    public static final RegistryObject<Item, Item> SCULK_SHELL = ITEMS.register("sculk_shell", () ->
            new Item(getFireResistantProperties().rarity(Rarity.RARE).food(WardenFoodItem.getSculkShellProperties())));

    public static final RegistryObject<Item, Item> WARDEN_TENDRIL = ITEMS.register("warden_tendril", () ->
            new Item(getFireResistantProperties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item, Item> SHRIEKER_FANG = ITEMS.register("shrieker_fang", () ->
            new Item(getFireResistantProperties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item, Item> ECHO_APPLE = ITEMS.register("echo_apple", () ->
            new Item(getFireResistantProperties().rarity(Rarity.RARE).food(WardenFoodItem.getEchoAppleProperties())));

    public static final RegistryObject<Item, Item> WARDEN_SOUL = ITEMS.register("warden_soul", () ->
            new Item(getFireResistantProperties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item, Item> WARDEN_INGOT = ITEMS.register("warden_ingot", () ->
            new Item(getFireResistantProperties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item, Item> ECHO_INGOT = ITEMS.register("echo_ingot", () ->
            new Item(getItemProperties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item, Item> ROSE_GOLD_INGOT = ITEMS.register("rose_gold_ingot", () ->
            new Item(getFireResistantProperties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item, Item> AMETHYST_INGOT = ITEMS.register("amethyst_ingot", () ->
            new Item(getFireResistantProperties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item, SculkArrowItem> SCULK_ARROW = ITEMS.register("sculk_arrow", () ->
            new SculkArrowItem(getFireResistantProperties()));

    public static final RegistryObject<Item, WardenTemplateItem> WARDEN_UPGRADE_SMITHING_TEMPLATE =
            ITEMS.register("warden_upgrade_smithing_template", WardenTemplateItem::createWardenUpgrade);


    public static Item.Properties getItemProperties() {
        return new Item.Properties();
    }

    public static Item.Properties getFireResistantProperties() {
        return new Item.Properties().fireResistant();
    }

    public static void init() {
        Constants.LOGGER.info("Registering items for {}...", Constants.MOD_NAME);
    }
}
