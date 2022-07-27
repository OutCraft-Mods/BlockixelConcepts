package com.OutCraft.blockixelconcepts.items;

import com.OutCraft.blockixelconcepts.lists.ItemList;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class TreasureGlove extends Item {

	public TreasureGlove() {
		super(new Item.Properties().rarity(Rarity.UNCOMMON).durability(4)
				.tab(ItemList.BlockixelConceptsCreativeModeTab));
	}

	@Override
	public ItemStack getDefaultInstance() {
		ItemStack itemStack = super.getDefaultInstance();
		itemStack.setDamageValue(this.getMaxDamage());
		return itemStack;
	}

	@Override
	public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
		if (this.allowedIn(pCategory)) {
			ItemStack itemStack = new ItemStack(this);
			itemStack.setDamageValue(this.getMaxDamage());
			pItems.add(itemStack);
		}
	}

	@Override
	public boolean isPiglinCurrency(ItemStack stack) {
		if (stack.getOrCreateTag().getInt("treasureType") == TreasureType.GOLD.ordinal())
			return true;
		return super.isPiglinCurrency(stack);
	}

	@Override
	public boolean isBarVisible(ItemStack pStack) {
		return true;
	}

	@Override
	public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction,
			Player pPlayer, SlotAccess pAccess) {
		if (pStack.getDamageValue() >= pStack.getMaxDamage() && pAction.equals(ClickAction.SECONDARY)) {
			if (pOther.getItem().equals(Items.IRON_INGOT)) {
				pOther.split(1);
				pStack.setDamageValue(0);
				pStack.getOrCreateTag().putInt("treasureType", TreasureType.IRON.ordinal());
				return true;
			} else if (pOther.getItem().equals(Items.COPPER_INGOT)) {
				pOther.split(1);
				pStack.setDamageValue(0);
				pStack.getOrCreateTag().putInt("treasureType", TreasureType.COPPER.ordinal());
				return true;
			} else if (pOther.getItem().equals(Items.GOLD_INGOT)) {
				pOther.split(1);
				pStack.setDamageValue(0);
				pStack.getOrCreateTag().putInt("treasureType", TreasureType.GOLD.ordinal());
				return true;
			} else if (pOther.getItem().equals(Items.EMERALD)) {
				pOther.split(1);
				pStack.setDamageValue(0);
				pStack.getOrCreateTag().putInt("treasureType", TreasureType.EMERALD.ordinal());
				return true;
			} else if (pOther.getItem().equals(Items.DIAMOND)) {
				pOther.split(1);
				pStack.setDamageValue(0);
				pStack.getOrCreateTag().putInt("treasureType", TreasureType.DIAMOND.ordinal());
				return true;
			} else if (pOther.getItem().equals(Items.AMETHYST_SHARD)) {
				pOther.split(1);
				pStack.setDamageValue(0);
				pStack.getOrCreateTag().putInt("treasureType", TreasureType.AMETHYST.ordinal());
				return true;
			}
		}
		return super.overrideOtherStackedOnMe(pStack, pOther, pSlot, pAction, pPlayer, pAccess);
	}

	public enum TreasureType {
		IRON, COPPER, GOLD, EMERALD, DIAMOND, AMETHYST
	}
}
