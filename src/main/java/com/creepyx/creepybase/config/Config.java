package com.creepyx.creepybase.config;

import com.creepyx.creepybase.CreepyBase;
import com.creepyx.creepybase.builder.ItemBuilder;
import com.creepyx.creepybase.util.LogType;
import com.creepyx.creepybase.util.LogUtil;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import lombok.experimental.PackagePrivate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@PackagePrivate
public class Config extends CustomConfig {

	private final File file;


	public Config(File file) {
		this.file = file;

		try {
			if (!this.file.exists()) {
				this.file.getParentFile().mkdirs();

				if (CreepyBase.getInstance().getResource(file.getName()) != null) {
					CreepyBase.getInstance().saveResource(file.getName(), false);
				} else {
					file.createNewFile();
				}
			}

			load(this.file);
			save();
		} catch (IOException | InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public Config(String file) {
		this(new File(CreepyBase.getInstance().getDataFolder(), file));
	}

	public Config(File folder, File file) {
		if (!folder.isDirectory()) {
			throw new IllegalArgumentException("The folder must be a directory");
		}
		if (!folder.exists()) {
			folder.mkdirs();
		}

		this.file = new File(folder, file.getName());

		try {
			if (!this.file.exists()) {
				File parentFile = this.file.getParentFile();
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}

				String resourcePath = folder.toPath().resolve(file.getName()).toFile().getPath();

				if (CreepyBase.getInstance().getResource(resourcePath) != null) {
					CreepyBase.getInstance().saveResource(resourcePath, false);
				} else {
					this.file.createNewFile();
				}
			}

			load(this.file);
			save();
		} catch (IOException | InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public @Nullable ItemStack getItemStack(@NotNull String path) {

		Material material = Material.matchMaterial(path + ".material");

		if (material == null) {
			LogUtil.log(LogType.DEBUG, "Material " + path + ".material is not a valid material, using default getItemStack method");
			return super.getItemStack(path);
		}

		if (material == Material.PLAYER_HEAD) {
			String headOwner = this.getString(path + ".head_owner");
			if (!headOwner.isBlank()) {
				return new ItemBuilder(Material.PLAYER_HEAD).headOwnerName(headOwner).build();
			}
			String headUrl = this.getString(path + ".head_url");
			if (!headUrl.isBlank()) {
				return new ItemBuilder(Material.PLAYER_HEAD).headURL(headUrl).build();
			}
			String headOwnerUUID = this.getString(path + ".head_owner_uuid");
			if (!headOwnerUUID.isBlank()) {
				return new ItemBuilder(Material.PLAYER_HEAD).headOwner(headOwnerUUID).build();
			}
		}

		String name = this.getString(path + ".name", ItemStack.of(material).translationKey());
		List<String> lore = this.getStringList(path + ".lore");
		Set<String> enchants = this.getConfigurationSection(path + ".enchants").getKeys(false);
		List<String> flags = this.getStringList(path + ".flags");
		int modelData = this.getInt(path + ".custom_model_data", 0);
//            List<Map<?, ?>> attributes = this.getMapList(path + ".attributes");
		int amount = this.getInt(path + ".amount", 1);
		boolean unbreakable = this.getBoolean(path + ".unbreakable", false);
		boolean glow = this.getBoolean(path + ".glow", false);
		boolean hideAll = this.getBoolean(path + ".hide_all", false);

		if (isConfigurationSection(path + ".persistent_data")) {
			ConfigurationSection persistentData = getConfigurationSection(path + ".persistent_data");
			for (String key : persistentData.getKeys(false)) {
				String value = persistentData.getString(key);
				if (value != null) {
					set(path + ".persistent_data." + key, value);
				}
			}
		}

		Map<Enchantment, Integer> enchantments = new HashMap<>();

		if (!enchants.isEmpty()) {
			for (String enchant : enchants) {
				Enchantment enchantment = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).get(new NamespacedKey("minecraft", enchant));
				if (enchantment != null) {
					enchantments.put(enchantment, this.getInt(path + ".enchants." + enchant));
				}
			}
		}

		ItemFlag[] itemFlags = new ItemFlag[flags.size()];
		for (int i = 0; i < flags.size(); i++) {
			itemFlags[i] = ItemFlag.valueOf(flags.get(i));
		}
		ItemStack itemStack = new ItemBuilder(material).name(name).lore(lore).enchants(enchantments).addFlag(itemFlags).modelData(modelData).glow(glow).unbreakable(unbreakable).build();
		if (hideAll) {
			itemStack = new ItemBuilder(material).name(name).lore(lore).enchants(enchantments).hideAll().modelData(modelData).glow(glow).unbreakable(unbreakable).build();
		}


		itemStack.setAmount(amount);

		return itemStack;
	}

	public Config(String folder, String file) {
		File directory = new File(CreepyBase.getInstance().getDataFolder(), folder);

		if (!directory.exists()) {
			directory.mkdirs();
		}

		this.file = new File(directory, file);

		try {
			if (!this.file.exists()) {
				if (!this.file.getParentFile().exists()) {
					this.file.getParentFile().mkdirs();
				}

				if (CreepyBase.getInstance().getResource(file) != null) {
					CreepyBase.getInstance().saveResource(file, false);
				} else {
					this.file.createNewFile();
				}
			}
			load(this.file);
			save();
		} catch (IOException | InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public @NotNull String getString(@NotNull String path) {
		if (!super.isSet(path)) {
			return "";
		}

		return Objects.requireNonNull(super.getString(path));
	}

	@Override
	public void set(@NotNull String path, @Nullable Object value) {
		if (value instanceof Location location) {
			this.set(path + ".World", location.getWorld().getName());
			this.set(path + ".X", location.getX());
			this.set(path + ".Y", location.getY());
			this.set(path + ".Z", location.getZ());
			this.set(path + ".Yaw", location.getYaw());
			this.set(path + ".Pitch", location.getPitch());
			return;
		}

		super.set(path, value);
	}

	@Override
	public @Nullable Location getLocation(@NotNull String path) {
		String world = this.getString(path + ".World");
		int x = this.getInt(path + ".X");
		int y = this.getInt(path + ".Y");
		int z = this.getInt(path + ".Z");
		float yaw = (float) this.getDouble(path + ".Yaw");
		float pitch = (float) this.getDouble(path + ".Pitch");

		return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
	}

	@Override
	public Config createOrLoadDefault(File file, InputStream resource) {
		return null;
	}

	@Override
	public CustomConfig createOrLoadDefault(String file, InputStream resource) {
		return null;
	}

	@Override
	public CustomConfig getOrCreate(File file) {
		return new Config(file);
	}

	@Override
	public CustomConfig getOrCreate(File directory, File file) {
		return new Config(directory, file);
	}

	@Override
	public CustomConfig getOrCreate(String file) {
		return new Config(file);
	}

	@Override
	public CustomConfig getOrCreate(String folder, String file) {
		return new Config(folder, file);
	}

	@Override
	public void save() {
		try {
			super.save(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
