package us.therighteo.warfront.maps;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import us.therighteo.warfront.WFP;
import us.therighteo.warfront.gamemode.GamemodeType;
import us.therighteo.warfront.gamemode.Map;
import us.therighteo.warfront.gamemode.util.CylinderMapRegion;
import us.therighteo.warfront.gamemode.util.ProtectedCuboid;
import us.therighteo.warfront.gamemode.util.SerializedLocation;

import java.util.Arrays;
import java.util.Random;

public class Segregation extends Map {

    public void readyAttributes() {
        setMapName("Segregation");
        setCreators(new String[]{"_Moist", "S4Y", "MiCkEyMiCE", "dashhhb", "AnomalousDyna"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.TDM});
        setDisabledDrops(new Material[]{Material.STONE_SWORD, Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.GOLD_LEGGINGS,
                Material.GOLD_BOOTS, Material.BREAD, Material.LOG, Material.BOW, Material.ARROW, Material.IRON_HELMET,
                Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.STONE_AXE, Material.STONE_PICKAXE,
                Material.IRON_PICKAXE, Material.IRON_AXE, Material.IRON_SWORD});
        setAllowBuild(true, true);
        setTimeLockTime(15000L);
        defineTeam1("Orange Team", ChatColor.GOLD);
        defineTeam2("Dark Team", ChatColor.DARK_GRAY);
    }

    protected void readySpawns() {
        team1Spawns.add(new SerializedLocation(43, 78, 51, 218F, 2.2F));
        team2Spawns.add(new SerializedLocation(-45, 78, -52, 37.6F, 1.4F));

        spectatorSpawns.add(new SerializedLocation(-38, 72, 36, 248F, 1.4F));
        spectatorSpawns.add(new SerializedLocation(35, 72, -37, 64.3F, -6.3F));

        protectedCuboids.add(new ProtectedCuboid(36, 74, 58, 47, 86, 46, "TEAM_1"));
        protectedCuboids.add(new ProtectedCuboid(-40, 74, -59, -49, 86, -47, "TEAM_2"));

        cylRegions.add(new CylinderMapRegion(new SerializedLocation(-1, 55, 0), 75));
    }

    protected void applyInventory(WFP target) {
        PlayerInventory inv = target.getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.getCurrentTeam()));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.getCurrentTeam()));
        inv.setLeggings(new ItemStack(Material.GOLD_LEGGINGS, 1));
        inv.setBoots(new ItemStack(Material.GOLD_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.STONE_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.STONE_PICKAXE, 1));
        inv.setItem(3, new ItemStack(Material.STONE_AXE, 1));
        inv.setItem(4, new ItemStack(Material.BREAD, 32));
        inv.setItem(5, new ItemStack(Material.GOLDEN_APPLE, 2));
        inv.setItem(6, new ItemStack(Material.WOOD, 16));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onEffectFeatherInteract(PlayerInteractEvent event) {
        WFP p = WFP.getWFP(event.getPlayer());
        Action ac = event.getAction();

        if (ac == Action.RIGHT_CLICK_AIR || ac == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand() != null && p.getItemInHand().getType() == Material.FEATHER) {
                ItemStack hand = p.getItemInHand();
                hand.setAmount(1);
                p.getInventory().removeItem(hand);
                p.addPotionEffect(new PotionEffect(randomEffect(), 25 * 20, 0));
            }
        }
    }

    private PotionEffectType randomEffect() {
        PotionEffectType[] types = {PotionEffectType.SPEED, PotionEffectType.JUMP, PotionEffectType.SLOW,
                PotionEffectType.WEAKNESS, PotionEffectType.REGENERATION, PotionEffectType.DAMAGE_RESISTANCE,
                PotionEffectType.CONFUSION, PotionEffectType.BLINDNESS, PotionEffectType.INVISIBILITY};
        return Arrays.asList(types).get(new Random().nextInt(types.length));
    }
}
