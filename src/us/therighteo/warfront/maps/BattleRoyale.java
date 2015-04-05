package us.therighteo.warfront.maps;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import us.therighteo.warfront.WFP;
import us.therighteo.warfront.WarFront;
import us.therighteo.warfront.gamemode.GamemodeType;
import us.therighteo.warfront.gamemode.Map;
import us.therighteo.warfront.gamemode.util.SerializedLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class BattleRoyale extends Map {

    ArrayList<String> selecting = new ArrayList<String>();
    ArrayList<Material> colors = new ArrayList<Material>();

    {
        colors.add(Material.GOLD_BLOCK);
        colors.add(Material.REDSTONE_BLOCK);
        colors.add(Material.EMERALD_BLOCK);
        colors.add(Material.PORTAL);
        colors.add(Material.LAPIS_BLOCK);
        colors.add(Material.DIAMOND_BLOCK);
        colors.add(Material.ICE);
    }

    ArrayList<Color> randomc = new ArrayList<Color>();

    {
        randomc.add(Color.GREEN);
        randomc.add(Color.BLUE);
        randomc.add(Color.YELLOW);
        randomc.add(Color.RED);
        randomc.add(Color.ORANGE);
        randomc.add(Color.PURPLE);
        randomc.add(Color.FUCHSIA);
    }

    HashMap<String, Integer> aura = new HashMap<String, Integer>();

    protected void readyAttributes() {
        setMapName("Battle Royale");
        setCreators(new String[]{"S4Y", "AdvancedDesire", "toxic_man11", "ep1cn00bt00b"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.TDM});
        setAllowBuild(false, false);
        setTimeLockTime(14500L);
        defineTeam1("Purple Team", ChatColor.DARK_PURPLE);
        defineTeam2("Cyan Team", ChatColor.DARK_AQUA);
        setDisabledDrops(Material.values());
    }

    protected void readySpawns() {
        team1Spawns.add(new SerializedLocation(-30, 85, 25, 180F, 0F));
        team1Spawns.add(new SerializedLocation(-31, 85, 25, 180F, 0F));
        team2Spawns.add(new SerializedLocation(-30, 85, -25, 0F, 0F));
        team2Spawns.add(new SerializedLocation(-31, 85, -25, 0F, 0F));
        spectatorSpawns.add(new SerializedLocation(-57, 89, 0, 270F, -10F));
        spectatorSpawns.add(new SerializedLocation(-4, 89, 0, 90F, -10F));
    }

    protected void applyInventory(WFP target) {
        target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 8 * 20, 5));
        target.getInventory().addItem(new ItemStack(Material.WOOD_SWORD));
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();
            aura.remove(player.getName());
            if (selecting.contains(player.getName())) {
                player.sendMessage(ChatColor.RED + "You are already selecting a class, please wait!");
                return;
            }
            Block block = event.getClickedBlock();

            if (block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
                BlockState state = block.getState();
                Sign sign = (Sign) state;
                // Sign options
                if (sign.getLine(1).contains("Scout")) {
                    handKit(player, Group.SCOUT);
                }
                if (sign.getLine(1).contains("Mercenary")) {
                    handKit(player, Group.MERCENARY);
                    return;
                }
                if (sign.getLine(1).contains("Sniper")) {
                    handKit(player, Group.SNIPER);
                    return;
                }
                if (sign.getLine(1).contains("Cleric")) {
                    handKit(player, Group.CLERIC);
                    return;
                }
                if (sign.getLine(1).contains("Great Knight")) {
                    handKit(player, Group.GREAT_KNIGHT);
                    return;
                }
                if (sign.getLine(1).contains("Spy")) {
                    handKit(player, Group.SPY);
                    return;
                }
                if (sign.getLine(1).contains("Rainbow Dasher")) {
                    handKit(player, Group.RAINBOW_DASHER);
                    return;
                }

                if (sign.getLine(1).contains("Potion Master") || sign.getLine(1).contains("Senseless"))
                    if (WarFront.getInstance().hasPermission(player, "1")) {
                        if (sign.getLine(1).contains("Potion Master")) {
                            handKit(player, Group.POTION_MASTER);
                        }
                        if (sign.getLine(1).contains("Senseless")) {
                            handKit(player, Group.SENSELESS);
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "That's a donator class!");
                    }
            }
        } catch (NullPointerException ex) {
            // Catches null ClickedBlock
        }
    }

    private void handKit(final Player player, final Group group) {
        selecting.add(player.getName());
        player.sendMessage(ChatColor.GREEN + "You have chosen " + ChatColor.AQUA + group.toString().toLowerCase().replace("_", " ") + ChatColor.GREEN + " as your class!");

        player.getInventory().clear();
        player.updateInventory();
        player.getInventory().setHelmet(new ItemStack(Material.AIR, 0));
        player.getInventory().setChestplate(new ItemStack(Material.AIR, 0));
        player.getInventory().setLeggings(new ItemStack(Material.AIR, 0));
        player.getInventory().setBoots(new ItemStack(Material.AIR, 0));
        player.updateInventory();

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        final WFP wfp = WFP.getWFP(player);

        Bukkit.getScheduler().runTaskLater(WarFront.getInstance(), new Runnable() {
            public void run() {
                switch (group) {
                    case SPY:
                        ItemStack SPY_WATCH = new ItemStack(Material.WATCH, 5);
                        ItemMeta spywatchMeta = SPY_WATCH.getItemMeta();
                        spywatchMeta.setDisplayName(ChatColor.BLUE + "Spy Watch");
                        List<String> spyLore = new ArrayList<String>();
                        spyLore.add(org.bukkit.ChatColor.BLUE + "Interact with this watch to go temporarily invisible!");
                        spywatchMeta.setLore(spyLore);
                        SPY_WATCH.setItemMeta(spywatchMeta);

                        ItemStack KB = new ItemStack(Material.DIAMOND_SWORD, 1, (short) 2000);
                        KB.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);

                        ItemMeta kill = KB.getItemMeta();
                        kill.setDisplayName(ChatColor.BLUE + "" + ChatColor.ITALIC + "Silencer");
                        KB.setItemMeta(kill);

                        player.getInventory().setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), Color.BLACK));
                        player.getInventory().setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), wfp.getCurrentTeam()));
                        player.getInventory().setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), Color.BLACK));
                        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));

                        player.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD, 1));
                        player.getInventory().setItem(2, SPY_WATCH);
                        player.getInventory().setItem(1, new ItemStack(Material.COOKED_BEEF, 3));
                        player.getInventory().setItem(8, KB);
                        break;

                    case MERCENARY:
                        player.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET, 1));
                        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
                        player.getInventory().setLeggings(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), wfp.getCurrentTeam()));
                        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));

                        player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD, 1));
                        player.getInventory().setItem(1, new ItemStack(Material.COOKED_BEEF, 3));
                        break;

                    case GREAT_KNIGHT:
                        ItemStack BOW = new ItemStack(Material.BOW, 1);

                        BOW.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
                        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
                        player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
                        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
                        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));

                        player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD, 1, (short) -200));
                        player.getInventory().setItem(1, BOW);
                        player.getInventory().setItem(2, new ItemStack(Material.COOKED_BEEF, 3));
                        player.getInventory().setItem(9, new ItemStack(Material.ARROW, 3));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20000 * 20, 1));
                        break;

                    case SNIPER:
                        player.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS, 1));
                        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
                        player.getInventory().setChestplate(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), wfp.getCurrentTeam()));
                        player.getInventory().setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), wfp.getCurrentTeam()));

                        ItemStack AMMO = new ItemStack(Material.FLINT, 16);
                        ItemStack BLAZE_ROD = new ItemStack(Material.BLAZE_ROD, 1);
                        ItemStack SBOW = new ItemStack(Material.BOW, 1);

                        SBOW.addEnchantment(Enchantment.ARROW_DAMAGE, 1);

                        ItemMeta blaze_rod = BLAZE_ROD.getItemMeta();
                        blaze_rod.setDisplayName(ChatColor.BLUE + "Musket");
                        BLAZE_ROD.setItemMeta(blaze_rod);

                        ItemMeta ammo = AMMO.getItemMeta();
                        ammo.setDisplayName(ChatColor.BLUE + "Round");
                        AMMO.setItemMeta(ammo);

                        player.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD, 1));
                        player.getInventory().setItem(1, SBOW);
                        player.getInventory().setItem(2, new ItemStack(Material.COOKED_BEEF, 3));
                        player.getInventory().setItem(3, BLAZE_ROD);
                        player.getInventory().setItem(9, new ItemStack(Material.ARROW, 64));
                        player.getInventory().setItem(10, AMMO);
                        break;

                    case CLERIC:
                        player.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET, 1));
                        player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
                        player.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS, 1));
                        player.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS, 1));
                        player.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD, 1));
                        player.getInventory().setItem(1, new ItemStack(Material.POTION, 48, (short) 16437));
                        player.getInventory().setItem(2, new ItemStack(Material.COOKED_BEEF, 3));
                        break;
                    case SCOUT:
                        player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
                        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));

                        player.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD, 1));
                        player.getInventory().setItem(1, new ItemStack(Material.GOLDEN_APPLE, 2));

                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20000 * 20, 1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20000 * 20, 2));
                        break;
                    case SENSELESS:
                        ItemStack AURAITEM = new ItemStack(Material.PRISMARINE_SHARD, 1);
                        ItemMeta AURA = AURAITEM.getItemMeta();
                        AURA.setDisplayName(ChatColor.BLUE + "Aura Shard (spam sneak!)");
                        AURAITEM.setItemMeta(AURA);

                        player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
                        player.getInventory().setHelmet(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), wfp.getCurrentTeam()));
                        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
                        player.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));

                        player.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD, 1));
                        player.getInventory().setItem(1, new ItemStack(Material.GOLDEN_APPLE, 5));

                        player.getInventory().setItem(8, AURAITEM);

                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20000 * 20, 1));
                        break;
                    case RAINBOW_DASHER:
                        player.sendMessage(ChatColor.GREEN + "You are awesome!");

                        ItemStack rainbowKB = new ItemStack(Material.NETHER_STAR, 1);

                        ItemMeta rainbowAmmo = rainbowKB.getItemMeta();
                        rainbowAmmo.setDisplayName(ChatColor.BLUE + "Royale's Rainbow Star");
                        rainbowKB.setItemMeta(rainbowAmmo);

                        ItemStack LEATHER_HELMET = new ItemStack(Material.LEATHER_HELMET, 1);
                        ItemStack RAINBOW_CHESTPLATE = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
                        ItemStack LEATHER_LEGGINGS = new ItemStack(Material.LEATHER_LEGGINGS, 1);
                        ItemStack LEATHER_BOOTS = new ItemStack(Material.LEATHER_BOOTS, 1);


                        player.getInventory().setHelmet(LEATHER_HELMET);
                        player.getInventory().setChestplate(RAINBOW_CHESTPLATE);
                        player.getInventory().setLeggings(LEATHER_LEGGINGS);
                        player.getInventory().setBoots(LEATHER_BOOTS);

                        player.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD, 1));
                        player.getInventory().setItem(1, new ItemStack(Material.COOKED_BEEF, 3));
                        player.getInventory().setItem(8, rainbowKB);
                        break;
                    case POTION_MASTER:
                        player.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET, 1));
                        player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
                        player.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS, 1));
                        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS, 1));
                        player.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD, 1));
                        player.getInventory().setItem(2, new ItemStack(Material.POTION, 12, (short) 16437));
                        player.getInventory().setItem(3, new ItemStack(Material.POTION, 12, (short) 16433));
                        player.getInventory().setItem(4, new ItemStack(Material.POTION, 12, (short) 16440));
                        player.getInventory().setItem(5, new ItemStack(Material.POTION, 6, (short) 16428));
                        player.getInventory().setItem(1, new ItemStack(Material.COOKED_BEEF, 3));
                        break;
                }
                selecting.remove(player.getName());
            }
        }, 30L);
    }

    @EventHandler
    public void gun(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        Action action = event.getAction();
        ItemStack itemStack = player.getItemInHand();
        Inventory inventory = player.getInventory();
        Material tool = itemStack.getType();
        final World world = location.getWorld();

        if (tool == Material.BLAZE_ROD) {

            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {

                if (inventory.contains(Material.FLINT)) {
                    player.launchProjectile(Arrow.class);
                    world.playSound(location, Sound.COW_WALK, 10, 10);
                    ItemStack AMMO = new ItemStack(Material.FLINT, 1);
                    inventory.removeItem(AMMO);

                    ItemMeta ammo = AMMO.getItemMeta();
                    ammo.setDisplayName(ChatColor.BLUE + "Round");
                    AMMO.setItemMeta(ammo);
                    inventory.removeItem(AMMO);

                    // Make it remove normal flints, too.
                    player.updateInventory();
                } else {
                    world.playSound(location, Sound.CLICK, 10, 10);
                }

            }

        }
    }

    @EventHandler
    public void aura(PlayerToggleSneakEvent event) {
        if (event.getPlayer().getItemInHand().getType() == Material.PRISMARINE_SHARD && event.getPlayer().isSneaking()) {
            if (aura.containsKey(event.getPlayer().getName()))
                aura.put(event.getPlayer().getName(), (aura.get(event.getPlayer().getName()) + 1));
            else
                aura.put(event.getPlayer().getName(), 1);
            int total = aura.get(event.getPlayer().getName());
            if (total > 100) return;
            if (total % 10 == 0 && total < 100)
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENDERDRAGON_HIT, 1F, 1F);
            if (total < 100)
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENDERDRAGON_HIT, 1F, 1F);
            if (total == 10)
                event.getPlayer().sendMessage(ChatColor.GRAY + "Aura level:" + ChatColor.RED + " weak");
            else if (total == 25)
                event.getPlayer().sendMessage(ChatColor.GRAY + "Aura level:" + ChatColor.GOLD + " average");
            else if (total == 50)
                event.getPlayer().sendMessage(ChatColor.GRAY + "Aura level:" + ChatColor.YELLOW + " strong");
            else if (total == 100) {
                event.getPlayer().sendMessage(ChatColor.GRAY + "Aura level:" + ChatColor.GREEN + " full power");
                event.getPlayer().sendMessage(ChatColor.GRAY + "Right click now for a reckless strike!");
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.HORSE_SKELETON_DEATH, 1F, 1F);
            }
        }
    }

    @EventHandler
    public void auraStrike(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            if (player.getItemInHand().getType() != Material.PRISMARINE_SHARD) return;
            if (!aura.containsKey(player.getName()))
                aura.put(player.getName(), 0);
            int power = aura.get(player.getName());
            if (power >= 100) {
                for (int i = 0; i < 15; i++) {
                    double x = Math.random() / 0.9;
                    double z = Math.random() / 0.9;

                    //Half chance of making it minus; locations go into the minuses too, you know?
                    if (Math.random() >= 0.5) x = x - (x * 2);
                    if (Math.random() >= 0.5) z = z - (z * 2);
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.COW_WALK, 5L, 5L);
                    Snowball a = (Snowball) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation().add(0, (Math.random()) + 1, 0), EntityType.SNOWBALL);
                    a.setShooter(event.getPlayer());
                    a.setVelocity(new Vector(x, Math.random(), z));
                }
            } else {
                event.getPlayer().sendMessage(ChatColor.RED + "Your strike failed because your power wasn't full enough!");
            }
            aura.put(player.getName(), 0);
        }
    }

    @EventHandler
    public void lght(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball)
            event.getEntity().getWorld().strikeLightning(event.getEntity().getLocation());
    }

    @EventHandler
    public void onSpyWatchInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (player.getItemInHand().getType() == Material.WATCH) {

                ItemStack SPY_WATCH = new ItemStack(Material.WATCH, 1);
                ItemMeta spyWatchMeta = SPY_WATCH.getItemMeta();
                spyWatchMeta.setDisplayName(ChatColor.BLUE + "Spy Watch");

                List<String> spyLore = new ArrayList<String>();
                spyLore.add(org.bukkit.ChatColor.BLUE + "Interact with this watch to go temporarily invisible!");
                spyWatchMeta.setLore(spyLore);
                SPY_WATCH.setItemMeta(spyWatchMeta);

                player.getInventory().removeItem(SPY_WATCH);
                player.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
                player.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
                player.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
                player.getInventory().setBoots(new ItemStack(Material.AIR, 1));
                player.getInventory().removeItem(new ItemStack(Material.LEATHER_BOOTS, 1));
                player.getInventory().removeItem(new ItemStack(Material.LEATHER_LEGGINGS, 1));
                player.getInventory().removeItem(new ItemStack(Material.LEATHER_HELMET, 1));
                player.getInventory().removeItem(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
                player.getInventory().addItem(colorArmor(new ItemStack(Material.LEATHER_HELMET, 1), Color.BLACK));
                player.getInventory().addItem(colorArmor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), WFP.getWFP(player).getCurrentTeam()));
                player.getInventory().addItem(colorArmor(new ItemStack(Material.LEATHER_LEGGINGS, 1), Color.BLACK));
                player.getInventory().addItem(new ItemStack(Material.DIAMOND_BOOTS, 1));

                player.updateInventory();
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 15 * 20, 0));
            }
        }
    }

    @EventHandler
    public void PotionsSplash(PotionSplashEvent event) {
        event.setCancelled(true);
        for (LivingEntity target : event.getAffectedEntities()) {
            ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>(event.getPotion().getEffects());
            if (event.getEntity().getShooter().equals(target)) {
                Player player = (Player) target;
                if (effects.get(0).toString().startsWith("HEAL")) {
                    player.sendMessage(ChatColor.RED + "You cannot heal yourself!");
                } else {
                    target.addPotionEffect(event.getPotion().getEffects().iterator().next());
                }
            } else {
                target.addPotionEffect(event.getPotion().getEffects().iterator().next());
            }
        }
    }

    @EventHandler
    public void arrowAway(org.bukkit.event.entity.ProjectileHitEvent event) {
        org.bukkit.entity.Entity projectile = event.getEntity();
        if (projectile instanceof Arrow) {
            Arrow arrow = (Arrow) projectile;
            arrow.remove();
        }
    }

    @EventHandler
    public void explode(EntityExplodeEvent event) {
        event.blockList().clear();
    }

    @EventHandler
    public void rainbow(final PlayerMoveEvent event) {
        if (event.getPlayer().getItemInHand().getType() == Material.NETHER_STAR && event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            Bukkit.getScheduler().runTaskLater(WarFront.getInstance(), new Runnable() {
                public void run() {
                    if (event.getFrom().distanceSquared(event.getTo()) == 0) return;
                    Location location = event.getPlayer().getLocation();
                    location.getWorld().playEffect(location, Effect.STEP_SOUND, colors.get(new Random().nextInt(colors.size())));
                    event.getPlayer().getInventory().setHelmet(colorArmor(event.getPlayer().getInventory().getHelmet(), randomc.get(new Random().nextInt(randomc.size()))));
                    event.getPlayer().getInventory().setChestplate(colorArmor(event.getPlayer().getInventory().getChestplate(), randomc.get(new Random().nextInt(randomc.size()))));
                    event.getPlayer().getInventory().setLeggings(colorArmor(event.getPlayer().getInventory().getLeggings(), randomc.get(new Random().nextInt(randomc.size()))));
                    event.getPlayer().getInventory().setBoots(colorArmor(event.getPlayer().getInventory().getBoots(), randomc.get(new Random().nextInt(randomc.size()))));
                }
            }, 10L);
        }
    }

    private ItemStack colorArmor(ItemStack itemStack, Color color) {
        try {
            LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
            meta.setColor(color);
            itemStack.setItemMeta(meta);
        } catch (Exception ignored) {
            //if dead
        }
        return itemStack;
    }

    @EventHandler
    public void drop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    public enum Group {
        CLERIC,
        SNIPER,
        SCOUT,
        SPY,
        SENSELESS,
        POTION_MASTER,
        GREAT_KNIGHT,
        MERCENARY,
        RAINBOW_DASHER
    }
}