package us.therighteo.warfront.maps;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
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

public class FairwickVillage extends Map {

    public void readyAttributes() {
        setMapName("Fairwick Village");
        setCreators(new String[]{"S4Y", "ninsai", "ep1cn00bt00b"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.CTF, GamemodeType.LTS});
        setDefaultDisabledDrops();
        setAllowPlace(false);
        setTimeLockTime(14000L);
        defineTeam1("Red Team", ChatColor.RED);
        defineTeam2("Blue Team", ChatColor.BLUE);
    }

    protected void readySpawns() {
        team1Spawns.add(new SerializedLocation(108.5, 73, 26.5, 0.76F, -1F));
        team1Spawns.add(new SerializedLocation(36.5, 73, 26.5, 0.76F, -1F));

        team2Spawns.add(new SerializedLocation(36.5, 73, 107.5, 180F, -0.767F));
        team2Spawns.add(new SerializedLocation(108.5, 73, 107.5, 180F, -0.767F));

        spectatorSpawns.add(new SerializedLocation(74.5, 78, 65.5, -135.7F, -1.7F));
        spectatorSpawns.add(new SerializedLocation(74.5, 78, 69.5, -44.9F, -2.3F));
        spectatorSpawns.add(new SerializedLocation(70.5, 78, 69.5, 45.4F, -2.3F));
        spectatorSpawns.add(new SerializedLocation(70.5, 78, 65.5, 134.8F, -2F));

        setTeam1Flag(new SerializedLocation(72, 74, -2));
        setTeam2Flag(new SerializedLocation(72, 74, 136));
    }

    protected void applyInventory(WFP target) {
        PlayerInventory inv = target.getInventory();

        ItemStack GADGET = InvUtil.setName(new ItemStack(Material.WATCH, 1), ChatColor.BLUE + "Invisi-gadget");

        inv.setHelmet(new ItemStack(Material.IRON_HELMET, 1));
        inv.setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), target.getCurrentTeam()));
        inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
        inv.setBoots(new ItemStack(Material.IRON_BOOTS, 1));

        inv.setItem(0, new ItemStack(Material.IRON_SWORD, 1));
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(2, new ItemStack(Material.COOKED_BEEF, 6));
        inv.setItem(3, new ItemStack(Material.GOLDEN_APPLE, 1));
        inv.setItem(9, new ItemStack(Material.ARROW, 32));
        inv.setItem(8, GADGET);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        Material brokenMat = event.getBlock().getType();
        if (brokenMat != Material.STAINED_GLASS_PANE) event.setCancelled(true);
    }

    @EventHandler
    public void onSpyWatchInteract(PlayerInteractEvent event) {
        WFP p = WFP.getWFP(event.getPlayer());
        Action ac = event.getAction();
        if (ac == Action.RIGHT_CLICK_AIR || ac == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand().getType() == Material.WATCH) {
                p.getInventory().remove(p.getItemInHand());
                p.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
                p.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
                p.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
                p.getInventory().setBoots(new ItemStack(Material.AIR, 1));
                p.getInventory().remove(new ItemStack(Material.IRON_BOOTS));
                p.getInventory().remove(new ItemStack(Material.IRON_LEGGINGS));
                p.getInventory().remove(new ItemStack(Material.IRON_HELMET));
                ItemStack LEATHER_CHESTPLATE = colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), p.getCurrentTeam());
                p.getInventory().remove(LEATHER_CHESTPLATE);
                p.getInventory().addItem(new ItemStack(Material.IRON_BOOTS));
                p.getInventory().addItem(new ItemStack(Material.IRON_LEGGINGS));
                p.getInventory().addItem(new ItemStack(Material.IRON_HELMET));
                p.getInventory().addItem(LEATHER_CHESTPLATE);
                p.updateInventory();
                p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 15 * 20, 0));
            }
        }
    }
}
