package me.Straiker123.Jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import me.Straiker123.TheAPI;

public class JobsCMD implements CommandExecutor, TabCompleter {
	Loader plugin;
	public JobsCMD(Loader loader) {
		plugin=loader;
	}
	public static void msg(String s, CommandSender d) {
		d.sendMessage(TheAPI.colorize("&eJobs &4&l► "+s));
	}//sender send target stats
	private void sendStats(CommandSender d, String s) {
		if(new Jobs(s).getJobs().isEmpty()) {
			if(!s.equals(d.getName()))
				msg("&6Player "+s+" aren't in any job",d);
			else
			msg("&6You aren't in any job",d);
			return;
		}
		String who = "Your";
		if(!s.equals(d.getName()))who=s+"'s";
		msg("&8----- &e"+who+" stats &8-----",d);
		for(String a : new Jobs(s).getJobs()) {
			Job ww = new Job(a);
			ww.updateLevel(s);
			double g = ww.getMultiplier(s)/100;
			double b = ww.getPoints(s)/g;
			double i = b;
		String f = "";
		for(int w = 0; w < i; ++w)f=f+"&a|";
		for(int w = 0; w < 100-i; ++w)f=f+"&8|";
		msg("&5"+ww.getName()+"&8: "+f+" &8: &eLevel: "+ww.getLevel(s),d);
		}
		return;
	}
	@Override
	public boolean onCommand(CommandSender s, Command arg1, String arg2, String[] args) {
		if(args.length==0 || args.length > 0 && args[0].equalsIgnoreCase("help")) {
			if(has("join",s))
			msg("&6/Jobs Join <job>",s);
			if(has("join",s))
			msg("&6/Jobs Leave <job>",s);
			if(has("browse",s))
			msg("&6/Jobs Browse",s);
			if(has("stats",s))
			msg("&6/Jobs Stats",s);
			if(has("admin",s))
			msg("&6/Jobs Admin",s);
			return true;
		}
		if(args[0].equalsIgnoreCase("admin")) {
			if(!has("admin",s)) {
				msg("&cYou do not have permissions to do that!",s);
				return true;
			}
			AdminCmd(s,args);
			return true;
		}

		if(args[0].equalsIgnoreCase("browse")) {
			new sar((Player) s).openBrowse(0);
			return true;
		}
		if(args[0].equalsIgnoreCase("leave")) {
			if(!s.hasPermission("jobs.join")) {
				msg("&cYou do not have permissions to do that!",s);
				return true;
			}
			if(args.length==1) {
				msg("&6/Jobs Leave <job>",s);
				return true;
			}
			Jobs j = new Jobs(s.getName());
			if(j.existJob(args[1])) {
				if(j.isInJob(args[1])) {
				new Job(args[1]).leave(s.getName());
				msg("&6You left job "+args[1],s);
				return true;
				}
				msg("&6You aren't in job "+args[1],s);
				return true;
			}
			msg("&6Job named "+args[1]+" doesn't exist",s);
			return true;
		}
		if(args[0].equalsIgnoreCase("leaveall")) {
			if(!s.hasPermission("jobs.leaveall")) {
				msg("&cYou do not have permissions to do that!",s);
				return true;
			}
			List<String> jobs = new Jobs(s.getName()).getJobs(); 
			if(jobs.isEmpty()) {
				msg("&6You aren't in any job",s);
				return true;
			}
			for(String d : jobs)
				new Job(d).leave(s.getName());
				msg("&6You left these jobs: "+StringUtils.join(jobs,", "),s);
				return true;
		}
		if(args[0].equalsIgnoreCase("stats")) {
			if(!s.hasPermission("jobs.stats")) {
				msg("&cYou do not have permissions to do that!",s);
				return true;
			}
			sendStats(s,s.getName());
			return true;
		}
		if(args[0].equalsIgnoreCase("join")) {
			if(!s.hasPermission("jobs.join")) {
				msg("&cYou do not have permissions to do that!",s);
				return true;
			}
			if(args.length==1) {
				new sar((Player) s).openBrowse(0);
				return true;
			}
			Jobs j = new Jobs(s.getName());
			if(j.existJob(args[1])) {
				if(!j.isInJob(args[1])) {
				new Job(args[1]).join(s.getName());
				msg("&6You joined to the job "+args[1],s);
				return true;
				}
				msg("&6You are already in job "+args[1],s);
				return true;
			}
			msg("&6Job named "+args[1]+" doesn't exist",s);
			return true;
		}
		return false;
	}
	
