package us.therighteo.warfront.maps;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
import us.therighteo.warfront.gamemode.util.SerializedLocation;
import us.therighteo.warfront.util.InvUtil;

import java.util.Arrays;
import java.util.Random;

public class Aldfort extends Map {

    public void readyAttributes() {
        setMapName("Aldfort");
        setCreators(new String[]{"S4Y"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.FFA, GamemodeType.LMS, GamemodeType.OUTBREAK});
        setDefaultDisabledDrops();
        setAllowBuild(false, false);
        setTimeLockTime(15000L);
        defineTeam1("Rebels", ChatColor.DARK_AQUA);
    }

    protected void readySpawns() {
        team1Spawns.add(new SerializedLocation(-33, 79, 26, 52F, -9.6F));
        team1Spawns.add(new SerializedLocation(-45, 74, 7, 316F, -3F));
        team1Spawns.add(new SerializedLocation(-56, 74, 35, 236F, -5.7F));
        team1Spawns.add(new SerializedLocation(-47, 68, 51, 255.7F, 8.9F));
        team1Spawns.add(new SerializedLocation(-34, 75, 54, 218.5F, -1.3F));
        team1Spawns.add(new SerializedLocation(-22, 79, 50, 202.3F, -10F));
        team1Spawns.add(new SerializedLocation(-5, 68, 55, 188F, -3F));
        team1Spawns.add(new SerializedLocation(-14, 68, 39, 230F, -1F));
        team1Spawns.add(new SerializedLocation(-3, 68, 28, 89.1F, 1F));
        team1Spawns.add(new SerializedLocation(-5, 68, 4, 48F, -5F));
        team1Spawns.add(new SerializedLocation(-23, 68, 7, 224.2F, -1.8F));
        team1Spawns.add(new SerializedLocation(-16, 74, 15, 48F, -1F));

        spectatorSpawns.add(new SerializedLocation(-26, 80, 33, 314F, 10F));
        spectatorSpawns.add(new SerializedLocation(-40.5, 80, 19.5, 313.6F, -2.5F));
        spectatorSpawns.add(new SerializedLocation(-26, 80, 19, 255F, 6F));
        spectatorSpawns.add(new SerializedLocation(-40, 80, 33, 42.7F, 18.1F));
    }

    protected void applyInventory(WFP target) {
        PlayerInventory inv = target.getInventory();

        ItemStack IRON_BOOTS = new ItemStack(Material.IRON_BOOTS, 1);
        IRON_BOOTS.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.getCurrentTeam()));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.getCurrentTeam()));
        inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
        inv.setBoots(new ItemStack(Material.IRON_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.STONE_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.FISHING_ROD, 1, (short) 50));
        inv.setItem(3, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(4, new ItemStack(Material.POTION, 1, (short) 8229));
        inv.setItem(8, InvUtil.setName(new ItemStack(Material.FEATHER, 1), ChatColor.BLUE + "Effect Feather"));
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
