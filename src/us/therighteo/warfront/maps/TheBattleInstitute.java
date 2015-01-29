package us.therighteo.warfront.maps;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;
import us.therighteo.warfront.RoundHelper;
import us.therighteo.warfront.Team;
import us.therighteo.warfront.WFP;
import us.therighteo.warfront.WarFront;
import us.therighteo.warfront.gamemode.GamemodeType;
import us.therighteo.warfront.gamemode.Map;
import us.therighteo.warfront.gamemode.util.SerializedLocation;

import java.util.ArrayList;
import java.util.Random;

public class TheBattleInstitute extends Map {

    public void readyAttributes() {
        setMapName("The Battle Institute");
        setCreators(new String[]{"S4Y", "dashhhb"});
        setGamemodeTypes(new GamemodeType[]{GamemodeType.LTS});
        setDefaultDisabledDrops();
        setAllowBuild(false, false);
        setTimeLockTime(14000L);
        defineTeam1("Magenta Team", ChatColor.DARK_PURPLE);
        defineTeam2("Cyan Team", ChatColor.DARK_AQUA);
    }

    protected void readySpawns() {
        team1Spawns.add(new SerializedLocation(0, 70, -22, 360F, 0F));

        team2Spawns.add(new SerializedLocation(0, 70, 22, 180F, 0F));

        spectatorSpawns.add(new SerializedLocation(11, 73, 17, 148F, -11.11F));
        spectatorSpawns.add(new SerializedLocation(-14, 73, -17, 317.5F, -2.1F));
    }

    protected void applyInventory(WFP target) {
        target.sendMessage(ChatColor.GOLD + "Welcome to The Battle Institute! Please wait until you are called to battle!");
    }

    ArrayList<String> t1 = new ArrayList<String>();
    ArrayList<String> t2 = new ArrayList<String>();

    String ct1 = "None";
    String ct2 = "None";

    boolean e1 = false;
    boolean e2 = false;

    @Override
    public void reset() {
        e1 = false;
        e2 = false;
        ct1 = "None";
        ct2 = "None";
        t1.clear();
        t2.clear();
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        t1.remove(event.getPlayer().getName());
        t2.remove(event.getPlayer().getName());
        Bukkit.broadcastMessage(ChatColor.GOLD + "[BattleInstitute] " + event.getPlayer().getName() + " disconnected!");
        if (ct1.equals(event.getPlayer().getName()) || ct2.equals(event.getPlayer().getName()))
            endRound();
    }

    @EventHandler
    public void death(PlayerDeathEvent event) {
        t1.remove(event.getEntity().getName());
        t2.remove(event.getEntity().getName());
        if (ct1.equals(event.getEntity().getName()) || ct2.equals(event.getEntity().getName()))
            endRound();
    }

    @Override
    public void postStart() {
        Bukkit.broadcastMessage(ChatColor.GOLD + "[BattleInstitute] Starting up!");
        Bukkit.getScheduler().runTaskLater(WarFront.getInstance(), new Runnable() {
            public void run() {
                if (!isActive()) {
                    return;
                }
                for (Player p : Bukkit.getOnlinePlayers()) {
                    try {
                        if (WFP.getWFP(p.getName()).getCurrentTeam() == Team.TEAM_1) {
                            t1.add(p.getName());
                        } else if (WFP.getWFP(p.getName()).getCurrentTeam() == Team.TEAM_2) {
                            t2.add(p.getName());
                        }
                    } catch (Exception ex) {
                        p.kickPlayer("Internal server error");
                        // Really bad ugly and inefficient temp fix
                    }
                }
                newRound();
            }
        }, 80L);
    }

    @EventHandler
    public void leaveSpec(final PlayerCommandPreprocessEvent event) {
        try {
            if (event.getMessage().toLowerCase().startsWith("/join")) {
                try {
                    if (!t1.contains(event.getPlayer().getName()) && !t2.contains(event.getPlayer().getName()) && RoundHelper.getCurrentWorld().getPlayers().size() != 0) {
                        if (RoundHelper.getCurrentWorld().getPlayers().size() == 0) return;
                        Bukkit.getScheduler().runTaskLater(WarFront.getInstance(), new Runnable() {
                            public void run() {
                                if (event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
                                    if (WFP.getWFP(event.getPlayer()).getCurrentTeam() == Team.TEAM_1) {
                                        if (!t1.contains(event.getPlayer().getName()))
                                            t1.add(event.getPlayer().getName());
                                        Bukkit.broadcastMessage(ChatColor.RED + event.getPlayer().getDisplayName() + getTeam1Color() + " joined the round!");
                                    } else if (WFP.getWFP(event.getPlayer()).getCurrentTeam() == Team.TEAM_2) {
                                        if (!t2.contains(event.getPlayer().getName()))
                                            t2.add(event.getPlayer().getName());
                                        Bukkit.broadcastMessage(ChatColor.RED + event.getPlayer().getDisplayName() + getTeam2Color() + " joined the round!");
                                    }
                                }
                            }
                        }, 2L);

                    }
                } catch (Exception exc) {
                    // TODO: This is inefficient, fix it.
                }
            }
            if (event.getMessage().toLowerCase().startsWith("/leave") || event.getMessage().toLowerCase().startsWith("/spectate")) {
                if (t1.contains(event.getPlayer().getName()) || t2.contains(event.getPlayer().getName())) {
                    Bukkit.broadcastMessage(ChatColor.RED + event.getPlayer().getName() + " left the round!!");
                    if (ct1.equals(event.getPlayer().getName()) || ct2.equals(event.getPlayer().getName()))
                        endRound();
                    t1.remove(event.getPlayer().getName());
                    t2.remove(event.getPlayer().getName());
                }
            }
        } catch (Exception ignored) {
        }
    }

