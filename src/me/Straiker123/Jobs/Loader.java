package me.Straiker123.Jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.Straiker123.ConfigAPI;
import me.Straiker123.ItemCreatorAPI;
import me.Straiker123.TheAPI;

public class Loader extends JavaPlugin {
	public static HashMap<Player,List<String>> bb = new HashMap<Player,List<String>>();
	public static HashMap<Player,List<String>> bp = new HashMap<Player,List<String>>();
	public static HashMap<Player,List<String>> ke = new HashMap<Player,List<String>>();
	public static HashMap<Player,List<String>> ci = new HashMap<Player,List<String>>();
	public static HashMap<Player,List<String>> cf = new HashMap<Player,List<String>>();
	public static Pagination<ItemStack> craft;
	public static Pagination<ItemStack> block;
	public static Pagination<ItemStack> mob;
	public static Pagination<ItemStack> jobs;
	static ConfigAPI a = TheAPI.getConfig("TheJobs", "Config");
	static ConfigAPI c = TheAPI.getConfig("TheJobs", "Jobs");
	static ConfigAPI data = TheAPI.getConfig("TheJobs", "Data");
	static boolean schedule;
	private void msg(String s) {
		TheAPI.getConsole().sendMessage(TheAPI.colorize("&0[&2TheJobs v"+getDescription().getVersion()+"&0] &e"+s));
	}
	public void onEnable() {
		long t =System.currentTimeMillis()/100;
		msg("Loading configs..");
		a.addDefault("Options.Schedule.Use", true);
		a.addDefault("Options.Schedule.Delay", 20);
		a.addDefault("Options.MobsFromSpawner", false);
		a.addDefault("Options.MaxJobs", 3);
		a.create();
		data.create();
		c.create();
		schedule=a.getConfig().getBoolean("Options.Schedule.Use");
		msg("Loaded configs");
		msg("Loading GUI items:");
		loadItems();
		msg("Loaded GUI items");
		msg("Registering commands and events..");
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		Bukkit.getPluginCommand("jobs").setExecutor(new JobsCMD(this));
		msg("Loaded in "+(System.currentTimeMillis()/100-t)+"ms");
	}
	public static void updateJobs() {
		if(jobs!=null)
		jobs.clear();
		List<ItemStack> item_gui = new ArrayList<ItemStack>();
		if(new Jobs("").getAllJobs().isEmpty()==false)
			for(String s : new Jobs("").getAllJobs()) {
				item_gui.add(sar.createItem(new Job(s).getName(),Material.STONE,Arrays.asList("&6In this job is "+new Job(s).getWorkers().size()+" players")));
			}
			int a=item_gui.size();
			int add = 0;
			for(int i = 0; i > -1; ++i) {
				if(a-28 < 0) {
					add=28-a;
					break;
				}
				if(a-28 > 0)a=a-28;
				if(a-28==0)break;
			}
			if(add != 0) {
				Material m = Material.matchMaterial("LIME_STAINED_GLASS_PANE");
				if(m==null)m=Material.matchMaterial("STAINED_GLASS_PANE");
			for(int i = 0; i < add; ++i) {
				item_gui.add(sar.createItem(" ",m,13,null));
			}}
		jobs= new Pagination<>(28, item_gui);
	}
	private void loadItems() {
		List<ItemStack> item_gui = new ArrayList<ItemStack>();
		msg("Loading GUI items of CraftItem event..");
		for(Material m : Material.values()) {
			ItemStack i = new ItemStack(m);
			if(m.isOccluding() && Bukkit.getRecipesFor(i) != null && Bukkit.getRecipesFor(i).isEmpty()==false)
				item_gui.add(i);
		}
		craft= new Pagination<>(45, item_gui);
		msg("Loading GUI items of Jobs..");
		updateJobs();
		item_gui.clear();
		msg("Loading GUI items of BlockBreak and BlockPlace event..");
		for(Material m : Material.values()) {
			if(m.isBlock() && m.isOccluding())
				item_gui.add(new ItemStack(m));
		}
		block= new Pagination<>(45, item_gui);
		item_gui.clear();
		msg("Loading GUI items of EntityKill and EntityBreed event..");
		for(EntityType t : EntityType.values()) {
				if(getEgg(t) == null) continue;
				ItemCreatorAPI a = TheAPI.getItemCreatorAPI(getEgg(t));
				a.setDisplayName(t.name());
				item_gui.add(a.create());
		}
		mob= new Pagination<>(45, item_gui);
	}
	private Material getEgg(EntityType e) {
		Material egg = null;
		switch(e) {
		case ARMOR_STAND:
			egg=Material.ARMOR_STAND;
			break;
		case BAT:
			try {
			egg=Material.BAT_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case BLAZE:
			try {
			egg=Material.BLAZE_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case BOAT:
			try {
			egg=Material.OAK_BOAT;
			}catch(Exception rer) {
			egg=Material.matchMaterial("BOAT");
			}
			break;
		case CAVE_SPIDER:
			try {
			egg=Material.CAVE_SPIDER_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case CHICKEN:
			try {
			egg=Material.CHICKEN_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case COW:
			try {
			egg=Material.COW_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case CREEPER:
			try {
			egg=Material.CREEPER_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case HORSE:
			try {
			egg=Material.HORSE_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case GUARDIAN:
			try {
			egg=Material.GUARDIAN_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case ENDERMAN:
			try {
			egg=Material.ENDERMAN_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case ENDERMITE:
			try {
			egg=Material.ENDERMITE_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case GHAST:
			try {
			egg=Material.GHAST_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case MAGMA_CUBE:
			try {
			egg=Material.MAGMA_CUBE_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case MUSHROOM_COW:
			try {
			egg=Material.MOOSHROOM_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case OCELOT:
			try {
			egg=Material.OCELOT_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case PIG:
			try {
			egg=Material.PIG_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case RABBIT:
			try {
			egg=Material.RABBIT_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case SHEEP:
			try {
			egg=Material.SHEEP_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case SILVERFISH:
			try {
			egg=Material.SILVERFISH_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case SKELETON:
			try {
			egg=Material.SKELETON_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case SLIME:
			try {
			egg=Material.SLIME_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case SPIDER:
			try {
			egg=Material.SPIDER_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case SQUID:
			try {
			egg=Material.SQUID_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case VILLAGER:
			try {
			egg=Material.VILLAGER_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case WITCH:
			try {
			egg=Material.WITCH_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case WITHER:
			try {
			egg=Material.WITHER_ROSE;
			}catch(Exception rer) {
			egg=Material.SOUL_SAND;
			}
			break;
		case WOLF:
			try {
			egg=Material.WOLF_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case ZOMBIE:
			try {
			egg=Material.ZOMBIE_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case PIG_ZOMBIE:
			try {
			egg=Material.ZOMBIE_PIGMAN_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case CAT:
			try {
			egg=Material.CAT_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case COD:
			try {
			egg=Material.COD_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("RAW_FISH");
			}
			break;
		case SALMON:
			try {
			egg=Material.SALMON_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("RAW_FISH");
			}
			break;
		case TROPICAL_FISH:
			try {
			egg=Material.TROPICAL_FISH_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("RAW_FISH");
			}
			break;
		case PUFFERFISH:
			try {
			egg=Material.PUFFERFISH_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("RAW_FISH");
			}
			break;
		case DONKEY:
			try {
			egg=Material.DONKEY_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("RAW_FISH");
			}
			break;
		case DOLPHIN:
			try {
			egg=Material.DOLPHIN_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("RAW_FISH");
			}
			break;
		case DROWNED:
			try {
			egg=Material.DROWNED_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("RAW_FISH");
			}
			break;
		case ELDER_GUARDIAN:
			try {
			egg=Material.ELDER_GUARDIAN_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("RAW_FISH");
			}
			break;
		case ENDER_DRAGON:
			try {
			egg=Material.DRAGON_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("RAW_FISH");
			}
			break;
		case EVOKER:
			try {
			egg=Material.EVOKER_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("RAW_FISH");
			}
			break;
		case FOX:
			try {
			egg=Material.FOX_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("RAW_FISH");
			}
			break;
		case GIANT:
			try {
			egg=Material.ZOMBIE_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("RAW_FISH");
			}
			break;
		case HUSK:
			try {
			egg=Material.HUSK_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("RAW_FISH");
			}
			break;
		case IRON_GOLEM:
			try {
			egg=Material.IRON_BLOCK;
			}catch(Exception rer) {
			egg=Material.matchMaterial("RAW_FISH");
			}
			break;
		case ILLUSIONER:
			try {
			egg=Material.EVOKER_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("RAW_FISH");
			}
			break;
		case LLAMA:
			try {
			egg=Material.LLAMA_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("RAW_FISH");
			}
			break;
		case TRADER_LLAMA:
			try {
			egg=Material.TRADER_LLAMA_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("RAW_FISH");
			}
			break;
		case MINECART:
			try {
			egg=Material.MINECART;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MINECART");
			}
			break;
		case MINECART_CHEST:
			try {
			egg=Material.CHEST_MINECART;
			}catch(Exception rer) {
			egg=Material.matchMaterial("CHEST_MINECART");
			}
			break;
		case MINECART_COMMAND:
			try {
			egg=Material.COMMAND_BLOCK_MINECART;
			}catch(Exception rer) {
			egg=Material.matchMaterial("COMMAND_MINECART");
			}
			break;
		case MINECART_FURNACE:
			try {
			egg=Material.FURNACE_MINECART;
			}catch(Exception rer) {
			egg=Material.matchMaterial("FURNACE_MINECART");
			}
			break;
		case MINECART_HOPPER:
			try {
			egg=Material.HOPPER_MINECART;
			}catch(Exception rer) {
			egg=Material.matchMaterial("HOPPER_MINECART");
			}
			break;
		case MINECART_MOB_SPAWNER:
			try {
			egg=Material.MINECART;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MINECART");
			}
			break;
		case MINECART_TNT:
			try {
			egg=Material.TNT_MINECART;
			}catch(Exception rer) {
			egg=Material.matchMaterial("TNT_MINECART");
			}
			break;
		case PANDA:
			try {
			egg=Material.PANDA_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case MULE:
			try {
			egg=Material.MULE_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case PARROT:
			try {
			egg=Material.PARROT_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case PLAYER:
			try {
			egg=Material.PLAYER_HEAD;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case PHANTOM:
			try {
			egg=Material.PHANTOM_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case PILLAGER:
			try {
			egg=Material.PILLAGER_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case POLAR_BEAR:
			try {
			egg=Material.POLAR_BEAR_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case RAVAGER:
			try {
			egg=Material.RAVAGER_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case SHULKER:
			try {
			egg=Material.SHULKER_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case SNOWMAN:
			try {
			egg=Material.SNOWBALL;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case STRAY:
			try {
			egg=Material.STRAY_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case TURTLE:
			try {
			egg=Material.TURTLE_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case VEX:
			try {
			egg=Material.VEX_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case VINDICATOR:
			try {
			egg=Material.VINDICATOR_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case ZOMBIE_VILLAGER:
			try {
			egg=Material.ZOMBIE_VILLAGER_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case ZOMBIE_HORSE:
			try {
			egg=Material.ZOMBIE_HORSE_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case WITHER_SKELETON:
			try {
			egg=Material.WITHER_SKELETON_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case WANDERING_TRADER:
			try {
			egg=Material.WANDERING_TRADER_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
		case SKELETON_HORSE:
			try {
			egg=Material.SKELETON_HORSE_SPAWN_EGG;
			}catch(Exception rer) {
			egg=Material.matchMaterial("MONSTER_EGG");
			}
			break;
			default:
				break;
		}
		return egg;
	}
	public void onDisable() {
		a.save();
		c.save();
		data.save();
	}
}
