package us.therighteo.warfront.maps;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import us.therighteo.warfront.WFP;
import us.therighteo.warfront.gamemode.GamemodeType;
import us.therighteo.warfront.gamemode.Map;
import us.therighteo.warfront.gamemode.util.SerializedLocation;
import us.therighteo.warfront.gamemode.util.Territory;
import us.therighteo.warfront.util.InvUtil;

public class ConvenienceWars extends Map {

    public void readyAttributes() {
        setMapName("Convenience Wars");
        setCreators(new String[]{"OptifineBanner", "S4Y", "dashhhb", "_StaticMC_"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.LTS, GamemodeType.DDM});
        setDisabledDrops(new Material[]{Material.STONE_SWORD, Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.GOLD_LEGGINGS,
                Material.CHAINMAIL_BOOTS, Material.PUMPKIN_PIE, Material.LOG, Material.BOW, Material.ARROW, Material.IRON_HELMET,
                Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.STONE_AXE, Material.STONE_PICKAXE,
                Material.IRON_PICKAXE, Material.IRON_AXE, Material.IRON_SWORD});
        setAllowBuild(false, false);
        setTimeLockTime(15000L);
        defineTeam1("Coles Clerks", ChatColor.DARK_RED);
        defineTeam2("Aldi Clerks", ChatColor.DARK_GREEN);
    }

    protected void readySpawns() {
        team2Spawns.add(new SerializedLocation(23, 20, -53, 0.5F, 0F));
        team2Spawns.add(new SerializedLocation(47, 20, -53, 0.5F, 0F));
        team2Spawns.add(new SerializedLocation(55, 20, -26, 0.7F, -1.6F));
        team2Spawns.add(new SerializedLocation(47, 20, -16, 180, 0F));

        team1Spawns.add(new SerializedLocation(126, 20, -16, 180, 0F));
        team1Spawns.add(new SerializedLocation(102, 20, -16, 180F, 0F));
        team1Spawns.add(new SerializedLocation(102, 20, -53, 0.5F, 0F));
        team1Spawns.add(new SerializedLocation(94, 20, -43, 180F, 0F));

        spectatorSpawns.add(new SerializedLocation(121, 37, -32, 80F, -10F));
        spectatorSpawns.add(new SerializedLocation(28, 37, -37, 254F, -16F));

        territories.add(new Territory(27, 23, -31, 27, 20, -35, "TEAM_2"));
        territories.add(new Territory(122, 23, -38, 122, 20, -34, "TEAM_1"));
    }

    protected void applyInventory(WFP target) {
        PlayerInventory inv = target.getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.getCurrentTeam()));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.getCurrentTeam()));
        inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
        inv.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.STONE_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.PUMPKIN_PIE, 32));
        inv.setItem(3, new ItemStack(Material.GOLDEN_APPLE, 2));
        inv.setItem(8, InvUtil.setLore(new ItemStack(Material.STONE_BUTTON, 1), ChatColor.BLUE + "Eject Button", "Eject! Eject!"));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onButtonEject(PlayerInteractEvent event) {
        WFP p = WFP.getWFP(event.getPlayer());
        Action ac = event.getAction();
        if (ac == Action.RIGHT_CLICK_AIR || ac == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand().getType() == Material.STONE_BUTTON) {
                p.setVelocity(p.getVelocity().multiply(-10));
                p.getInventory().removeItem(p.getItemInHand());
            }
        }
    }
}
