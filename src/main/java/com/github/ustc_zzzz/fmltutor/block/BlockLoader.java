package com.github.ustc_zzzz.fmltutor.block;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLoader
{
    public static Block grassBlock = new BlockGrassBlock();
    public static Block fluidMercury = new BlockFluidMercury();

    public BlockLoader(FMLPreInitializationEvent event)
    {
        register(grassBlock, "grass_block");
        register(fluidMercury, "fluid_mercury");
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
        registerRender(grassBlock);
    }

    private static void register(Block block, String name)
    {
        GameRegistry.registerBlock(block, name);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(Block block)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0,
                new ModelResourceLocation(GameData.getBlockRegistry().getNameForObject(block).toString(), "inventory"));
    }
}
