package cn.liukay.items;

import cn.liukay.Lightningskill;
import cn.liukay.behavior.LightningStick;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;

import java.util.function.Function;

public class ModItems {

    /**
     * 定义修复材料
     */
    public static final TagKey<Item> REPAIRS_LIGHTNING_GEM = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Lightningskill.MOD_ID, "repairs_lightning_gem"));

    /**
     * 创建工具材料
     */
    public static final ToolMaterial LIGHTNING_TOOL_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL, //下界合金
            455,
            5.0F,
            1.5F,
            22,
            REPAIRS_LIGHTNING_GEM
    );

    /**
     * 注册物品
     */
    public static final Item LIGHTNING_GEM = register(
            "lightning_gem",
            LightningStick::new,
            new Item.Properties().sword(LIGHTNING_TOOL_MATERIAL, 1f, 1f)
    );

    /**
     * 创建创意标签页
     */
    public static final ResourceKey<CreativeModeTab> LIGHTNING_CREATIVE_TAB_KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(Lightningskill.MOD_ID, "creative_tab")
    );
    public static final CreativeModeTab LIGHTNING_CREATIVE_TAB = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.LIGHTNING_GEM))
            .title(Component.translatable("creativeTab.lightningskill"))
            .displayItems((_, output) -> output.accept(ModItems.LIGHTNING_GEM))
            .build();

    /**
     * 注册物品时使用名字和设置。
     *
     * @param name        物品名称。
     * @param itemFactory 一个创建物品实例的函数。
     * @param settings    物品的设置。
     * @param <T>         物品的类型。
     * @return 登记物品。
     */
    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Lightningskill.MOD_ID, name));
        // Create the item instance.
        T item = itemFactory.apply(settings.setId(itemKey));
        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }

    public static void init() {
        // 注册该物品组。
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, LIGHTNING_CREATIVE_TAB_KEY, LIGHTNING_CREATIVE_TAB);
    }
}