	private void AdminCmd(CommandSender s, String[] args) {
		if(args.length==1) {
			msg("&6/Jobs Help",s); //
			msg("&6/Jobs &3Admin &6Join <player> <job>",s); //
			msg("&6/Jobs &3Admin &6Leave <player> <job>",s); //
			msg("&6/Jobs &3Admin &6List <player>",s); //
			msg("&6/Jobs &3Admin &6Stats <player>",s);//
			msg("&6/Jobs &3Admin &6Editor",s); //
			msg("&6/Jobs &3Admin &6Create <job>",s); //
			msg("&6/Jobs &3Admin &6Delete <job>",s); //
			msg("&6/Jobs &3Admin &6Points Give <player> <job> <points>",s);
			msg("&6/Jobs &3Admin &6Points Take <player> <job> <points>",s);
			msg("&6/Jobs &3Admin &6Points Set <player> <job> <points>",s);
			msg("&6/Jobs &3Admin &6Points Balance <player> <job>",s);
			msg("&6/Jobs &3Admin &6Reload",s);//
			return;
		}
		if(args[1].equalsIgnoreCase("points")) {
			Points(s, args);
			return;
		}
		if(args[1].equalsIgnoreCase("stats")) {
			if(args.length==2) {
				msg("&6/Jobs &3Admin &6Stats <player>",s);
				return;
			}
			sendStats(s, args[2]);
			return;
		}

		if(args[1].equalsIgnoreCase("reload")) {
			Loader.a.reload();
			Loader.c.reload();
			Loader.data.save();
			msg("&aConfig reloaded!",s);
			return;
		}
		if(args[1].equalsIgnoreCase("editor")) {
			new sar((Player) s).openEditor();
			return;
		}
		if(args[1].equalsIgnoreCase("list")) {
			if(args.length==2) {
				msg("&6/Jobs &3Admin &6List <player>",s);
				return;
			}
			if(new Jobs(args[2]).isWorking()) {
				msg("&8------- &eJobs of player "+args[2]+" &8-------",s);
				for(String d : new Jobs(args[2]).getJobs())msg("&7- &e"+new Job(d).getName(),s);
				return;
			}
			msg("&6Player "+args[2]+" doens't exist or isn't in any job",s);
			return;
		}
		if(args[1].equalsIgnoreCase("join")) {
			if(args.length==2) {
				msg("&6/Jobs &3Admin &6Join <player> <job>",s);
				return;
			}
			if(args.length==3) {
				msg("&6/Jobs &3Admin &6Join <player> <job>",s);
				return;
			}
			if(!new Jobs(s.getName()).existJob(args[3])) {
				msg("&6Job named "+args[3]+" doesn't exist",s);
				return;
			}
			if(new Jobs(args[2]).isInJob(args[3])) {
				msg("&6Player "+args[2]+" is already in job "+args[3],s);
				return;
			}
			new Job(args[3]).join(args[2]);
			if(Bukkit.getPlayer(args[2])!=null)
			msg("&6You joined to the job "+args[3],Bukkit.getPlayer(args[2]));
			msg("&6Player "+args[2]+" joined to the job "+args[3],s);
			return;
		}		if(args[1].equalsIgnoreCase("leave")) {
			if(args.length==2) {
				msg("&6/Jobs &3Admin &6Leave <player> <job>",s);
				return;
			}
			if(args.length==3) {
				msg("&6/Jobs &3Admin &6Leave <player> <job>",s);
				return;
			}
			if(!new Jobs(s.getName()).existJob(args[3])) {
				msg("&6Job named "+args[3]+" doesn't exist",s);
				return;
			}
			if(!new Jobs(args[2]).isInJob(args[3])) {
				msg("&6Player "+args[2]+" isn't in job "+args[3],s);
				return;
			}
			new Job(args[3]).join(args[2]);
			if(Bukkit.getPlayer(args[2])!=null)
			msg("&6You left job "+args[3],Bukkit.getPlayer(args[2]));
			msg("&6Player "+args[2]+" left job "+args[3],s);
			return;
		}
		if(args[1].equalsIgnoreCase("create")) {
			if(args.length==2) {
				msg("&6/Jobs &3Admin &6Create <job>",s);
				return;
			}
			if(new Jobs(s.getName()).existJob(args[2])) {
				msg("&6Job named "+args[2]+" already exist",s);
				return;
			}
			new Jobs(s.getName()).createJob(args[2]);
			msg("&6Job named "+args[2]+" created",s);
			return;
		}
		if(args[1].equalsIgnoreCase("delete")) {
			if(args.length==2) {
				msg("&6/Jobs &3Admin &6Delete <job>",s); //
				return;
			}
			if(new Jobs(s.getName()).existJob(args[2])) {
			new Jobs(s.getName()).deleteJob(args[2]);
			msg("&6Job named "+args[2]+" deleted",s);
			return;
			}
			msg("&6Job named "+args[2]+" doesn't exist",s);
			return;
		}
	}
	private void Points(CommandSender s, String[] args) {
		if(args.length==2) {
			msg("&6/Jobs &3Admin &6Points Give <player> <job> <points>",s);
			msg("&6/Jobs &3Admin &6Points Take <player> <job> <points>",s);
			msg("&6/Jobs &3Admin &6Points Set <player> <job> <points>",s);
			msg("&6/Jobs &3Admin &6Points Balance <player> <job>",s);
			return;
		}
		if(args[2].equalsIgnoreCase("give")) {
			if(args.length==3 || args.length==4) {
				msg("&6/Jobs &3Admin &6Points Give <player> <job> <points>",s);
				return;
			}
			if(!new Jobs(s.getName()).existJob(args[4])) {
				msg("&6Job named "+args[4]+" doesn't exist",s);
				return;
			}
			if(!new Jobs(args[3]).isInJob(args[4])) {
				msg("&6Player "+args[3]+" isn't in job "+args[4],s);
				return;
			}
			if(args.length==5) {
				msg("&6/Jobs &3Admin &6Points Give <player> <job> <points>",s);
				return;
			}
			new Job(args[4]).setPoints(args[3], new Job(args[4]).getPoints(args[3])+TheAPI.getNumbersAPI(args[5]).getDouble());
			msg("&6Given &c"+TheAPI.getNumbersAPI(args[5]).getDouble()+" points &6to player &c"+args[3]+" &6to the job &c"+args[4],s);
			return;
		}
		if(args[2].equalsIgnoreCase("take")) {
			if(args.length==3 || args.length==4) {
				msg("&6/Jobs &3Admin &6Points Take <player> <job> <points>",s);
				return;
			}
			if(!new Jobs(s.getName()).existJob(args[4])) {
				msg("&6Job named "+args[4]+" doesn't exist",s);
				return;
			}
			if(!new Jobs(args[3]).isInJob(args[4])) {
				msg("&6Player "+args[3]+" isn't in job "+args[4],s);
				return;
			}
			if(args.length==5) {
				msg("&6/Jobs &3Admin &6Points Take <player> <job> <points>",s);
				return;
			}
			new Job(args[4]).setPoints(args[3], new Job(args[4]).getPoints(args[3])-TheAPI.getNumbersAPI(args[5]).getDouble());
			msg("&6Taken &c"+TheAPI.getNumbersAPI(args[5]).getDouble()+" points &6to player &c"+args[3]+" &6to the job &c"+args[4],s);
			return;
		}
		if(args[2].equalsIgnoreCase("set")) {
			if(args.length==3 || args.length==4) {
				msg("&6/Jobs &3Admin &6Points Set <player> <job> <points>",s);
				return;
			}
			if(!new Jobs(s.getName()).existJob(args[4])) {
				msg("&6Job named "+args[4]+" doesn't exist",s);
				return;
			}
			if(!new Jobs(args[3]).isInJob(args[4])) {
				msg("&6Player "+args[3]+" isn't in job "+args[4],s);
				return;
			}
			if(args.length==5) {
				msg("&6/Jobs &3Admin &6Points Set <player> <job> <points>",s);
				return;
			}
			new Job(args[4]).setPoints(args[3], TheAPI.getNumbersAPI(args[5]).getDouble());
			msg("&6Set &c"+TheAPI.getNumbersAPI(args[5]).getDouble()+" points &6to player &c"+args[3]+" &6to the job &c"+args[4],s);
			return;
		}
		if(args[2].equalsIgnoreCase("balance")) {
			if(args.length==3 || args.length==4) {
				msg("&6/Jobs &3Admin &6Points Balance <player> <job>",s);
				return;
			}
			if(!new Jobs(s.getName()).existJob(args[4])) {
				msg("&6Job named "+args[4]+" doesn't exist",s);
				return;
			}
			if(!new Jobs(args[3]).isInJob(args[4])) {
				msg("&6Player "+args[3]+" isn't in job "+args[4],s);
				return;
			}
			msg("&6Player "+args[3]+" have &c"+new Job(args[4]).getPoints(args[3])+" &6points in job &c"+args[4],s);
			return;
		}
		
	}
	public static boolean has(String ss, CommandSender s) {
		return s.hasPermission("jobs."+ss);
	}
	
