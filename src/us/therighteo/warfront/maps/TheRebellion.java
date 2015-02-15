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
import us.therighteo.warfront.gamemode.util.Monument;
import us.therighteo.warfront.gamemode.util.NoBuildRegion;
import us.therighteo.warfront.gamemode.util.ProtectedCuboid;
import us.therighteo.warfront.gamemode.util.SerializedLocation;

public class TheRebellion extends Map {

    public void readyAttributes() {
        setMapName("The Rebellion");
        setCreators(new String[]{"DanShrdr", "S4Y", "dashhhb"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.GTM, GamemodeType.TDM});
        setDefaultDisabledDrops();
        setAllowBuild(true, true);
        setRemainRequirement(0);
        setMobSpawning(false);
        setTimeLockTime(14000L);
        defineTeam1("Protectors", ChatColor.RED);
        defineTeam2("Invaders", ChatColor.BLUE);
    }

    protected void readySpawns() {
        team1Spawns.add(new SerializedLocation(-112, 146, 43, 180F, -0.3F));
        team1Spawns.add(new SerializedLocation(-67, 146, 100, 0F, 0F));

        team2Spawns.add(new SerializedLocation(-13, 89, 39, 127.7F, 2F));
        team2Spawns.add(new SerializedLocation(-26, 89, 12, 37F, -1F));

        spectatorSpawns.add(new SerializedLocation(-67, 174, 100, 225F, 0F));
        spectatorSpawns.add(new SerializedLocation(-112, 174, 43, 237.8F, 0F));

        protectedCuboids.add(new ProtectedCuboid(-25, 89, 10, -28, 91, 13, "TEAM_2"));
        protectedCuboids.add(new ProtectedCuboid(-15, 88, 37, -12, 91, 40, "TEAM_2"));
        protectedCuboids.add(new ProtectedCuboid(-64, 140, 103, -71, 152, 97, "TEAM_1"));
        protectedCuboids.add(new ProtectedCuboid(-109, 140, 40, -115, 152, 47, "TEAM_1"));

        noBuildRegions.add(new NoBuildRegion(-73, 132, 106, -61, 176, 94));
        noBuildRegions.add(new NoBuildRegion(-118, 132, 49, -106, 176, 37));
        setMonument(new Monument(-93, 137, 76, -96, 139, 73, Material.PRISMARINE));
    }

    protected void applyInventory(WFP target) {
        PlayerInventory inv = target.getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.getCurrentTeam()));
        inv.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE, 1));
        inv.setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), target.getCurrentTeam()));
        inv.setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.IRON_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.STONE_PICKAXE, 1));
        inv.setItem(3, new ItemStack(Material.IRON_AXE, 1));
        inv.setItem(4, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(5, new ItemStack(Material.GOLDEN_APPLE, 1));
        inv.setItem(6, new ItemStack(Material.LOG, 16));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Location equiv = event.getBlock().getLocation().clone();
        equiv.setY(65);
        if (equiv.getBlock().getType() != Material.BEDROCK) event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Location equiv = event.getBlock().getLocation().clone();
        equiv.setY(65);
        if (equiv.getBlock().getType() != Material.BEDROCK) event.setCancelled(true);
    }
}
