package com.creepyx.creepybase.kotlin

import org.bukkit.inventory.Inventory

fun Inventory.closeForViewers() = HashSet(viewers).forEach { it.closeInventory() }