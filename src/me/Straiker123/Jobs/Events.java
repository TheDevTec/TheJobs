package me.Straiker123.Jobs;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.metadata.FixedMetadataValue;

import me.Straiker123.LoaderClass;
import me.Straiker123.TheAPI;

public class Events implements Listener {

	 @EventHandler
	 public void onSpawn(CreatureSpawnEvent e) {
		 if(e.getSpawnReason()==SpawnReason.SPAWNER && e.getEntityType() != EntityType.DROPPED_ITEM) {
			 e.getEntity().setMetadata("spawner", new FixedMetadataValue(LoaderClass.plugin, true));
		 }
	 }
	 @EventHandler
	 public void onKill(EntityDeathEvent e) {
		 if(!Loader.a.getConfig().getBoolean("Options.MobsFromSpawner")) {
			if(e.getEntity().hasMetadata("spawner"))return;
		 }
		 if(e.getEntity().getKiller() instanceof Player) {
			 
			 Player f = (Player)e.getEntity().getKiller();
			 String s = f.getName();
			 if(Loader.schedule)
			 DelayReward.addItem(f, me.Straiker123.Jobs.Job.JobAction.KillEntity, e.getEntity().getName());
			 else {
				 for(String d  :new Jobs(s).getJobs()) {
						new Job(d).setPoints(s,
							new Job(d).getPoints(s)+
							Loader.c.getConfig().getDouble("Jobs."+s+".Items.KillEntity."+s+".Points"));
					f.setTotalExperience(f.getTotalExperience()+Loader.c.getConfig().getInt("Jobs."+s+".Items.KillEntity."+s+".Exp"));
					TheAPI.getEconomyAPI().depositPlayer(s, Loader.c.getConfig().getDouble("Jobs."+s+".Items.KillEntity."+s+".Money"));
					}
			 }
		 }
	 }
	 @EventHandler
	 public void onChat(AsyncPlayerChatEvent e) {
		if(Loader.data.getConfig().getString("data."+e.getPlayer().getName()+".editor") != null) {
			if(e.getMessage().equalsIgnoreCase("cancel")) {
				e.setCancelled(true);
				
			}
		}
	 }
	 @EventHandler
	 public void onBreak(BlockBreakEvent e) {
		 Player f = e.getPlayer();
		 String s = f.getName();
		 if(Loader.schedule)
			 DelayReward.addItem(f, me.Straiker123.Jobs.Job.JobAction.BlockBreak, e.getBlock().getType().name());
		 else {
			 for(String d  :new Jobs(s).getJobs()) {
					new Job(d).setPoints(s,
						new Job(d).getPoints(s)+
						Loader.c.getConfig().getDouble("Jobs."+s+".Items.BlockBreak."+s+".Points"));
				f.setTotalExperience(f.getTotalExperience()+Loader.c.getConfig().getInt("Jobs."+s+".Items.BlockBreak."+s+".Exp"));
				TheAPI.getEconomyAPI().depositPlayer(s, Loader.c.getConfig().getDouble("Jobs."+s+".Items.BlockBreak."+s+".Money"));
				}
		 }
		 }
	 @EventHandler
	 public void onPlace(BlockPlaceEvent e) {
		 Player f = e.getPlayer();
		 String s = f.getName();
		 if(Loader.schedule)
			 DelayReward.addItem(e.getPlayer(), me.Straiker123.Jobs.Job.JobAction.BlockPlace, e.getBlock().getType().name());
			 else {
				 for(String d  :new Jobs(s).getJobs()) {
						new Job(d).setPoints(s,
							new Job(d).getPoints(s)+
							Loader.c.getConfig().getDouble("Jobs."+s+".Items.BlockPlace."+s+".Points"));
					f.setTotalExperience(f.getTotalExperience()+Loader.c.getConfig().getInt("Jobs."+s+".Items.BlockPlace."+s+".Exp"));
					TheAPI.getEconomyAPI().depositPlayer(s, Loader.c.getConfig().getDouble("Jobs."+s+".Items.BlockPlace."+s+".Money"));
					}
			 }
		 }
	 @EventHandler
	 public void onCatch(PlayerFishEvent e) {
		 if(e.getCaught() != null && e.getCaught() instanceof Item) {
		 String name = ((Item)e.getCaught()).getItemStack().getType().name();
		 if(name.equalsIgnoreCase("RAW_FISH")||name.equalsIgnoreCase("PUFFERFISH")||name.equalsIgnoreCase("COD")||name.equalsIgnoreCase("SALMON")
				 ||name.equalsIgnoreCase("TROPICAL_FISH")) {
			 Player f = e.getPlayer();
			 String s = f.getName();
			 if(Loader.schedule)
			 DelayReward.addItem(f, me.Straiker123.Jobs.Job.JobAction.CatchFish, name);
			 else {
				 for(String d  :new Jobs(s).getJobs()) {
						new Job(d).setPoints(s,
							new Job(d).getPoints(s)+
							Loader.c.getConfig().getDouble("Jobs."+s+".Items.CaughtFish."+s+".Points"));
					f.setTotalExperience(f.getTotalExperience()+Loader.c.getConfig().getInt("Jobs."+s+".Items.CaughtFish."+s+".Exp"));
					TheAPI.getEconomyAPI().depositPlayer(s, Loader.c.getConfig().getDouble("Jobs."+s+".Items.CaughtFish."+s+".Money"));
					}
			 }
		 }
		 }}
	 @EventHandler
	 public void onCraft(CraftItemEvent e) {
		 if(e.getWhoClicked() instanceof Player) {
			 if(e.getSlotType()==SlotType.RESULT) {
				 Player f = (Player)e.getWhoClicked();
				 String s = f.getName();
				 if(Loader.schedule)
			 DelayReward.addItem(f, me.Straiker123.Jobs.Job.JobAction.CraftItem, e.getCurrentItem().getType().name());
			 else {
				 for(String d  :new Jobs(s).getJobs()) {
						new Job(d).setPoints(s,
							new Job(d).getPoints(s)+
							Loader.c.getConfig().getDouble("Jobs."+s+".Items.CraftItem."+s+".Points"));
					f.setTotalExperience(f.getTotalExperience()+Loader.c.getConfig().getInt("Jobs."+s+".Items.CraftItem."+s+".Exp"));
					TheAPI.getEconomyAPI().depositPlayer(s, Loader.c.getConfig().getDouble("Jobs."+s+".Items.CraftItem."+s+".Money"));
					}
			 }
		 }
	 }
}}


