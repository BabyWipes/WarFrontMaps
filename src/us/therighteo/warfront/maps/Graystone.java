package us.therighteo.warfront.maps;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import us.therighteo.warfront.WFP;
import us.therighteo.warfront.gamemode.GamemodeType;
import us.therighteo.warfront.gamemode.Map;
import us.therighteo.warfront.gamemode.util.SerializedLocation;
import us.therighteo.warfront.gamemode.util.Territory;
import us.therighteo.warfront.util.InvUtil;

public class Graystone extends Map {

    public void readyAttributes() {
        setMapName("Graystone");
        setCreators(new String[]{"OptifineBanner", "S4Y"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.LTS, GamemodeType.DDM, GamemodeType.TDM});
        setDisabledDrops(new Material[]{Material.STONE_SWORD, Material.LEATHER_HELMET, Material.IRON_CHESTPLATE, Material.LEATHER_LEGGINGS,
                Material.IRON_BOOTS, Material.PUMPKIN_PIE, Material.LOG, Material.BOW, Material.ARROW, Material.IRON_HELMET,
                Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.MAGMA_CREAM, Material.STONE_PICKAXE,
                Material.IRON_PICKAXE, Material.IRON_AXE, Material.STONE_SWORD});
        setAllowBuild(false, false);
        setTimeLockTime(15000L);
        defineTeam1("Yellow Team", ChatColor.YELLOW);
        defineTeam2("Orange Team", ChatColor.GOLD);
    }

    protected void readySpawns() {
        team2Spawns.add(new SerializedLocation(29, 96, 38, 180F, 0F));
        team2Spawns.add(new SerializedLocation(4, 96, 38, 180F, 0F));

        team1Spawns.add(new SerializedLocation(4, 96, -17, 0F, 0F));
        team1Spawns.add(new SerializedLocation(29, 96, -16, 0F, 0F));

        spectatorSpawns.add(new SerializedLocation(16, 106, 41, 181F, -15F));
        spectatorSpawns.add(new SerializedLocation(17, 106, -19, 2.6F, -6.8F));

        territories.add(new Territory(25, 95, 37, 28, 90, 40, "TEAM_2"));
        territories.add(new Territory(5, 95, 37, 8, 90, 40, "TEAM_2"));
        territories.add(new Territory(29, 94, -15, 25, 90, -18, "TEAM_1"));
        territories.add(new Territory(8, 95, -15, 5, 90, -18, "TEAM_1"));
    }

    protected void applyInventory(WFP target) {
        PlayerInventory inv = target.getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.getCurrentTeam()));
        inv.setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
        inv.setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), target.getCurrentTeam()));
        inv.setBoots(new ItemStack(Material.IRON_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.STONE_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.PUMPKIN_PIE, 32));
        inv.setItem(3, new ItemStack(Material.POTION, 2, (short) 8229));
        inv.setItem(8, InvUtil.setLore(new ItemStack(Material.MAGMA_CREAM, 1), ChatColor.BLUE + "Sunscreen", "SPF1337+!"));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onButtonEject(PlayerInteractEvent event) {
        WFP p = WFP.getWFP(event.getPlayer());
        Action ac = event.getAction();
        if (ac == Action.RIGHT_CLICK_AIR || ac == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand().getType() == Material.MAGMA_CREAM) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000 * 20, 0));
                p.getInventory().removeItem(p.getItemInHand());
            }
        }
    }
}
