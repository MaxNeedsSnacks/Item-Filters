package dev.latvian.mods.itemfilters.item;

import net.minecraft.world.item.ItemStack;

/**
 * @author LatvianModder
 */
public class AlwaysTrueFilterItem extends BaseFilterItem
{
	@Override
	public boolean filter(ItemStack filter, ItemStack stack)
	{
		return true;
	}
}