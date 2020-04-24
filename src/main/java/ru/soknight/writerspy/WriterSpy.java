package ru.soknight.writerspy;

import org.bukkit.plugin.java.JavaPlugin;

import ru.soknight.lib.configuration.Configuration;
import ru.soknight.lib.configuration.Messages;
import ru.soknight.writerspy.command.CommandWriterspy;
import ru.soknight.writerspy.listener.BookListener;

public class WriterSpy extends JavaPlugin {

	protected Configuration pluginConfig;
	protected Messages messages;
	
	@Override
	public void onEnable() {
		long start = System.currentTimeMillis();
    	
		// Commands executors initialization
		registerCommand();
    	
    	// Kit open listener initialization
		registerListener();
    			
    	long time = System.currentTimeMillis() - start;
    	getLogger().info("Bootstrapped in " + time + " ms.");
	}
	
	public void registerCommand() {
		CommandWriterspy commandWriterspy = new CommandWriterspy(this);
		
		getCommand("writerspy").setExecutor(commandWriterspy);
	}
	
	public void registerListener() {
		BookListener bookListener = new BookListener();
    	getServer().getPluginManager().registerEvents(bookListener, this);
	}
	
}
