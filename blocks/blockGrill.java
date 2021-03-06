package com.mofood.blocks;

import com.mofood.util.MoFoodTab;

import net.drpcore.main.DarkRoleplayCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class blockGrill extends Block {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static blockGrill blockGrill = new blockGrill();

	protected blockGrill() {
		super(Material.wood);
		this.setUnlocalizedName("blockGrill");
		this.setStepSound(Block.soundTypeMetal);
		this.setCreativeTab(MoFoodTab.MoCrafting);
	}

	 @Override
	    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	    {
	        if(!worldIn.isRemote){
	                player.openGui(DarkRoleplayCore.instance, net.drpcore.server.GuiHandler.GUI_CRAFTING, player.worldObj, pos.getX(), pos.getY(), pos.getZ());
	        }
	        return true;
	    }
	        

	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		if (!worldIn.isSideSolid(pos.offset(EnumFacing.DOWN), EnumFacing.UP, true))
			return Blocks.air.getDefaultState();
		EntityPlayer entity = (EntityPlayer) placer;
		if (entity != null) {
			int dir = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			switch (dir) {
			case 2:
				return this.getDefaultState().withProperty(FACING, EnumFacing.NORTH);
			case 3:
				return this.getDefaultState().withProperty(FACING, EnumFacing.EAST);
			case 0:
				return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
			case 1:
				return this.getDefaultState().withProperty(FACING, EnumFacing.WEST);
			default:
				return this.getDefaultState().withProperty(FACING, EnumFacing.NORTH);
			}
		}
		return Blocks.air.getDefaultState();
	}

	public IBlockState getStateFromMeta(int meta) {
		switch (meta) {
		case 0:
			return this.getDefaultState().withProperty(FACING, EnumFacing.NORTH);
		case 1:
			return this.getDefaultState().withProperty(FACING, EnumFacing.EAST);
		case 2:
			return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
		case 3:
			return this.getDefaultState().withProperty(FACING, EnumFacing.WEST);
		default:
			return this.getDefaultState().withProperty(FACING, EnumFacing.NORTH);
		}
	}

	public int getMetaFromState(IBlockState state) {
		EnumFacing facing = (EnumFacing) state.getValue(FACING);
		if (facing.equals(EnumFacing.NORTH))
			return 0;
		if (facing.equals(EnumFacing.EAST))
			return 1;
		if (facing.equals(EnumFacing.SOUTH))
			return 2;
		if (facing.equals(EnumFacing.WEST))
			return 3;
		return 0;
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING });
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	public boolean isSolidFullCube() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	// Ground Blocks
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (!this.canBlockStay(worldIn, pos, EnumFacing.UP)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
	}

	protected boolean canBlockStay(World worldIn, BlockPos pos, EnumFacing facing) {
		return worldIn.isSideSolid(pos.offset(facing.getOpposite()), facing, true);
	}

	public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}
}
