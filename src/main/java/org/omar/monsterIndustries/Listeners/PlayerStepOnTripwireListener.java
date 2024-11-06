package org.omar.monsterIndustries.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class PlayerStepOnTripwireListener implements Listener {

    @EventHandler
    public void onPlayerStepOnTripwire(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) return;
        Player player = event.getPlayer();

        Block clickedBlock = event.getClickedBlock();
        Material type = clickedBlock.getType();

        if (type != Material.TRIPWIRE && type != Material.TRIPWIRE_HOOK) return;

        Block nearestNetherBricks = findNearestNetherBricks(player);

        if (nearestNetherBricks != null) {
            player.teleport(nearestNetherBricks.getLocation().add(0.5, 1, 0.5));
            player.sendActionBar(ChatColor.RED + "+1 Spider Eye");
            player.getInventory().addItem(new ItemStack(Material.SPIDER_EYE));
        }
    }

    // Finds the nearest NETHER_BRICKS block within a 5-block radius
    private Block findNearestNetherBricks(Player player) {
        Block nearestNetherBricks = null;
        double nearestDistance = Double.MAX_VALUE;

        for (int x = -5; x <= 5; x++) {
            for (int y = -5; y <= 5; y++) {
                for (int z = -5; z <= 5; z++) {
                    Block nearbyBlock = player.getWorld().getBlockAt(player.getLocation().add(x, y, z));
                    if (nearbyBlock.getType() == Material.NETHER_BRICKS) {
                        double distance = nearbyBlock.getLocation().distance(player.getLocation());
                        if (distance < nearestDistance) {
                            nearestDistance = distance;
                            nearestNetherBricks = nearbyBlock;
                        }
                    }
                }
            }
        }
        return nearestNetherBricks;
    }
}
