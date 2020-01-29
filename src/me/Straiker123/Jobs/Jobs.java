package me.Straiker123.Jobs;

import java.util.ArrayList;
import java.util.List;

public class Jobs {
	String s; //hrac
	public Jobs(String s) {
		this.s=s;
	}
	public List<String> getJobs(){
		List<String> a = new ArrayList<String>();
		if(Loader.c.getConfig().getString("Jobs")!=null)
		for(String d : Loader.c.getConfig().getConfigurationSection("Jobs").getKeys(false)) {
			if(new Job(d).getWorkers().contains(s))
			a.add(d);
		}
		return a;
	}

	
	
	public void createJob(String a) {
		Loader.c.getConfig().set("Jobs."+a+".Name", a);
	}
	public void deleteJob(String a) {
		Loader.c.getConfig().set("Jobs."+a, null);
	}
	public boolean isWorking() {
		return Loader.data.getConfig().getString("data."+s)!=null;
	}
	public boolean isInJob(String name) {
		return getJobs().contains(name);
	}
	public boolean existJob(String name) {
		return getAllJobs().contains(name);
	}

	public List<String> getAllJobs() {
		List<String> a = new ArrayList<String>();
		if(Loader.c.getConfig().getString("Jobs")!=null)
		for(String d : Loader.c.getConfig().getConfigurationSection("Jobs").getKeys(false))a.add(d);
		return a;
	}
}
