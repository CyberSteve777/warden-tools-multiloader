package net.trique.wardentools.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.trique.wardentools.Constants;
import net.trique.wardentools.registration.RegistryObject;

import java.util.LinkedHashMap;

import static net.trique.wardentools.registry.ItemRegistry.*;

public class WTItemModelProvider extends ItemModelProvider {
    private static final LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();

    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public WTItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ECHO_INGOT.get());
        handheldItem(SCULKIFIED_AXE.get());
        handheldItem(SCULKIFIED_PICKAXE.get());
        handheldItem(SCULKIFIED_HOE.get());
        handheldItem(SCULKIFIED_SHOVEL.get());
        handheldItem(SCULKIFIED_SWORD.get());
        trimmedArmorItem(SCULKIFIED_HELMET);
        trimmedArmorItem(SCULKIFIED_CHESTPLATE);
        trimmedArmorItem(SCULKIFIED_LEGGINGS);
        trimmedArmorItem(SCULKIFIED_BOOTS);

        basicItem(SCULK_SHELL.get());
        basicItem(WARDEN_TENDRIL.get());
        basicItem(WARDEN_INGOT.get());
        handheldItem(WARDEN_AXE.get());
        handheldItem(WARDEN_PICKAXE.get());
        handheldItem(WARDEN_HOE.get());
        handheldItem(WARDEN_SHOVEL.get());
        handheldItem(WARDEN_SWORD.get());
        trimmedArmorItem(WARDEN_HELMET);
        trimmedArmorItem(WARDEN_CHESTPLATE);
        trimmedArmorItem(WARDEN_LEGGINGS);
        trimmedArmorItem(WARDEN_BOOTS);
        basicItem(WARDEN_MASK.get());

        basicItem(WARDEN_UPGRADE_SMITHING_TEMPLATE.get());
        basicItem(STAFF_UPGRADE_SMITHING_TEMPLATE.get());
        basicItem(ECHO_APPLE.get());

        basicItem(WARDEN_SOUL.get());
        handheldItem(ECHO_STAFF.get());

        basicItem(ROSE_GOLD_INGOT.get());
        handheldItem(ROSE_GOLD_UPGRADED_ECHO_STAFF.get());

        basicItem(AMETHYST_INGOT.get());
        handheldItem(AMETHYST_UPGRADED_ECHO_STAFF.get());

        handheldItem(ENDER_UPGRADED_ECHO_STAFF.get());

        handheldItem(WARDEN_ECHO_STAFF.get());
        basicItem(SCULK_ARROW.get());
        basicItem(SHRIEKER_FANG.get());
        bowItem(ECHO_SHRIEKER);
        bowItem(ECHO_LOCATOR);
        basicItem(WARDEN_TENDRIL.get());
    }

    private <T extends ArmorItem> void trimmedArmorItem(RegistryObject<Item, T> itemDeferredItem) {

        if (itemDeferredItem.get() instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);
                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "assets/wardentools/textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                withExistingParent(itemDeferredItem.getId().getPath(),
                        mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace() + ":item/" + trimNameResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID,
                                        "item/" + itemDeferredItem.getId().getPath()));
            });
        }
    }

    private <T extends BowItem> void bowItem(RegistryObject<Item, T> bowItemRegistryObject) {
        if (bowItemRegistryObject.get() instanceof BowItem) {
            String bowName = bowItemRegistryObject.getId().getPath();
//            Constants.LOGGER.info(modLoc(bowName).toString());
            // Main bow model with overrides and display properties
            existingFileHelper.trackGenerated(modLoc("item/" + bowName), PackType.CLIENT_RESOURCES, ".png", "assets/wardentools/textures");
            for (int i = 0; i < 3; i++) {
                String pullingPath = "item/" + bowName + "_pulling_" + i;
                getBuilder(pullingPath)
                        .parent(new ModelFile.UncheckedModelFile(modLoc("item/" + bowName)))
                        .texture("layer0", modLoc(pullingPath));
            }
            ItemModelBuilder mainBow = withExistingParent("item/" + bowName, mcLoc("item/generated"))
                    .texture("layer0", modLoc("item/" + bowName))
                    .override()
                    .predicate(mcLoc("pulling"), 1.0f)
                    .model(getExistingFile(modLoc("item/" + bowName + "_pulling_0")))
                    .end()
                    .override()
                    .predicate(mcLoc("pull"), 0.65f)
                    .predicate(mcLoc("pulling"), 1.0f)
                    .model(getExistingFile(modLoc("item/" + bowName + "_pulling_1")))
                    .end()
                    .override()
                    .predicate(mcLoc("pull"), 0.9f)
                    .predicate(mcLoc("pulling"), 1.0f)
                    .model(getExistingFile(modLoc("item/" + bowName + "_pulling_2")))
                    .end();
            addBowDisplayProperties(mainBow);
        }
    }

    private void addBowDisplayProperties(ItemModelBuilder modelBuilder) {
        modelBuilder
                // Thirdperson righthand display
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .rotation(-80f, 260f, -40f)
                .translation(-1f, -2f, 2.5f)
                .scale(0.9f, 0.9f, 0.9f)
                .end()
                // Thirdperson lefthand display
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
                .rotation(-80f, -280f, 40f)
                .translation(-1f, -2f, 2.5f)
                .scale(0.9f, 0.9f, 0.9f)
                .end()
                // Firstperson righthand display
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                .rotation(0f, -90f, 25f)
                .translation(1.13f, 3.2f, 1.13f)
                .scale(0.68f, 0.68f, 0.68f)
                .end()
                // Firstperson lefthand display
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
                .rotation(0f, 90f, -25f)
                .translation(1.13f, 3.2f, 1.13f)
                .scale(0.68f, 0.68f, 0.68f)
                .end();
    }
}
