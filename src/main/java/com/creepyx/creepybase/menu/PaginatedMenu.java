package com.creepyx.creepybase.menu;

import org.bukkit.entity.Player;

public abstract class PaginatedMenu extends Menu {

	private int page = 0;


	private int maxItemsPerPage = 27;

	private int index = 10;

	public PaginatedMenu(Player player) {
		super(player);
	}

	public int getPage() {
		return page;
	}

	public int getMaxItemsPerPage() {
		return maxItemsPerPage;
	}

	public int getIndex() {
		return index;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setMaxItemsPerPage(int maxItemsPerPage) {
		this.maxItemsPerPage = maxItemsPerPage;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
