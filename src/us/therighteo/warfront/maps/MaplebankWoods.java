package us.therighteo.warfront.maps;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import us.therighteo.warfront.WFP;
import us.therighteo.warfront.gamemode.GamemodeType;
import us.therighteo.warfront.gamemode.Map;
import us.therighteo.warfront.gamemode.util.NoBuildRegion;
import us.therighteo.warfront.gamemode.util.ProtectedCuboid;
import us.therighteo.warfront.gamemode.util.SerializedLocation;

public class MaplebankWoods extends Map {

    public void readyAttributes() {
        setMapName("Maplebank Woods");
        setCreators(new String[]{"S4Y", "DanShrdr"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.TDM, GamemodeType.KOTH});
        setDefaultDisabledDrops();
        setAllowBuild(true, true);
        setTimeLockTime(15000L);
        defineTeam1("Forest Team", ChatColor.DARK_GREEN);
        defineTeam2("River Team", ChatColor.DARK_AQUA);
    }

    protected void readySpawns() {
        team2Spawns.add(new SerializedLocation(41, 90, 90, 180F, 0F));
        team1Spawns.add(new SerializedLocation(-13, 90, 90, 180F, 0F));

        spectatorSpawns.add(new SerializedLocation(-5, 116, 80, 230F, -20F));
        spectatorSpawns.add(new SerializedLocation(33, 116, 80, 143F, -20F));

        protectedCuboids.add(new ProtectedCuboid(45, 93, 87, 37, 89, 93, "TEAM_2"));
        protectedCuboids.add(new ProtectedCuboid(-9, 89, 93, -17, 93, 87, "TEAM_1"));

        noBuildRegions.add(new NoBuildRegion(-23, 59, 81, -1, 123, 98));
        noBuildRegions.add(new NoBuildRegion(52, 59, 81, 26, 123, 99));
        noBuildRegions.add(new NoBuildRegion(19, 95, -12, 9, 110, -2));

        setKothFlag(new SerializedLocation(14, 100, -7));
    }

    protected void applyInventory(WFP target) {
        PlayerInventory inv = target.getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.getCurrentTeam()));
        inv.setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
        inv.setLeggings(new ItemStack(Material.GOLD_LEGGINGS, 1));
        inv.setBoots(new ItemStack(Material.GOLD_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.STONE_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.STONE_PICKAXE, 1));
        inv.setItem(3, new ItemStack(Material.STONE_AXE, 1));
        inv.setItem(4, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(5, new ItemStack(Material.GOLDEN_APPLE, 2));
        inv.setItem(6, new ItemStack(Material.LOG, 16));
        inv.setItem(10, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Location equiv = event.getBlock().getLocation().clone();
        equiv.setY(59);
        if (equiv.getBlock().getType() != Material.BEDROCK) event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Location equiv = event.getBlock().getLocation().clone();
        equiv.setY(59);
        if (equiv.getBlock().getType() != Material.BEDROCK) event.setCancelled(true);
    }
}
