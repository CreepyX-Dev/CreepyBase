package com.creepyx.creepybase.builder;

import com.creepyx.creepybase.CreepyBase;
import com.creepyx.creepybase.config.Config;
import com.creepyx.creepybase.config.CustomConfig;
import com.creepyx.creepybase.util.StringUtil;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ItemBuilder {

    @Setter
    private static CustomConfig config;
    private final ItemStack item;
    @Getter
	private final ItemMeta meta;

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(String itemKey, String configName) {
        config = new Config(configName.endsWith(".yml") ? configName : configName + ".yml" );
        String name = config.getString(itemKey + ".name", itemKey);
        List<String> lore = config.getStringList(itemKey + ".lore");
        String materialString = config.getString(itemKey + ".material", "DIRT");
        Material material = Material.matchMaterial(materialString);

        Preconditions.checkNotNull(material, "Invalid material " + materialString);

        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();

        if (config.isConfigurationSection(itemKey + ".persistent_data")) {
            ConfigurationSection persistentData = config.getConfigurationSection(itemKey + ".persistent_data");
            for (String key : persistentData.getKeys(false)) {
                String value = persistentData.getString(key, "null");
                this.addPersistentString(new NamespacedKey(CreepyBase.getInstance(), key), value);
            }
        }
        this.name(name);
        this.lore(lore);
    }

    public ItemBuilder(String itemKey, Map<String, String> placeholders) {
        String name = config.getString(itemKey + ".name", itemKey);
        List<String> lore = config.getStringList(itemKey + ".lore");
        String materialString = config.getString(itemKey + ".material", "DIRT");
        Material material = Material.matchMaterial(materialString);

        if (material == null) {
            throw new IllegalArgumentException("Invalid material " + materialString);
        }

        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
        this.name(StringUtil.asComponent(name, placeholders));
        this.compLore(StringUtil.asFormattedList(lore, placeholders));
    }

    public ItemBuilder(Material material, int amount) {
        this.item = new ItemStack(material, amount);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public ItemBuilder name(String name) {
        this.meta.displayName(StringUtil.asComponent(name));
        return this;
    }

    public ItemBuilder name(Component name) {
        this.meta.displayName(name);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        return getItemLore(lore);
    }

    @NotNull
    private ItemBuilder getItemLore(String[] lore) {
        List<Component> loreList = new ArrayList<>();
        for (String string : lore) {
            StringUtil.asComponent(string);
            loreList.add(StringUtil.asComponent(string));
        }
        this.meta.lore(loreList);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        return getItemLore(lore.toArray(new String[0]));
    }

    public ItemBuilder lore(Component... lore) {
        this.meta.lore(Arrays.stream(lore).toList());
        return this;
    }

    public ItemBuilder compLore(List<Component> loreComponents) {
        this.meta.lore(loreComponents);
        return this;
    }

    public ItemBuilder modelData(int number) {
        this.meta.setCustomModelData(number);
        return this;
    }

    public ItemBuilder addPersistent(NamespacedKey key, PersistentDataType<Object, Object> type, Object value) {
        this.meta.getPersistentDataContainer().set(key, type, value);
        return this;
    }

    public ItemBuilder addPersistentString(NamespacedKey key, String value) {
        this.meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, value);
        return this;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        this.meta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder addFlag(ItemFlag... flags) {
        this.meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder removeFlag(ItemFlag... flags) {
        this.meta.removeItemFlags(flags);
        return this;
    }

    public ItemBuilder clearLore() {
        this.meta.lore(new ArrayList<>());
        return this;
    }

    public ItemBuilder hideTags() {
        for (ItemFlag value : ItemFlag.values()) {
            this.meta.addItemFlags(value);
        }
        this.meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        return this;
    }

    public ItemBuilder hideAll() {
        for (ItemFlag value : ItemFlag.values()) {
            this.meta.addItemFlags(value);
        }
        return this;
    }

    public ItemBuilder showTags() {
        for (ItemFlag value : ItemFlag.values()) {
            this.meta.removeItemFlags(value);
        }
        return this;
    }

    public ItemBuilder glow(boolean glow) {
        this.meta.setEnchantmentGlintOverride(glow);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder enchants(Map<Enchantment, Integer> enchantments) {
        enchantments.forEach(this::enchant);
        return this;
    }

    public ItemBuilder removeEnchant(Enchantment enchantment, int level) {
        this.meta.removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder removeEnchants() {
        for (Enchantment enchantment : this.meta.getEnchants().keySet()) {
            this.meta.removeEnchant(enchantment);
        }
        return this;
    }

    public ItemBuilder attribute(Attribute attribute, AttributeModifier modifier) {
        this.meta.addAttributeModifier(attribute, modifier);
        return this;
    }

    public ItemBuilder removeAttribute(Attribute attribute, AttributeModifier modifier) {
        this.meta.removeAttributeModifier(attribute, modifier);
        return this;
    }

    public ItemBuilder removeAttribute(Attribute attribute) {
        this.meta.removeAttributeModifier(attribute);
        return this;
    }

    public ItemBuilder headOwner(String uuid) {
        if (this.meta instanceof SkullMeta skullMeta)
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(uuid)));
        return this;
    }

    public ItemBuilder headOwnerName(String name) {
        if (this.meta instanceof SkullMeta skullMeta)
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(name));
        return this;
    }

    public ItemBuilder headValue(String base64) {
        if (this.meta instanceof SkullMeta skullMeta) {
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
            profile.setProperty(new ProfileProperty("textures", base64));
            skullMeta.setPlayerProfile(profile);
        }
        return this;
    }

    public ItemBuilder headURL(String url) {
        String base64 = convertURLToBase64(url);
        return headValue(base64);
    }

	private String convertURLToBase64(String url) {
        String json = "{\"textures\":{\"SKIN\":{\"url\":\"" + url + "\"}}}";
        return java.util.Base64.getEncoder().encodeToString(json.getBytes());
    }

    public ItemStack build() {
        this.item.setItemMeta(this.meta);
        return this.item;
    }
}
