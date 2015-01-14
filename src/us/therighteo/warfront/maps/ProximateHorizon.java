package us.therighteo.warfront.maps;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import us.therighteo.warfront.WFP;
import us.therighteo.warfront.gamemode.GamemodeType;
import us.therighteo.warfront.gamemode.Map;
import us.therighteo.warfront.gamemode.util.CuboidMapRegion;
import us.therighteo.warfront.gamemode.util.NoBuildRegion;
import us.therighteo.warfront.gamemode.util.ProtectedCuboid;
import us.therighteo.warfront.gamemode.util.SerializedLocation;

public class ProximateHorizon extends Map {

    public void readyAttributes() {
        setMapName("Proximate Horizon");
        setCreators(new String[]{"_Moist"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.CTF, GamemodeType.TDM});
        setDefaultDisabledDrops();
        setAllowBreak(true);
        setTimeLockTime(6000L);
        setFlagCaptureRequirement(1);
        defineTeam1("Red Team", ChatColor.RED);
        defineTeam2("Blue Team", ChatColor.BLUE);
    }

    protected void readySpawns() {
        team1Spawns.add(new SerializedLocation(-3, 23, 49, 90F, 0.7F));

        team2Spawns.add(new SerializedLocation(-349, 23, 51, 270F, 2.6F));

        spectatorSpawns.add(new SerializedLocation(-213, 41, 10, 300F, -3.7F));
        spectatorSpawns.add(new SerializedLocation(-139, 41, 90, 123F, -12F));

        protectedCuboids.add(new ProtectedCuboid(-336, 0, 42, -352, 256, 60, "TEAM_2"));
        protectedCuboids.add(new ProtectedCuboid(-16, 0, 58, 0, 256, 40, "TEAM_1"));

        cuboidRegions.add(new CuboidMapRegion(-355, 10, 100, 2, 120, -1));
        noBuildRegions.add(new NoBuildRegion(-75, 40, 56, -67, 120, 65));
        noBuildRegions.add(new NoBuildRegion(-285, 40, 35, -277, 120, 44));

        setTeam1Flag(new SerializedLocation(-71, 45, 60));
        setTeam2Flag(new SerializedLocation(-281, 45, 40));
    }

    protected void applyInventory(WFP target) {
        PlayerInventory inv = target.getInventory();

        inv.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET, 1));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.getCurrentTeam()));
        inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
        inv.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.IRON_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.STONE_PICKAXE, 1));
        inv.setItem(3, new ItemStack(Material.PUMPKIN_PIE, 6));
        inv.setItem(4, new ItemStack(Material.GOLDEN_APPLE, 1));
        inv.setItem(5, new ItemStack(Material.LOG, 16));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }
}