	@Override
	public List<String> onTabComplete(CommandSender s, Command arg1, String arg2, String[] args) {
		List<String> c = new ArrayList<>();
		if(args.length==1) {
			if(has("join",s))
		    	c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Join","Leave","Browse"), new ArrayList<>()));
			if(has("stats",s))
		    	c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Stats"), new ArrayList<>()));
			if(has("admin",s))
		    	c.addAll(StringUtil.copyPartialMatches(args[0], Arrays.asList("Admin"), new ArrayList<>()));
		}
		if(args[0].equalsIgnoreCase("join")) {
			List<String> l= new Jobs(s.getName()).getAllJobs();
			l.removeAll(new Jobs(s.getName()).getJobs());
			if(args.length==2)
	    	c.addAll(StringUtil.copyPartialMatches(args[1], l, new ArrayList<>()));
		}
		if(args[0].equalsIgnoreCase("leave")) {
			if(args.length==2)
	    	c.addAll(StringUtil.copyPartialMatches(args[1], new Jobs(s.getName()).getJobs(), new ArrayList<>()));
		}
		if(args[0].equalsIgnoreCase("admin")) {
			if(args.length==2)
	    	c.addAll(StringUtil.copyPartialMatches(args[1], Arrays.asList("Join","Leave","List","Stats","Editor","Create","Delete","Points","Reload"), new ArrayList<>()));
			if(args.length==3) {
			if(args[1].equalsIgnoreCase("join")||args[1].equalsIgnoreCase("leave")||
					args[1].equalsIgnoreCase("list")||args[1].equalsIgnoreCase("stats"))return null;
			if(args[1].equalsIgnoreCase("editor")||args[1].equalsIgnoreCase("delete"))
		    	c.addAll(StringUtil.copyPartialMatches(args[2], new Jobs(s.getName()).getAllJobs(), new ArrayList<>()));
			if(args[1].equalsIgnoreCase("create"))
		    	return Arrays.asList("?");
			if(args[1].equalsIgnoreCase("points"))
		    	c.addAll(StringUtil.copyPartialMatches(args[2], Arrays.asList("Give","Take","Set","Balance"), new ArrayList<>()));
			}
			if(args.length==4) {
				if(args[1].equalsIgnoreCase("points"))
					if(args[2].equalsIgnoreCase("give")||args[2].equalsIgnoreCase("take")
							||args[2].equalsIgnoreCase("set")||args[2].equalsIgnoreCase("Balance"))
			    	return null;
				if(args[1].equalsIgnoreCase("join"))
					if(new Jobs(args[2]).isWorking()) {
						List<String> l = new Jobs(args[2]).getAllJobs();
						l.removeAll(new Jobs(args[2]).getJobs());
				    	c.addAll(StringUtil.copyPartialMatches(args[3], l, new ArrayList<>()));
					}
				if(args[1].equalsIgnoreCase("leave"))
					if(new Jobs(args[2]).getJobs().isEmpty()==false) {
				    	c.addAll(StringUtil.copyPartialMatches(args[3], new Jobs(args[2]).getJobs(), new ArrayList<>()));
					}
			}
			if(args.length==5) {
				if(args[1].equalsIgnoreCase("points"))
					if(args[2].equalsIgnoreCase("give")||args[2].equalsIgnoreCase("take")
							||args[2].equalsIgnoreCase("set")||args[2].equalsIgnoreCase("Balance"))
						if(new Jobs(args[3]).getJobs().isEmpty()==false) 
					    	c.addAll(StringUtil.copyPartialMatches(args[4], new Jobs(args[3]).getJobs(), new ArrayList<>()));
			}
			if(args.length==6) {
				if(args[1].equalsIgnoreCase("points"))
					if(args[2].equalsIgnoreCase("give")||args[2].equalsIgnoreCase("take")
							||args[2].equalsIgnoreCase("set")||args[2].equalsIgnoreCase("Balance"))
						if(new Jobs(args[3]).getJobs().isEmpty()==false && new Jobs(args[3]).getJobs().contains(args[4])) {
					    	c.addAll(StringUtil.copyPartialMatches(args[5], Arrays.asList("?"), new ArrayList<>()));
						}
			}
		}
		return c;
	}


}
