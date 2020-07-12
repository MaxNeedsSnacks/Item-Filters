package com.latmod.mods.itemfilters.filters;

import com.latmod.mods.itemfilters.api.IItemFilter;
import com.latmod.mods.itemfilters.api.ItemFiltersAPI;
import com.latmod.mods.itemfilters.item.ItemMissing;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public class ANDFilter extends LogicFilter implements INBTSerializable<NBTTagList>
{
	public final List<ItemStack> items = new ArrayList<>();

	@Override
	public String getID()
	{
		return "and";
	}

	@Override
	public boolean filter(ItemStack stack)
	{
		if (items.size() == 1)
		{
			return ItemFiltersAPI.filter(items.get(0), stack);
		}

		for (ItemStack stack1 : items)
		{
			if (!ItemFiltersAPI.filter(stack1, stack))
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public NBTTagList serializeNBT()
	{
		NBTTagList list = new NBTTagList();

		for (ItemStack stack : items)
		{
			if (!stack.isEmpty())
			{
				list.appendTag(ItemMissing.write(stack, true));
			}
		}

		return list;
	}

	@Override
	public void deserializeNBT(NBTTagList nbt)
	{
		items.clear();

		for (int i = 0; i < nbt.tagCount(); i++)
		{
			ItemStack stack = ItemMissing.read(nbt.get(i));

			if (!stack.isEmpty())
			{
				items.add(stack);
			}
		}
	}

	@Override
	public void clearCache()
	{
		super.clearCache();

		for (ItemStack item : items)
		{
			IItemFilter f = ItemFiltersAPI.getFilter(item);

			if (f != null)
			{
				f.clearCache();
			}
		}
	}

	@Override
	public void resetData()
	{
		items.clear();
	}
}