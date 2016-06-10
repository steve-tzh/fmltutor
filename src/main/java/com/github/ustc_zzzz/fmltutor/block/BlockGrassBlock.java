package com.github.ustc_zzzz.fmltutor.block;

import java.util.List;

import com.github.ustc_zzzz.fmltutor.common.EventLoader;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGrassBlock extends Block
{
    public static final PropertyBool WET = PropertyBool.create("wet");
    public static final PropertyEnum FACING = PropertyEnum.create("facing", EnumFacing.Axis.class);

    public BlockGrassBlock()
    {
        super(Material.ground);
        this.setUnlocalizedName("grassBlock");
        this.setHardness(0.5F);
        this.setStepSound(soundTypeGrass);
        this.setDefaultState(this.blockState.getBaseState().withProperty(WET, Boolean.FALSE).withProperty(FACING,
                EnumFacing.Axis.Y));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
            int meta, EntityLivingBase placer)
    {
        IBlockState origin = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        return origin.withProperty(FACING, facing.getAxis());
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
        EventLoader.PlayerClickGrassBlockEvent event;
        EventLoader.EVENT_BUS.post(event = new EventLoader.PlayerClickGrassBlockEvent(playerIn, pos, worldIn));
        if (!event.isCanceled() && !worldIn.isRemote)
        {
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return ((Boolean) state.getValue(WET)).booleanValue() ? 1 : 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, @SuppressWarnings("rawtypes") List list)
    {
        list.add(new ItemStack(itemIn, 1, 0));
        list.add(new ItemStack(itemIn, 1, 1));
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(WET, Boolean.valueOf(0 != (meta & 1))).withProperty(FACING,
                EnumFacing.Axis.values()[meta < 6 ? meta >> 1 : 1]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing.Axis) (state.getValue(FACING))).ordinal() << 1
                | (((Boolean) state.getValue(WET)).booleanValue() ? 1 : 0);
    }

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, BlockGrassBlock.WET, BlockGrassBlock.FACING);
    }
}
