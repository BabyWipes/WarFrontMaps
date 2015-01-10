package us.therighteo.warfront.maps;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import us.therighteo.warfront.WFP;
import us.therighteo.warfront.gamemode.GamemodeType;
import us.therighteo.warfront.gamemode.Map;
import us.therighteo.warfront.gamemode.util.SerializedLocation;

public class Esquivalience extends Map {

    public void readyAttributes() {
        setMapName("Esquivalience");
        setCreators(new String[]{"MiCkEyMiCE", "dashhhb", "S4Y"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.FFA, GamemodeType.LMS, GamemodeType.OUTBREAK});
        setDisabledDrops(new Material[]{Material.STONE_SWORD, Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE,
                Material.LEATHER_HELMET, Material.BOW, Material.ARROW});
        setAllowBuild(false, false);
        setTimeLockTime(14000L);
        defineTeam1("Illusory", ChatColor.DARK_BLUE);
    }

    protected void readySpawns() {
        team1Spawns.add(new SerializedLocation(50, 68, -77, 59.5F, -4.5F));
        team1Spawns.add(new SerializedLocation(36, 68, -100, 5.7F, -3.5F));
        team1Spawns.add(new SerializedLocation(16, 68, -111, 300.7F, 0.8F));
        team1Spawns.add(new SerializedLocation(15, 68, -88, 63.5F, -1.5F));
        team1Spawns.add(new SerializedLocation(4, 70, -56, 136.77F, -3.4F));
        team1Spawns.add(new SerializedLocation(11, 70, -43, 141.1F, 8.8F));
        team1Spawns.add(new SerializedLocation(7, 80, -28, 163.2F, 6.6F));
        team1Spawns.add(new SerializedLocation(18, 80, -15, 169F, 2.9F));
        team1Spawns.add(new SerializedLocation(-13, 80, -7, 179.1F, 0.1F));
        team1Spawns.add(new SerializedLocation(-18, 80, -37, 223.6F, -2F));
        team1Spawns.add(new SerializedLocation(-45, 80, -39, 246.5F, 7F));
        team1Spawns.add(new SerializedLocation(-51, 80, -60, 232.2F, 6.6F));
        team1Spawns.add(new SerializedLocation(-55, 83, -90, 263F, 1.7F));
        team1Spawns.add(new SerializedLocation(-47, 68, -121, 315.5F, -0.2F));
        team1Spawns.add(new SerializedLocation(-39, 71, -99, 340F, 0.7F));
        team1Spawns.add(new SerializedLocation(-35, 71, -80, 250F, 0F));
        team1Spawns.add(new SerializedLocation(-13, 70, -84, 251.4F, -1.6F));
        team1Spawns.add(new SerializedLocation(-22, 69, -129, 253.1F, -3F));

        spectatorSpawns.add(new SerializedLocation(7, 96, -104, 43.1F, 0.5F));
        spectatorSpawns.add(new SerializedLocation(23, 91, -43, 131.6F, 3.37F));
        spectatorSpawns.add(new SerializedLocation(-56, 95, -40, 258.8F, 10.5F));
        spectatorSpawns.add(new SerializedLocation(-51, 115, -101, 309.4F, -22.5F));
    }

    protected void applyInventory(WFP target) {
        PlayerInventory inv = target.getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.getCurrentTeam()));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.getCurrentTeam()));
        inv.setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), target.getCurrentTeam()));
        inv.setBoots(colorArmor(new ItemStack(Material.LEATHER_BOOTS, 1), target.getCurrentTeam()));

        inv.setItem(0, new ItemStack(Material.STONE_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(3, new ItemStack(Material.GOLDEN_APPLE, 2));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }
}
