package us.therighteo.warfront.maps;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import us.therighteo.warfront.WFP;
import us.therighteo.warfront.gamemode.GamemodeType;
import us.therighteo.warfront.gamemode.Map;
import us.therighteo.warfront.gamemode.util.Monument;
import us.therighteo.warfront.gamemode.util.ProtectedCuboid;
import us.therighteo.warfront.gamemode.util.SerializedLocation;
import us.therighteo.warfront.util.InvUtil;

public class Cubed extends Map {

    public void readyAttributes() {
        setMapName("Cubed");
        setCreators(new String[]{"S4Y", "x15", "Wulix", "Pinkanite"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.GTM, GamemodeType.TDM});
        setDisabledDrops(new Material[]{Material.IRON_SWORD, Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE,
                Material.LEATHER_HELMET, Material.GLASS_BOTTLE, Material.LOG, Material.BOW, Material.ARROW, Material.IRON_PICKAXE, Material.IRON_AXE, Material.FEATHER});
        setAllowBuild(true, true);
        setTimeLockTime(14500L);
        setRemainRequirement(0);
        setMatchDuration(300);
        defineTeam1("Green Team", ChatColor.DARK_GREEN);
        defineTeam2("Red Team", ChatColor.DARK_RED);
    }

    protected void readySpawns() {
        team2Spawns.add(new SerializedLocation(41, 89, 38, 90F, 4F));

        team1Spawns.add(new SerializedLocation(-21, 106, 38, 270F, 0F));

        spectatorSpawns.add(new SerializedLocation(1, 103, 12, 337F, -8F));
        spectatorSpawns.add(new SerializedLocation(2, 105, 62, 216F, -3F));

        protectedCuboids.add(new ProtectedCuboid(36, 89, 31, 49, 102, 44, "TEAM_2"));
        protectedCuboids.add(new ProtectedCuboid(-29, 116, 45, -14, 105, 30, "TEAM_1"));

        setMonument(new Monument(-4, 89, 36, -7, 101, 39, Material.WOOD));
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
        inv.setItem(3, new ItemStack(Material.IRON_AXE, 1));
        inv.setItem(4, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(5, new ItemStack(Material.POTION, 2, (short) 8229));
        inv.setItem(6, new ItemStack(Material.LOG, 16));
        inv.setItem(7, InvUtil.setLore(new ItemStack(Material.FEATHER, 1), ChatColor.BLUE + "Soft-lander"));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void damage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL && event.getEntity() instanceof Player) {
            Player target = (Player) event.getEntity();
            if (target.getItemInHand().getType() == Material.FEATHER) {
                event.setDamage(0);
                target.sendMessage(ChatColor.BLUE + "Fall damage negated!");
            }
        }
    }
}
