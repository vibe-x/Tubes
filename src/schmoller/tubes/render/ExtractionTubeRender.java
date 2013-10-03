package schmoller.tubes.render;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import schmoller.tubes.IDirectionalTube;
import schmoller.tubes.ITube;
import schmoller.tubes.TubeHelper;
import schmoller.tubes.definitions.EjectionTube;
import schmoller.tubes.definitions.ExtractionTube;
import schmoller.tubes.definitions.TubeDefinition;
import org.lwjgl.opengl.GL11;

public class ExtractionTubeRender extends NormalTubeRender
{
	private int mDir;
	
	@Override
	public void renderStatic( TubeDefinition type, ITube tube, World world, int x, int y, int z )
	{
		int connections = tube.getConnections();
		int direction = ((IDirectionalTube)tube).getFacing();
		
		mRender.resetTransform();
		mRender.enableNormals = false;
		mRender.setLightingFromBlock(world, x, y, z);
		mRender.resetTextureFlip();
		mRender.resetTextureRotation();
		
		mRender.setLocalLights(0.5f, 1.0f, 0.8f, 0.8f, 0.6f, 0.6f);
		
		mRender.translate(x, y, z);
		
		int invCons = 0;
		
		for(int i = 0; i < 6; ++i)
		{
			if((connections & (1 << i)) != 0)
			{
				TileEntity tile = world.getBlockTileEntity(x + ForgeDirection.getOrientation(i).offsetX, y + ForgeDirection.getOrientation(i).offsetY, z + ForgeDirection.getOrientation(i).offsetZ);
				
				if(tile instanceof IInventory && TubeHelper.getTubeConnectable(tile) == null)
					invCons |= (1 << i);
			}
		}
		
		int tubeCons = connections - invCons;
		
		renderCore(connections | (1 << direction), type);
		renderConnections(tubeCons, type);
		
		renderInventoryConnections(invCons, type);
		
		renderExtractor(direction);
	}
	
	@Override
	protected void renderCore( int connections, TubeDefinition def )
	{
		//connections |= (1 << mDir);
		super.renderCore(connections, def);
	}
	
	private void renderExtractor(int side)
	{
		mRender.setIcon(ExtractionTube.icon);
		
		switch(side)
		{
		case 0:
			mRender.drawBox(60, 0.25f, 0.0f, 0.25f, 0.75f, 0.25f, 0.75f);
			mRender.setIcon(EjectionTube.endIcon, EjectionTube.endIcon, ExtractionTube.icon, ExtractionTube.icon, ExtractionTube.icon, ExtractionTube.icon);
			mRender.drawBox(63, 0.1875f, 0.0f, 0.1875f, 0.8125f, 0.0625f, 0.8125f);
			mRender.drawBox(63, 0.1875f, 0.1875f, 0.1875f, 0.8125f, 0.25f, 0.8125f);
			break;
		case 1:
			mRender.drawBox(60, 0.25f, 0.75f, 0.25f, 0.75f, 1.0f, 0.75f);
			mRender.setIcon(EjectionTube.endIcon, EjectionTube.endIcon, ExtractionTube.icon, ExtractionTube.icon, ExtractionTube.icon, ExtractionTube.icon);
			mRender.drawBox(63, 0.1875f, 0.9375f, 0.1875f, 0.8125f, 1.0f, 0.8125f);
			mRender.drawBox(63, 0.1875f, 0.75f, 0.1875f, 0.8125f, 0.8125f, 0.8125f);
			break;
		case 2:
			mRender.setTextureRotation(0, 0, 0, 0, 1, 1);
			mRender.drawBox(51, 0.25f, 0.25f, 0.0f, 0.75f, 0.75f, 0.25f);
			mRender.setIcon(ExtractionTube.icon, ExtractionTube.icon, EjectionTube.endIcon, EjectionTube.endIcon, ExtractionTube.icon, ExtractionTube.icon);
			mRender.drawBox(63, 0.1875f, 0.1875f, 0.0f, 0.8125f, 0.8125f, 0.0625f);
			mRender.drawBox(63, 0.1875f, 0.1875f, 0.1875f, 0.8125f, 0.8125f, 0.25f);
			break;
		case 3:
			mRender.setTextureRotation(0, 0, 0, 0, 1, 1);
			mRender.drawBox(51, 0.25f, 0.25f, 0.75f, 0.75f, 0.75f, 1.0f);
			mRender.setIcon(ExtractionTube.icon, ExtractionTube.icon, EjectionTube.endIcon, EjectionTube.endIcon, ExtractionTube.icon, ExtractionTube.icon);
			mRender.drawBox(63, 0.1875f, 0.1875f, 0.9375f, 0.8125f, 0.8125f, 1.0f);
			mRender.drawBox(63, 0.1875f, 0.1875f, 0.75f, 0.8125f, 0.8125f, 0.8125f);
			break;
		case 4:
			mRender.setTextureRotation(1, 1, 1, 1, 0, 0);
			mRender.drawBox(15, 0.0f, 0.25f, 0.25f, 0.25f, 0.75f, 0.75f);
			mRender.setIcon(ExtractionTube.icon, ExtractionTube.icon, ExtractionTube.icon, ExtractionTube.icon, EjectionTube.endIcon, EjectionTube.endIcon);
			mRender.drawBox(63, 0.0f, 0.1875f, 0.1875f, 0.0625f, 0.8125f, 0.8125f);
			mRender.drawBox(63, 0.1875f, 0.1875f, 0.1875f, 0.25f, 0.8125f, 0.8125f);
			break;
		case 5:
			mRender.setTextureRotation(1, 1, 1, 1, 0, 0);
			mRender.drawBox(15, 0.75f, 0.25f, 0.25f, 1.0f, 0.75f, 0.75f);
			mRender.setIcon(ExtractionTube.icon, ExtractionTube.icon, ExtractionTube.icon, ExtractionTube.icon, EjectionTube.endIcon, EjectionTube.endIcon);
			mRender.drawBox(63, 0.9375f, 0.1875f, 0.1875f, 1.0f, 0.8125f, 0.8125f);
			mRender.drawBox(63, 0.75f, 0.1875f, 0.1875f, 0.8125f, 0.8125f, 0.8125f);
			break;
		}
		
		mRender.resetTextureRotation();
	}
	
