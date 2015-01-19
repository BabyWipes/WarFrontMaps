package us.therighteo.warfront.maps;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import us.therighteo.warfront.WFP;
import us.therighteo.warfront.gamemode.GamemodeType;
import us.therighteo.warfront.gamemode.Map;
import us.therighteo.warfront.gamemode.util.SerializedLocation;

public class SecondDimension extends Map {

    public void readyAttributes() {
        setMapName("Second Dimension");
        setCreators(new String[]{"S4Y", "DinoPlax", "bishoptaj"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.TDM, GamemodeType.LMS, GamemodeType.LTS, GamemodeType.OUTBREAK});
        setDefaultDisabledDrops();
        setAllowBuild(false, false);
        setTimeLockTime(14000L);
        defineTeam1("Red Team", ChatColor.RED);
        defineTeam2("Blue Team", ChatColor.BLUE);
    }

    protected void readySpawns() {
        team1Spawns.add(new SerializedLocation(1, 70, -3, 270F, 4.1F));
        team1Spawns.add(new SerializedLocation(2, 70, -22, 270F, 6.7F));
        team1Spawns.add(new SerializedLocation(2, 70, -37, 257F, 4F));
        team1Spawns.add(new SerializedLocation(2, 70, -53, 255F, 12.2F));
        team1Spawns.add(new SerializedLocation(2, 70, -65, 313.3F, -0.7F));
        team1Spawns.add(new SerializedLocation(18, 76, -65, 351F, 3.6F));
        team1Spawns.add(new SerializedLocation(32, 70, -65, 344.8F, 12.7F));
        team1Spawns.add(new SerializedLocation(46, 70, -65, 351.7F, 2.2F));
        team1Spawns.add(new SerializedLocation(57, 72, -65, 351.774F, 0.5F));

        team2Spawns.add(new SerializedLocation(67, 70, -65, 41.2F, 5.5F));
        team2Spawns.add(new SerializedLocation(67, 70, -65, 41.2F, 5.5F));
        team2Spawns.add(new SerializedLocation(68, 70, -32, 80F, 8.7F));
        team2Spawns.add(new SerializedLocation(67, 70, -12, 118.6F, 12.4F));
        team2Spawns.add(new SerializedLocation(66, 70, -1, 134.6F, -1F));
        team2Spawns.add(new SerializedLocation(51, 75, -1, 161F, -1.8F));
        team2Spawns.add(new SerializedLocation(34, 70, -1, 161.3F, 4.5F));
        team2Spawns.add(new SerializedLocation(22, 70, -1, 152.8F, -0.4F));
        team2Spawns.add(new SerializedLocation(13, 72, -1, 183F, 0.9F));
        spectatorSpawns.add(new SerializedLocation(67, 85, -65, 44.5F, -3F));
        spectatorSpawns.add(new SerializedLocation(2, 85, -1, 227F, -15F));
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
