package org.omar.monsterIndustries.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Powerable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class PlayerStepOnPressurePlateListeners implements Listener {

    // Cooldown map to store the last time a block (pressure plate) was triggered
    private final HashMap<Block, Long> cooldowns = new HashMap<>();
    private final long COOLDOWN_TIME = 1000;

    @EventHandler
    public void onPlayerStepOnPressurePlate(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block toBlock = event.getTo().getBlock();
        Block fromBlock = event.getFrom().getBlock();

        // Check if the player is moving onto a pressure plate
        if (toBlock.getType() == Material.STONE_PRESSURE_PLATE && fromBlock.getType() != Material.STONE_PRESSURE_PLATE) {

            long currentTime = System.currentTimeMillis();

            // Check if the block is on cooldown
            if (cooldowns.containsKey(toBlock)) {
                long lastTriggered = cooldowns.get(toBlock);
                if (currentTime - lastTriggered < COOLDOWN_TIME) {
                    return;
                }
            }

            // Update the cooldown for the block
            cooldowns.put(toBlock, currentTime);

            Block belowBlock = toBlock.getLocation().add(0, -1, 0).getBlock();
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