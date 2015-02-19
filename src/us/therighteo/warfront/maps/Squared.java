package us.therighteo.warfront.maps;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import us.therighteo.warfront.WFP;
import us.therighteo.warfront.gamemode.GamemodeType;
import us.therighteo.warfront.gamemode.Map;
import us.therighteo.warfront.gamemode.util.ProtectedCuboid;
import us.therighteo.warfront.gamemode.util.SerializedLocation;

public class Squared extends Map {

    public void readyAttributes() {
        setMapName("Squared");
        setCreators(new String[]{"S4Y", "jakemake2"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.TDM});
        setDisabledDrops(new Material[]{Material.IRON_SWORD, Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE,
                Material.LEATHER_HELMET, Material.GLASS_BOTTLE, Material.LOG, Material.BOW, Material.ARROW, Material.IRON_PICKAXE});
        setAllowBuild(true, true);
        setTimeLockTime(6000L);
        defineTeam1("Red Team", ChatColor.RED);
        defineTeam2("Green Team", ChatColor.GREEN);
    }

    protected void readySpawns() {
        team1Spawns.add(new SerializedLocation(-116, 93, -20, 270F, -1.75F));

        team2Spawns.add(new SerializedLocation(24, 93, -20, 90F, -0.4F));

        spectatorSpawns.add(new SerializedLocation(-88, 109, -3, 270F, -2.7F));
        spectatorSpawns.add(new SerializedLocation(-88, 109, -36, 270F, -2.7F));
        spectatorSpawns.add(new SerializedLocation(-3, 109, -36, 90F, -4.3F));
        spectatorSpawns.add(new SerializedLocation(-3, 109, -3, 90F, -4.3F));

        protectedCuboids.add(new ProtectedCuboid(-105, 120, -35, -119, 77, -4, "TEAM_1"));
        protectedCuboids.add(new ProtectedCuboid(14, 120, -4, 28, 77, -35, "TEAM_2"));
    }

    protected void applyInventory(WFP target) {
        PlayerInventory inv = target.getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.getCurrentTeam()));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.getCurrentTeam()));
        inv.setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), target.getCurrentTeam()));
        inv.setBoots(colorArmor(new ItemStack(Material.LEATHER_BOOTS, 1), target.getCurrentTeam()));

        inv.setItem(0, new ItemStack(Material.IRON_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.IRON_PICKAXE, 1));
        inv.setItem(3, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(4, new ItemStack(Material.POTION, 1, (short) 8229));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }
}
