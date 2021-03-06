package com.bukkit.mcteam.vampire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.bukkit.mcteam.util.SortUtil;
import com.bukkit.mcteam.util.TextUtil;

public class Recipe {
	public Map<Material, Integer> materialQuantities;
	
	// GSON needs this noarg constructor;
	public Recipe() {
		materialQuantities = new HashMap<Material, Integer>();
	}
	
	public void removeFromInventory(Inventory inventory) {
		for (Material material: this.materialQuantities.keySet()) {
			inventory.removeItem(new ItemStack(material.getId(), this.materialQuantities.get(material)));
		}
	}
	
	public boolean inventoryContainsEnough(Inventory inventory) {
		for (Material material: this.materialQuantities.keySet()) {
			if (getMaterialCountFromInventory(material, inventory) < this.materialQuantities.get(material)) {
				return false;
			}
		}
		return true;
	}
	
	public static int getMaterialCountFromInventory(Material material, Inventory inventory) {
		int count = 0;
		for(ItemStack stack : inventory.all(material).values()) {
			count += stack.getAmount();
		}
		return count;
	}
	
	public String getRecipeLine() {
		ArrayList<String> lines = new ArrayList<String>();
		for (Entry<Material, Integer> item : SortUtil.entriesSortedByValues(this.materialQuantities)) {
			lines.add(""+item.getValue()+" "+TextUtil.getMaterialName(item.getKey()));
		}
		return TextUtil.implode(lines, ", ");
	}
}
