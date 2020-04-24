package ru.soknight.writerspy.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import ru.soknight.lib.command.ExtendedCommandExecutor;
import ru.soknight.lib.validation.validator.PermissionValidator;
import ru.soknight.lib.validation.validator.Validator;
import ru.soknight.writerspy.WriterSpy;

public class CommandWriterspy extends ExtendedCommandExecutor {

	private final String pluginVersion;
	private final String libVersion;
	
	public CommandWriterspy(WriterSpy plugin) {
		super(null);
		
		this.pluginVersion = plugin.getDescription().getVersion();
		
		Plugin lib = Bukkit.getPluginManager().getPlugin("SKLibrary");
		this.libVersion = plugin != null ? lib.getDescription().getVersion() : "???";
		
		Validator permval = new PermissionValidator("writerspy.info", ChatColor.RED + "Недостаточно прав.");
		
		super.addValidators(permval);
	}

	@Override
	public void executeCommand(CommandSender sender, String[] args) {
		if(!validateExecution(sender, args)) return;
		
		sender.sendMessage(ChatColor.GRAY + "   WriterSpy");
		sender.sendMessage(ChatColor.YELLOW + " Версия плагина: " + ChatColor.GOLD + pluginVersion);
		sender.sendMessage(ChatColor.YELLOW + " Версия SKLibrary: " + ChatColor.GOLD + libVersion);
		sender.sendMessage(ChatColor.YELLOW + " Разработчик: " + ChatColor.GOLD + "SoKnight");
		sender.sendMessage(" ");
	}

}
