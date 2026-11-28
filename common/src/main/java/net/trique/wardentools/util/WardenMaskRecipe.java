package net.trique.wardentools.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.trique.wardentools.registry.RecipeSerializerRegistry;


public class WardenMaskRecipe extends ShapedRecipe {
    private final Ingredient baseHelmet;

    public WardenMaskRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result,
                            Ingredient baseHelmet, boolean showNotification) {
        super(group, category, pattern, result, showNotification);
        this.baseHelmet = baseHelmet;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack out = super.assemble(input, registries);
        for (int i = 0; i < input.size(); i++) {
            ItemStack currentStack = input.getItem(i);
            if (baseHelmet.test(currentStack)) {
                out.applyComponents(currentStack.getComponentsPatch());
                break;
            }
        }
        return out;
    }

    public ShapedRecipePattern getPattern() {
        return this.pattern;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.WARDEN_MASK_RECIPE_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<WardenMaskRecipe> {
        public static final MapCodec<WardenMaskRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(r -> r.group),
                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC)
                        .forGetter(r -> r.category),
                ShapedRecipePattern.MAP_CODEC.forGetter(r -> r.pattern),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.result),
                Ingredient.CODEC_NONEMPTY.fieldOf("base_helmet").forGetter(r -> r.baseHelmet),
                Codec.BOOL.optionalFieldOf("show_notification", true).forGetter(r -> r.showNotification)
        ).apply(instance, WardenMaskRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, WardenMaskRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<WardenMaskRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, WardenMaskRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static WardenMaskRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            String group = buf.readUtf();
            CraftingBookCategory category = buf.readEnum(CraftingBookCategory.class);
            ShapedRecipePattern pattern = ShapedRecipePattern.STREAM_CODEC.decode(buf);
            ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
            Ingredient helmet = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            boolean notify = buf.readBoolean();
            return new WardenMaskRecipe(group, category, pattern, result, helmet, notify);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, WardenMaskRecipe r) {
            buf.writeUtf(r.group);
            buf.writeEnum(r.category);
            ShapedRecipePattern.STREAM_CODEC.encode(buf, r.pattern);
            ItemStack.STREAM_CODEC.encode(buf, r.result);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, r.baseHelmet);
            buf.writeBoolean(r.showNotification);
        }
    }
}
