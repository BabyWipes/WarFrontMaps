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
import us.therighteo.warfront.gamemode.util.SerializedLocation;

public class DynamicGTM1 extends Map {

    public void readyAttributes() {
        setMapName("Dynamic GTM 1");
        setCreators(new String[]{"[TR]"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.GTM});
        setDefaultDisabledDrops();
        setAllowBuild(true, true);
        setRemainRequirement(0);
        setMobSpawning(false);
        setTimeLockTime(14000L);
        setMatchDuration(300);
        defineTeam1("Defense", ChatColor.RED);
        defineTeam2("Offense", ChatColor.GREEN);
    }

    protected void readySpawns() {
        team1Spawns.add(new SerializedLocation(0.5, 164, -39.5, 0F, 0F));
        team1Spawns.add(new SerializedLocation(40.5, 164, 1.5, 90F, 0F));
        team1Spawns.add(new SerializedLocation(0.5, 164, 41.5, 180F, 0F));
        team1Spawns.add(new SerializedLocation(-40.5, 164, 1.5, 270F, 0F));

        team2Spawns.add(new SerializedLocation(60.5, 155, 1.5, 90F, 0F));
        team2Spawns.add(new SerializedLocation(0.5, 155, 61.5, 180F, 0F));
        team2Spawns.add(new SerializedLocation(-60.5, 155, 1.5, 270F, 0F));
        team2Spawns.add(new SerializedLocation(0.5, 155, -59.5, 0F, 0F));

        spectatorSpawns.add(new SerializedLocation(-20, 109, -26, 325.4F, 9F));
        spectatorSpawns.add(new SerializedLocation(20, 109, 26, 135F, 9F));

        setMonument(new Monument(-2, 93, -1, 2, 97, 3, Material.STAINED_CLAY));
    }

    protected void applyInventory(WFP target) {
        PlayerInventory inv = target.getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.getCurrentTeam()));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.getCurrentTeam()));
        inv.setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), target.getCurrentTeam()));
        inv.setBoots(colorArmor(new ItemStack(Material.LEATHER_BOOTS, 1), target.getCurrentTeam()));

        inv.setItem(0, new ItemStack(Material.STONE_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.STONE_PICKAXE, 1));
        inv.setItem(3, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(4, new ItemStack(Material.GOLDEN_APPLE, 1));
        inv.setItem(5, new ItemStack(Material.STONE, 64));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Location equiv = event.getBlock().getLocation().clone();
        equiv.setY(81);
        if (equiv.getBlock().getType() != Material.BEDROCK) event.setCancelled(true);
        equiv.setY(90);
        if(equiv.getBlock().getType() == Material.ENDER_PORTAL_FRAME)
            event.setCancelled(true);
        equiv.setY(81);
        if(equiv.getBlock().getType() == Material.ENDER_PORTAL_FRAME)
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Location equiv = event.getBlock().getLocation().clone();
        if(equiv.getY() > 100){
            event.setCancelled(true);
            return;
        }
        equiv.setY(81);
        if (equiv.getBlock().getType() != Material.BEDROCK) event.setCancelled(true);
        equiv.setY(90);
        if(equiv.getBlock().getType() == Material.ENDER_PORTAL_FRAME)
            event.setCancelled(true);
        equiv.setY(81);
        if(equiv.getBlock().getType() == Material.ENDER_PORTAL_FRAME)
            event.setCancelled(true);
    }
}
