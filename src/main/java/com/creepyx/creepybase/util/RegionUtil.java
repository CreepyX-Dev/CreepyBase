package com.creepyx.creepybase.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.World;

@UtilityClass
public class RegionUtil {

	public ProtectedRegion getRegion(String world, String regionName) {
		if (!worldGuardEnabled()) {
			return null;
		}
		World bukkitWorld = Bukkit.getWorld(world);
		if (bukkitWorld == null) {
			return null;
		}
		RegionContainer container =  WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionManager manager = container.get(BukkitAdapter.adapt(bukkitWorld));
		if (manager == null) {
			return null;
		}
		return manager.getRegion(regionName);
	}

	public boolean regionExists(String world, String regionName) {
		if (!worldGuardEnabled()) {
			return false;
		}
		return getRegion(world, regionName) != null;
	}

	public boolean insideRegion(String world, String regionName, double x, double y, double z) {
		if (!worldGuardEnabled()) {
			return false;
		}
		ProtectedRegion region = getRegion(world, regionName);
		if (region == null) {
			return false;
		}

		return region.contains(BlockVector3.at(x, y, z));
	}

	public boolean insideRegion(String world, String regionName, double x, double z) {
		if (!worldGuardEnabled()) {
			return false;
		}
		ProtectedRegion region = getRegion(world, regionName);
		if (region == null) {
			return false;
		}

		return region.contains(BlockVector2.at(x, z));
	}

	private boolean worldGuardEnabled() {
		return Bukkit.getPluginManager().isPluginEnabled("WorldGuard");
	}
}
