package us.therighteo.warfront.maps;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Egg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.BlockIterator;
import us.therighteo.warfront.WFP;
import us.therighteo.warfront.gamemode.GamemodeType;
import us.therighteo.warfront.gamemode.Map;
import us.therighteo.warfront.gamemode.util.ProtectedCuboid;
import us.therighteo.warfront.gamemode.util.SerializedLocation;
import us.therighteo.warfront.util.InvUtil;

public class ExoticPastures extends Map {

    public void readyAttributes() {
        setMapName("Exotic Pastures");
        setCreators(new String[]{"DanShrdr", "doritopig", "S4Y", "Squidildo"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.TDM});
        setDefaultDisabledDrops();
        setAllowBuild(true, true);
        setTimeLockTime(15000L);
        defineTeam1("Farmhands", ChatColor.GOLD);
        defineTeam2("Ranchers", ChatColor.DARK_GREEN);
    }

    protected void readySpawns() {
        team2Spawns.add(new SerializedLocation(-48, 134, -45, 90F, 0F));
        team1Spawns.add(new SerializedLocation(46, 134, -1, 270F, 0F));

        spectatorSpawns.add(new SerializedLocation(-39, 130, -47, 300F, -6F));
        spectatorSpawns.add(new SerializedLocation(37, 130, 3, 118, 118F - 11F));

        protectedCuboids.add(new ProtectedCuboid(50, 133, -5, 42, 143, 3, "TEAM_1"));
        protectedCuboids.add(new ProtectedCuboid(-53, 133, -41, -44, 141, -49, "TEAM_2"));
    }

    protected void applyInventory(WFP target) {
        PlayerInventory inv = target.getInventory();

        inv.setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), target.getCurrentTeam()));
        inv.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE, 1));
        inv.setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), target.getCurrentTeam()));
        inv.setBoots(new ItemStack(Material.IRON_BOOTS, 1));

        ItemStack HOE = new ItemStack(Material.GOLD_HOE, 1);
        HOE.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);

        ItemStack ROTOTILL = new ItemStack(Material.EGG, 5);
        ROTOTILL = InvUtil.setName(ROTOTILL, ChatColor.BLUE + "Insta-Rototill");

        inv.setItem(0, HOE);
        inv.setItem(1, new ItemStack(Material.BOW, 1));
        inv.setItem(3, new ItemStack(Material.STONE_AXE, 1));
        inv.setItem(4, new ItemStack(Material.BREAD, 6));
        inv.setItem(5, new ItemStack(Material.GOLDEN_APPLE, 2));
        inv.setItem(6, new ItemStack(Material.LOG, 16));
        inv.setItem(7, ROTOTILL);
        inv.setItem(10, new ItemStack(Material.ARROW, 32));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Location equiv = event.getBlock().getLocation().clone();
        equiv.setY(77);
        if (equiv.getBlock().getType() != Material.BEDROCK) event.setCancelled(true);
    }

    @EventHandler
    public void rototill(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Egg)) return;
        BlockIterator iterator = new BlockIterator(event.getEntity().getWorld(), event.getEntity().getLocation().toVector(), event.getEntity().getVelocity().normalize(), 0.0D, 4);
        Block hitBlock = null;
        while (iterator.hasNext()) {
            hitBlock = iterator.next();

            if (hitBlock.getTypeId() != 0) {
                break;
            }
        }

        assert hitBlock != null;
        if (hitBlock.getType() == Material.GRASS) {
            if (hitBlock.getRelative(BlockFace.UP).getType() == Material.AIR) {
                hitBlock.getWorld().playEffect(hitBlock.getLocation(), Effect.STEP_SOUND, hitBlock.getTypeId());
                hitBlock.setType(Material.SOIL);
                hitBlock.setData((byte) 1);
                hitBlock.getRelative(BlockFace.UP).setType(Material.CROPS);
                hitBlock.getRelative(BlockFace.UP).setData((byte) 7);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Location equiv = event.getBlock().getLocation().clone();
        equiv.setY(77);
        if (equiv.getBlock().getType() != Material.BEDROCK) event.setCancelled(true);
    }
}
