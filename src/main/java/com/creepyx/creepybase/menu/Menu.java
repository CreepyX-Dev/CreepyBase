package com.creepyx.creepybase.menu;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class Menu implements InventoryHolder {

    //Protected values that can be accessed in the menus
    protected Inventory inventory;
    protected Player player;

    /**
     * @param player The player who will be viewing the inventory
     */
    public Menu(Player player) {
        this.player = player;
    }

    /**
     * The ItemStack that will be used to fill the empty slots in the inventory
     */
    public abstract ItemStack fillerItem();

    /**
     * @return The name of the inventory menu
     */
    public abstract TextComponent menuName();


    /**
     * @return The number of slots in the inventory menu
     */
    public abstract int getSlots();

    /**
     * @param event The Event that is triggered when a player clicks in the inventory
     */
    public abstract void handleMenu(InventoryClickEvent event);

    /**
     * Sets the items in the inventory menu
     */
    public abstract void setMenuItems();

    /**
     * Opens the inventory menu
     */
    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), menuName());

        this.setMenuItems();
        player.openInventory(inventory);
    }

    /**
     * @return The inventory for the menu
     */
    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }


    /**
     * Sets the filler item to empty slots in the inventory
     */
    public void fill() {
        for (int i = 0; i < getSlots(); i++) {
            if (inventory.getItem(i) != null) continue;
            inventory.setItem(i, fillerItem());
        }
    }

    /**
     * Sets the filler item to the top and bottom slots
     */
    public void fillTopAndBottom() {
        for (int i = 0; i < getSlots(); i++) {
            if (i < 9 || i <= getSlots() - 10) {
                if (inventory.getItem(i) != null) continue;
                inventory.setItem(i, fillerItem());
            }
        }
    }
}
