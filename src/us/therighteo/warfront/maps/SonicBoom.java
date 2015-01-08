package us.therighteo.warfront.maps;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import us.therighteo.warfront.WFP;
import us.therighteo.warfront.gamemode.GamemodeType;
import us.therighteo.warfront.gamemode.Map;
import us.therighteo.warfront.gamemode.util.SerializedLocation;

public class SonicBoom extends Map {

    public void readyAttributes() {
        setMapName("Sonic Boom");
        setCreators(new String[]{"S4Y"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.FFA, GamemodeType.OUTBREAK});
        setDisabledDrops(new Material[]{Material.IRON_SWORD, Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE,
                Material.LEATHER_HELMET, Material.BOW, Material.ARROW});
        setAllowBuild(true, true);
        setTimeLockTime(14000L);
        defineTeam1("Survivors", ChatColor.YELLOW);
    }

    protected void readySpawns() {
        team1Spawns.add(new SerializedLocation(0, 73, 22, 180F, -0.9F));
        team1Spawns.add(new SerializedLocation(-17, 76, 17, 225F, 0.8F));
        team1Spawns.add(new SerializedLocation(-22, 73, 0, 270F, 0.2F));
        team1Spawns.add(new SerializedLocation(-16, 76, -16, 318F, -0.8F));
        team1Spawns.add(new SerializedLocation(0, 73, 22, 359.6F, -0.5F));
        team1Spawns.add(new SerializedLocation(16, 76, -16, 43.1F, 0.4F));
        team1Spawns.add(new SerializedLocation(22, 73, 0, 89F, 0.5F));
        team1Spawns.add(new SerializedLocation(17, 76, 17, 132.3F, 1.7F));
        team1Spawns.add(new SerializedLocation(-40, 73, 0, 270F, -5.3F));
        team1Spawns.add(new SerializedLocation(-29, 74, -29, 304F, -4.7F));
        team1Spawns.add(new SerializedLocation(0, 73, -40, 358.7F, 0.444F));
        team1Spawns.add(new SerializedLocation(29, 74, 29, 40.8F, -7F));
        team1Spawns.add(new SerializedLocation(39, 73, 0, 87.3F, -0.1F));
        team1Spawns.add(new SerializedLocation(29, 74, 29, 130.7F, -8.9F));
        team1Spawns.add(new SerializedLocation(0, 73, 40, 180F, 2.6F));
        team1Spawns.add(new SerializedLocation(-29, 74, 29, 223.1F, -10.2F));

        spectatorSpawns.add(new SerializedLocation(0, 72, 0, 0F, -3F));
        spectatorSpawns.add(new SerializedLocation(0, 72, 0, 90F, -3F));
        spectatorSpawns.add(new SerializedLocation(0, 72, 0, 180F, -3F));
        spectatorSpawns.add(new SerializedLocation(0, 72, 0, 270F, -3F));
    }

    protected void applyInventory(WFP target) {
        PlayerInventory inv = target.getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.getCurrentTeam()));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.getCurrentTeam()));
        inv.setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), target.getCurrentTeam()));
        inv.setBoots(colorArmor(new ItemStack(Material.LEATHER_BOOTS, 1), target.getCurrentTeam()));

        inv.setItem(0, new ItemStack(Material.STONE_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.IRON_PICKAXE, 1));
        inv.setItem(3, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(4, new ItemStack(Material.GOLDEN_APPLE, 2));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }
}
