package org.omar.monsterIndustries;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.omar.monsterIndustries.Listeners.PlayerStepOnPressurePlateListeners;

public final class MonsterIndustries extends JavaPlugin {

    private static MonsterIndustries plugin;

    public static MonsterIndustries getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        Bukkit.getPluginManager().registerEvents(new PlayerStepOnPressurePlateListeners(), this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}