package me.Straiker123.Jobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

public class Job {
	String s;
	public Job(String s) { //job name
		this.s=s;
	}
	
	public String getName() {
		return Loader.c.getConfig().getString("Jobs."+s+".Name");
	}
	
	public List<String> getWorkers(){
		List<String> a = new ArrayList<String>();
		if(Loader.c.getConfig().getString("Jobs."+s+".Workers")!=null)
		for(String d:Loader.c.getConfig().getConfigurationSection("Jobs."+s+".Workers").getKeys(false))a.add(d);
		return a;
	}
	
	public void leave(String d) {
		Loader.c.getConfig().set("Jobs."+s+".Workers."+d, null);
		Loader.c.save();
	}
	
	public void join(String d) {
		Loader.c.getConfig().set("Jobs."+s+".Workers."+d+".Level", 0);
		Loader.c.getConfig().set("Jobs."+s+".Workers."+d+".Points", 0);
		Loader.c.save();
	}
	public int getLevel(String d) {
		return Loader.c.getConfig().getInt("Jobs."+s+".Workers."+d+".Level");
	}
	public void setLevel(String d, int level) {
		Loader.c.getConfig().set("Jobs."+s+".Workers."+d+".Level", level);
		Loader.c.save();
	}
	public double getPoints(String d) {
		return Loader.c.getConfig().getInt("Jobs."+s+".Workers."+d+".Points");
	}
	public void setPoints(String d, double a) {
		Loader.c.getConfig().set("Jobs."+s+".Workers."+d+".Points", a);
		Loader.c.save();
		if(a >= getMultiplier(d))
		updateLevel(d);
	}
	public void updateLevel(String d) {
		if(getPoints(d) >= getMultiplier(d)) {
		setLevel(d,getLevel(d)+1);
		setPoints(d,getPoints(d)-getMultiplier(d));
	}}
	public static enum Stat{
		Money,
		Points,
		Xp
	}
	public static enum JobAction{
		BlockBreak,
		BlockPlace,
		KillEntity,
		CraftItem,
		CatchFish
	}
	
	public void processItem(String d,String item,JobAction ac) {
		setPoints(d,getPoints(d)+Loader.c.getConfig().getDouble("Jobs."+s+".Items."+ac.toString()+"."+item+".Points"));
		if(Bukkit.getPlayer(d)!=null) {
			
		}
		setPoints(d,getPoints(d)+Loader.c.getConfig().getDouble("Jobs."+s+".Items."+ac.toString()+"."+item+".Points"));
		setPoints(d,getPoints(d)+Loader.c.getConfig().getDouble("Jobs."+s+".Items."+ac.toString()+"."+item+".Points"));
	}
	
	public void setItem(String item, JobAction ac, Stat stat, double value) {
		Loader.c.getConfig().set("Jobs."+s+".Items."+ac.toString()+"."+item+"."+stat.toString(), value);
		Loader.c.save();
	}
	public void deleteItem(String item, JobAction ac) {
		Loader.c.getConfig().set("Jobs."+s+".Items."+ac.toString()+"."+item, null);
		Loader.c.save();
	}
	
	public boolean isItem(String item, JobAction ac) {
		return Loader.c.getConfig().getString("Jobs."+s+".Items."+ac.toString()+"."+item) != null;
	}

	
	public int maxLevel() {
		if(Loader.c.getConfig().getInt("Jobs."+s+".MaxLevel")!=0)
		return Loader.c.getConfig().getInt("Jobs."+s+".MaxLevel");
		return 100;
	}
	
	public int getMultiplier(String d) {
		int i = 300;
		int set = getLevel(d)/100+maxLevel()*maxLevel()/100+getLevel(d)/2;
		if(getLevel(d) > 0)i=i+set;
		return i;
	}
}
