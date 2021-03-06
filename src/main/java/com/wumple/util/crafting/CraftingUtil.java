package com.wumple.util.crafting;

import java.lang.reflect.Field;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class CraftingUtil
{
	/*
	 * Grow results in craftMatrix by 1 - useful for recipes that don't consume an ingredient
	 */
	public static void growByOne(IInventory craftMatrix, ItemStack results)
	{
		for (int i = craftMatrix.getSizeInventory() - 1; i >= 0; i--)
		{
			final ItemStack slot = craftMatrix.getStackInSlot(i);

			if (slot == null)
			{
				continue;
			}
			else if (slot == results)
			{
				// increment stack size by 1 so when decreased automatically by 1 there is still 1 there
				slot.grow(1);
			}
		}
	}
	
    public static boolean isItemBeingCraftedBy(ItemStack stack, Entity entity)
    {
        boolean beingCrafted = false;

        PlayerEntity player = (PlayerEntity) (entity);
        if (player != null)
        {
            if (player.openContainer != null)
            {
                Slot slot = player.openContainer.getSlot(0);
                if ((slot != null) && (slot instanceof CraftingResultSlot) && (slot.getStack() == stack))
                {
                    beingCrafted = true;
                }
            }
        }

        return beingCrafted;
    }
    
    // adapted from http://www.minecraftforge.net/forum/topic/22927-player-based-crafting-recipes/
    public static Field eventHandlerField = ObfuscationReflectionHelper.findField(CraftingInventory.class, "field_70465_c"); // "eventHandler"
    public static Field PlayerContainerPlayerField = ObfuscationReflectionHelper.findField(PlayerContainer.class, "field_82862_h"); // "player"
    public static Field slotCraftingPlayerField = ObfuscationReflectionHelper.findField(CraftingResultSlot.class, "field_75238_b"); // "player"
	
    public static PlayerEntity findPlayer(CraftingInventory inv)
    {
        try
        {
            Container container = (Container) eventHandlerField.get(inv);
            if (container instanceof PlayerContainer)
            {
                return (PlayerEntity) PlayerContainerPlayerField.get(container);
            }
            else if (container instanceof WorkbenchContainer)
            {
                return (PlayerEntity) slotCraftingPlayerField.get(container.getSlot(0));
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            //throw e;
            return null;
        }
    }
    
    public static World findWorld(CraftingInventory inv)
    {
        PlayerEntity player = findPlayer(inv);
        return (player != null) ? player.getEntityWorld() : null;
    }
}
