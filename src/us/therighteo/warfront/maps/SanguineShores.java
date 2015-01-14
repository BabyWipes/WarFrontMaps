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

public class SanguineShores extends Map {

    public void readyAttributes() {
        setMapName("Sanguine Shores");
        setCreators(new String[]{"ep1cn00bt00b", "DinoPlax", "S4Y"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.KOTH, GamemodeType.TDM});
        setDefaultDisabledDrops();
        setAllowBuild(false, false);
        setTimeLockTime(6000L);
        defineTeam1("Tourists", ChatColor.DARK_PURPLE);
        defineTeam2("Locals", ChatColor.AQUA);
    }

    protected void readySpawns() {
        team1Spawns.add(new SerializedLocation(68, 93, -3, 88.9F, 1.7F));

        team2Spawns.add(new SerializedLocation(-69, 93, 3, 263.8F, -1.75F));

        spectatorSpawns.add(new SerializedLocation(-30, 110, 31, 226.6F, -4.4F));
        spectatorSpawns.add(new SerializedLocation(30, 110, -31, 52.2F, -12F));

        protectedCuboids.add(new ProtectedCuboid(-66, 90, -1, -70, 97, 8, "TEAM_2"));
        protectedCuboids.add(new ProtectedCuboid(66, 90, 1, 70, 97, -8, "TEAM_1"));
        setKothFlag(new SerializedLocation(0, 93, 0));
    }

    protected void applyInventory(WFP target) {
        PlayerInventory inv = target.getInventory();

        inv.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET, 1));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.getCurrentTeam()));
        inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
        inv.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.IRON_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.PUMPKIN_PIE, 6));
        inv.setItem(3, new ItemStack(Material.GOLDEN_APPLE, 1));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }
}
