package com.creepyx.creepybase.util;

import com.creepyx.creepybase.CreepyBase;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class RegionUtil {

	public static ProtectedRegion getRegion(String world, String regionName) {
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

	/**
	 * @param world the world of the requested region
	 * @param regionName the region name
	 * @return true, if a region with the given world and name exists
	 */
	public static boolean regionExists(String world, String regionName) {
		if (!worldGuardEnabled()) {
			return false;
		}
		return getRegion(world, regionName) != null;
	}

	/**
	 * @param world the world of the Region
	 * @param regionName the name of the Region
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 * @return true, if the region contains the x, y and z coordinate
	 */
	public static boolean insideRegion(String world, String regionName, double x, double y, double z) {
		if (worldGuardEnabled() && regionExists(world, regionName)) {
			ProtectedRegion region = getRegion(world, regionName);
			return region.contains(BlockVector3.at(x, y, z));
		}
		return false;
	}

	/**
	 * @param world the world of the Region
	 * @param regionName the name of the Region
	 * @param x x coordinate
	 * @param z z coordinate
	 * @return true, if the region contains the x and z coordinate
	 */
	public static boolean insideRegion(String world, String regionName, double x, double z) {
		if (!worldGuardEnabled()) {
			return false;
		}
		ProtectedRegion region = getRegion(world, regionName);
		if (!regionExists(world, regionName)) {
			return false;
		}

		return region.contains(BlockVector2.at(x, z));
	}

	/**
	 * @return true, if worldgaurd is enabled
	 */
	private static boolean worldGuardEnabled() {
		return CreepyBase.getInstance().isPluginEnabled("WorldGuard");
	}
}
