package org.omar.monsterIndustries.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class PlayerStepOnPressurePlateListener implements Listener {

    // Cooldown map to store the last time a block (pressure plate) was triggered
    private final HashMap<Block, Long> cooldowns = new HashMap<>();
    private final long COOLDOWN_TIME = 1000;

    @EventHandler
    public void onPlayerStepOnPressurePlate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        Material type = clickedBlock == null ? null : clickedBlock.getType();

        if (event.getAction() != Action.PHYSICAL) {
            return;
        }

        // Check if the player is moving onto a pressure plate
        if (type == Material.STONE_PRESSURE_PLATE) {

            long currentTime = System.currentTimeMillis();

            // Check if the block is on cooldown
            if (cooldowns.containsKey(clickedBlock)) {
                long lastTriggered = cooldowns.get(clickedBlock);
                if (currentTime - lastTriggered < COOLDOWN_TIME) {
                    return;
                }
            }

            // Update the cooldown for the block
            cooldowns.put(clickedBlock, currentTime);

            Block belowBlock = clickedBlock.getLocation().add(0, -1, 0).getBlock();
            ItemStack paper = new ItemStack(Material.PAPER);

            Team team = Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(player.getName());

            if (team == null) { return; }

            if (belowBlock.getType() == Material.PURPLE_CONCRETE) {
                if (!team.getName().equals("Enderman")) { return; }

                player.getInventory().addItem(paper);
                player.sendActionBar(ChatColor.DARK_PURPLE + "+1 Paper");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f);
            } else if (belowBlock.getType() == Material.GREEN_CONCRETE || belowBlock.getType() == Material.LIME_CONCRETE) {
                if (!team.getName().equals("Creeper")) { return; }

                player.getInventory().addItem(paper);
                player.sendActionBar(ChatColor.GREEN + "+1 Paper");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f);
            }

        }
    }
}