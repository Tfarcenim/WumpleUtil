package com.wumple.util.adapter;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemStackThingBase implements IThingBase
{
    public ItemStack owner = null;

    public ItemStackThingBase(ItemStack ownerIn)
    {
        owner = ownerIn;
    }

    @Override
    public World getWorld()
    {
        return null; // owner.getEntityWorld();
    }
    
    @Override
    public BlockPos getPos()
    {
        return null; // owner.getPosition();
    }

    @Override
    public boolean isInvalid()
    {
        return false;
    }

    @Override
    public void markDirty()
    {
    }

    @Override
    public void invalidate()
    {
        owner = null;
    }

    @Override
    public boolean sameAs(IThing entity)
    {
        if (entity instanceof ItemStackThingBase)
        {
            return owner == ((ItemStackThingBase) entity).owner;
        }
        return false;
    }
    
    @Override
    public Object object()
    {
        return owner;
    }
    
    @Override
    public int getCount()
    {
        return (owner != null) ? owner.getCount() : 0;
    }
    
    @Override
    public ICapabilityProvider capProvider()
    { return owner; }
    
    @Override
    public void forceUpdate()
    { 
    }
}