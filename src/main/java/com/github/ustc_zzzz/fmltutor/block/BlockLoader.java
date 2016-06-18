package com.github.ustc_zzzz.fmltutor.block;

import com.github.ustc_zzzz.fmltutor.FMLTutor;
import com.google.common.base.Function;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLoader
{
    public static Block grassBlock = new BlockGrassBlock();
    public static Block fluidMercury = new BlockFluidMercury();
    public static Block metalFurnace = new BlockMetalFurnace();

    public BlockLoader(FMLPreInitializationEvent event)
    {
        register(grassBlock, "grass_block");
        register(fluidMercury, "fluid_mercury");
        register(metalFurnace, new ItemMultiTexture(metalFurnace, metalFurnace, new Function<ItemStack, String>()
        {
            @Override
            public String apply(ItemStack input)
            {
                return BlockMetalFurnace.EnumMaterial.values()[input.getMetadata() >> 3].getName();
            }
        }), "metal_furnace");

        registerModelVariant(metalFurnace, "iron_furnace", "gold_furnace");
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
        registerRender(grassBlock);
        registerRender(metalFurnace, 0, "iron_furnace");
        registerRender(metalFurnace, 8, "gold_furnace");
    }

    @SuppressWarnings("unchecked")
    private static void register(Block block, ItemBlock itemBlock, String name)
    {
        GameRegistry.registerBlock(block, null, name);
        GameRegistry.registerItem(itemBlock, name);
        GameData.getBlockItemMap().put(block, itemBlock);
    }

    private static void register(Block block, String name)
    {
        GameRegistry.registerBlock(block, name);
    }

    @SideOnly(Side.CLIENT)
    private static void registerModelVariant(Block block, String... names)
    {
        if (FMLCommonHandler.instance().getSide().isClient())
        {
            for (int i = names.length - 1; i >= 0; --i)
            {
                names[i] = FMLTutor.MODID + ":" + names[i];
            }
            ModelLoader.addVariantName(Item.getItemFromBlock(block), names);
        }
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(Block block, int meta, String name)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), meta,
                new ModelResourceLocation(FMLTutor.MODID + ":" + name, "inventory"));
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(Block block)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0,
                new ModelResourceLocation(GameData.getBlockRegistry().getNameForObject(block).toString(), "inventory"));
    }
}
