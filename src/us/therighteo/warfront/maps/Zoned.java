package us.therighteo.warfront.maps;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import us.therighteo.warfront.WFP;
import us.therighteo.warfront.gamemode.GamemodeType;
import us.therighteo.warfront.gamemode.Map;
import us.therighteo.warfront.gamemode.util.SerializedLocation;
import us.therighteo.warfront.util.InvUtil;

public class Zoned extends Map {

    public void readyAttributes() {
        setMapName("Zoned");
        setCreators(new String[]{"S4Y", "MiCkEyMiCE", "AnomalousDyna"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.CTF, GamemodeType.OUTBREAK, GamemodeType.LTS});
        setDefaultDisabledDrops();
        setAllowBuild(false, false);
        setMobSpawning(false);
        setTimeLockTime(14000L);
        defineTeam1("Magenta Team", ChatColor.DARK_PURPLE);
        defineTeam2("Cyan Team", ChatColor.DARK_AQUA);
    }

    protected void readySpawns() {
        team1Spawns.add(new SerializedLocation(37, 93, 98, 120.4F, 0F));
        team1Spawns.add(new SerializedLocation(-43, 93, 99, 228.7F, 1.6F));

        team2Spawns.add(new SerializedLocation(-43, 93, -76, 316F, 1.8F));
        team2Spawns.add(new SerializedLocation(37, 93, -76, 46F, 0.6F));

        spectatorSpawns.add(new SerializedLocation(-3, 88, -26, 358.7F, 0.7F));
        spectatorSpawns.add(new SerializedLocation(-3, 88, 48, 180F, -0.1F));

        setTeam1Flag(new SerializedLocation(-3, 89, 78));
        setTeam2Flag(new SerializedLocation(-3, 89, -56));
    }

    protected void applyInventory(WFP target) {
        PlayerInventory inv = target.getInventory();

        ItemStack GADGET = InvUtil.setLore(new ItemStack(Material.EYE_OF_ENDER, 1), ChatColor.BLUE + "Eye of Vigor",
                ChatColor.BLUE + "Take a whiff and you'll be stealing flags like unchained bicyles!");

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.getCurrentTeam()));
        inv.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
        inv.setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), target.getCurrentTeam()));
        inv.setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.IRON_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(3, new ItemStack(Material.GOLDEN_APPLE, 1));
        inv.setItem(7, new ItemStack(Material.ENDER_PEARL, 3));
        inv.setItem(8, GADGET);
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        event.setTo(event.getTo().clone().add(0, 1, 0));
    }

    @EventHandler
    public void onDyeInteract(PlayerInteractEvent event) {
        WFP p = WFP.getWFP(event.getPlayer());
        Action ac = event.getAction();
        if (ac == Action.RIGHT_CLICK_AIR || ac == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand().getType() == Material.EYE_OF_ENDER) {
                p.getInventory().remove(p.getItemInHand());
                p.sendMessage(ChatColor.GOLD + "You suddenly feel a great rush through your body...");
                p.updateInventory();
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 10 * 20, 9));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10 * 20, 2));
            }
        }
    }
}
