package me.Straiker123.Jobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Straiker123.LoaderClass;
import me.Straiker123.TheAPI;

public class DelayReward {

	public static void addItem(Player p, me.Straiker123.Jobs.Job.JobAction ac, String item) {
		switch(ac) {
		case BlockBreak:{
			List<String> s = Loader.bb.get(p);
			if(s==null)s=new ArrayList<String>();
			s.add(item);
			Loader.bb.put(p,s);
		}break;
		case BlockPlace:{
			List<String> s = Loader.bp.get(p);
			if(s==null)s=new ArrayList<String>();
			s.add(item);
			Loader.bp.put(p,s);
		}break;
		case CatchFish:{
			List<String> s = Loader.cf.get(p);
			if(s==null)s=new ArrayList<String>();
			s.add(item);
			Loader.cf.put(p,s);
		}break;
		case CraftItem:{
			List<String> s = Loader.ci.get(p);
			if(s==null)s=new ArrayList<String>();
			s.add(item);
			Loader.ci.put(p,s);
		}break;
		case KillEntity:{
			List<String> s = Loader.ke.get(p);
			if(s==null)s=new ArrayList<String>();
			s.add(item);
			Loader.ke.put(p,s);
		}break;
		}
	}
	
	public static void run() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(LoaderClass.plugin, new Runnable() {

			@Override
			public void run() {
				if(Loader.bb.isEmpty()==false)
				for(Player p : Loader.bb.keySet()) {
					Jobs a = new Jobs(p.getName());
					for(String s : Loader.bb.get(p)) {
						for(String d  :a.getJobs()) {
						new Job(d).setPoints(p.getName(),
								new Job(d).getPoints(p.getName())+
								Loader.c.getConfig().getDouble("Jobs."+s+".Items.BlockBreak."+s+".Points"));
						p.setTotalExperience(p.getTotalExperience()+Loader.c.getConfig().getInt("Jobs."+s+".Items.BlockBreak."+s+".Exp"));
						TheAPI.getEconomyAPI().depositPlayer(p.getName(), Loader.c.getConfig().getDouble("Jobs."+s+".Items.BlockBreak."+s+".Money"));
						}
						}
					Loader.bb.remove(p);
				}
				
				if(Loader.bp.isEmpty()==false)
				for(Player p : Loader.bp.keySet()) {
					Jobs a = new Jobs(p.getName());
					for(String s : Loader.bp.get(p)) {
						for(String d  :a.getJobs()) {
							new Job(d).setPoints(p.getName(),
								new Job(d).getPoints(p.getName())+
								Loader.c.getConfig().getDouble("Jobs."+s+".Items.BlockPlace."+s+".Points"));
						p.setTotalExperience(p.getTotalExperience()+Loader.c.getConfig().getInt("Jobs."+s+".Items.BlockPlace."+s+".Exp"));
						TheAPI.getEconomyAPI().depositPlayer(p.getName(), Loader.c.getConfig().getDouble("Jobs."+s+".Items.BlockPlace."+s+".Money"));
						}
						}
					Loader.bp.remove(p);
				}
				if(Loader.ci.isEmpty()==false)
				for(Player p : Loader.ci.keySet()) {
					Jobs a = new Jobs(p.getName());
					for(String s : Loader.ci.get(p)) {
						for(String d  :a.getJobs()) {
							new Job(d).setPoints(p.getName(),
									new Job(d).getPoints(p.getName())+
								Loader.c.getConfig().getDouble("Jobs."+s+".Items.CraftItem."+s+".Points"));
						p.setTotalExperience(p.getTotalExperience()+Loader.c.getConfig().getInt("Jobs."+s+".Items.CraftItem."+s+".Exp"));
						TheAPI.getEconomyAPI().depositPlayer(p.getName(), Loader.c.getConfig().getDouble("Jobs."+s+".Items.CraftItem."+s+".Money"));
						}
						}
					Loader.ci.remove(p);
				}
				if(Loader.cf.isEmpty()==false)
				for(Player p : Loader.cf.keySet()) {
					Jobs a = new Jobs(p.getName());
					for(String s : Loader.cf.get(p)) {
						for(String d  :a.getJobs()) {
							new Job(d).setPoints(p.getName(),
								new Job(d).getPoints(p.getName())+
								Loader.c.getConfig().getDouble("Jobs."+s+".Items.CatchFish."+s+".Points"));
						p.setTotalExperience(p.getTotalExperience()+Loader.c.getConfig().getInt("Jobs."+s+".Items.CatchFish."+s+".Exp"));
						TheAPI.getEconomyAPI().depositPlayer(p.getName(), Loader.c.getConfig().getDouble("Jobs."+s+".Items.CatchFish."+s+".Money"));
						}
						}
					Loader.cf.remove(p);
				}
				if(Loader.ke.isEmpty()==false)
				for(Player p : Loader.ke.keySet()) {
					Jobs a = new Jobs(p.getName());
					for(String s : Loader.ke.get(p)) {
						for(String d  :a.getJobs()) {
						new Job(d).setPoints(p.getName(),
								new Job(d).getPoints(p.getName())+
								Loader.c.getConfig().getDouble("Jobs."+s+".Items.KillEntity."+s+".Points"));
						p.setTotalExperience(p.getTotalExperience()+Loader.c.getConfig().getInt("Jobs."+s+".Items.KillEntity."+s+".Exp"));
						TheAPI.getEconomyAPI().depositPlayer(p.getName(), Loader.c.getConfig().getDouble("Jobs."+s+".Items.KillEntity."+s+".Money"));
						}
						}
					Loader.ke.remove(p);
				}
				
			}
			
		}, 20, 20*Loader.a.getConfig().getInt("Options.Schedule.Delay"));
	}
}
