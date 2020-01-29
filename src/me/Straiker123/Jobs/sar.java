package me.Straiker123.Jobs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Straiker123.GUICreatorAPI;
import me.Straiker123.GUICreatorAPI.Options;
import me.Straiker123.ItemCreatorAPI;
import me.Straiker123.TheAPI;

public class sar {
	Player s;
	public sar(Player p) {
		s=p;
		Loader.updateJobs();
	}
	static ItemStack createItem(String name, Material material, List<String> lore) {
    	ItemCreatorAPI a = TheAPI.getItemCreatorAPI(material);
    	a.setDisplayName(name);
    	a.setLore(lore);
    	return a.create();
    }
	static ItemStack createItem(String name, Material material, int i, List<String> lore) {
    	ItemCreatorAPI a = TheAPI.getItemCreatorAPI(material);
    	a.setDurability(i);
    	a.setDisplayName(name);
    	a.setLore(lore);
    	return a.create();
    }
		
	public static enum AllEvents{
		EntityKill,
		EntityBreed,
		BlockPlace,
		BlockBreak,
		CatchFish,
		CraftItem
	}
	
	public void openEdit(String j) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(s);
		a.setSize(54);
		a.setTitle("&eJobs Editor &8- &5Select event");
		pre(a);
		HashMap<Options, Object> setting = new HashMap<Options, Object>();
		setting.put(Options.CANT_BE_TAKEN, true);
		setting.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				manage(j);
			}});
		a.setItem(49, createItem("&cBack", Material.BARRIER, null),setting);
		for(AllEvents w : AllEvents.values()) {
			setting.remove(Options.RUNNABLE);
			setting.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
						openEditorWith(j,w,0);
				}});
		a.addItem(createItem(w.name(),Material.STONE, null),setting);
		}
		a.open();
	}
	
	public void openEditorWith(String job, AllEvents d, int i) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(s);
		a.setSize(54);
		a.setTitle("&eJobs Editor &8- &5Editor of Event items");
		prer(a);
		HashMap<Options, Object> setting = new HashMap<Options, Object>();
		setting.put(Options.CANT_BE_TAKEN, true);
		setting.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				openEdit(job);
			}});
		a.setItem(49, createItem("&cBack", Material.BARRIER, null),setting);
		
		switch(d) {
		case BlockBreak:
			for(ItemStack m : Loader.block.getPage(i)) {
				boolean ss = Loader.c.getConfig().getString("Jobs."+job+".Events."+d.toString()+"."+m.getType().name())!=null;
				setting.remove(Options.RUNNABLE);
				setting.remove(Options.RUNNABLE_LEFT_CLICK);
				setting.remove(Options.RUNNABLE_SHIFT_WITH_RIGHT_CLICK);
				setting.remove(Options.RUNNABLE_MIDDLE_CLICK);
				setting.put(Options.RUNNABLE_LEFT_CLICK, new Runnable() {
					@Override
					public void run() {
						if(!ss) {
						Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+"."+m.getType().name()+".Points", 0.0);
						Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+"."+m.getType().name()+".Money", 0.0);
						Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+"."+m.getType().name()+".Exp", 0.0);
						Loader.c.save();
						openEditorWith(job, d, i);
						}
					}});
				setting.put(Options.RUNNABLE_SHIFT_WITH_RIGHT_CLICK, new Runnable() {
					@Override
					public void run() {
						if(ss) {
						Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+"."+m.getType().name(), null);
						Loader.c.save();
						openEditorWith(job, d, i);
						}
					}});
				setting.put(Options.RUNNABLE_MIDDLE_CLICK, new Runnable() {
					@Override
					public void run() {
						if(ss) {
						openEditorItem(job,d,m.getType().name());
						}}});
				a.addItem(createItem("&6"+(m.getItemMeta().hasDisplayName() ? m.getItemMeta().getDisplayName() : m.getType().name()),m.getType(), Arrays.asList("&7- Left click to &aadd item"
						,"&7- Middle click to &6edit rewards","&7- Shift + Right click to &cremove item", "&7","&7- Item "+(ss ? "is &aadded" : "&cisn't added"))),setting);
		}
			break;
		case BlockPlace:
			for(ItemStack m : Loader.block.getPage(i)) {
				boolean ss = Loader.c.getConfig().getString("Jobs."+job+".Events."+d.toString()+"."+m.getType().name())!=null;
				setting.remove(Options.RUNNABLE);
				setting.remove(Options.RUNNABLE_LEFT_CLICK);
				setting.remove(Options.RUNNABLE_SHIFT_WITH_RIGHT_CLICK);
				setting.remove(Options.RUNNABLE_MIDDLE_CLICK);
				setting.put(Options.RUNNABLE_LEFT_CLICK, new Runnable() {
					@Override
					public void run() {
						if(!ss) {
						Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+"."+m.getType().name()+".Points", 0.0);
						Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+"."+m.getType().name()+".Money", 0.0);
						Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+"."+m.getType().name()+".Exp", 0.0);
						Loader.c.save();
						openEditorWith(job, d, i);
						}
					}});
				setting.put(Options.RUNNABLE_SHIFT_WITH_RIGHT_CLICK, new Runnable() {
					@Override
					public void run() {
						if(ss) {
						Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+"."+m.getType().name(), null);
						Loader.c.save();
						openEditorWith(job, d, i);
						}
					}});
				setting.put(Options.RUNNABLE_MIDDLE_CLICK, new Runnable() {
					@Override
					public void run() {
						if(ss) {
						openEditorItem(job,d,m.getType().name());
						}}});
				a.addItem(createItem("&6"+(m.getItemMeta().hasDisplayName() ? m.getItemMeta().getDisplayName() : m.getType().name()),m.getType(), Arrays.asList("&7- Left click to &aadd item"
						,"&7- Middle click to &6edit rewards","&7- Shift + Right click to &cremove item", "&7","&7- Item "+(ss ? "is &aadded" : "&cisn't added"))),setting);
		}
			break;
		case CatchFish:
			if(Loader.c.getConfig().getString("Jobs."+job+".Events."+d.toString()+".RAW_FISH")==null) {
			setting.remove(Options.RUNNABLE);
			setting.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+".RAW_FISH.Points", 0.0);
					Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+".RAW_FISH.Money", 0.0);
					Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+".RAW_FISH.Exp", 0.0);
					Loader.c.save();
					openEditorWith(job, d, i);
				}});
			a.addItem(createItem("&6FISH", Material.matchMaterial("RAW_FISH") != null ? Material.matchMaterial("RAW_FISH") : Material.matchMaterial("COD"), Arrays.asList("&8Click to add this block")),setting);
			}
			break;
		case CraftItem:
			for(ItemStack m : Loader.craft.getPage(i)) {
				boolean ss = Loader.c.getConfig().getString("Jobs."+job+".Events."+d.toString()+"."+m.getType().name())!=null;
				setting.remove(Options.RUNNABLE);
				setting.remove(Options.RUNNABLE_LEFT_CLICK);
				setting.remove(Options.RUNNABLE_SHIFT_WITH_RIGHT_CLICK);
				setting.remove(Options.RUNNABLE_MIDDLE_CLICK);
				setting.put(Options.RUNNABLE_LEFT_CLICK, new Runnable() {
					@Override
					public void run() {
						if(!ss) {
						Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+"."+m.getType().name()+".Points", 0.0);
						Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+"."+m.getType().name()+".Money", 0.0);
						Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+"."+m.getType().name()+".Exp", 0.0);
						Loader.c.save();
						openEditorWith(job, d, i);
						}
					}});
				setting.put(Options.RUNNABLE_SHIFT_WITH_RIGHT_CLICK, new Runnable() {
					@Override
					public void run() {
						if(ss) {
						Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+"."+m.getType().name(), null);
						Loader.c.save();
						openEditorWith(job, d, i);
						}
					}});
				setting.put(Options.RUNNABLE_MIDDLE_CLICK, new Runnable() {
					@Override
					public void run() {
						if(ss) {
						openEditorItem(job,d,m.getType().name());
						}}});
				a.addItem(createItem("&6"+(m.getItemMeta().hasDisplayName() ? m.getItemMeta().getDisplayName() : m.getType().name()),m.getType(), Arrays.asList("&7- Left click to &aadd item"
						,"&7- Middle click to &6edit rewards","&7- Shift + Right click to &cremove item", "&7","&7- Item "+(ss ? "is &aadded" : "&cisn't added"))),setting);
		}
			break;
		case EntityBreed:
			break;
		case EntityKill:
			for(ItemStack m : Loader.mob.getPage(i)) {
				boolean ss = Loader.c.getConfig().getString("Jobs."+job+".Events."+d.toString()+"."+m.getType().name())!=null;
				setting.remove(Options.RUNNABLE);
				setting.remove(Options.RUNNABLE_LEFT_CLICK);
				setting.remove(Options.RUNNABLE_SHIFT_WITH_RIGHT_CLICK);
				setting.remove(Options.RUNNABLE_MIDDLE_CLICK);
				setting.put(Options.RUNNABLE_LEFT_CLICK, new Runnable() {
					@Override
					public void run() {
						if(!ss) {
						Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+"."+m.getType().name()+".Points", 0.0);
						Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+"."+m.getType().name()+".Money", 0.0);
						Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+"."+m.getType().name()+".Exp", 0.0);
						Loader.c.save();
						openEditorWith(job, d, i);
						}
					}});
				setting.put(Options.RUNNABLE_SHIFT_WITH_RIGHT_CLICK, new Runnable() {
					@Override
					public void run() {
						if(ss) {
						Loader.c.getConfig().set("Jobs."+job+".Events."+d.toString()+"."+m.getType().name(), null);
						Loader.c.save();
						openEditorWith(job, d, i);
						}
					}});
				setting.put(Options.RUNNABLE_MIDDLE_CLICK, new Runnable() {
					@Override
					public void run() {
						if(ss) {
						openEditorItem(job,d,m.getType().name());
						}}});
					a.addItem(createItem("&6"+(m.getItemMeta().hasDisplayName() ? m.getItemMeta().getDisplayName() : m.getType().name()),m.getType(), Arrays.asList("&7- Left click to &aadd item"
							,"&7- Middle click to &6edit rewards","&7- Shift + Right click to &cremove item", "&7","&7- Item "+(ss ? "is &aadded" : "&cisn't added"))),setting);
			}
			break;
		}
		a.open();
	}

	public void openEditorItem(String job, AllEvents d, String name) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(s);
		a.setSize(54);
		a.setTitle("&eJobs Editor &8- &5Editor of Event item");		
		pre(a);
		HashMap<Options, Object> setting = new HashMap<Options, Object>();
		setting.put(Options.CANT_BE_TAKEN, true);
		setting.put(Options.RUNNABLE, new Runnable() {

			@Override
			public void run() {
				openEditorWith(job, d, 0);
			}
			
		});
		a.setItem(49,createItem("&cBack",Material.BARRIER, null),setting);
		setting.remove(Options.RUNNABLE);
		a.open();
	}
	public void manage(String job) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(s);
		a.setSize(54);
		a.setTitle("&eJobs Editor &8- &5Editor of Event item");		
		pre(a);
		HashMap<Options, Object> setting = new HashMap<Options, Object>();
		setting.put(Options.CANT_BE_TAKEN, true);
		setting.put(Options.RUNNABLE, new Runnable() {

			@Override
			public void run() {
			selectJob(0);
			}
			
		});
		a.setItem(49,createItem("&cBack",Material.BARRIER, null),setting);
		setting.remove(Options.RUNNABLE);
		setting.put(Options.RUNNABLE_LEFT_CLICK, new Runnable() {

			@Override
			public void run() {
				
			}
			
		});
		setting.put(Options.RUNNABLE_RIGHT_CLICK, new Runnable() {

			@Override
			public void run() {
				
			}
			
		});
		a.setItem(31,createItem("&eDescription",Material.PAPER, Arrays.asList("&7- Left click to &aadd new message","&7- Right click to &cremove last message")),setting);
		setting.remove(Options.RUNNABLE_RIGHT_CLICK);
		setting.remove(Options.RUNNABLE_LEFT_CLICK);
		setting.put(Options.RUNNABLE_LEFT_CLICK, new Runnable() {

			@Override
			public void run() {
				Loader.c.getConfig().set("Jobs."+job+".MaxLevel",Loader.c.getConfig().getInt("Jobs."+job+".MaxLevel")+1);
				Loader.c.save();
				manage(job);
			}
			
		});
		setting.put(Options.RUNNABLE_RIGHT_CLICK, new Runnable() {

			@Override
			public void run() {
				int r= Loader.c.getConfig().getInt("Jobs."+job+".MaxLevel")-1;
				if(r>0) {
				Loader.c.getConfig().set("Jobs."+job+".MaxLevel",r);
				Loader.c.save();
				manage(job);
				}
			}
			
		});
		a.setItem(32,createItem("&2Max level",Material.WHEAT_SEEDS, Arrays.asList("&7- Left click to &aadd +1","&7- Right click to &cremove -1","&7- Max level of job: "+Loader.c.getConfig().getInt("Jobs."+job+".MaxLevel"))),setting);
		setting.remove(Options.RUNNABLE_RIGHT_CLICK);
		setting.remove(Options.RUNNABLE_LEFT_CLICK);
		setting.put(Options.RUNNABLE, new Runnable() {

			@Override
			public void run() {
				openEdit(job);
			}
			
		});
		a.setItem(30,createItem("&6Events",Material.CHEST, null),setting);
		setting.remove(Options.RUNNABLE);
		setting.put(Options.RUNNABLE, new Runnable() {

			@Override
			public void run() {
				//set config, clsoe
			}
			
		});
		a.setItem(22,createItem("&5Name",Material.NAME_TAG, null),setting);
		a.open();
	}
	public void createNewJob() {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(s);
		a.setSize(54);
		a.setTitle("&eJobs Creator");
		pre(a);
		HashMap<Options, Object> setting = new HashMap<Options, Object>();
		setting.put(Options.CANT_BE_TAKEN, true);
		setting.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
					s.getOpenInventory().close();
					Loader.data.getConfig().set("data."+s.getName()+".editor.name", "-");
					TheAPI.getPlayerAPI(s).sendTitle("&6Type to the chat", "&6Name of job");
			}});
	a.open();
	}
	public void selectJob(int e) {
		List<ItemStack> page = Loader.jobs.getPage(e);
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(s);
		a.setSize(54);
		a.setTitle("&eJobs Selector &c"+(e+1)+"/"+Loader.jobs.totalPages());
		pre(a);
		HashMap<Options, Object> setting = new HashMap<Options, Object>();
		setting.put(Options.CANT_BE_TAKEN, true);
		for(ItemStack i:page) {
				setting.remove(Options.RUNNABLE);
				if(new Jobs("").existJob(i.getItemMeta().getDisplayName())) {
				setting.put(Options.RUNNABLE, new Runnable() {
					@Override
					public void run() {

						manage(i.getItemMeta().getDisplayName());
					}});
				}
		a.addItem(i, setting);
		
		}
		if(Loader.jobs.totalPages()>0 && Loader.jobs.totalPages() > e+1) {

			setting.remove(Options.RUNNABLE);
			setting.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					selectJob(e+1);
				}});
			a.setItem(51, createItem("&6Next page", Material.ARROW, Arrays.asList("Next page "+(e+2)+"/"+Loader.jobs.totalPages())),setting);
		}
		if(Loader.jobs.totalPages()>0 && 0 <= e-1) {

			setting.remove(Options.RUNNABLE);
			setting.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					selectJob(e-1);
				}});
			a.setItem(47, createItem("&6Previous page", Material.ARROW, Arrays.asList("Previous page "+e+"/"+Loader.jobs.totalPages())),setting);
		}
			setting.remove(Options.RUNNABLE);
			setting.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					openEditor();
				}});
			a.setItem(49, createItem("&cBack", Material.BARRIER, null),setting);
		
		a.open();
	}
	public void deleteJob(int e) {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(s);
		a.setSize(54);
		a.setTitle("&eJobs Remover &c"+(e+1)+"/"+Loader.jobs.totalPages());
		pre(a);
		HashMap<Options, Object> setting = new HashMap<Options, Object>();
		setting.put(Options.CANT_BE_TAKEN, true);
		for(ItemStack i:Loader.jobs.getPage(e)) {
			setting.remove(Options.RUNNABLE);
			if(new Jobs("").existJob(i.getItemMeta().getDisplayName())) {
				setting.put(Options.RUNNABLE, new Runnable() {
					@Override
					public void run() {
						new Jobs(s.getName()).deleteJob(i.getItemMeta().getDisplayName());
						Loader.updateJobs();
						deleteJob(e);
						JobsCMD.msg("&6Job "+i.getItemMeta().getDisplayName()+" &cdeleted", s);
					}});
			
			}
		a.addItem(i, setting);
		}
		if(Loader.jobs.totalPages()>0 && Loader.jobs.totalPages() > e+1) {

			setting.remove(Options.RUNNABLE);
			setting.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					deleteJob(e+1);
				}});
			a.setItem(51, createItem("&6Next page", Material.ARROW, Arrays.asList("Next page "+(e+2)+"/"+Loader.jobs.totalPages())),setting);
		}
		if(Loader.jobs.totalPages()>0 && 0 <= e-1) {

			setting.remove(Options.RUNNABLE);
			setting.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					deleteJob(e-1);
				}});
			a.setItem(47, createItem("&6Previous page", Material.ARROW, Arrays.asList("Previous page "+e+"/"+Loader.jobs.totalPages())),setting);
		}
			setting.remove(Options.RUNNABLE);
			setting.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					openEditor();
				}});
			a.setItem(49, createItem("&cBack", Material.BARRIER, null),setting);
		
		a.open();
	}
	
	public void openEditor() {
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(s);
		a.setSize(54);
		a.setTitle("&eJobs Editor");
		pre(a);
		HashMap<Options, Object> setting = new HashMap<Options, Object>();
		setting.put(Options.CANT_BE_TAKEN, true);
		setting.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				createNewJob();
			}});
		Material m = Material.matchMaterial("LIME_WOOL");
		if(m==null)m=Material.matchMaterial("WOOL");
		a.setItem(20, createItem("&aCreate new job", m, 5, null),setting);
		setting.remove(Options.RUNNABLE);
		setting.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				deleteJob(0);
			}});
		Material md = Material.matchMaterial("RED_WOOL");
		if(md==null)md=Material.matchMaterial("WOOL");
		a.setItem(31, createItem("&cDelete job", md, 4, null),setting);
		setting.remove(Options.RUNNABLE);
		setting.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				selectJob(0);
			}});

		Material ms = Material.matchMaterial("ORANGE_WOOL");
		if(ms==null)ms=Material.matchMaterial("WOOL");
		a.setItem(24, createItem("&6Edit existing job", ms, null),setting);
		setting.remove(Options.RUNNABLE);
		setting.put(Options.RUNNABLE, new Runnable() {
			@Override
			public void run() {
				s.getOpenInventory().close();
			}});
		a.setItem(49, createItem("&cClose", Material.BARRIER, null),setting);
		a.open();
	}
	
	private void prer(GUICreatorAPI inv) { //45
		Material m = Material.matchMaterial("BLACK_STAINED_GLASS_PANE");
		if(m==null)m=Material.matchMaterial("STAINED_GLASS_PANE");
		ItemStack item = createItem(" ", m, null);
		HashMap<Options, Object> setting = new HashMap<Options, Object>();
		setting.put(Options.CANT_BE_TAKEN, true);
		setting.put(Options.CANT_PUT_ITEM, true);
		for(int i = 45; i<54; ++i)
		inv.setItem(i, item,setting);
	}
	
	private void pre(GUICreatorAPI inv) { //28
		Material m = Material.matchMaterial("BLACK_STAINED_GLASS_PANE");
		if(m==null)m=Material.matchMaterial("STAINED_GLASS_PANE");
		ItemStack item = createItem(" ", m, null);
		HashMap<Options, Object> setting = new HashMap<Options, Object>();
		setting.put(Options.CANT_BE_TAKEN, true);
		setting.put(Options.CANT_PUT_ITEM, true);
		for(int i = 0; i<10; ++i)
		inv.setItem(i, item,setting);
		inv.setItem(17, item,setting);
		inv.setItem(18, item,setting);
		inv.setItem(26, item,setting);
		inv.setItem(27, item,setting);
		inv.setItem(35, item,setting);
		inv.setItem(36, item,setting);
		for(int i = 44; i<54; ++i)
		inv.setItem(i, item,setting);
	}

	public void openBrowse(int e) {
		List<ItemStack> page = Loader.jobs.getPage(e);
		GUICreatorAPI a = TheAPI.getGUICreatorAPI(s);
		a.setSize(54);
		a.setTitle("&eJobs Browse &c"+(e+1)+"/"+Loader.jobs.totalPages());
		pre(a);
		HashMap<Options, Object> setting = new HashMap<Options, Object>();
		setting.put(Options.CANT_BE_TAKEN, true);
		for(ItemStack i:page) {
				setting.remove(Options.RUNNABLE);
				if(new Jobs("").existJob(i.getItemMeta().getDisplayName())) {
				setting.put(Options.RUNNABLE, new Runnable() {
					@Override
					public void run() {
					if(new Jobs(s.getName()).isInJob(i.getItemMeta().getDisplayName())) {
						new Job(i.getItemMeta().getDisplayName()).leave(s.getName());
						JobsCMD.msg("&6You left job "+i.getItemMeta().getDisplayName(),s);
						openBrowse(e);
					}else {
						new Job(i.getItemMeta().getDisplayName()).join(s.getName());
						JobsCMD.msg("&6You joined to the job "+i.getItemMeta().getDisplayName(),s);
						openBrowse(e);
					}
					}});
				}
			
		a.addItem(i, setting);
		}
		if(Loader.jobs.totalPages()>0 && Loader.jobs.totalPages() > e+1) {

			setting.remove(Options.RUNNABLE);
			setting.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					openBrowse(e+1);
				}});
			a.setItem(51, createItem("&6Next page", Material.ARROW, Arrays.asList("Next page "+(e+2)+"/"+Loader.jobs.totalPages())),setting);
		}
		if(Loader.jobs.totalPages()>0 && 0 <= e-1) {

			setting.remove(Options.RUNNABLE);
			setting.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					openBrowse(e-1);
				}});
			a.setItem(47, createItem("&6Previous page", Material.ARROW, Arrays.asList("Previous page "+e+"/"+Loader.jobs.totalPages())),setting);
		}
			setting.remove(Options.RUNNABLE);
			setting.put(Options.RUNNABLE, new Runnable() {
				@Override
				public void run() {
					s.getOpenInventory().close();
				}});
			a.setItem(49, createItem("&cClose", Material.BARRIER, null),setting);
		
		a.open();
	}
}
