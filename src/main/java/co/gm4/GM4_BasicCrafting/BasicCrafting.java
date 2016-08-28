package co.gm4.GM4_BasicCrafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class BasicCrafting extends JavaPlugin {
	private HashMap<ItemStack[], ItemStack[]> recipes = new HashMap<>();
	
	@Override
	public void onEnable() {
		saveDefaultConfig();

		getLogger().log(Level.INFO,
				ChatColor.GREEN + "[StandardCrafting] Module enabled!");

		registerRecipe(new ItemStack[] {
				new ItemStack(Material.STICK)
		}, new ItemStack[] {
				new ItemStack(Material.WEB)
		});
		
		
		
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {

			@Override
			public void run() {
				for (World w : Bukkit.getWorlds()) {
					ArrayList<ArmorStand> stands = new ArrayList<>(w
							.getEntitiesByClass(ArmorStand.class));
					for (ArmorStand a : stands) {
						Block b;
						if (a.getCustomName().equals("GM4_CustomCrafter")
								&& (b = w.getBlockAt(a.getLocation())).getType()
										.equals(Material.DROPPER)) {
							Dropper d = (Dropper) b;
							
							ItemStack[] items = d.getInventory().getContents();
							
							d.getInventory().setContents(recipes.getOrDefault(items, items));
						}
					}
				}
			}

		}, 0L, 20L);

	}
	
	public void registerRecipe(ItemStack[] from, ItemStack[] to) {
		recipes.put(from, to);
	}
}
