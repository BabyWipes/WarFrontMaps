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
import us.therighteo.warfront.gamemode.util.SerializedLocation;
import us.therighteo.warfront.util.InvUtil;

public class TheMantle extends Map {

    public void readyAttributes() {
        setMapName("The Mantle");
        setCreators(new String[]{"S4Y", "eli12310987", "chillhill3", "MiCkEyMiCE", "FaazM"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.CTF, GamemodeType.OUTBREAK});
        setDefaultDisabledDrops();
        setAllowBuild(false, false);
        setMobSpawning(false);
        defineTeam1("Red Team", ChatColor.RED);
        defineTeam2("Orange Team", ChatColor.GOLD);
    }

    protected void readySpawns() {
        team1Spawns.add(new SerializedLocation(-59, 84, 126, 270F, 1.1F));
        team1Spawns.add(new SerializedLocation(-6, 84, 129, 90F, 2F));

        team2Spawns.add(new SerializedLocation(-11, 84, 12, 90.1F, -0.4F));
        team2Spawns.add(new SerializedLocation(-64, 84, 9, 270F, 0.6F));

        spectatorSpawns.add(new SerializedLocation(-37, 85, 79, 189F, 2.7F));
        spectatorSpawns.add(new SerializedLocation(-33, 85, 59, 11F, 0.6F));

        setTeam1Flag(new SerializedLocation(-35, 86, 121));
        setTeam2Flag(new SerializedLocation(-35, 86, 17));
    }

    protected void applyInventory(WFP target) {
        PlayerInventory inv = target.getInventory();

        ItemStack GADGET = InvUtil.setLore(new ItemStack(Material.INK_SACK, 1, (byte) 14), ChatColor.BLUE + "Haste",
                ChatColor.BLUE + "Punch the flag off its podium like a beach ball on spring break!");

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.getCurrentTeam()));
        inv.setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
        inv.setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), target.getCurrentTeam()));
        inv.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.IRON_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(3, new ItemStack(Material.GOLDEN_APPLE, 1));
        inv.setItem(8, GADGET);
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onDyeInteract(PlayerInteractEvent event) {
        WFP p = WFP.getWFP(event.getPlayer());
        Action ac = event.getAction();
        if (ac == Action.RIGHT_CLICK_AIR || ac == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand().getType() == Material.INK_SACK) {
                p.getInventory().remove(p.getItemInHand());
                p.sendMessage(ChatColor.GOLD + "You suddenly feel a great strength...");
                p.updateInventory();
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 10 * 20, 9));
            }
        }
    }
}
