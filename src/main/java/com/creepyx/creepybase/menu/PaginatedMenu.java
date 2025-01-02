package com.creepyx.creepybase.menu;

import lombok.Getter;
import org.bukkit.entity.Player;

public abstract class PaginatedMenu extends Menu {

	protected int page = 0;

	@Getter
	protected int maxItemsPerPage = 27;

	protected int index = 10;

	public PaginatedMenu(Player player) {
		super(player);
	}
}
