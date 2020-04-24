package ru.soknight.writerspy.listener;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookListener implements Listener {
	
	private final String online;
	private final String offline;
	
	public BookListener() {
		this.online = ChatColor.RESET + " " + ChatColor.GREEN + "(Онлайн)";
		this.offline = ChatColor.RESET + " " + ChatColor.RED + "(Оффлайн)";
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		ItemStack item = event.getCursor();
		if(item == null) return;
		
		if(!item.getType().equals(Material.WRITTEN_BOOK)) return;
		
		updateBookAuthorState(item);
	}
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		final Inventory inventory = event.getInventory();
		if(inventory == null) return;
		
		ItemStack[] items = inventory.getContents();
		Arrays.stream(items).parallel()
				.filter(i -> i != null && i.getType().equals(Material.WRITTEN_BOOK))
				.forEach(i -> updateBookAuthorState(i));
	}
	
	private void updateBookAuthorState(ItemStack book) {
		BookMeta meta = (BookMeta) book.getItemMeta();
		
		String author = meta.getAuthor();
		author = ChatColor.stripColor(author);
		
		if(author.endsWith(" (Оффлайн)")) {
			author = author.substring(0, author.length() - 10);
			OfflinePlayer offline = Bukkit.getOfflinePlayer(author);
			if(offline == null || offline != null && !offline.isOnline()) return;
			
			author += this.online;
		} else if(author.endsWith(" (Онлайн)")) {
			author = author.substring(0, author.length() - 9);
			OfflinePlayer offline = Bukkit.getOfflinePlayer(author);
			if(offline != null && offline.isOnline()) return;
			
			author += this.offline;
		} else {
			OfflinePlayer offline = Bukkit.getOfflinePlayer(author);
			if(offline == null) return;
			
			author += offline.isOnline() ? this.online : this.offline;
		}
		
		meta.setAuthor(author);
		book.setItemMeta(meta);
	}
	
}
