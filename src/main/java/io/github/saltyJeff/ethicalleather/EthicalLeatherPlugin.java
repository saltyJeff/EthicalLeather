package io.github.saltyJeff.ethicalleather;

import io.papermc.lib.PaperLib;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Levi Muniz on 7/29/20.
 *
 * @author Copyright (c) Levi Muniz. All Rights Reserved.
 */
public class EthicalLeatherPlugin extends JavaPlugin implements Listener {
  int leatherPerPlayerDeath;
  @Override
  public void onEnable() {
    saveDefaultConfig();
    leatherPerPlayerDeath = getConfig().getInt("ethical_leather.leather_per_player_death");

    // flesh -> leather recipe
    ShapedRecipe fleshToLeather = new ShapedRecipe(new NamespacedKey(this, "fleshToLeather"), ItemStack.of(Material.LEATHER));
    fleshToLeather.shape("AAA", "AAA", "AAA");
    fleshToLeather.setIngredient('A', Material.ROTTEN_FLESH);
    getServer().addRecipe(fleshToLeather);

    // stripped log -> leather recipe
    ShapedRecipe stripLogToLeather = new ShapedRecipe(new NamespacedKey(this, "stripLogToLeather"), ItemStack.of(Material.LEATHER, 2));
    stripLogToLeather.shape("AAA", "AAA", "AAA");
    stripLogToLeather.setIngredient('A', allStrippedWoodAndLogs());
    getServer().addRecipe(stripLogToLeather);

    // enable player death event
    getServer().getPluginManager().registerEvents(this, this);
  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent e) {
    e.getDrops().add(ItemStack.of(Material.LEATHER, leatherPerPlayerDeath));
  }

  RecipeChoice.MaterialChoice allStrippedWoodAndLogs() {
    List<Material> accepted = new ArrayList<>();
    for(Material mat : Material.values()) {
      if(mat.name().startsWith("STRIPPED_") && (
              mat.name().endsWith("_LOG") || mat.name().endsWith("_WOOD")
              )) {
        accepted.add(mat);
      }
    }
    return new RecipeChoice.MaterialChoice(accepted);
  }
}