    @EventHandler
    public void ironBlock(PlayerInteractEvent event) {
        try {
            if (event.getClickedBlock().getType() == Material.IRON_BLOCK) {
                join(event.getPlayer().getName());
            }
        } catch (NullPointerException ignored) {
        }
    }

    public void newRound() {
        String nt1 = t1.get(new Random().nextInt(t1.size()));
        String nt2 = t2.get(new Random().nextInt(t2.size()));
        e1 = false;
        e2 = false;
        Bukkit.broadcastMessage(ChatColor.GOLD + "[BattleInstitute] The players have been selected!");
        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + nt1);
        Bukkit.broadcastMessage(ChatColor.DARK_AQUA + nt2);
        ct1 = nt1;
        ct2 = nt2;
        Bukkit.getScheduler().runTaskLater(WarFront.getInstance(), new Runnable() {
            public void run() {
                if (!e1) join(ct1);
                if (!e2) join(ct2);
            }
        }, 5 * 20L);
    }

    private void endRound() {
        Bukkit.broadcastMessage(ChatColor.GOLD + "[BattleInstitute] Round over! Picking new players!");
        try {
            Bukkit.getPlayer(ct1).teleport(new Location(RoundHelper.getCurrentWorld(), 0, 70, -22, 360F, 0F));
            Bukkit.getPlayer(ct2).teleport(new Location(RoundHelper.getCurrentWorld(), 0, 70, 22, 180F, 0F));
        } catch (NullPointerException ignored) {
        }
        if (t1.size() == 0 || t2.size() == 0) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "[BattleInstitute] GAME OVER!");
            return;
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (WFP.getWFP(p).isPlaying() && !WFP.getWFP(p).isJoined()) {
                p.getInventory().clear();
                p.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
                p.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
                p.getInventory().setBoots(new ItemStack(Material.AIR, 1));
                p.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
                for (PotionEffect po : p.getActivePotionEffects()) {
                    p.removePotionEffect(po.getType());
                }
                p.setHealth(20);
                p.setFoodLevel(20);
                p.updateInventory();
            }
        }
        Bukkit.getScheduler().runTaskLater(WarFront.getInstance(), new Runnable() {
            public void run() {
                newRound();
            }
        }, 40L);
    }

    private void join(String player) {
        if (ct1.equals(player) && !e1) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "[BattleInstitute] " + getTeam1Color() + player + ChatColor.GOLD + " joins!");
            e1 = true;
            Player p = Bukkit.getPlayer(ct1);
            p.teleport(new Location(RoundHelper.getCurrentWorld(), 0, 70, -13, 0F, 0F));
            for (PotionEffect po : p.getActivePotionEffects()) {
                p.removePotionEffect(po.getType());
            }
            p.setHealth(20);
            p.setFoodLevel(20);
            handKit(p);
        }
        if (ct2.equals(player) && !e2) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "[BattleInstitute] " + getTeam2Color() + player + ChatColor.GOLD + " joins!");
            e2 = true;
            Player p = Bukkit.getPlayer(ct2);
            p.teleport(new Location(RoundHelper.getCurrentWorld(), 0, 70, 13, 180F, 0F));
            for (PotionEffect po : p.getActivePotionEffects()) {
                p.removePotionEffect(po.getType());
            }
            p.setHealth(20);
            p.setFoodLevel(20);
            handKit(p);
        }
    }

    private void handKit(Player p) {
        PlayerInventory i = p.getInventory();
        ItemStack HEALTH_POTION = new ItemStack(Material.POTION, 1, (short) 16373);
        ItemStack BOW = new ItemStack(Material.BOW, 1);
        ItemStack ARROWS = new ItemStack(Material.ARROW, 12);
        ItemStack IRON_HELMET = new ItemStack(Material.CHAINMAIL_HELMET, 1);
        ItemStack IRON_CHESTPLATE = new ItemStack(Material.GOLD_CHESTPLATE, 1);
        ItemStack IRON_PANTS = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemStack IRON_BOOTS = new ItemStack(Material.DIAMOND_BOOTS, 1);
        ItemStack IRON_SWORD = new ItemStack(Material.IRON_SWORD, 1);

        p.getInventory().setBoots(IRON_BOOTS);
        p.getInventory().setLeggings(IRON_PANTS);
        p.getInventory().setChestplate(IRON_CHESTPLATE);
        p.getInventory().setHelmet(IRON_HELMET);

        i.setItem(0, IRON_SWORD);
        i.setItem(1, BOW);
        i.setItem(2, HEALTH_POTION);
        i.setItem(3, HEALTH_POTION);
        i.setItem(9, ARROWS);
        p.updateInventory();
    }

    @EventHandler
    public void worldLoad(FoodLevelChangeEvent event) {
        event.setFoodLevel(20);
    }

    @EventHandler
    public void damage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            String p = event.getEntity().getName();
            if (!ct1.equals(p) && !ct2.equals(p)) event.setCancelled(true);
        }
    }

    @EventHandler
    public void move(PlayerMoveEvent event) {
        if (event.getTo().getY() >= 73 && event.getPlayer().getGameMode() == GameMode.SURVIVAL)
            event.getPlayer().setVelocity(new Vector(0, -0.1, 0));
    }
}