	private void renderPump(int side)
	{
		FMLClientHandler.instance().getClient().renderGlobal.renderEngine.bindTexture("/mods/Tubes/textures/models/tube-extractor-pump.png");
		
		mRender.setTextureIndex(0);
		mRender.setTextureSize(1, 1);
		
		Tessellator tes = Tessellator.instance;
		tes.setColorOpaque_F(1, 1, 1);
		
		tes.startDrawingQuads();
		
		float amount = ((float)Math.sin(((System.currentTimeMillis() % 1000) / 1000.0f) * Math.PI * 2) / 2f + 0.5f) * 0.0625f;
		
		mRender.resetTransform();
		switch(side)
		{
		case 0:
			mRender.translate(0, amount, 0);
			mRender.drawBox(63, 0.1875f, 0.0625f, 0.1875f, 0.8125f, 0.125f, 0.8125f);
			break;
		case 1:
			mRender.translate(0, -amount, 0);
			mRender.drawBox(63, 0.1875f, 0.875f, 0.1875f, 0.8125f, 0.9375f, 0.8125f);
			break;
		case 2:
			mRender.translate(0, 0, amount);
			mRender.drawBox(63, 0.1875f, 0.1875f, 0.0625f, 0.8125f, 0.8125f, 0.125f);
			break;
		case 3:
			mRender.translate(0, 0, -amount);
			mRender.drawBox(63, 0.1875f, 0.1875f, 0.875f, 0.8125f, 0.8125f, 0.9375f);
			break;
		case 4:
			mRender.translate(amount, 0, 0);
			mRender.drawBox(63, 0.0625f, 0.1875f, 0.1875f, 0.125f, 0.8125f, 0.8125f);
			break;
		case 5:
			mRender.translate(-amount, 0, 0);
			mRender.drawBox(63, 0.875f, 0.1875f, 0.1875f, 0.9375f, 0.8125f, 0.8125f);
			break;
		}
		
		tes.draw();
	}
	
	@Override
	public void renderItem( TubeDefinition type, ItemStack item )
	{
		mRender.resetTransform();
		mRender.enableNormals = true;
		mRender.resetTextureFlip();
		mRender.resetTextureRotation();
		mRender.resetLighting(15728880);
		
		mRender.setLocalLights(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
		
		Tessellator tes = Tessellator.instance;
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glPushMatrix();
		GL11.glTranslated(-0.5, -0.5, -0.5);
		
		FMLClientHandler.instance().getClient().renderGlobal.renderEngine.bindTexture("/terrain.png");
		tes.startDrawingQuads();
		
		mRender.setIcon(type.getCenterIcon());
		mRender.drawBox(55, 0.25f, 0.25f, 0.25f, 0.75f, 0.75f, 0.75f);
		
		renderExtractor(3);
		
		tes.draw();
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		renderPump(3);
		
		GL11.glPopMatrix();
	}
	
	
	@Override
	public boolean renderDynamic( TubeDefinition type, ITube tube, World world, int x, int y, int z )
	{
		int direction = ((IDirectionalTube)tube).getFacing();
		
		mRender.resetTransform();
		mRender.enableNormals = false;
		mRender.setLightingFromBlock(world, x, y, z);
		mRender.resetTextureFlip();
		mRender.resetTextureRotation();
		
		mRender.setLocalLights(0.5f, 1.0f, 0.8f, 0.8f, 0.6f, 0.6f);
		
		renderPump(direction);
		
		return false;
	}
}
