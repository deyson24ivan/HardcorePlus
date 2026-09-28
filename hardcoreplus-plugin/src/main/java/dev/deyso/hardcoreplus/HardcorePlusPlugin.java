package dev.deyso.hardcoreplus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public final class HardcorePlusPlugin extends JavaPlugin implements Listener {
    private static final String SHOP_TITLE = ChatColor.DARK_RED + "" + ChatColor.BOLD + "HardcorePlus Tienda";
    private static final String BUY_TITLE = ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Tienda - Comprar";
    private static final String SELL_TITLE = ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Tienda - Vender";
    private static final String PROFILE_TITLE = ChatColor.GOLD + "" + ChatColor.BOLD + "Perfil HardcorePlus";
    private static final String WAYSTONE_TITLE = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Waystones";
    private static final String POINTS_OBJECTIVE = "hp.points";
    private static final long ANGEL_GRACE_COOLDOWN_MS = 420_000L;
    private static final double ANGEL_HELMET_ARMOR = 4.0;
    private static final double ANGEL_CHESTPLATE_ARMOR = 9.0;
    private static final double ANGEL_LEGGINGS_ARMOR = 7.0;
    private static final double ANGEL_BOOTS_ARMOR = 4.0;
    private static final double NETHERITE_TOUGHNESS = 3.0;
    private static final double ANGEL_TOUGHNESS = 4.0;
    private static final double NETHERITE_KNOCKBACK_RESISTANCE = 0.1;
    private static final int MAX_WAYSTONES = 5;
    private static final long TITANIUM_COOLDOWN_MS = 30_000L;
    private static final long LEVIATHAN_COOLDOWN_MS = 300_000L;
    private static final long PHOENIX_COOLDOWN_MS = 900_000L;
    private static final long DRAGON_COOLDOWN_MS = 60_000L;
    private static final long VOID_COOLDOWN_MS = 600_000L;
    private static final long COLOSSUS_COOLDOWN_MS = 120_000L;
    private static final long ECLIPSE_COOLDOWN_MS = 180_000L;
    private static final long CHAOS_COOLDOWN_MS = 300_000L;
    private static final long TIME_COOLDOWN_MS = 480_000L;
    private static final long CELESTIAL_COOLDOWN_MS = 720_000L;
    private static final long INFINITY_COOLDOWN_MS = 1_200_000L;
    private static final double TITANIUM_MINING_CHANCE = 0.05;
    private static final double SEA_ESSENCE_FISHING_CHANCE = 0.02;
    private static final long HEAD_HUNTERS_DURATION_MS = 600_000L;
    private static final long MAZE_DURATION_MS = 600_000L;
    private static final long BOAT_RACE_DURATION_MS = 600_000L;
    private static final List<List<String>> ARMOR_PROGRESSION_PHASES = List.of(
            List.of("angel", "titanium", "leviathan"),
            List.of("colossus", "eclipse", "void"),
            List.of("phoenix", "chaos", "time"),
            List.of("dragon", "celestial", "infinity"));
    private static final List<String> DEATH_TOTEM_IDS = List.of(
            "phoenix_totem",
            "colossus_totem",
            "time_totem",
            "celestial_totem",
            "totemcito");
    private static final List<String> BOSS_KEY_IDS = List.of(
            "colossus_key",
            "eclipse_key",
            "void_key",
            "phoenix_key",
            "celestial_key",
            "infinity_key");
    private static final List<String> BOSS_RELIC_IDS = List.of(
            "chaos_star",
            "warden_heart",
            "dragon_heart");
    private static final List<String> BOSS_ALTAR_IDS = List.of(
            "colossus_altar",
            "eclipse_altar",
            "void_altar",
            "phoenix_altar",
            "celestial_altar",
            "infinity_altar");
    private static final List<String> REQUIRED_INFINITY_BOSSES = List.of(
            "colossus",
            "eclipse",
            "void",
            "phoenix",
            "chaos",
            "time",
            "dragon",
            "celestial");
    private static final double BOSS_BAR_RADIUS = 96.0;

    private NamespacedKey actionKey;
    private NamespacedKey buyKey;
    private NamespacedKey sellKey;
    private NamespacedKey specialKey;
    private NamespacedKey waystoneKey;
    private NamespacedKey progressionKey;
    private NamespacedKey bossKey;
    private NamespacedKey bossAltarBlockKey;

    private final Map<String, BuyEntry> buyEntries = new HashMap<>();
    private final Map<String, SellEntry> sellEntries = new HashMap<>();
    private final Map<String, CustomMaterial> customMaterials = new LinkedHashMap<>();
    private final Map<String, ArmorSet> armorSets = new LinkedHashMap<>();
    private final Map<String, GearFamily> gearFamilies = new LinkedHashMap<>();
    private final Map<String, CustomGear> customGears = new LinkedHashMap<>();
    private final Map<String, BossDefinition> bossDefinitions = new LinkedHashMap<>();
    private final Map<UUID, LivingEntity> activeBosses = new HashMap<>();
    private final Map<UUID, BossBar> bossBars = new HashMap<>();
    private final List<NamespacedKey> recipeBookKeys = new ArrayList<>();
    private final Map<NamespacedKey, String> recipeProgressFamilies = new HashMap<>();
    private final Map<UUID, Long> angelGraceCooldowns = new HashMap<>();
    private final Map<String, Long> armorCooldowns = new HashMap<>();
    private final Map<String, Long> gearTraitCooldowns = new HashMap<>();
    private final Map<UUID, Long> titaniumImmuneUntil = new HashMap<>();
    private final Map<UUID, Long> infinityImmuneUntil = new HashMap<>();
    private final Map<UUID, Long> chaosDamageUntil = new HashMap<>();
    private final Map<UUID, Long> infinityDamageUntil = new HashMap<>();
    private final Map<UUID, Location> pvpReturnLocations = new LinkedHashMap<>();
    private final Map<UUID, PvpSnapshot> pvpSnapshots = new LinkedHashMap<>();
    private final Set<UUID> pvpEliminated = new HashSet<>();
    private String openEventId;
    private String runningEventId;
    private Location runningEventCenter;
    private long runningEventEndsAtMs;
    private boolean pvpFightRunning;

    @Override
    public void onEnable() {
        actionKey = new NamespacedKey(this, "menu_action");
        buyKey = new NamespacedKey(this, "buy_item");
        sellKey = new NamespacedKey(this, "sell_item");
        specialKey = new NamespacedKey(this, "special_item");
        waystoneKey = new NamespacedKey(this, "waystone_id");
        progressionKey = new NamespacedKey(this, "armor_progression_phase");
        bossKey = new NamespacedKey(this, "boss_id");
        bossAltarBlockKey = new NamespacedKey(this, "boss_altar_block");

        saveDefaultConfig();
        registerCustomMaterials();
        registerArmorSets();
        registerGearFamilies();
        registerBosses();
        registerBuyEntries();
        registerSellEntries();
        registerRecipes();
        Bukkit.getPluginManager().registerEvents(this, this);
        for (Player player : Bukkit.getOnlinePlayers()) {
            advanceProgression(player);
            unlockRecipeBook(player);
        }

        Objects.requireNonNull(getCommand("tienda")).setExecutor(this);
        Objects.requireNonNull(getCommand("shop")).setExecutor(this);
        Objects.requireNonNull(getCommand("perfil")).setExecutor(this);
        Objects.requireNonNull(getCommand("estadisticas")).setExecutor(this);
        Objects.requireNonNull(getCommand("puntos")).setExecutor(this);
        Objects.requireNonNull(getCommand("hpgivepoints")).setExecutor(this);
        Objects.requireNonNull(getCommand("hpgivearmor")).setExecutor(this);
        Objects.requireNonNull(getCommand("hpgivematerial")).setExecutor(this);
        Objects.requireNonNull(getCommand("waystone")).setExecutor(this);
        Objects.requireNonNull(getCommand("waystones")).setExecutor(this);
        Objects.requireNonNull(getCommand("lobby")).setExecutor(this);
        Objects.requireNonNull(getCommand("pvp")).setExecutor(this);
        Objects.requireNonNull(getCommand("tntrun")).setExecutor(this);
        Objects.requireNonNull(getCommand("botes")).setExecutor(this);
        Objects.requireNonNull(getCommand("cabezas")).setExecutor(this);
        Objects.requireNonNull(getCommand("laberinto")).setExecutor(this);
        Objects.requireNonNull(getCommand("evento")).setExecutor(this);
        Objects.requireNonNull(getCommand("jefe")).setExecutor(this);
        Objects.requireNonNull(getCommand("jefes")).setExecutor(this);

        Bukkit.getScheduler().runTaskTimer(this, this::tickArmorSets, 60L, 100L);
        Bukkit.getScheduler().runTaskTimer(this, this::tickEvents, 20L, 20L);
        Bukkit.getScheduler().runTaskTimer(this, this::tickBossBars, 20L, 20L);
        clearBossVisuals();
        Bukkit.getScheduler().runTask(this, this::configureLoadedBosses);
        Bukkit.getScheduler().runTask(this, this::refreshBossAltarDisplays);

        getLogger().info("HardcorePlusPlugin listo: tienda GUI, venta, perfil y waystones.");
    }

    @Override
    public void onDisable() {
        for (UUID uuid : new ArrayList<>(pvpSnapshots.keySet())) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                restorePvpPlayer(player, true);
            }
        }
        pvpReturnLocations.clear();
        pvpSnapshots.clear();
        pvpEliminated.clear();
        openEventId = null;
        runningEventId = null;
        runningEventCenter = null;
        runningEventEndsAtMs = 0L;
        pvpFightRunning = false;
        clearBossBars();
        clearBossVisuals();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String name = command.getName().toLowerCase(Locale.ROOT);

        if (name.equals("hpgivepoints")) {
            return handleGivePoints(sender, args);
        }
        if (name.equals("hpgivearmor")) {
            return handleGiveArmor(sender, args);
        }
        if (name.equals("hpgivematerial")) {
            return handleGiveMaterial(sender, args);
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Este comando es para jugadores.");
            return true;
        }

        switch (name) {
            case "tienda":
            case "shop":
                openShopMain(player);
                return true;
            case "perfil":
            case "estadisticas":
                openProfile(player);
                return true;
            case "puntos":
                player.sendMessage(ChatColor.GOLD + "* Puntos: " + ChatColor.YELLOW + getScore(player, POINTS_OBJECTIVE));
                player.sendMessage(ChatColor.GRAY + "Progresion: " + ChatColor.AQUA + progressionLabel(unlockedPhase(player)));
                return true;
            case "waystone":
            case "waystones":
                return handleWaystone(player, args);
            case "lobby":
                return handleLobby(player, args);
            case "pvp":
                return handlePvp(player, args);
            case "tntrun":
            case "botes":
            case "cabezas":
            case "laberinto":
                return handleEventAlias(player, name, args);
            case "evento":
                return handleEvento(player, args);
            case "jefe":
                return handleJefe(player, args);
            case "jefes":
                return handleJefes(player, args);
            default:
                return false;
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        advanceProgression(event.getPlayer());
        unlockRecipeBook(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (pvpSnapshots.containsKey(player.getUniqueId())) {
            restorePvpPlayer(player, true);
            checkPvpWinner();
        } else {
            pvpReturnLocations.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player && pvpSnapshots.containsKey(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (pvpSnapshots.containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        if (!(event.getView().getPlayer() instanceof Player player)) {
            return;
        }

        String itemId = data(event.getInventory().getResult(), specialKey);
        String familyId = progressionFamilyForItem(itemId);
        if (familyId != null && !isSetUnlocked(player, familyId)) {
            event.getInventory().setResult(null);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (pvpSnapshots.containsKey(event.getPlayer().getUniqueId())) {
            handleEventBlockBreak(event);
            return;
        }

        Material type = event.getBlock().getType();
        if (isBossAltarBlock(type)) {
            String altarBossId = bossAltarBossId(event.getBlock());
            if (altarBossId != null) {
                removeBossAltarDisplay(event.getBlock());
                clearBossAltar(event.getBlock());
                event.setDropItems(false);
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    String altarItemId = bossAltarItemIdForBoss(altarBossId);
                    if (altarItemId != null) {
                        giveOrDrop(event.getPlayer(), bossAltarItem(altarItemId));
                    }
                }
                BossDefinition boss = bossDefinitions.get(altarBossId);
                String bossName = boss == null ? altarBossId : boss.displayName();
                event.getPlayer().sendMessage(ChatColor.GRAY + "Quitaste el altar de "
                        + ChatColor.YELLOW + bossName + ChatColor.GRAY + ".");
            }
            return;
        }
        if (type != Material.IRON_ORE && type != Material.DEEPSLATE_IRON_ORE) {
            return;
        }
        if (ThreadLocalRandom.current().nextDouble() >= TITANIUM_MINING_CHANCE) {
            return;
        }

        Player player = event.getPlayer();
        giveOrDrop(player, customMaterialItem("titanium_ingot"));
        player.sendMessage(ChatColor.GRAY + "Encontraste un " + ChatColor.YELLOW + "Lingote de Titanio"
                + ChatColor.GRAY + " entre el mineral de hierro.");
        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 0.7f, 1.4f);
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH
                || ThreadLocalRandom.current().nextDouble() >= SEA_ESSENCE_FISHING_CHANCE) {
            return;
        }

        Player player = event.getPlayer();
        giveOrDrop(player, customMaterialItem("sea_essence"));
        player.sendMessage(ChatColor.AQUA + "La pesca revelo una " + ChatColor.LIGHT_PURPLE
                + "Esencia del Mar" + ChatColor.AQUA + ".");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_SPLASH, 0.8f, 1.35f);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        String altarId = data(event.getItemInHand(), specialKey);
        BossAltarInfo altar = bossAltarInfo(altarId);
        if (altar == null) {
            return;
        }

        Block block = event.getBlockPlaced();
        saveBossAltar(block, altar.bossId());
        spawnBossAltarDisplay(block, altar);
        event.getPlayer().sendMessage(altar.color() + "" + ChatColor.BOLD + altar.displayName()
                + ChatColor.GRAY + " colocado. Usa aqui " + bossKeyNameForBoss(altar.bossId()) + ".");
        block.getWorld().spawnParticle(Particle.ENCHANT, block.getLocation().add(0.5, 0.8, 0.5),
                24, 0.45, 0.35, 0.45, 0.05);
        block.getWorld().playSound(block.getLocation(), Sound.BLOCK_TRIAL_SPAWNER_SPAWN_MOB, 0.7f, 1.25f);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block == null || !isBossAltarBlock(block.getType())) {
            return;
        }

        Player player = event.getPlayer();
        String keyId = data(player.getInventory().getItemInMainHand(), specialKey);
        BossKeyInfo keyInfo = bossKeyInfo(keyId);
        if (keyInfo == null || keyInfo.bossId() == null) {
            return;
        }

        event.setCancelled(true);
        String altarBossId = bossAltarBossId(block);
        if (altarBossId != null && !altarBossId.equals(keyInfo.bossId())) {
            BossDefinition expectedBoss = bossDefinitions.get(altarBossId);
            player.sendMessage(ChatColor.RED + "Esa llave no encaja en este altar.");
            player.sendMessage(ChatColor.GRAY + "Este altar es de "
                    + (expectedBoss == null ? ChatColor.YELLOW + altarBossId : expectedBoss.color() + expectedBoss.displayName())
                    + ChatColor.GRAY + ".");
            return;
        }
        activateBossAltar(player, block, keyInfo);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (cleanupLegacyBossVisual(event.getEntity())) {
            return;
        }
        if (!(event.getEntity() instanceof LivingEntity living)) {
            return;
        }

        String vanillaBossId = vanillaBossId(living);
        if (vanillaBossId != null) {
            BossDefinition boss = bossDefinitions.get(vanillaBossId);
            if (boss != null) {
                Bukkit.getScheduler().runTask(this, () -> configureBoss(living, boss, null));
            }
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            cleanupLegacyBossVisual(entity);
            cleanupBossAltarDisplay(entity);
        }
        refreshBossAltarDisplays(event.getWorld(), event.getChunk().getX(), event.getChunk().getZ());
    }

    @EventHandler
    public void onBossDeath(EntityDeathEvent event) {
        BossDefinition boss = bossDefinition(event.getEntity());
        if (boss == null) {
            return;
        }

        removeBossBar(event.getEntity().getUniqueId());
        List<Player> rewarded = new ArrayList<>();
        Player killer = event.getEntity().getKiller();
        if (killer != null) {
            rewarded.add(killer);
        }
        for (Entity nearby : event.getEntity().getNearbyEntities(boss.rewardRadius(), boss.rewardRadius(), boss.rewardRadius())) {
            if (!(nearby instanceof Player player) || player.getGameMode() == org.bukkit.GameMode.SPECTATOR) {
                continue;
            }
            if (!rewarded.contains(player)) {
                rewarded.add(player);
            }
        }

        for (Player player : rewarded) {
            giveOrDrop(player, customMaterialItem(boss.dropMaterialId()));
            if (boss.nextSpecialDropId() != null) {
                giveOrDrop(player, specialItem(boss.nextSpecialDropId()));
            }
            markBossDefeated(player, boss.id());
            addScore(player, "hp.bosses", 1);
            addScore(player, POINTS_OBJECTIVE, boss.points());
            advanceProgression(player);
            unlockRecipeBook(player);
            player.sendMessage(ChatColor.GOLD + "Boss derrotado: " + boss.color() + boss.displayName());
            player.sendMessage(ChatColor.GRAY + "Recompensa: " + ChatColor.YELLOW
                    + customMaterials.get(boss.dropMaterialId()).displayName()
                    + bossRewardSuffix(boss.nextSpecialDropId())
                    + ChatColor.GRAY + " + " + ChatColor.YELLOW + boss.points() + " pts");
        }
        Bukkit.broadcastMessage(boss.color() + "" + ChatColor.BOLD + boss.displayName()
                + ChatColor.GRAY + " fue derrotado.");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        String title = event.getView().getTitle();
        if (title.equals(SHOP_TITLE)) {
            event.setCancelled(true);
            handleActionClick(player, event.getCurrentItem());
            return;
        }

        if (title.equals(BUY_TITLE)) {
            event.setCancelled(true);
            ItemStack clicked = event.getCurrentItem();
            if (handleActionClick(player, clicked)) {
                return;
            }
            String id = data(clicked, buyKey);
            if (id != null) {
                buy(player, id);
                openBuyShop(player);
            }
            return;
        }

        if (title.equals(SELL_TITLE)) {
            event.setCancelled(true);
            ItemStack clicked = event.getCurrentItem();
            if (handleActionClick(player, clicked)) {
                return;
            }
            String id = data(clicked, sellKey);
            if (id != null) {
                sell(player, id, event.isShiftClick());
                openSellShop(player);
            }
            return;
        }

        if (title.equals(WAYSTONE_TITLE)) {
            event.setCancelled(true);
            ItemStack clicked = event.getCurrentItem();
            if (handleActionClick(player, clicked)) {
                return;
            }
            String id = data(clicked, waystoneKey);
            if (id != null) {
                teleportToWaystone(player, id);
            }
            return;
        }

        if (title.equals(PROFILE_TITLE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity living && bossDefinition(living) != null) {
            Bukkit.getScheduler().runTask(this, () -> updateBossBar(living));
        }

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (pvpSnapshots.containsKey(player.getUniqueId())) {
            String eventId = runningEventId;
            if (eventId == null || pvpEliminated.contains(player.getUniqueId())) {
                event.setCancelled(true);
                return;
            }
            if (eventId.equals("botes") || eventId.equals("laberinto")) {
                event.setCancelled(true);
                return;
            }
            if (event instanceof EntityDamageByEntityEvent && !eventId.equals("pvp") && !eventId.equals("cabezas")) {
                event.setCancelled(true);
                return;
            }

            double remainingHealth = player.getHealth() - event.getFinalDamage();
            if (remainingHealth <= 0.0) {
                event.setCancelled(true);
                if (eventId.equals("cabezas")) {
                    Player killer = event instanceof EntityDamageByEntityEvent damageEvent
                            ? attackingPlayer(damageEvent)
                            : null;
                    Bukkit.getScheduler().runTask(this, () -> scoreHeadHunterKill(player, killer));
                } else {
                    Bukkit.getScheduler().runTask(this, () -> eliminateEventPlayer(player));
                }
                return;
            }
            return;
        }

        long now = System.currentTimeMillis();
        if (now < titaniumImmuneUntil.getOrDefault(player.getUniqueId(), 0L)
                || now < infinityImmuneUntil.getOrDefault(player.getUniqueId(), 0L)) {
            event.setCancelled(true);
            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 0.4f, 1.8f);
            return;
        }

        if (hasFullAngelSet(player) && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
            player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 0.5f, 1.7f);
            return;
        }

        double remainingHealth = player.getHealth() - event.getFinalDamage();
        if (remainingHealth <= 0.0) {
            if (tryDeathTotem(event, player)) {
                return;
            }
            if (hasFullAngelSet(player) && canUseAngelGrace(player)) {
                event.setCancelled(true);
                angelGraceCooldowns.put(player.getUniqueId(), now + ANGEL_GRACE_COOLDOWN_MS);
                Bukkit.getScheduler().runTask(this, () -> activateAngelGrace(player));
                return;
            }
            if (hasFullSet(player, "phoenix") && useSetCooldown(player, "phoenix", PHOENIX_COOLDOWN_MS)) {
                event.setCancelled(true);
                Bukkit.getScheduler().runTask(this, () -> activatePhoenixRebirth(player));
                return;
            }
            if (hasFullSet(player, "void") && useSetCooldown(player, "void", VOID_COOLDOWN_MS)) {
                event.setCancelled(true);
                Bukkit.getScheduler().runTask(this, () -> activateVoidShift(player));
                return;
            }
            if (hasFullSet(player, "infinity") && useSetCooldown(player, "infinity", INFINITY_COOLDOWN_MS)) {
                event.setCancelled(true);
                Bukkit.getScheduler().runTask(this, () -> activateInfinityCore(player, true));
                return;
            }
        }

        if (hasFullSet(player, "titanium") && useSetCooldown(player, "titanium", TITANIUM_COOLDOWN_MS)) {
            titaniumImmuneUntil.put(player.getUniqueId(), now + 3_000L);
            player.sendMessage(ChatColor.GRAY + "Titanio endurecido: " + ChatColor.YELLOW + "3s de inmunidad.");
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.45f, 1.4f);
        } else if (hasFullSet(player, "leviathan") && useSetCooldown(player, "leviathan", LEVIATHAN_COOLDOWN_MS)) {
            activateLeviathanGuardian(player);
        } else if (hasFullSet(player, "dragon") && useSetCooldown(player, "dragon", DRAGON_COOLDOWN_MS)) {
            activateDragonRoar(player);
        } else if (hasFullSet(player, "colossus") && useSetCooldown(player, "colossus", COLOSSUS_COOLDOWN_MS)) {
            activateColossusSlam(player);
        } else if (hasFullSet(player, "eclipse") && useSetCooldown(player, "eclipse", ECLIPSE_COOLDOWN_MS)) {
            activateEclipseRay(player);
        } else if (hasFullSet(player, "chaos") && useSetCooldown(player, "chaos", CHAOS_COOLDOWN_MS)) {
            chaosDamageUntil.put(player.getUniqueId(), now + 10_000L);
            player.sendMessage(ChatColor.DARK_RED + "Caos liberado: " + ChatColor.YELLOW + "daño x2 durante 10s.");
            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 0.4f, 1.7f);
        } else if (hasFullSet(player, "time") && useSetCooldown(player, "time", TIME_COOLDOWN_MS)) {
            activateTimeFreeze(player);
        } else if (hasFullSet(player, "celestial") && useSetCooldown(player, "celestial", CELESTIAL_COOLDOWN_MS)) {
            activateCelestialStorm(player);
        } else if (hasFullSet(player, "infinity") && useSetCooldown(player, "infinity", INFINITY_COOLDOWN_MS)) {
            activateInfinityCore(player, false);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player victim && pvpSnapshots.containsKey(victim.getUniqueId())) {
            Player attacker = attackingPlayer(event);
            String eventId = runningEventId;
            if (eventId == null || pvpEliminated.contains(victim.getUniqueId())
                    || (attacker != null && pvpEliminated.contains(attacker.getUniqueId()))) {
                event.setCancelled(true);
                return;
            }
            if (!eventId.equals("pvp") && !eventId.equals("cabezas")) {
                event.setCancelled(true);
                return;
            }
            if ((eventId.equals("pvp") || eventId.equals("cabezas"))
                    && (attacker == null || !pvpSnapshots.containsKey(attacker.getUniqueId()))) {
                event.setCancelled(true);
                return;
            }
        }

        Player player = attackingPlayer(event);
        if (player == null) {
            return;
        }
        if (pvpSnapshots.containsKey(player.getUniqueId())
                && !(event.getEntity() instanceof Player victim && pvpSnapshots.containsKey(victim.getUniqueId()))) {
            event.setCancelled(true);
            return;
        }

        long now = System.currentTimeMillis();
        if (hasFullSet(player, "infinity") && now < infinityDamageUntil.getOrDefault(player.getUniqueId(), 0L)) {
            event.setDamage(event.getDamage() * 3.0);
            return;
        }
        if (hasFullSet(player, "chaos") && now < chaosDamageUntil.getOrDefault(player.getUniqueId(), 0L)) {
            event.setDamage(event.getDamage() * 2.0);
        }

        CustomGear gear = attackingGear(event, player);
        if (gear != null && event.getEntity() instanceof LivingEntity target && !(target instanceof Player)) {
            applyGearHit(event, player, target, gear);
        }
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        CustomGear gear = customGear(data(event.getBow(), specialKey));
        if (gear != null && gear.type() == GearType.BOW) {
            setData(event.getProjectile(), specialKey, gear.id());
        }
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player player)) {
            return;
        }
        if (data(event.getEntity(), specialKey) != null) {
            return;
        }

        CustomGear mainHandGear = customGear(data(player.getInventory().getItemInMainHand(), specialKey));
        if (mainHandGear != null && mainHandGear.type() == GearType.TRIDENT) {
            setData(event.getEntity(), specialKey, mainHandGear.id());
        }
    }

    private Player attackingPlayer(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            return player;
        }
        if (event.getDamager() instanceof Projectile projectile && projectile.getShooter() instanceof Player player) {
            return player;
        }
        return null;
    }

    private CustomGear attackingGear(EntityDamageByEntityEvent event, Player player) {
        if (event.getDamager() instanceof Projectile projectile) {
            return customGear(data(projectile, specialKey));
        }
        return customGear(data(player.getInventory().getItemInMainHand(), specialKey));
    }

    private void applyGearHit(EntityDamageByEntityEvent event, Player player, LivingEntity target, CustomGear gear) {
        if (gear.type().projectile()) {
            event.setDamage(event.getDamage() + Math.max(2.0, gear.family().power() * 0.65));
        }

        if (!useGearTraitCooldown(player, gear.family().id(), gearTraitCooldownMs(gear.family().id()))) {
            return;
        }

        double bonus = activateGearTrait(player, target, gear.family());
        if (bonus > 0.0) {
            event.setDamage(event.getDamage() + bonus);
        }
    }

    private boolean useGearTraitCooldown(Player player, String familyId, long cooldownMs) {
        String key = player.getUniqueId() + ":" + familyId;
        long now = System.currentTimeMillis();
        if (now < gearTraitCooldowns.getOrDefault(key, 0L)) {
            return false;
        }
        gearTraitCooldowns.put(key, now + cooldownMs);
        return true;
    }

    private long gearTraitCooldownMs(String familyId) {
        return switch (familyId) {
            case "angel" -> 2_500L;
            case "titanium" -> 6_000L;
            case "leviathan" -> 4_000L;
            case "phoenix" -> 3_000L;
            case "dragon" -> 4_500L;
            case "void" -> 6_000L;
            case "colossus" -> 5_000L;
            case "eclipse" -> 5_000L;
            case "chaos" -> 3_500L;
            case "time" -> 7_000L;
            case "celestial" -> 8_000L;
            case "infinity" -> 10_000L;
            default -> 5_000L;
        };
    }

    private double activateGearTrait(Player player, LivingEntity target, GearFamily family) {
        switch (family.id()) {
            case "angel":
                healPlayer(player, 2.0);
                player.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, target.getLocation().add(0, 1, 0), 18, 0.25, 0.5, 0.25, 0.03);
                return 0.0;
            case "titanium":
                applyEffect(player, PotionEffectType.RESISTANCE, 80, 0);
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.35f, 1.6f);
                return 1.0;
            case "leviathan":
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 80, 2, true, false, true));
                target.getWorld().spawnParticle(Particle.SPLASH, target.getLocation().add(0, 1, 0), 25, 0.4, 0.5, 0.4, 0.1);
                return 2.0;
            case "phoenix":
                target.setFireTicks(120);
                target.getWorld().spawnParticle(Particle.FLAME, target.getLocation().add(0, 1, 0), 28, 0.35, 0.6, 0.35, 0.04);
                return 2.5;
            case "dragon":
                pushTarget(player, target, 1.7, 0.35);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.35f, 1.6f);
                return 3.0;
            case "void":
                target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 70, 0, true, false, true));
                target.getWorld().spawnParticle(Particle.PORTAL, target.getLocation().add(0, 1, 0), 34, 0.45, 0.7, 0.45, 0.22);
                return 3.5;
            case "colossus":
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 90, 5, true, false, true));
                target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 90, 1, true, false, true));
                target.getWorld().spawnParticle(Particle.EXPLOSION, target.getLocation(), 1, 0, 0, 0, 0);
                return 4.0;
            case "eclipse":
                target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 1, true, false, true));
                target.getWorld().spawnParticle(Particle.FLASH, target.getLocation().add(0, 1, 0), 1, 0, 0, 0, 0);
                return 4.5;
            case "chaos":
                target.getWorld().spawnParticle(Particle.ENCHANT, target.getLocation().add(0, 1, 0), 45, 0.5, 0.7, 0.5, 0.35);
                return 6.0;
            case "time":
                freezeEntity(target, 60L);
                target.getWorld().spawnParticle(Particle.SNOWFLAKE, target.getLocation().add(0, 1, 0), 35, 0.45, 0.6, 0.45, 0.03);
                return 3.0;
            case "celestial":
                target.getWorld().strikeLightningEffect(target.getLocation());
                target.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, target.getLocation().add(0, 1, 0), 32, 0.45, 0.8, 0.45, 0.12);
                return 6.0;
            case "infinity":
                target.getWorld().strikeLightningEffect(target.getLocation());
                target.getWorld().spawnParticle(Particle.FLASH, target.getLocation().add(0, 1, 0), 1, 0, 0, 0, 0);
                healPlayer(player, 4.0);
                return 10.0;
            default:
                return 0.0;
        }
    }

    private void healPlayer(Player player, double amount) {
        player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + amount));
    }

    private void pushTarget(Player player, LivingEntity target, double horizontalPower, double verticalPower) {
        Vector direction = target.getLocation().toVector().subtract(player.getLocation().toVector());
        if (direction.lengthSquared() < 0.01) {
            direction = player.getLocation().getDirection();
        }
        direction.normalize().multiply(horizontalPower);
        direction.setY(verticalPower);
        target.setVelocity(direction);
    }

    private void freezeEntity(LivingEntity target, long ticks) {
        target.setVelocity(new Vector(0, 0, 0));
        target.setAI(false);
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, (int) ticks + 20, 255, true, false, true));
        Bukkit.getScheduler().runTaskLater(this, () -> {
            if (!target.isDead()) {
                target.setAI(true);
            }
        }, ticks);
    }

    private void tickArmorSets() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            advanceProgression(player);
            if (hasFullAngelSet(player)) {
                applyEffect(player, PotionEffectType.REGENERATION, 140, 1);
            }
            if (hasFullSet(player, "titanium")) {
                applyEffect(player, PotionEffectType.RESISTANCE, 140, 0);
            }
            if (hasFullSet(player, "leviathan")) {
                applyEffect(player, PotionEffectType.WATER_BREATHING, 140, 0);
                applyEffect(player, PotionEffectType.DOLPHINS_GRACE, 140, 0);
                applyEffect(player, PotionEffectType.CONDUIT_POWER, 140, 0);
            }
            if (hasFullSet(player, "phoenix")) {
                applyEffect(player, PotionEffectType.FIRE_RESISTANCE, 140, 0);
                applyEffect(player, PotionEffectType.REGENERATION, 140, 0);
            }
            if (hasFullSet(player, "dragon")) {
                applyEffect(player, PotionEffectType.RESISTANCE, 140, 2);
            }
            if (hasFullSet(player, "void")) {
                applyEffect(player, PotionEffectType.INVISIBILITY, 140, 0);
            }
            if (hasFullSet(player, "colossus")) {
                applyEffect(player, PotionEffectType.SLOWNESS, 140, 1);
                applyEffect(player, PotionEffectType.STRENGTH, 140, 2);
            }
            if (hasFullSet(player, "eclipse")) {
                applyEffect(player, PotionEffectType.NIGHT_VISION, 260, 0);
                player.removePotionEffect(PotionEffectType.WITHER);
            }
            if (hasFullSet(player, "chaos")) {
                applyEffect(player, PotionEffectType.SPEED, 140, 1);
                applyEffect(player, PotionEffectType.STRENGTH, 140, 1);
            }
            if (hasFullSet(player, "time")) {
                slowNearbyMonsters(player, 8.0, 120, 1);
            }
            if (hasFullSet(player, "celestial")) {
                applyEffect(player, PotionEffectType.REGENERATION, 140, 2);
                applyEffect(player, PotionEffectType.RESISTANCE, 140, 3);
            }
            if (hasFullSet(player, "infinity")) {
                applyEffect(player, PotionEffectType.REGENERATION, 140, 3);
                applyEffect(player, PotionEffectType.RESISTANCE, 140, 4);
                removeNegativeEffects(player);
            }
        }
    }

    private boolean hasFullAngelSet(Player player) {
        PlayerInventory inv = player.getInventory();
        return isSpecial(inv.getHelmet(), "angel_helmet")
                && isSpecial(inv.getChestplate(), "angel_chestplate")
                && isSpecial(inv.getLeggings(), "angel_leggings")
                && isSpecial(inv.getBoots(), "angel_boots");
    }

    private boolean hasFullSet(Player player, String setId) {
        PlayerInventory inv = player.getInventory();
        return isSpecial(inv.getHelmet(), setId + "_helmet")
                && isSpecial(inv.getChestplate(), setId + "_chestplate")
                && isSpecial(inv.getLeggings(), setId + "_leggings")
                && isSpecial(inv.getBoots(), setId + "_boots");
    }

    private void advanceProgression(Player player) {
        int phase = unlockedPhase(player);
        while (phase < ARMOR_PROGRESSION_PHASES.size() - 1
                && hasAnyCompleteSet(player, ARMOR_PROGRESSION_PHASES.get(phase))) {
            phase++;
            player.getPersistentDataContainer().set(progressionKey, PersistentDataType.INTEGER, phase);
            player.sendMessage(ChatColor.GOLD + "Progresion desbloqueada: " + progressionLabel(phase));
            unlockRecipeBook(player);
        }
    }

    private boolean hasAnyCompleteSet(Player player, List<String> setIds) {
        for (String setId : setIds) {
            if (hasCompleteSetInInventory(player, setId)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasCompleteSetInInventory(Player player, String setId) {
        for (ArmorPiece piece : ArmorPiece.values()) {
            if (!containsSpecial(player, setId + "_" + piece.id())) {
                return false;
            }
        }
        return true;
    }

    private boolean containsSpecial(Player player, String id) {
        for (ItemStack stack : player.getInventory().getContents()) {
            if (isSpecial(stack, id)) {
                return true;
            }
        }
        for (ItemStack stack : player.getInventory().getArmorContents()) {
            if (isSpecial(stack, id)) {
                return true;
            }
        }
        return false;
    }

    private int unlockedPhase(Player player) {
        Integer phase = player.getPersistentDataContainer().get(progressionKey, PersistentDataType.INTEGER);
        if (phase == null) {
            return 0;
        }
        return Math.max(0, Math.min(ARMOR_PROGRESSION_PHASES.size() - 1, phase));
    }

    private boolean isSetUnlocked(Player player, String setId) {
        int phase = phaseForSet(setId);
        return phase < 0 || phase <= unlockedPhase(player);
    }

    private int phaseForSet(String setId) {
        for (int phase = 0; phase < ARMOR_PROGRESSION_PHASES.size(); phase++) {
            if (ARMOR_PROGRESSION_PHASES.get(phase).contains(setId)) {
                return phase;
            }
        }
        return -1;
    }

    private String progressionLabel(int phase) {
        List<String> names = new ArrayList<>();
        for (String setId : ARMOR_PROGRESSION_PHASES.get(phase)) {
            names.add(progressionSetName(setId));
        }
        return "Fase " + (phase + 1) + "/" + ARMOR_PROGRESSION_PHASES.size()
                + " (" + String.join(", ", names) + ")";
    }

    private String progressionSetName(String setId) {
        return switch (setId) {
            case "angel" -> "Angel Caido";
            case "titanium" -> "Titanio";
            case "leviathan" -> "Leviatan";
            case "phoenix" -> "Fenix";
            case "dragon" -> "Dragon";
            case "void" -> "Vacio";
            case "colossus" -> "Coloso";
            case "eclipse" -> "Eclipse";
            case "chaos" -> "Caos";
            case "time" -> "Tiempo";
            case "celestial" -> "Celestial";
            case "infinity" -> "Infinito";
            default -> setId;
        };
    }

    private String progressionFamilyForItem(String itemId) {
        if (itemId == null) {
            return null;
        }

        String materialFamily = switch (itemId) {
            case "angel_essence" -> "angel";
            case "titanium_ingot" -> "titanium";
            case "sea_essence" -> "leviathan";
            case "phoenix_feather", "phoenix_totem" -> "phoenix";
            case "dragon_scale" -> "dragon";
            case "void_fragment" -> "void";
            case "golem_heart", "colossus_totem" -> "colossus";
            case "solar_essence" -> "eclipse";
            case "chaos_fragment" -> "chaos";
            case "temporal_crystal", "time_totem" -> "time";
            case "celestial_fragment", "celestial_totem" -> "celestial";
            case "infinity_core" -> "infinity";
            case "colossus_key" -> "colossus";
            case "eclipse_key" -> "eclipse";
            case "void_key" -> "void";
            case "phoenix_key" -> "phoenix";
            case "chaos_star" -> "chaos";
            case "warden_heart" -> "time";
            case "dragon_heart" -> "dragon";
            case "celestial_key" -> "celestial";
            case "infinity_key" -> "infinity";
            default -> null;
        };
        if (materialFamily != null) {
            return materialFamily;
        }

        for (List<String> phase : ARMOR_PROGRESSION_PHASES) {
            for (String familyId : phase) {
                if (itemId.startsWith(familyId + "_")) {
                    return familyId;
                }
            }
        }
        return null;
    }

    private boolean isBuyUnlocked(Player player, String id) {
        String familyId = progressionFamilyForItem(id);
        return familyId == null || isSetUnlocked(player, familyId);
    }

    private BossDefinition bossDefinition(Entity entity) {
        String id = data(entity, bossKey);
        if (id == null) {
            id = legacyBossIdFromTags(entity);
        }
        if (id == null) {
            id = vanillaBossId(entity);
        }
        return id == null ? null : bossDefinitions.get(id);
    }

    private String legacyBossIdFromTags(Entity entity) {
        if (entity.getScoreboardTags().contains("hp.boss_ceniza")) {
            return "phoenix";
        }
        if (entity.getScoreboardTags().contains("hp.boss_dragona")) {
            return "dragon";
        }
        if (entity.getScoreboardTags().contains("hp.boss_abismo")) {
            return "void";
        }
        if (entity.getScoreboardTags().contains("hp.boss_titan")) {
            return "colossus";
        }
        if (entity.getScoreboardTags().contains("hp.boss_bruja")) {
            return "eclipse";
        }
        return null;
    }

    private String vanillaBossId(Entity entity) {
        return switch (entity.getType()) {
            case WITHER -> "chaos";
            case WARDEN -> "time";
            case ENDER_DRAGON -> "dragon";
            default -> null;
        };
    }

    private String bossRewardSuffix(String nextSpecialDropId) {
        if (nextSpecialDropId == null) {
            return "";
        }
        return ChatColor.GRAY + " + " + ChatColor.YELLOW + giveableItemName(nextSpecialDropId);
    }

    private boolean isBossAltarBlock(Material material) {
        return material == Material.TRIAL_SPAWNER || material == Material.VAULT;
    }

    private String bossAltarPath(Block block) {
        return "bossAltars." + block.getWorld().getUID() + "." + block.getX() + "_" + block.getY() + "_" + block.getZ();
    }

    private String bossAltarBossId(Block block) {
        return getConfig().getString(bossAltarPath(block));
    }

    private void saveBossAltar(Block block, String bossId) {
        getConfig().set(bossAltarPath(block), bossId);
        saveConfig();
    }

    private void clearBossAltar(Block block) {
        getConfig().set(bossAltarPath(block), null);
        saveConfig();
    }

    private void refreshBossAltarDisplays() {
        ConfigurationSection root = getConfig().getConfigurationSection("bossAltars");
        if (root == null) {
            return;
        }

        for (World world : Bukkit.getWorlds()) {
            ConfigurationSection worldAltars = root.getConfigurationSection(world.getUID().toString());
            if (worldAltars == null) {
                continue;
            }
            for (String coordinates : worldAltars.getKeys(false)) {
                refreshBossAltarDisplay(world, coordinates, worldAltars.getString(coordinates));
            }
        }
    }

    private void refreshBossAltarDisplays(World world, int chunkX, int chunkZ) {
        ConfigurationSection root = getConfig().getConfigurationSection("bossAltars");
        if (root == null) {
            return;
        }
        ConfigurationSection worldAltars = root.getConfigurationSection(world.getUID().toString());
        if (worldAltars == null) {
            return;
        }

        for (String coordinates : worldAltars.getKeys(false)) {
            int[] parsed = parseBossAltarCoordinates(coordinates);
            if (parsed == null || (parsed[0] >> 4) != chunkX || (parsed[2] >> 4) != chunkZ) {
                continue;
            }
            refreshBossAltarDisplay(world, coordinates, worldAltars.getString(coordinates));
        }
    }

    private void refreshBossAltarDisplay(World world, String coordinates, String bossId) {
        int[] parsed = parseBossAltarCoordinates(coordinates);
        if (parsed == null || bossId == null) {
            return;
        }
        String altarId = bossAltarItemIdForBoss(bossId);
        BossAltarInfo altar = bossAltarInfo(altarId);
        if (altar == null) {
            return;
        }
        Block block = world.getBlockAt(parsed[0], parsed[1], parsed[2]);
        if (isBossAltarBlock(block.getType())) {
            spawnBossAltarDisplay(block, altar);
        }
    }

    private int[] parseBossAltarCoordinates(String coordinates) {
        String[] parts = coordinates.split("_");
        if (parts.length != 3) {
            return null;
        }
        try {
            return new int[] {
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            };
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private String bossAltarItemIdForBoss(String bossId) {
        for (String altarId : BOSS_ALTAR_IDS) {
            BossAltarInfo altar = bossAltarInfo(altarId);
            if (altar != null && altar.bossId().equals(bossId)) {
                return altar.id();
            }
        }
        return null;
    }

    private void spawnBossAltarDisplay(Block block, BossAltarInfo altar) {
        removeBossAltarDisplay(block);
        AltarStyle style = altarStyle(altar.bossId());
        spawnAltarPart(block, altar.id(), style.base(), 0.0, 1.01, 0.0, 0.98, 0.08, 0.98, false);
        spawnAltarPart(block, altar.id(), style.trim(), -0.43, 1.08, -0.43, 0.20, 0.18, 0.20, false);
        spawnAltarPart(block, altar.id(), style.trim(), 0.43, 1.08, -0.43, 0.20, 0.18, 0.20, false);
        spawnAltarPart(block, altar.id(), style.trim(), -0.43, 1.08, 0.43, 0.20, 0.18, 0.20, false);
        spawnAltarPart(block, altar.id(), style.trim(), 0.43, 1.08, 0.43, 0.20, 0.18, 0.20, false);
        spawnAltarPart(block, altar.id(), style.rune(), 0.0, 1.14, -0.50, 0.55, 0.08, 0.08, true);
        spawnAltarPart(block, altar.id(), style.rune(), 0.0, 1.14, 0.50, 0.55, 0.08, 0.08, true);
        spawnAltarPart(block, altar.id(), style.rune(), -0.50, 1.14, 0.0, 0.08, 0.08, 0.55, true);
        spawnAltarPart(block, altar.id(), style.rune(), 0.50, 1.14, 0.0, 0.08, 0.08, 0.55, true);
        spawnAltarPart(block, altar.id(), style.core(), 0.0, 1.13, 0.0, 0.46, 0.24, 0.46, true);
        spawnAltarPart(block, altar.id(), style.crystal(), 0.0, 1.38, 0.0, 0.24, 0.46, 0.24, true);
    }

    private void removeBossAltarDisplay(Block block) {
        String path = bossAltarPath(block);
        Location center = block.getLocation().add(0.5, 1.2, 0.5);
        for (Entity entity : block.getWorld().getNearbyEntities(center, 1.6, 2.2, 1.6)) {
            if (!entity.getScoreboardTags().contains("hp.boss_altar_display")
                    || !path.equals(data(entity, bossAltarBlockKey))) {
                continue;
            }
            entity.remove();
        }
    }

    private void spawnAltarPart(Block block, String altarId, Material material,
            double x, double y, double z, double width, double height, double depth, boolean glowing) {
        Location location = block.getLocation().add(0.5 + x, y, 0.5 + z);
        block.getWorld().spawn(location, BlockDisplay.class, display -> {
            display.setBlock(Bukkit.createBlockData(material));
            display.setTransformation(blockDisplayTransform(width, height, depth));
            display.setBillboard(Display.Billboard.FIXED);
            display.setGravity(false);
            display.setPersistent(true);
            display.setInvulnerable(true);
            display.setGlowing(glowing);
            display.addScoreboardTag("hp.boss_altar_display");
            setData(display, specialKey, altarId);
            setData(display, bossAltarBlockKey, bossAltarPath(block));
        });
    }

    private Transformation blockDisplayTransform(double width, double height, double depth) {
        return new Transformation(
                new Vector3f((float) (-width / 2.0), 0.0f, (float) (-depth / 2.0)),
                new AxisAngle4f(0.0f, 0.0f, 1.0f, 0.0f),
                new Vector3f((float) width, (float) height, (float) depth),
                new AxisAngle4f(0.0f, 0.0f, 1.0f, 0.0f));
    }

    private AltarStyle altarStyle(String bossId) {
        return switch (bossId) {
            case "colossus" -> new AltarStyle(Material.POLISHED_DEEPSLATE, Material.CUT_COPPER,
                    Material.SEA_LANTERN, Material.LIGHT_BLUE_STAINED_GLASS, Material.GOLD_BLOCK);
            case "eclipse" -> new AltarStyle(Material.DEEPSLATE_TILES, Material.GOLD_BLOCK,
                    Material.MAGENTA_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.GLOWSTONE);
            case "void" -> new AltarStyle(Material.OBSIDIAN, Material.CRYING_OBSIDIAN,
                    Material.PURPLE_STAINED_GLASS, Material.BLACK_STAINED_GLASS, Material.AMETHYST_BLOCK);
            case "phoenix" -> new AltarStyle(Material.MAGMA_BLOCK, Material.GOLD_BLOCK,
                    Material.ORANGE_STAINED_GLASS, Material.RED_STAINED_GLASS, Material.SHROOMLIGHT);
            case "celestial" -> new AltarStyle(Material.QUARTZ_BLOCK, Material.SEA_LANTERN,
                    Material.LIGHT_BLUE_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.AMETHYST_BLOCK);
            case "infinity" -> new AltarStyle(Material.NETHERITE_BLOCK, Material.PURPUR_BLOCK,
                    Material.MAGENTA_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.END_STONE_BRICKS);
            default -> new AltarStyle(Material.DEEPSLATE_TILES, Material.AMETHYST_BLOCK,
                    Material.PURPLE_STAINED_GLASS, Material.LIGHT_BLUE_STAINED_GLASS, Material.GLOWSTONE);
        };
    }

    private void activateBossAltar(Player player, Block block, BossKeyInfo keyInfo) {
        BossDefinition boss = bossDefinitions.get(keyInfo.bossId());
        if (boss == null || !boss.altarBoss()) {
            player.sendMessage(ChatColor.RED + "Esa llave no corresponde a un altar.");
            return;
        }

        if (boss.id().equals("infinity") && !hasDefeatedAllRequiredBosses(player)) {
            player.sendMessage(ChatColor.RED + "El altar del Infinito aun no responde.");
            player.sendMessage(ChatColor.GRAY + "Te falta vencer: " + ChatColor.YELLOW
                    + missingRequiredBosses(player));
            return;
        }

        Location center = block.getLocation().add(0.5, 1.0, 0.5);
        if (hasActiveBossNear(center, 48.0)) {
            player.sendMessage(ChatColor.RED + "Ya hay un jefe despierto cerca de este altar.");
            return;
        }

        if (player.getGameMode() != GameMode.CREATIVE && !consumeSpecial(player, keyInfo.id())) {
            player.sendMessage(ChatColor.RED + "Necesitas " + keyInfo.color() + keyInfo.displayName()
                    + ChatColor.RED + " para usar este altar.");
            return;
        }

        center.getWorld().spawnParticle(Particle.TRIAL_SPAWNER_DETECTION, center, 24, 0.6, 0.4, 0.6, 0.02);
        center.getWorld().spawnParticle(Particle.ENCHANT, center, 70, 0.9, 0.9, 0.9, 0.25);
        center.getWorld().playSound(center, Sound.BLOCK_TRIAL_SPAWNER_SPAWN_MOB, 1.0f, 0.75f);
        LivingEntity spawned = summonBoss(player, boss, center);
        if (spawned != null) {
            Bukkit.broadcastMessage(boss.color() + "" + ChatColor.BOLD + boss.displayName()
                    + ChatColor.GRAY + " desperto en un altar por " + ChatColor.YELLOW + player.getName()
                    + ChatColor.GRAY + ".");
        }
    }

    private LivingEntity summonBoss(Player summoner, BossDefinition boss, Location location) {
        if (location.getWorld() == null) {
            return null;
        }

        Location spawn = location.clone();
        spawn.getChunk().load();
        Entity entity = spawn.getWorld().spawnEntity(spawn, boss.entityType());
        if (!(entity instanceof LivingEntity living)) {
            entity.remove();
            return null;
        }

        configureBoss(living, boss, summoner);
        return living;
    }

    private void configureBoss(LivingEntity entity, BossDefinition boss, Player target) {
        if (entity.isDead() || !entity.isValid()) {
            return;
        }

        setData(entity, bossKey, boss.id());
        entity.addScoreboardTag("hp.boss");
        entity.addScoreboardTag("hp.boss_" + boss.id());
        entity.setCustomName(boss.color() + "" + ChatColor.BOLD + boss.displayName());
        entity.setCustomNameVisible(true);
        entity.setRemoveWhenFarAway(false);
        entity.setCanPickupItems(false);
        setAttribute(entity, Attribute.MAX_HEALTH, boss.maxHealth());
        setAttribute(entity, Attribute.ATTACK_DAMAGE, boss.attackDamage());
        setAttribute(entity, Attribute.MOVEMENT_SPEED, boss.movementSpeed());
        AttributeInstance maxHealth = entity.getAttribute(Attribute.MAX_HEALTH);
        if (maxHealth != null) {
            entity.setHealth(Math.max(1.0, Math.min(maxHealth.getValue(), boss.maxHealth())));
        }
        if (target != null && entity instanceof Mob mob) {
            mob.setTarget(target);
        }
        if (boss.id().equals("colossus")) {
            entity.removePotionEffect(PotionEffectType.INVISIBILITY);
            entity.setGlowing(true);
        }

        activeBosses.put(entity.getUniqueId(), entity);
        updateBossBar(entity);
    }

    private void setAttribute(LivingEntity entity, Attribute attribute, double value) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if (instance != null) {
            instance.setBaseValue(value);
        }
    }

    private boolean hasActiveBossNear(Location center, double radius) {
        if (center.getWorld() == null) {
            return false;
        }
        double radiusSquared = radius * radius;
        for (LivingEntity boss : activeBosses.values()) {
            if (boss.isDead() || !boss.isValid() || !boss.getWorld().equals(center.getWorld())) {
                continue;
            }
            if (boss.getLocation().distanceSquared(center) <= radiusSquared) {
                return true;
            }
        }
        for (Entity entity : center.getWorld().getNearbyEntities(center, radius, radius, radius)) {
            if (entity instanceof LivingEntity living && bossDefinition(living) != null && !living.isDead()) {
                return true;
            }
        }
        return false;
    }

    private void configureLoadedBosses() {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof LivingEntity living)) {
                    continue;
                }
                BossDefinition boss = bossDefinition(living);
                if (boss != null) {
                    configureBoss(living, boss, null);
                }
            }
        }
    }

    private void tickBossBars() {
        for (LivingEntity boss : new ArrayList<>(activeBosses.values())) {
            if (boss.isDead() || !boss.isValid()) {
                removeBossBar(boss.getUniqueId());
                continue;
            }
            updateBossBar(boss);
            tickBossAura(boss);
        }
    }

    private void updateBossBar(LivingEntity entity) {
        BossDefinition boss = bossDefinition(entity);
        if (boss == null || entity.isDead() || !entity.isValid()) {
            removeBossBar(entity.getUniqueId());
            return;
        }

        activeBosses.put(entity.getUniqueId(), entity);
        BossBar bar = bossBars.computeIfAbsent(entity.getUniqueId(),
                ignored -> Bukkit.createBossBar("", boss.barColor(), BarStyle.SEGMENTED_20));
        double maxHealth = Math.max(1.0, boss.maxHealth());
        AttributeInstance maxHealthAttribute = entity.getAttribute(Attribute.MAX_HEALTH);
        if (maxHealthAttribute != null) {
            maxHealth = Math.max(maxHealth, maxHealthAttribute.getValue());
        }
        double health = Math.max(0.0, Math.min(entity.getHealth(), maxHealth));
        bar.setTitle(boss.color() + "" + ChatColor.BOLD + boss.displayName() + ChatColor.GRAY
                + "  " + ChatColor.RED + (int) Math.ceil(health) + ChatColor.DARK_GRAY + "/"
                + (int) Math.ceil(maxHealth));
        bar.setColor(boss.barColor());
        bar.setProgress(Math.max(0.0, Math.min(1.0, health / maxHealth)));
        syncBossBarPlayers(entity, boss, bar);
    }

    private void syncBossBarPlayers(LivingEntity entity, BossDefinition boss, BossBar bar) {
        double radiusSquared = Math.max(BOSS_BAR_RADIUS, boss.rewardRadius()) * Math.max(BOSS_BAR_RADIUS, boss.rewardRadius());
        for (Player player : Bukkit.getOnlinePlayers()) {
            boolean show = player.getWorld().equals(entity.getWorld())
                    && player.getLocation().distanceSquared(entity.getLocation()) <= radiusSquared;
            if (show) {
                bar.addPlayer(player);
            } else {
                bar.removePlayer(player);
            }
        }
    }

    private void removeBossBar(UUID uuid) {
        BossBar bar = bossBars.remove(uuid);
        if (bar != null) {
            bar.removeAll();
        }
        activeBosses.remove(uuid);
    }

    private void clearBossBars() {
        for (BossBar bar : bossBars.values()) {
            bar.removeAll();
        }
        bossBars.clear();
        activeBosses.clear();
    }

    private void tickBossAura(LivingEntity boss) {
        BossDefinition definition = bossDefinition(boss);
        if (definition == null || !definition.id().equals("colossus")) {
            return;
        }

        Location center = boss.getLocation().add(0.0, 1.2, 0.0);
        boss.getWorld().spawnParticle(Particle.TRIAL_SPAWNER_DETECTION, center, 3, 0.45, 0.55, 0.45, 0.01);
        boss.getWorld().spawnParticle(Particle.CRIT, center, 5, 0.55, 0.7, 0.55, 0.02);
    }

    private void clearBossVisuals() {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                cleanupLegacyBossVisual(entity);
                cleanupBossAltarDisplay(entity);
            }
        }
    }

    private boolean cleanupLegacyBossVisual(Entity entity) {
        if (!entity.getScoreboardTags().contains("hp.boss_visual")) {
            return false;
        }
        entity.remove();
        return true;
    }

    private boolean cleanupBossAltarDisplay(Entity entity) {
        if (!entity.getScoreboardTags().contains("hp.boss_altar_display")) {
            return false;
        }
        entity.remove();
        return true;
    }

    private void markBossDefeated(Player player, String bossId) {
        getConfig().set(bossProgressPath(player, bossId), true);
        saveConfig();
    }

    private boolean hasDefeatedBoss(Player player, String bossId) {
        return getConfig().getBoolean(bossProgressPath(player, bossId), false);
    }

    private boolean hasDefeatedAllRequiredBosses(Player player) {
        for (String bossId : REQUIRED_INFINITY_BOSSES) {
            if (!hasDefeatedBoss(player, bossId)) {
                return false;
            }
        }
        return true;
    }

    private String missingRequiredBosses(Player player) {
        List<String> missing = new ArrayList<>();
        for (String bossId : REQUIRED_INFINITY_BOSSES) {
            if (!hasDefeatedBoss(player, bossId)) {
                BossDefinition boss = bossDefinitions.get(bossId);
                missing.add(boss == null ? bossId : boss.displayName());
            }
        }
        return missing.isEmpty() ? "ninguno" : String.join(", ", missing);
    }

    private String bossProgressPath(Player player, String bossId) {
        return "bosses." + player.getUniqueId() + "." + bossId;
    }

    private String bossKeyNameForBoss(String bossId) {
        for (String keyId : BOSS_KEY_IDS) {
            BossKeyInfo key = bossKeyInfo(keyId);
            if (key != null && bossId.equals(key.bossId())) {
                return key.displayName();
            }
        }
        return "sin llave";
    }

    private String bossVanillaStartLabel(String bossId) {
        return switch (bossId) {
            case "chaos" -> "matar Wither modificado";
            case "time" -> "matar Guarden modificado";
            case "dragon" -> "matar Dragona modificada";
            default -> "encuentro especial";
        };
    }

    private String normalizeBossId(String value) {
        return switch (normalize(value)) {
            case "coloso", "colossus", "golem" -> "colossus";
            case "eclipse", "lobo", "hombre_lobo" -> "eclipse";
            case "vacio", "vac_o", "void", "arana", "ara_a" -> "void";
            case "fenix", "f_nix", "phoenix", "blaze" -> "phoenix";
            case "caos", "chaos", "wither", "whiter" -> "chaos";
            case "tiempo", "time", "warden", "guarden", "guardian", "guardi_n" -> "time";
            case "dragon", "dragona", "ender_dragon" -> "dragon";
            case "celestial", "serafin", "seraf_n" -> "celestial";
            case "infinito", "infinity", "infinite" -> "infinity";
            default -> normalize(value);
        };
    }

    private boolean isSpecial(ItemStack stack, String id) {
        return id.equals(data(stack, specialKey));
    }

    private boolean canUseAngelGrace(Player player) {
        long readyAt = angelGraceCooldowns.getOrDefault(player.getUniqueId(), 0L);
        return System.currentTimeMillis() >= readyAt;
    }

    private boolean useSetCooldown(Player player, String setId, long cooldownMs) {
        String key = player.getUniqueId() + ":" + setId;
        long now = System.currentTimeMillis();
        if (now < armorCooldowns.getOrDefault(key, 0L)) {
            return false;
        }
        armorCooldowns.put(key, now + cooldownMs);
        return true;
    }

    private boolean tryDeathTotem(EntityDamageEvent event, Player player) {
        String totemId = heldDeathTotemId(player);
        if (totemId == null) {
            return false;
        }

        event.setCancelled(true);
        consumeHeldSpecial(player, totemId);
        Bukkit.getScheduler().runTask(this, () -> activateDeathTotem(player, totemId));
        return true;
    }

    private String heldDeathTotemId(Player player) {
        PlayerInventory inv = player.getInventory();
        String offhand = data(inv.getItemInOffHand(), specialKey);
        if (isDeathTotemId(offhand)) {
            return offhand;
        }

        String mainHand = data(inv.getItemInMainHand(), specialKey);
        if (isDeathTotemId(mainHand)) {
            return mainHand;
        }
        return null;
    }

    private boolean isDeathTotemId(String id) {
        return id != null && DEATH_TOTEM_IDS.contains(id);
    }

    private void consumeHeldSpecial(Player player, String specialId) {
        PlayerInventory inv = player.getInventory();
        if (consumeOneHeldSpecial(inv, inv.getItemInOffHand(), specialId, true)) {
            return;
        }
        consumeOneHeldSpecial(inv, inv.getItemInMainHand(), specialId, false);
    }

    private boolean consumeOneHeldSpecial(PlayerInventory inv, ItemStack stack, String specialId, boolean offHand) {
        if (!specialId.equals(data(stack, specialKey))) {
            return false;
        }
        if (stack.getAmount() <= 1) {
            if (offHand) {
                inv.setItemInOffHand(null);
            } else {
                inv.setItemInMainHand(null);
            }
        } else {
            stack.setAmount(stack.getAmount() - 1);
        }
        return true;
    }

    private void activateDeathTotem(Player player, String totemId) {
        if (!player.isOnline() || player.isDead()) {
            return;
        }

        player.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0, 1, 0), 45, 0.8, 1.0, 0.8, 0.08);
        player.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1.0f, 1.0f);

        switch (totemId) {
            case "phoenix_totem" -> activatePhoenixTotem(player);
            case "colossus_totem" -> activateColossusTotem(player);
            case "time_totem" -> activateTimeTotem(player);
            case "celestial_totem" -> activateCelestialTotem(player);
            case "totemcito" -> activateTotemcito(player);
            default -> {
            }
        }
    }

    private void activatePhoenixTotem(Player player) {
        revivePlayer(player, Math.max(1.0, player.getMaxHealth() * 0.5));
        applyEffect(player, PotionEffectType.FIRE_RESISTANCE, 220, 0);
        applyEffect(player, PotionEffectType.REGENERATION, 500, 0);
        startPhoenixFireAura(player);
        player.sendMessage(ChatColor.GOLD + "Totem del Fenix: reviviste con fuego protector.");
    }

    private void activateColossusTotem(Player player) {
        revivePlayer(player, 8.0);
        applyEffect(player, PotionEffectType.RESISTANCE, 300, 3);
        applyEffect(player, PotionEffectType.STRENGTH, 300, 2);
        pushNearbyMonsters(player, 8.0, 2.0, 0.55);
        player.getWorld().spawnParticle(Particle.EXPLOSION, player.getLocation(), 1, 0.0, 0.0, 0.0, 0.0);
        player.playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 0.9f, 0.8f);
        player.sendMessage(ChatColor.GRAY + "Totem del Coloso: resistencia, fuerza y empuje liberados.");
    }

    private void activateTimeTotem(Player player) {
        revivePlayer(player, 8.0);
        int affected = freezeNearbyMonsters(player, 14.0, 100L);
        applyEffect(player, PotionEffectType.SPEED, 200, 2);
        player.getWorld().spawnParticle(Particle.ENCHANT, player.getLocation().add(0, 1, 0), 55, 1.2, 1.0, 1.2, 0.25);
        if (affected == 0) {
            player.sendMessage(ChatColor.AQUA + "Totem del Tiempo: no habia mobs cerca, pero recibiste velocidad III.");
        } else {
            player.sendMessage(ChatColor.AQUA + "Totem del Tiempo: " + affected + " mobs congelados y velocidad III.");
        }
    }

    private void activateCelestialTotem(Player player) {
        revivePlayer(player, player.getMaxHealth());
        int affected = strikeNearbyMonsters(player, 8.0, 10.0);
        applyEffect(player, PotionEffectType.REGENERATION, 200, 2);
        if (affected == 0) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Totem Celestial: no habia enemigos cerca, pero reviviste completo.");
        } else {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Totem Celestial: vida completa y " + affected + " rayos cercanos.");
        }
    }

    private void activateTotemcito(Player player) {
        revivePlayer(player, 2.0);
        teleportRandomNearby(player, 50.0);
        PotionEffectType randomEffect = List.of(
                PotionEffectType.SPEED,
                PotionEffectType.REGENERATION,
                PotionEffectType.RESISTANCE,
                PotionEffectType.STRENGTH,
                PotionEffectType.FIRE_RESISTANCE,
                PotionEffectType.ABSORPTION).get(ThreadLocalRandom.current().nextInt(6));
        applyEffect(player, randomEffect, 200, 0);
        player.sendMessage(ChatColor.RED + "Totemcito: sobreviviste por poquito y fuiste movido al azar.");
    }

    private void revivePlayer(Player player, double health) {
        player.setHealth(Math.min(player.getMaxHealth(), Math.max(1.0, health)));
        player.setFallDistance(0.0f);
        player.setFireTicks(0);
    }

    private void startPhoenixFireAura(Player player) {
        new BukkitRunnable() {
            private int seconds;

            @Override
            public void run() {
                if (seconds++ >= 10 || !player.isOnline() || player.isDead()) {
                    cancel();
                    return;
                }
                player.getWorld().spawnParticle(Particle.FLAME, player.getLocation().add(0, 1, 0), 65, 2.6, 0.9, 2.6, 0.02);
                for (LivingEntity mob : nearbyMonsters(player.getLocation(), 5.0)) {
                    mob.setFireTicks(80);
                    mob.damage(3.0, player);
                }
            }
        }.runTaskTimer(this, 0L, 20L);
    }

    private int freezeNearbyMonsters(Player player, double radius, long ticks) {
        List<LivingEntity> mobs = nearbyMonsters(player.getLocation(), radius);
        if (mobs.isEmpty()) {
            return 0;
        }
        for (LivingEntity mob : mobs) {
            mob.setVelocity(new Vector(0, 0, 0));
            mob.setAI(false);
            mob.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, (int) ticks + 20, 255, true, false, true));
            mob.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, (int) ticks + 20, 10, true, false, true));
        }
        new BukkitRunnable() {
            private long lived;

            @Override
            public void run() {
                if (lived++ >= ticks) {
                    cancel();
                    return;
                }
                for (LivingEntity mob : mobs) {
                    if (!mob.isDead()) {
                        mob.setVelocity(new Vector(0, 0, 0));
                    }
                }
            }
        }.runTaskTimer(this, 0L, 1L);
        Bukkit.getScheduler().runTaskLater(this, () -> {
            for (LivingEntity mob : mobs) {
                if (!mob.isDead()) {
                    mob.setAI(true);
                }
            }
        }, ticks);
        player.getWorld().spawnParticle(Particle.SNOWFLAKE, player.getLocation().add(0, 1, 0), 80, radius * 0.35, 1.2, radius * 0.35, 0.04);
        player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 0.65f, 0.6f);
        return mobs.size();
    }

    private int strikeNearbyMonsters(Player player, double radius, double damage) {
        List<LivingEntity> mobs = nearbyMonsters(player.getLocation(), radius);
        for (LivingEntity mob : mobs) {
            Location target = mob.getLocation();
            player.getWorld().strikeLightningEffect(target);
            player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, target.add(0, 1, 0), 35, 0.45, 0.9, 0.45, 0.12);
            mob.damage(damage, player);
        }
        if (!mobs.isEmpty()) {
            player.playSound(player.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 1.0f, 1.25f);
        }
        return mobs.size();
    }

    private void teleportRandomNearby(Player player, double maxDistance) {
        Location origin = player.getLocation();
        World world = player.getWorld();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int attempt = 0; attempt < 12; attempt++) {
            double angle = random.nextDouble(Math.PI * 2.0);
            double distance = random.nextDouble(12.0, maxDistance + 1.0);
            int x = origin.getBlockX() + (int) Math.round(Math.cos(angle) * distance);
            int z = origin.getBlockZ() + (int) Math.round(Math.sin(angle) * distance);
            Location target = world.getHighestBlockAt(x, z).getLocation().add(0.5, 1.0, 0.5);
            target.setYaw(origin.getYaw());
            target.setPitch(origin.getPitch());
            if (isSafeTeleport(target)) {
                player.teleport(target);
                player.playSound(target, Sound.ENTITY_ENDERMAN_TELEPORT, 0.9f, 1.2f);
                return;
            }
        }
        player.playSound(origin, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 0.7f);
    }

    private boolean isSafeTeleport(Location location) {
        return location.getBlock().isPassable()
                && location.clone().add(0, 1, 0).getBlock().isPassable()
                && location.clone().add(0, -1, 0).getBlock().getType().isSolid();
    }

    private void activateAngelGrace(Player player) {
        if (!player.isOnline() || player.isDead() || !hasFullAngelSet(player)) {
            return;
        }

        player.setHealth(Math.min(player.getMaxHealth(), 8.0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 220, 2, true, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 300, 2, true, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 180, 1, true, false, true));
        player.setFireTicks(0);
        player.sendMessage(ChatColor.DARK_PURPLE + "El Angel Caido nego tu muerte. Cooldown: 7 minutos.");
        player.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 0.9f, 1.4f);
    }

    private void activatePhoenixRebirth(Player player) {
        if (!player.isOnline() || player.isDead() || !hasFullSet(player, "phoenix")) {
            return;
        }

        player.setHealth(Math.max(1.0, player.getMaxHealth() * 0.5));
        player.setFireTicks(0);
        applyEffect(player, PotionEffectType.FIRE_RESISTANCE, 260, 0);
        applyEffect(player, PotionEffectType.REGENERATION, 260, 2);
        damageNearbyMonsters(player, 6.0, 8.0);
        player.getWorld().strikeLightningEffect(player.getLocation());
        player.sendMessage(ChatColor.GOLD + "El Fenix te levanto de las cenizas. Cooldown: 15 minutos.");
        player.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 0.9f, 0.8f);
    }

    private void activateVoidShift(Player player) {
        if (!player.isOnline() || player.isDead() || !hasFullSet(player, "void")) {
            return;
        }

        Location target = player.getLocation().clone().add(player.getLocation().getDirection().normalize().multiply(-10.0));
        if (!target.getBlock().isPassable() || !target.clone().add(0, 1, 0).getBlock().isPassable()) {
            target = player.getLocation();
        }
        player.teleport(target);
        player.setHealth(Math.min(player.getMaxHealth(), 6.0));
        applyEffect(player, PotionEffectType.INVISIBILITY, 220, 0);
        applyEffect(player, PotionEffectType.ABSORPTION, 220, 1);
        player.sendMessage(ChatColor.DARK_PURPLE + "El Vacio rechazo tu muerte. Cooldown: 10 minutos.");
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.9f, 0.7f);
    }

    private void activateLeviathanGuardian(Player player) {
        if (!player.isOnline() || !hasFullSet(player, "leviathan")) {
            return;
        }

        LivingEntity guardian = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ELDER_GUARDIAN);
        guardian.setCustomName(ChatColor.AQUA + "Guardian del Leviatan");
        guardian.setCustomNameVisible(false);
        guardian.setInvulnerable(true);
        guardian.setAI(false);
        player.sendMessage(ChatColor.AQUA + "El Leviatan invoco un guardian aliado por 20s.");
        player.playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 0.6f, 1.6f);

        new BukkitRunnable() {
            private int seconds;

            @Override
            public void run() {
                if (seconds++ >= 20 || guardian.isDead() || !player.isOnline()) {
                    guardian.remove();
                    cancel();
                    return;
                }
                for (LivingEntity mob : nearbyMonsters(guardian.getLocation(), 7.0)) {
                    mob.damage(4.0, player);
                    mob.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 60, 1, true, false, true));
                }
            }
        }.runTaskTimer(this, 0L, 20L);
    }

    private void activateDragonRoar(Player player) {
        pushNearbyMonsters(player, 9.0, 1.8, 0.45);
        damageNearbyMonsters(player, 9.0, 6.0);
        player.sendMessage(ChatColor.DARK_RED + "Rugido Draconico: enemigos empujados.");
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.75f, 1.4f);
    }

    private void activateColossusSlam(Player player) {
        for (LivingEntity mob : nearbyMonsters(player.getLocation(), 7.0)) {
            mob.damage(10.0, player);
            mob.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 5, true, false, true));
            mob.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 2, true, false, true));
        }
        player.sendMessage(ChatColor.GRAY + "Impacto del Coloso: area aturdida.");
        player.playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 0.8f, 0.75f);
    }

    private void activateEclipseRay(Player player) {
        for (LivingEntity mob : nearbyMonsters(player.getLocation(), 12.0)) {
            player.getWorld().strikeLightningEffect(mob.getLocation());
            mob.damage(9.0, player);
        }
        player.sendMessage(ChatColor.YELLOW + "Rayo del Eclipse liberado.");
        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.8f, 1.5f);
    }

    private void activateTimeFreeze(Player player) {
        int affected = freezeNearbyMonsters(player, 10.0, 100L);
        player.sendMessage(ChatColor.AQUA + "Tiempo congelado: " + affected + " mobs detenidos por 5s.");
        player.playSound(player.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 0.8f, 1.8f);
    }

    private void activateCelestialStorm(Player player) {
        for (LivingEntity mob : nearbyMonsters(player.getLocation(), 14.0)) {
            player.getWorld().strikeLightningEffect(mob.getLocation());
            mob.damage(12.0, player);
        }
        player.sendMessage(ChatColor.GOLD + "Tormenta Celestial desatada.");
        player.playSound(player.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 0.8f, 1.4f);
    }

    private void activateInfinityCore(Player player, boolean fatal) {
        if (!player.isOnline() || !hasFullSet(player, "infinity")) {
            return;
        }

        long now = System.currentTimeMillis();
        infinityImmuneUntil.put(player.getUniqueId(), now + 10_000L);
        infinityDamageUntil.put(player.getUniqueId(), now + 10_000L);
        if (fatal) {
            player.setHealth(Math.min(player.getMaxHealth(), 12.0));
        }
        applyEffect(player, PotionEffectType.ABSORPTION, 240, 4);
        applyEffect(player, PotionEffectType.STRENGTH, 240, 3);
        removeNegativeEffects(player);
        player.sendMessage(ChatColor.LIGHT_PURPLE + "Nucleo del Infinito activo: 10s invulnerable y daño x3.");
        player.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1.0f, 0.55f);
    }

    private void applyEffect(Player player, PotionEffectType type, int duration, int amplifier) {
        player.addPotionEffect(new PotionEffect(type, duration, amplifier, true, false, true));
    }

    private void removeNegativeEffects(Player player) {
        player.removePotionEffect(PotionEffectType.POISON);
        player.removePotionEffect(PotionEffectType.WITHER);
        player.removePotionEffect(PotionEffectType.SLOWNESS);
        player.removePotionEffect(PotionEffectType.WEAKNESS);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        player.removePotionEffect(PotionEffectType.DARKNESS);
        player.removePotionEffect(PotionEffectType.HUNGER);
        player.removePotionEffect(PotionEffectType.NAUSEA);
        player.removePotionEffect(PotionEffectType.MINING_FATIGUE);
        player.removePotionEffect(PotionEffectType.LEVITATION);
    }

    private void slowNearbyMonsters(Player player, double radius, int duration, int amplifier) {
        for (LivingEntity mob : nearbyMonsters(player.getLocation(), radius)) {
            mob.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, duration, amplifier, true, false, true));
        }
    }

    private void damageNearbyMonsters(Player player, double radius, double damage) {
        for (LivingEntity mob : nearbyMonsters(player.getLocation(), radius)) {
            mob.damage(damage, player);
        }
    }

    private void pushNearbyMonsters(Player player, double radius, double horizontalPower, double verticalPower) {
        for (LivingEntity mob : nearbyMonsters(player.getLocation(), radius)) {
            Vector direction = mob.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
            direction.multiply(horizontalPower);
            direction.setY(verticalPower);
            mob.setVelocity(direction);
        }
    }

    private List<LivingEntity> nearbyMonsters(Location center, double radius) {
        List<LivingEntity> mobs = new ArrayList<>();
        for (Entity entity : center.getWorld().getNearbyEntities(center, radius, radius, radius)) {
            if (entity instanceof Monster monster && !monster.isDead()) {
                mobs.add(monster);
            }
        }
        return mobs;
    }

    private void registerCustomMaterials() {
        addCustomMaterial("angel_essence", Material.ECHO_SHARD, ChatColor.WHITE, "Esencia de Angel", 350,
                "Material para craftear la Armadura Angel Caido.");
        addCustomMaterial("titanium_ingot", Material.IRON_INGOT, ChatColor.GRAY, "Lingote de Titanio", 450,
                "Metal pesado para la Armadura de Titanio.");
        addCustomMaterial("sea_essence", Material.HEART_OF_THE_SEA, ChatColor.AQUA, "Esencia del Mar", 650,
                "Nucleo acuatico para la Armadura del Leviatan.");
        addCustomMaterial("golem_heart", Material.REDSTONE_BLOCK, ChatColor.GRAY, "Corazon de Golem", 900,
                "Motor pesado para la Armadura del Coloso.");
        addCustomMaterial("solar_essence", Material.GLOWSTONE_DUST, ChatColor.YELLOW, "Esencia Solar", 1050,
                "Luz comprimida para la Armadura del Eclipse.");
        addCustomMaterial("void_fragment", Material.ENDER_EYE, ChatColor.DARK_PURPLE, "Fragmento del Vacio", 1200,
                "Restos de energia ender para la Armadura del Vacio.");
        addCustomMaterial("phoenix_feather", Material.FEATHER, ChatColor.GOLD, "Pluma de Fenix", 1350,
                "Catalizador ardiente para la Armadura del Fenix.");
        addCustomMaterial("chaos_fragment", Material.TNT, ChatColor.DARK_RED, "Fragmento del Caos", 1450,
                "Energia inestable para la Armadura del Caos.");
        addCustomMaterial("temporal_crystal", Material.AMETHYST_SHARD, ChatColor.AQUA, "Cristal Temporal", 1650,
                "Cristal raro para la Armadura del Tiempo.");
        addCustomMaterial("dragon_scale", Material.DRAGON_BREATH, ChatColor.DARK_RED, "Escama de Dragon", 1900,
                "Escama viva para la Armadura del Dragon.");
        addCustomMaterial("celestial_fragment", Material.NETHER_STAR, ChatColor.LIGHT_PURPLE, "Fragmento Celestial", 2200,
                "Fragmento divino para la Armadura Celestial.");
        addCustomMaterial("infinity_core", Material.END_CRYSTAL, ChatColor.LIGHT_PURPLE, "Nucleo del Infinito", 5000,
                "Nucleo final para la Armadura del Infinito.");
    }

    private void registerBosses() {
        bossDefinitions.clear();
        addBoss("colossus", "Coloso Ancestral", ChatColor.GRAY, BarColor.WHITE, EntityType.IRON_GOLEM,
                220.0, 14.0, 0.26, "golem_heart", "eclipse_key", 300, true, 64.0);
        addBoss("eclipse", "Hombre Lobo del Eclipse", ChatColor.YELLOW, BarColor.YELLOW, EntityType.WOLF,
                180.0, 11.0, 0.38, "solar_essence", "void_key", 350, true, 64.0);
        addBoss("void", "Arana del Vacio", ChatColor.DARK_PURPLE, BarColor.PURPLE, EntityType.SPIDER,
                210.0, 12.0, 0.34, "void_fragment", "phoenix_key", 400, true, 64.0);
        addBoss("phoenix", "Fenix Ardiente", ChatColor.GOLD, BarColor.RED, EntityType.BLAZE,
                240.0, 13.0, 0.32, "phoenix_feather", null, 450, true, 72.0);
        addBoss("chaos", "Wither del Caos", ChatColor.DARK_RED, BarColor.RED, EntityType.WITHER,
                450.0, 15.0, 0.34, "chaos_fragment", "chaos_star", 700, false, 96.0);
        addBoss("time", "Guarden del Tiempo", ChatColor.AQUA, BarColor.BLUE, EntityType.WARDEN,
                560.0, 20.0, 0.30, "temporal_crystal", "warden_heart", 800, false, 96.0);
        addBoss("dragon", "Dragona Ancestral", ChatColor.DARK_RED, BarColor.PURPLE, EntityType.ENDER_DRAGON,
                520.0, 18.0, 0.30, "dragon_scale", "dragon_heart", 900, false, 160.0);
        addBoss("celestial", "Serafin Celestial", ChatColor.LIGHT_PURPLE, BarColor.PINK, EntityType.BREEZE,
                420.0, 16.0, 0.36, "celestial_fragment", "infinity_key", 1000, true, 96.0);
        addBoss("infinity", "Avatar del Infinito", ChatColor.LIGHT_PURPLE, BarColor.PURPLE, EntityType.ENDERMAN,
                650.0, 22.0, 0.34, "infinity_core", null, 1500, true, 128.0);
    }

    private void addBoss(String id, String displayName, ChatColor color, BarColor barColor, EntityType entityType,
            double maxHealth, double attackDamage, double movementSpeed, String dropMaterialId,
            String nextSpecialDropId, int points, boolean altarBoss, double rewardRadius) {
        bossDefinitions.put(id, new BossDefinition(id, displayName, color, barColor, entityType, maxHealth,
                attackDamage, movementSpeed, dropMaterialId, nextSpecialDropId, points, altarBoss, rewardRadius));
    }

    private void registerArmorSets() {
        addArmorSet("titanium", "Armadura de Titanio", ChatColor.GRAY, 3, 3, "titanium_ingot",
                Material.NETHERITE_SCRAP, Material.IRON_INGOT,
                List.of("Resistencia I", "Mayor durabilidad", "3s de inmunidad al recibir daño"));
        addArmorSet("leviathan", "Armadura del Leviatan", ChatColor.AQUA, 4, 4, "sea_essence",
                Material.PRISMARINE_SHARD, Material.PRISMARINE_CRYSTALS,
                List.of("Respiracion infinita", "Velocidad acuatica", "Invoca un guardian aliado 20s"));
        addArmorSet("colossus", "Armadura del Coloso", ChatColor.GRAY, 5, 5, "golem_heart",
                Material.IRON_BLOCK, Material.IRON_INGOT,
                List.of("Fuerza III", "Lentitud II", "Golpe de area que aturde mobs"));
        addArmorSet("eclipse", "Armadura del Eclipse", ChatColor.YELLOW, 6, 5, "solar_essence",
                Material.GHAST_TEAR, Material.GLOWSTONE_DUST,
                List.of("Vision nocturna", "Limpia Wither", "Rayo de energia al recibir daño"));
        addArmorSet("void", "Armadura del Vacio", ChatColor.DARK_PURPLE, 7, 6, "void_fragment",
                Material.ENDER_PEARL, Material.OBSIDIAN,
                List.of("Invisibilidad pasiva", "Teletransporte de emergencia al recibir daño fatal"));
        addArmorSet("phoenix", "Armadura del Fenix", ChatColor.GOLD, 8, 7, "phoenix_feather",
                Material.BLAZE_POWDER, Material.MAGMA_CREAM,
                List.of("Inmunidad al fuego", "Regeneracion menor", "Revive con 50% de vida"));
        addArmorSet("chaos", "Armadura del Caos", ChatColor.DARK_RED, 10, 8, "chaos_fragment",
                Material.TNT, Material.GUNPOWDER,
                List.of("Velocidad II", "Fuerza II", "Daño x2 durante 10s"));
        addArmorSet("time", "Armadura del Tiempo", ChatColor.AQUA, 12, 9, "temporal_crystal",
                Material.CLOCK, Material.AMETHYST_SHARD,
                List.of("Ralentiza mobs cercanos", "Congela mobs por 5s al recibir daño"));
        addArmorSet("dragon", "Armadura del Dragon", ChatColor.DARK_RED, 14, 10, "dragon_scale",
                Material.DRAGON_BREATH, Material.END_CRYSTAL,
                List.of("Resistencia III", "Resistencia al empuje", "Rugido que empuja enemigos"));
        addArmorSet("celestial", "Armadura Celestial", ChatColor.LIGHT_PURPLE, 16, 11, "celestial_fragment",
                Material.NETHER_STAR, Material.DIAMOND_BLOCK,
                List.of("Regeneracion III", "Resistencia IV", "Lluvia de rayos sobre enemigos"));
        addArmorSet("infinity", "Armadura del Infinito", ChatColor.LIGHT_PURPLE, 20, 14, "infinity_core",
                Material.NETHERITE_BLOCK, Material.NETHER_STAR,
                List.of("Limpia efectos negativos", "10s invulnerable", "Daño x3 durante la pasiva"));
    }

    private void addCustomMaterial(String id, Material material, ChatColor color, String displayName, int cost, String description) {
        customMaterials.put(id, new CustomMaterial(id, material, color, displayName, cost, description));
    }

    private void addArmorSet(String id, String displayName, ChatColor color, int armorBonus, int toughnessBonus,
            String materialId, Material secondary, Material tertiary, List<String> bonusLore) {
        armorSets.put(id, new ArmorSet(id, displayName, color, armorBonus, toughnessBonus,
                materialId, secondary, tertiary, bonusLore));
    }

    private void registerGearFamilies() {
        gearFamilies.clear();
        customGears.clear();
        addGearFamily("angel", "Angel Caido", "del Angel Caido", ChatColor.WHITE, "angel_essence",
                Material.FEATHER, Material.DIAMOND, 1.0, "Cura al portador al golpear.");
        addGearFamily("titanium", "Titanio", "de Titanio", ChatColor.GRAY, "titanium_ingot",
                Material.NETHERITE_SCRAP, Material.IRON_INGOT, 2.0, "Da resistencia breve al golpear.");
        addGearFamily("leviathan", "Leviatan", "del Leviatan", ChatColor.AQUA, "sea_essence",
                Material.PRISMARINE_SHARD, Material.PRISMARINE_CRYSTALS, 3.0, "Ralentiza enemigos con energia marina.");
        addGearFamily("colossus", "Coloso", "del Coloso", ChatColor.GRAY, "golem_heart",
                Material.IRON_BLOCK, Material.IRON_INGOT, 4.5, "Aturde enemigos con impacto pesado.");
        addGearFamily("eclipse", "Eclipse", "del Eclipse", ChatColor.YELLOW, "solar_essence",
                Material.GHAST_TEAR, Material.GLOWSTONE_DUST, 5.5, "Marchita enemigos con luz corrupta.");
        addGearFamily("void", "Vacio", "del Vacio", ChatColor.DARK_PURPLE, "void_fragment",
                Material.ENDER_PEARL, Material.OBSIDIAN, 6.5, "Ciega enemigos con energia del Vacio.");
        addGearFamily("phoenix", "Fenix", "del Fenix", ChatColor.GOLD, "phoenix_feather",
                Material.BLAZE_POWDER, Material.MAGMA_CREAM, 7.5, "Quema enemigos con fuego del Fenix.");
        addGearFamily("chaos", "Caos", "del Caos", ChatColor.DARK_RED, "chaos_fragment",
                Material.TNT, Material.GUNPOWDER, 9.0, "Explota energia inestable y hace dano extra.");
        addGearFamily("time", "Tiempo", "del Tiempo", ChatColor.AQUA, "temporal_crystal",
                Material.CLOCK, Material.AMETHYST_SHARD, 10.5, "Congela brevemente al enemigo golpeado.");
        addGearFamily("dragon", "Dragon", "del Dragon", ChatColor.DARK_RED, "dragon_scale",
                Material.DRAGON_BREATH, Material.END_CRYSTAL, 12.0, "Empuja enemigos con fuerza draconica.");
        addGearFamily("celestial", "Celestial", "Celestial", ChatColor.LIGHT_PURPLE, "celestial_fragment",
                Material.NETHER_STAR, Material.DIAMOND_BLOCK, 14.0, "Invoca un rayo sobre el enemigo.");
        addGearFamily("infinity", "Infinito", "del Infinito", ChatColor.LIGHT_PURPLE, "infinity_core",
                Material.NETHERITE_BLOCK, Material.NETHER_STAR, 18.0, "Rompe enemigos con energia infinita.");
    }

    private void addGearFamily(String id, String displayName, String itemSuffix, ChatColor color, String materialId,
            Material secondary, Material tertiary, double power, String traitLore) {
        GearFamily family = new GearFamily(id, displayName, itemSuffix, color, materialId, secondary, tertiary, power, traitLore);
        gearFamilies.put(id, family);
        for (GearType type : GearType.values()) {
            String gearId = gearId(family, type);
            customGears.put(gearId, new CustomGear(gearId, family, type));
        }
    }

    private void registerRecipes() {
        recipeBookKeys.clear();
        recipeProgressFamilies.clear();
        addAngelEssenceRecipe();
        addBossKeyRecipes();
        addAngelRecipe("angel_chestplate_recipe", angelArmor("chestplate"), 'C', Material.DIAMOND_CHESTPLATE,
                "D D",
                "PCP",
                "EEE");
        addAngelRecipe("angel_leggings_recipe", angelArmor("leggings"), 'L', Material.DIAMOND_LEGGINGS,
                "ELE",
                "P P",
                "D D");
        addAngelRecipe("angel_helmet_recipe", angelArmor("helmet"), 'H', Material.DIAMOND_HELMET,
                "EHE",
                "P P");
        addAngelRecipe("angel_boots_recipe", angelArmor("boots"), '\0', null,
                "P P",
                "E E");

        for (ArmorSet set : armorSets.values()) {
            addArmorSetRecipes(set);
        }
        addGearRecipes();
        addDeathTotemRecipes();
    }

    private void addArmorSetRecipes(ArmorSet set) {
        addArmorRecipe(set, ArmorPiece.CHESTPLATE,
                "SAS",
                "MXM",
                "MMM");
        addArmorRecipe(set, ArmorPiece.LEGGINGS,
                "MAM",
                "S S",
                "X X");
        addArmorRecipe(set, ArmorPiece.HELMET,
                "MAM",
                "S S");
        addArmorRecipe(set, ArmorPiece.BOOTS,
                "S S",
                "MAM");
    }

    private void addArmorRecipe(ArmorSet set, ArmorPiece piece, String... shape) {
        NamespacedKey key = new NamespacedKey(this, set.id() + "_" + piece.id() + "_recipe");
        Bukkit.removeRecipe(key);

        ShapedRecipe recipe = new ShapedRecipe(key, armorItem(set, piece));
        recipe.setGroup("hardcoreplus_" + set.id() + "_" + piece.id());
        recipe.setCategory(CraftingBookCategory.EQUIPMENT);
        recipe.shape(shape);
        if (shapeContains(shape, 'A')) {
            recipe.setIngredient('A', piece.material());
        }
        if (shapeContains(shape, 'M')) {
            recipe.setIngredient('M', new RecipeChoice.ExactChoice(customMaterialItem(set.materialId())));
        }
        if (shapeContains(shape, 'S')) {
            recipe.setIngredient('S', set.secondary());
        }
        if (shapeContains(shape, 'X')) {
            recipe.setIngredient('X', set.tertiary());
        }
        Bukkit.addRecipe(recipe);
        recipeBookKeys.add(key);
        recipeProgressFamilies.put(key, set.id());
    }

    private void addBossKeyRecipes() {
        addColossusKeyRecipe();
        addCelestialKeyRecipe();
    }

    private void addColossusKeyRecipe() {
        NamespacedKey key = new NamespacedKey(this, "colossus_key_recipe");
        Bukkit.removeRecipe(key);

        ShapedRecipe recipe = new ShapedRecipe(key, bossKeyItem("colossus_key"));
        recipe.setGroup("hardcoreplus_colossus_key");
        recipe.setCategory(CraftingBookCategory.MISC);
        recipe.shape(
                "AG",
                "TL");
        recipe.setIngredient('A', new RecipeChoice.ExactChoice(customMaterialItem("angel_essence")));
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('T', new RecipeChoice.ExactChoice(customMaterialItem("titanium_ingot")));
        recipe.setIngredient('L', new RecipeChoice.ExactChoice(customMaterialItem("sea_essence")));
        Bukkit.addRecipe(recipe);
        recipeBookKeys.add(key);
        recipeProgressFamilies.put(key, "colossus");
    }

    private void addCelestialKeyRecipe() {
        NamespacedKey key = new NamespacedKey(this, "celestial_key_recipe");
        Bukkit.removeRecipe(key);

        ShapedRecipe recipe = new ShapedRecipe(key, bossKeyItem("celestial_key"));
        recipe.setGroup("hardcoreplus_celestial_key");
        recipe.setCategory(CraftingBookCategory.MISC);
        recipe.shape("CWD");
        recipe.setIngredient('C', new RecipeChoice.ExactChoice(bossRelicItem("chaos_star")));
        recipe.setIngredient('W', new RecipeChoice.ExactChoice(bossRelicItem("warden_heart")));
        recipe.setIngredient('D', new RecipeChoice.ExactChoice(bossRelicItem("dragon_heart")));
        Bukkit.addRecipe(recipe);
        recipeBookKeys.add(key);
        recipeProgressFamilies.put(key, "celestial");
    }

    private void addDeathTotemRecipes() {
        addDeathTotemRecipe("phoenix_totem_recipe", deathTotem("phoenix_totem"), "angel_essence", Material.BLAZE_POWDER);
        addDeathTotemRecipe("colossus_totem_recipe", deathTotem("colossus_totem"), "golem_heart", Material.IRON_INGOT);
        addDeathTotemRecipe("time_totem_recipe", deathTotem("time_totem"), "temporal_crystal", Material.CLOCK);
        addDeathTotemRecipe("celestial_totem_recipe", deathTotem("celestial_totem"), "celestial_fragment", Material.NETHER_STAR);
    }

    private void addGearRecipes() {
        for (CustomGear gear : customGears.values()) {
            addGearRecipe(gear);
        }
    }

    private void addGearRecipe(CustomGear gear) {
        NamespacedKey key = new NamespacedKey(this, gear.id() + "_recipe");
        Bukkit.removeRecipe(key);

        ShapedRecipe recipe = new ShapedRecipe(key, gearItem(gear));
        recipe.setGroup("hardcoreplus_" + gear.id());
        recipe.setCategory(CraftingBookCategory.EQUIPMENT);
        recipe.shape(gear.type().shape());
        recipe.setIngredient('A', gear.type().baseIngredient());
        recipe.setIngredient('M', new RecipeChoice.ExactChoice(customMaterialItem(gear.family().materialId())));
        if (shapeContains(gear.type().shape(), 'S')) {
            recipe.setIngredient('S', gear.family().secondary());
        }
        if (shapeContains(gear.type().shape(), 'X')) {
            recipe.setIngredient('X', gear.family().tertiary());
        }
        Bukkit.addRecipe(recipe);
        recipeBookKeys.add(key);
        recipeProgressFamilies.put(key, gear.family().id());
    }

    private void addDeathTotemRecipe(String keyName, ItemStack result, String materialId, Material vanillaMaterial) {
        NamespacedKey key = new NamespacedKey(this, keyName);
        Bukkit.removeRecipe(key);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.setGroup("hardcoreplus_" + keyName);
        recipe.setCategory(CraftingBookCategory.MISC);
        recipe.shape(
                " S ",
                "MTM",
                " S ");
        recipe.setIngredient('S', vanillaMaterial);
        recipe.setIngredient('M', new RecipeChoice.ExactChoice(customMaterialItem(materialId)));
        recipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
        Bukkit.addRecipe(recipe);
        recipeBookKeys.add(key);
        recipeProgressFamilies.put(key, progressionFamilyForItem(data(result, specialKey)));
    }

    private void addAngelEssenceRecipe() {
        NamespacedKey key = new NamespacedKey(this, "angel_essence_recipe");
        Bukkit.removeRecipe(key);

        ShapedRecipe recipe = new ShapedRecipe(key, customMaterialItem("angel_essence"));
        recipe.setGroup("hardcoreplus_angel_essence");
        recipe.setCategory(CraftingBookCategory.MISC);
        recipe.shape(
                " D ",
                "F E",
                " G ");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('F', Material.FEATHER);
        recipe.setIngredient('E', Material.EMERALD);
        recipe.setIngredient('G', Material.GOLD_INGOT);
        Bukkit.addRecipe(recipe);
        recipeBookKeys.add(key);
        recipeProgressFamilies.put(key, "angel");
    }

    private void addAngelRecipe(String keyName, ItemStack result, char armorKey, Material diamondArmor, String... shape) {
        NamespacedKey key = new NamespacedKey(this, keyName);
        Bukkit.removeRecipe(key);

        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.setGroup("hardcoreplus_" + keyName);
        recipe.setCategory(CraftingBookCategory.EQUIPMENT);
        recipe.shape(shape);
        if (shapeContains(shape, 'E')) {
            recipe.setIngredient('E', new RecipeChoice.ExactChoice(angelEssence()));
        }
        if (shapeContains(shape, 'P')) {
            recipe.setIngredient('P', Material.FEATHER);
        }
        if (shapeContains(shape, 'D')) {
            recipe.setIngredient('D', Material.DIAMOND);
        }
        if (armorKey != '\0' && diamondArmor != null) {
            recipe.setIngredient(armorKey, diamondArmor);
        }
        Bukkit.addRecipe(recipe);
        recipeBookKeys.add(key);
        recipeProgressFamilies.put(key, "angel");
    }

    private void unlockRecipeBook(Player player) {
        if (recipeBookKeys.isEmpty()) {
            return;
        }

        List<NamespacedKey> unlocked = new ArrayList<>();
        List<NamespacedKey> locked = new ArrayList<>();
        for (NamespacedKey key : recipeBookKeys) {
            String familyId = recipeProgressFamilies.get(key);
            if (familyId == null || isSetUnlocked(player, familyId)) {
                unlocked.add(key);
            } else {
                locked.add(key);
            }
        }
        if (!unlocked.isEmpty()) {
            player.discoverRecipes(unlocked);
        }
        if (!locked.isEmpty()) {
            player.undiscoverRecipes(locked);
        }
    }

    private boolean shapeContains(String[] shape, char key) {
        for (String row : shape) {
            if (row.indexOf(key) >= 0) {
                return true;
            }
        }
        return false;
    }

    private void registerBuyEntries() {
        addBuy("waystone_token", 750, waystoneToken(), Material.LODESTONE,
                "Cristal de Ruta", "Permite guardar un lugar para volver despues.");
        addBuy("revive_totem", 900, reviveTotem(), Material.CARROT_ON_A_STICK,
                "Totem de Resurreccion", "Objeto especial para revivir a un compañero eliminado.");
        for (CustomMaterial material : customMaterials.values()) {
            String familyId = progressionFamilyForItem(material.id());
            if (phaseForSet(familyId) > 0) {
                addBuy(material.id(), material.cost(), customMaterialItem(material.id()), material.material(),
                        material.displayName(), material.description());
            }
        }
        addBuy("wind_charge", 450, stack(Material.WIND_CHARGE, 8), Material.WIND_CHARGE,
                "Cargas de Viento", "8 cargas para movilidad y escapes.");
        addBuy("enchanted_apple", 1200, stack(Material.ENCHANTED_GOLDEN_APPLE, 1), Material.ENCHANTED_GOLDEN_APPLE,
                "Manzana Encantada", "Curacion extrema para situaciones imposibles.");
        addBuy("undying_totem", 3000, stack(Material.TOTEM_OF_UNDYING, 1), Material.TOTEM_OF_UNDYING,
                "Totem de Inmortalidad", "Muy caro porque salva una run.");
    }

    private void registerSellEntries() {
        addSell("diamond", Material.DIAMOND, 35, "Diamante");
        addSell("emerald", Material.EMERALD, 15, "Esmeralda");
        addSell("ancient_debris", Material.ANCIENT_DEBRIS, 80, "Escombros ancestrales");
        addSell("netherite_scrap", Material.NETHERITE_SCRAP, 90, "Fragmento de netherita");
        addSell("netherite_ingot", Material.NETHERITE_INGOT, 360, "Lingote de netherita");
        addSell("echo_shard", Material.ECHO_SHARD, 120, "Fragmento de eco");
        addSell("blaze_rod", Material.BLAZE_ROD, 14, "Vara de blaze");
        addSell("ender_pearl", Material.ENDER_PEARL, 10, "Perla de ender");
        addSell("phantom_membrane", Material.PHANTOM_MEMBRANE, 18, "Membrana de phantom");
        addSell("ghast_tear", Material.GHAST_TEAR, 55, "Lagrima de ghast");
    }

    private void openShopMain(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, SHOP_TITLE);
        fill(inv);

        inv.setItem(4, item(Material.NETHER_STAR, ChatColor.GOLD + "Tus puntos",
                List.of(ChatColor.GRAY + "Tienes " + ChatColor.YELLOW + getScore(player, POINTS_OBJECTIVE)
                        + ChatColor.GRAY + " puntos.")));
        inv.setItem(10, actionItem(Material.CHEST, ChatColor.GREEN + "" + ChatColor.BOLD + "Comprar",
                List.of(ChatColor.GRAY + "Armas, armaduras y objetos especiales.",
                        ChatColor.YELLOW + "Click para entrar."),
                "buy"));
        inv.setItem(13, actionItem(Material.RECOVERY_COMPASS, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Waystones",
                List.of(ChatColor.GRAY + "Lugares guardados para volver rapido.",
                        ChatColor.YELLOW + "Click para abrir."),
                "waystones"));
        inv.setItem(16, actionItem(Material.HOPPER, ChatColor.AQUA + "" + ChatColor.BOLD + "Vender",
                List.of(ChatColor.GRAY + "Cambia minerales y drops raros por puntos.",
                        ChatColor.YELLOW + "Click para entrar."),
                "sell"));
        inv.setItem(22, actionItem(Material.PLAYER_HEAD, ChatColor.YELLOW + "Perfil",
                List.of(ChatColor.GRAY + "Ver tus estadisticas actuales."),
                "profile"));

        player.openInventory(inv);
    }

    private void openBuyShop(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, BUY_TITLE);
        fill(inv);

        inv.setItem(4, item(Material.NETHER_STAR, ChatColor.GOLD + "Tus puntos",
                List.of(ChatColor.GRAY + "Tienes " + ChatColor.YELLOW + getScore(player, POINTS_OBJECTIVE)
                        + ChatColor.GRAY + " puntos.")));

        placeBuy(player, inv, 10, "waystone_token");
        placeBuy(player, inv, 11, "revive_totem");
        placeBuy(player, inv, 15, "phoenix_feather");
        placeBuy(player, inv, 16, "dragon_scale");
        placeBuy(player, inv, 19, "void_fragment");
        placeBuy(player, inv, 20, "golem_heart");
        placeBuy(player, inv, 21, "solar_essence");
        placeBuy(player, inv, 22, "chaos_fragment");
        placeBuy(player, inv, 23, "temporal_crystal");
        placeBuy(player, inv, 24, "celestial_fragment");
        placeBuy(player, inv, 25, "infinity_core");
        placeBuy(player, inv, 33, "wind_charge");
        placeBuy(player, inv, 34, "enchanted_apple");
        placeBuy(player, inv, 40, "undying_totem");

        inv.setItem(45, actionItem(Material.ARROW, ChatColor.YELLOW + "Volver", List.of(), "main"));
        inv.setItem(49, actionItem(Material.BARRIER, ChatColor.RED + "Cerrar", List.of(), "close"));

        player.openInventory(inv);
    }

    private void openSellShop(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, SELL_TITLE);
        fill(inv);

        inv.setItem(4, item(Material.NETHER_STAR, ChatColor.GOLD + "Tus puntos",
                List.of(ChatColor.GRAY + "Tienes " + ChatColor.YELLOW + getScore(player, POINTS_OBJECTIVE)
                        + ChatColor.GRAY + " puntos.")));

        placeSell(inv, 10, "diamond");
        placeSell(inv, 11, "emerald");
        placeSell(inv, 12, "ancient_debris");
        placeSell(inv, 13, "netherite_scrap");
        placeSell(inv, 14, "netherite_ingot");
        placeSell(inv, 15, "echo_shard");
        placeSell(inv, 16, "blaze_rod");
        placeSell(inv, 28, "ender_pearl");
        placeSell(inv, 29, "phantom_membrane");
        placeSell(inv, 30, "ghast_tear");

        inv.setItem(45, actionItem(Material.ARROW, ChatColor.YELLOW + "Volver", List.of(), "main"));
        inv.setItem(49, actionItem(Material.BARRIER, ChatColor.RED + "Cerrar", List.of(), "close"));

        player.openInventory(inv);
    }

    private void openProfile(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, PROFILE_TITLE);
        fill(inv);

        inv.setItem(4, item(Material.PLAYER_HEAD, ChatColor.YELLOW + player.getName(),
                List.of(ChatColor.GRAY + "Resumen personal del modo HardcorePlus.")));
        inv.setItem(10, stat(player, Material.RED_DYE, "Vidas", "hp.lives", "/3", ChatColor.RED));
        inv.setItem(11, stat(player, Material.NETHER_STAR, "Puntos", POINTS_OBJECTIVE, " pts", ChatColor.GOLD));
        inv.setItem(12, stat(player, Material.SKELETON_SKULL, "Muertes", "hp.deaths", "", ChatColor.DARK_RED));
        inv.setItem(13, stat(player, Material.IRON_SWORD, "Hostiles", "hp.hostiles", "", ChatColor.YELLOW));
        inv.setItem(14, stat(player, Material.WHEAT, "Granja", "hp.animals", "", ChatColor.GREEN));
        inv.setItem(15, stat(player, Material.DRAGON_HEAD, "Bosses", "hp.bosses", "", ChatColor.LIGHT_PURPLE));
        inv.setItem(16, stat(player, Material.CLOCK, "Dias sobrevividos", "hp.surv_days", "", ChatColor.AQUA));
        inv.setItem(20, stat(player, Material.DIAMOND, "Diamantes minados", "hp.diamonds", "", ChatColor.AQUA));
        inv.setItem(21, stat(player, Material.ANCIENT_DEBRIS, "Netherita", "hp.netherite", "", ChatColor.DARK_PURPLE));
        inv.setItem(22, stat(player, Material.TOTEM_OF_UNDYING, "Revividos", "hp.revives", "", ChatColor.GREEN));
        inv.setItem(23, stat(player, Material.REDSTONE, "Daño realizado", "hp.damage", "", ChatColor.RED));
        inv.setItem(24, stat(player, Material.EMERALD, "Logros", "hp.achievements", "", ChatColor.GREEN));
        inv.setItem(25, item(Material.CRAFTING_TABLE, ChatColor.AQUA + "Progresion de armaduras",
                List.of(ChatColor.GRAY + progressionLabel(unlockedPhase(player)),
                        ChatColor.YELLOW + "Completa un set para desbloquear la siguiente fase.")));

        player.openInventory(inv);
    }

    private boolean handleWaystone(Player player, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("lista")) {
            openWaystones(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("crear")) {
            if (args.length < 2) {
                player.sendMessage(ChatColor.YELLOW + "Uso: /waystone crear <nombre>");
                return true;
            }
            createWaystone(player, joinArgs(args, 1));
            return true;
        }

        if (args[0].equalsIgnoreCase("borrar")) {
            if (args.length < 2) {
                player.sendMessage(ChatColor.YELLOW + "Uso: /waystone borrar <nombre>");
                return true;
            }
            deleteWaystone(player, joinArgs(args, 1));
            return true;
        }

        player.sendMessage(ChatColor.YELLOW + "Uso: /waystone, /waystone crear <nombre>, /waystone borrar <nombre>");
        return true;
    }

    private boolean handleLobby(Player player, String[] args) {
        if (args.length > 0 && (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("setspawn"))) {
            if (!requireEventAdmin(player)) {
                return true;
            }
            saveLobbySpawn(player);
            return true;
        }

        if (args.length > 0 && (args[0].equalsIgnoreCase("ayuda") || args[0].equalsIgnoreCase("help"))) {
            player.sendMessage(ChatColor.GOLD + "Lobby:");
            player.sendMessage(ChatColor.YELLOW + "/lobby" + ChatColor.GRAY + " - ir al lobby");
            if (isEventAdmin(player)) {
                player.sendMessage(ChatColor.YELLOW + "/lobby set" + ChatColor.GRAY + " - guardar el punto exacto del lobby");
            }
            return true;
        }

        teleportToLobby(player);
        return true;
    }

    private void teleportToLobby(Player player) {
        Location target = loadLobbySpawn();
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Todavia no hay lobby definido.");
            if (isEventAdmin(player)) {
                player.sendMessage(ChatColor.YELLOW + "Parate donde quieras el lobby y usa "
                        + ChatColor.GOLD + "/lobby set" + ChatColor.YELLOW + ".");
            }
            return;
        }

        player.teleport(target);
        player.sendMessage(ChatColor.AQUA + "Te llevamos al lobby.");
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.8f, 1.2f);
    }

    private Location loadLobbySpawn() {
        String base = "lobby.spawn";
        String worldName = getConfig().getString(base + ".world", "");
        if (worldName == null || worldName.isBlank()) {
            return null;
        }

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return null;
        }
        return new Location(
                world,
                getConfig().getDouble(base + ".x", world.getSpawnLocation().getX() + 0.5),
                getConfig().getDouble(base + ".y", world.getSpawnLocation().getY()),
                getConfig().getDouble(base + ".z", world.getSpawnLocation().getZ() + 0.5),
                (float) getConfig().getDouble(base + ".yaw", 0.0),
                (float) getConfig().getDouble(base + ".pitch", 0.0));
    }

    private void saveLobbySpawn(Player admin) {
        Location loc = admin.getLocation();
        getConfig().set("lobby.spawn.world", loc.getWorld().getName());
        getConfig().set("lobby.spawn.x", loc.getX());
        getConfig().set("lobby.spawn.y", loc.getY());
        getConfig().set("lobby.spawn.z", loc.getZ());
        getConfig().set("lobby.spawn.yaw", loc.getYaw());
        getConfig().set("lobby.spawn.pitch", loc.getPitch());
        saveConfig();

        admin.sendMessage(ChatColor.GREEN + "Punto del lobby guardado en " + loc.getWorld().getName() + ".");
        admin.playSound(admin.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.6f, 1.5f);
    }

    private boolean handleJefes(Player player, String[] args) {
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Ruta de jefes HardcorePlus");
        player.sendMessage(ChatColor.GRAY + "Inicio sin jefe: Angel Caido, Titanio y Leviatan.");
        for (BossDefinition boss : bossDefinitions.values()) {
            String status = hasDefeatedBoss(player, boss.id())
                    ? ChatColor.GREEN + "derrotado"
                    : ChatColor.RED + "pendiente";
            String start = boss.altarBoss()
                    ? ChatColor.YELLOW + "altar + " + bossKeyNameForBoss(boss.id())
                    : ChatColor.YELLOW + bossVanillaStartLabel(boss.id());
            player.sendMessage(boss.color() + boss.displayName() + ChatColor.GRAY + " - "
                    + status + ChatColor.DARK_GRAY + " | " + start);
        }
        if (isBossAdmin(player)) {
            player.sendMessage(ChatColor.DARK_GRAY + "Admin: /jefe <coloso|eclipse|vacio|fenix|caos|tiempo|dragon|celestial|infinito>");
        }
        return true;
    }

    private boolean handleJefe(Player player, String[] args) {
        if (!requireBossAdmin(player)) {
            return true;
        }
        if (args.length == 0) {
            player.sendMessage(ChatColor.YELLOW + "Uso: /jefe <nombre>");
            player.sendMessage(ChatColor.GRAY + "Jefes: coloso, eclipse, vacio, fenix, caos, tiempo, dragon, celestial, infinito");
            return true;
        }

        String bossId = normalizeBossId(args[0]);
        BossDefinition boss = bossDefinitions.get(bossId);
        if (boss == null) {
            player.sendMessage(ChatColor.RED + "Jefe desconocido.");
            return true;
        }

        Location spawn = player.getLocation().clone().add(player.getLocation().getDirection().normalize().multiply(5.0));
        spawn.setY(player.getLocation().getY());
        summonBoss(player, boss, spawn);
        return true;
    }

    private boolean handleEvento(Player player, String[] args) {
        if (args.length == 0) {
            sendEventoHelp(player);
            return true;
        }

        String first = normalize(args[0]);
        if (first.equals("entrar")) {
            joinOpenEvent(player);
            return true;
        }
        if (first.equals("salir")) {
            leaveEventList(player);
            return true;
        }
        if (first.equals("ayuda") || first.equals("help")) {
            sendEventoHelp(player);
            return true;
        }

        String eventId = normalizeEventId(first);
        if (eventId == null) {
            player.sendMessage(ChatColor.RED + "Evento desconocido. Usa /evento ayuda.");
            return true;
        }
        if (!requireEventAdmin(player)) {
            return true;
        }

        String action = args.length >= 2 ? normalize(args[1]) : "abrir";
        switch (action) {
            case "abrir":
            case "open":
                openEvent(player, eventId);
                return true;
            case "cerrar":
            case "close":
                closeEvent(player, eventId);
                return true;
            case "iniciar":
            case "start":
            case "enviar":
            case "mandar":
                Location point = loadEventPoint(eventId, "point");
                if (point == null) {
                    player.sendMessage(ChatColor.RED + "Todavia no hay punto para " + eventDisplayName(eventId)
                            + ". Parate en el inicio y usa /evento " + eventId + " set.");
                    return true;
                }
                startEvent(player, eventId, point);
                return true;
            case "traer":
            case "tp":
            case "tphere":
                startEvent(player, eventId, player.getLocation());
                return true;
            case "set":
            case "setpunto":
            case "punto":
                saveEventPoint(player, eventId, "point");
                return true;
            case "meta":
            case "finish":
                if (!eventId.equals("botes")) {
                    player.sendMessage(ChatColor.YELLOW + "La meta solo se usa para /evento botes meta.");
                    return true;
                }
                saveEventPoint(player, eventId, "finish");
                return true;
            case "salida":
            case "exit":
                if (!eventId.equals("laberinto")) {
                    player.sendMessage(ChatColor.YELLOW + "La salida solo se usa para /evento laberinto salida.");
                    return true;
                }
                saveEventPoint(player, eventId, "exit");
                return true;
            case "lista":
            case "list":
                showEventList(player);
                return true;
            case "volver":
            case "regresar":
            case "terminar":
            case "end":
                returnEventList(player);
                return true;
            case "limpiar":
            case "clear":
                clearEventList(player);
                return true;
            case "sacar":
            case "remove":
                if (args.length < 3) {
                    player.sendMessage(ChatColor.YELLOW + "Uso: /evento " + eventId + " sacar <jugador>");
                    return true;
                }
                removeEventPlayer(player, args[2]);
                return true;
            default:
                sendEventoHelp(player);
                return true;
        }
    }

    private boolean handlePvp(Player player, String[] args) {
        return handleEventAlias(player, "pvp", args);
    }

    private boolean handleEventAlias(Player player, String eventId, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("entrar")) {
            joinEventList(player, eventId);
            return true;
        }

        String subcommand = normalize(args[0]);
        if (subcommand.equals("salir")) {
            leaveEventList(player);
            return true;
        }
        if (subcommand.equals("ayuda") || subcommand.equals("help")) {
            sendEventAliasHelp(player, eventId);
            return true;
        }

        if (!isEventAdmin(player)) {
            sendEventAliasHelp(player, eventId);
            return true;
        }

        return handleEvento(player, prependArg(eventId, args));
    }

    private void sendEventoHelp(Player player) {
        player.sendMessage(ChatColor.GOLD + "Eventos:");
        player.sendMessage(ChatColor.YELLOW + "/evento pvp" + ChatColor.GRAY + " - PvP a mano limpia");
        player.sendMessage(ChatColor.YELLOW + "/evento tntrun" + ChatColor.GRAY + " - nieve + pala, ultimo vivo gana");
        player.sendMessage(ChatColor.YELLOW + "/evento botes" + ChatColor.GRAY + " - carrera con botes");
        player.sendMessage(ChatColor.YELLOW + "/evento cabezas" + ChatColor.GRAY + " - ballestas y cabezas por 10 min");
        player.sendMessage(ChatColor.YELLOW + "/evento laberinto" + ChatColor.GRAY + " - laberinto oscuro con ceguera");
        player.sendMessage(ChatColor.YELLOW + "/evento entrar" + ChatColor.GRAY + " - entrar al evento abierto");
        if (isEventAdmin(player)) {
            player.sendMessage(ChatColor.DARK_GRAY + "Admin: /evento <evento> set, iniciar, volver, lista, sacar, limpiar");
            player.sendMessage(ChatColor.DARK_GRAY + "Extras: /evento botes meta, /evento laberinto salida");
        }
    }

    private void sendEventAliasHelp(Player player, String eventId) {
        player.sendMessage(ChatColor.GOLD + eventDisplayName(eventId) + ":");
        player.sendMessage(ChatColor.YELLOW + eventJoinCommand(eventId)
                + ChatColor.GRAY + " - entrar cuando ese evento este abierto");
        player.sendMessage(ChatColor.YELLOW + eventJoinCommand(eventId) + " salir"
                + ChatColor.GRAY + " - salir de la lista");
        if (isEventAdmin(player)) {
            player.sendMessage(ChatColor.DARK_GRAY + "Admin: /evento " + eventId
                    + " abrir, set, iniciar, volver, lista");
        }
    }

    private void openEvent(Player admin, String eventId) {
        if (isEventBusy()) {
            admin.sendMessage(ChatColor.RED + "Ya hay un evento abierto o en curso. Usa /evento "
                    + currentEventId() + " volver/limpiar antes de abrir otro.");
            return;
        }

        openEventId = eventId;
        pvpReturnLocations.clear();
        pvpEliminated.clear();
        Bukkit.broadcastMessage(eventColor(eventId) + "" + ChatColor.BOLD + "Evento " + eventDisplayName(eventId)
                + " abierto" + ChatColor.GRAY + " | " + ChatColor.YELLOW
                + "Usa " + eventJoinCommand(eventId) + " o /evento entrar para entrar.");
        admin.playSound(admin.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.7f, 1.2f);
    }

    private void closeEvent(Player admin, String eventId) {
        if (!eventId.equals(openEventId)) {
            admin.sendMessage(ChatColor.YELLOW + "Ese evento no esta abierto ahora mismo.");
            return;
        }

        openEventId = null;
        Bukkit.broadcastMessage(eventColor(eventId) + "Evento " + eventDisplayName(eventId) + " cerrado."
                + ChatColor.GRAY + " Ya no se puede entrar.");
    }

    private void joinOpenEvent(Player player) {
        if (openEventId == null) {
            player.sendMessage(ChatColor.YELLOW + "No hay ningun evento abierto ahora mismo.");
            if (isEventAdmin(player)) {
                player.sendMessage(ChatColor.GRAY + "Admin: usa " + ChatColor.YELLOW
                        + "/evento <nombre>" + ChatColor.GRAY + " para abrir uno.");
            }
            return;
        }
        joinEventList(player, openEventId);
    }

    private void joinEventList(Player player, String eventId) {
        if (runningEventId != null || !pvpSnapshots.isEmpty()) {
            player.sendMessage(ChatColor.RED + "El evento ya empezo. Espera al siguiente.");
            return;
        }
        if (!eventId.equals(openEventId)) {
            player.sendMessage(ChatColor.YELLOW + "No hay evento " + eventDisplayName(eventId) + " abierto ahora.");
            if (openEventId != null) {
                player.sendMessage(ChatColor.GRAY + "Evento abierto: " + ChatColor.YELLOW
                        + eventDisplayName(openEventId) + ChatColor.GRAY + ". Usa " + eventJoinCommand(openEventId));
            }
            return;
        }

        UUID uuid = player.getUniqueId();
        if (pvpReturnLocations.containsKey(uuid)) {
            player.sendMessage(ChatColor.YELLOW + "Ya estas en la lista. Usa /evento salir para salir.");
            return;
        }

        pvpReturnLocations.put(uuid, player.getLocation().clone());
        int count = pvpPlayers().size();
        player.sendMessage(eventColor(eventId) + "" + ChatColor.BOLD + eventDisplayName(eventId)
                + ChatColor.GRAY + " | " + ChatColor.YELLOW + "Entraste a la lista del evento.");
        player.sendMessage(ChatColor.GRAY + "Un admin lo iniciara cuando toque.");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.7f, 1.4f);
        notifyEventAdmins(eventColor(eventId) + "[" + eventDisplayName(eventId) + "] " + ChatColor.YELLOW
                + player.getName() + ChatColor.GRAY + " entro a la lista. Total: " + ChatColor.AQUA + count);
    }

    private void leaveEventList(Player player) {
        if (pvpSnapshots.containsKey(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "Ya estas dentro del evento. Un admin debe usar /evento "
                    + currentEventId() + " volver.");
            return;
        }

        Location removed = pvpReturnLocations.remove(player.getUniqueId());
        if (removed == null) {
            player.sendMessage(ChatColor.YELLOW + "No estabas en ninguna lista de evento.");
            return;
        }

        player.sendMessage(ChatColor.GRAY + "Saliste de la lista del evento.");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.5f, 1.0f);
        notifyEventAdmins(ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " salio de la lista de evento.");
    }

    private boolean requireEventAdmin(Player player) {
        if (isEventAdmin(player)) {
            return true;
        }
        player.sendMessage(ChatColor.RED + "No tienes permiso para administrar eventos.");
        return false;
    }

    private boolean requireBossAdmin(Player player) {
        if (isBossAdmin(player)) {
            return true;
        }
        player.sendMessage(ChatColor.RED + "No tienes permiso para invocar jefes.");
        return false;
    }

    private boolean isPvpAdmin(Player player) {
        return isEventAdmin(player) || player.hasPermission("hardcoreplus.pvpadmin");
    }

    private boolean isBossAdmin(Player player) {
        return player.isOp() || player.hasPermission("hardcoreplus.bossadmin");
    }

    private boolean isEventAdmin(Player player) {
        return player.isOp() || player.hasPermission("hardcoreplus.eventadmin");
    }

    private void showEventList(Player admin) {
        List<Player> players = pvpPlayers();
        if (players.isEmpty()) {
            admin.sendMessage(ChatColor.YELLOW + "No hay jugadores en la lista.");
            return;
        }

        String id = currentEventId();
        admin.sendMessage(eventColor(id) + eventDisplayName(id) + ChatColor.GRAY + " (" + players.size() + "): "
                + ChatColor.YELLOW + playerNames(players));
    }

    private void saveEventPoint(Player admin, String eventId, String pointId) {
        Location loc = admin.getLocation();
        String base = eventPointPath(eventId, pointId);
        getConfig().set(base + ".world", loc.getWorld().getName());
        getConfig().set(base + ".x", loc.getX());
        getConfig().set(base + ".y", loc.getY());
        getConfig().set(base + ".z", loc.getZ());
        getConfig().set(base + ".yaw", loc.getYaw());
        getConfig().set(base + ".pitch", loc.getPitch());
        saveConfig();

        admin.sendMessage(ChatColor.GREEN + pointDisplayName(eventId, pointId) + " guardado para "
                + eventDisplayName(eventId) + ".");
        admin.playSound(admin.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.6f, 1.5f);
    }

    private Location loadEventPoint(String eventId, String pointId) {
        String base = eventPointPath(eventId, pointId);
        if (!getConfig().contains(base + ".world")) {
            return null;
        }

        World world = Bukkit.getWorld(getConfig().getString(base + ".world", ""));
        if (world == null) {
            return null;
        }

        return new Location(
                world,
                getConfig().getDouble(base + ".x"),
                getConfig().getDouble(base + ".y"),
                getConfig().getDouble(base + ".z"),
                (float) getConfig().getDouble(base + ".yaw"),
                (float) getConfig().getDouble(base + ".pitch"));
    }

    private String eventPointPath(String eventId, String pointId) {
        if (eventId.equals("pvp") && pointId.equals("point")) {
            return "pvp.point";
        }
        return "events." + eventId + "." + pointId;
    }

    private String pointDisplayName(String eventId, String pointId) {
        if (eventId.equals("botes") && pointId.equals("finish")) {
            return "Meta";
        }
        if (eventId.equals("laberinto") && pointId.equals("exit")) {
            return "Salida";
        }
        return "Punto de inicio";
    }

    private void startEvent(Player admin, String eventId, Location center) {
        if (runningEventId != null || !pvpSnapshots.isEmpty()) {
            admin.sendMessage(ChatColor.RED + "Ya hay un evento en curso. Usa /evento "
                    + currentEventId() + " volver para terminarlo.");
            return;
        }
        if (!eventId.equals(openEventId) && !pvpReturnLocations.isEmpty()) {
            admin.sendMessage(ChatColor.RED + "La lista abierta es de " + eventDisplayName(openEventId)
                    + ". Limpiala antes de iniciar otro evento.");
            return;
        }
        if (eventId.equals("botes") && loadEventPoint(eventId, "finish") == null) {
            admin.sendMessage(ChatColor.RED + "Falta la meta. Parate en la meta y usa /evento botes meta.");
            return;
        }
        if (eventId.equals("laberinto") && loadEventPoint(eventId, "exit") == null) {
            admin.sendMessage(ChatColor.RED + "Falta la salida. Parate en la salida y usa /evento laberinto salida.");
            return;
        }

        List<Player> players = pvpPlayers();
        int minimum = minimumPlayers(eventId);
        if (players.size() < minimum) {
            admin.sendMessage(ChatColor.YELLOW + "Necesitas al menos " + minimum + " jugador(es) en la lista.");
            return;
        }

        openEventId = null;
        runningEventId = eventId;
        runningEventCenter = center.clone();
        runningEventEndsAtMs = eventDurationMs(eventId) > 0L
                ? System.currentTimeMillis() + eventDurationMs(eventId)
                : 0L;
        pvpFightRunning = eventId.equals("pvp");
        pvpEliminated.clear();

        for (int i = 0; i < players.size(); i++) {
            Player target = players.get(i);
            if (!pvpSnapshots.containsKey(target.getUniqueId())) {
                pvpSnapshots.put(target.getUniqueId(), snapshotPvpPlayer(target));
            }
            prepareEventPlayer(target, eventId);
            Location destination = spreadAround(center, i, players.size());
            target.teleport(destination);
            target.sendMessage(startMessage(eventId));
            target.playSound(target.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.8f, 1.1f);
            target.getWorld().spawnParticle(Particle.PORTAL, target.getLocation().add(0, 1, 0), 35, 0.4, 0.7, 0.4, 0.12);
        }

        Bukkit.broadcastMessage(eventColor(eventId) + "" + ChatColor.BOLD + eventDisplayName(eventId)
                + ChatColor.GRAY + " | " + ChatColor.YELLOW + players.size()
                + ChatColor.GRAY + " jugador(es) entraron al evento.");
        admin.sendMessage(ChatColor.GRAY + "Cuando termine usa " + ChatColor.YELLOW
                + "/evento " + eventId + " volver" + ChatColor.GRAY + " para devolverlos y restaurar todo.");
    }

    private void returnEventList(Player admin) {
        List<UUID> ids = new ArrayList<>(pvpReturnLocations.keySet());
        if (ids.isEmpty()) {
            admin.sendMessage(ChatColor.YELLOW + "No hay jugadores en la lista/evento.");
            resetEventState();
            return;
        }

        int moved = 0;
        for (UUID uuid : ids) {
            Player target = Bukkit.getPlayer(uuid);
            if (target == null || !target.isOnline()) {
                continue;
            }
            restorePvpPlayer(target, true);
            moved++;
        }

        resetEventState();
        admin.sendMessage(ChatColor.GREEN + "Devolviste " + moved
                + " jugador(es), restauraste inventarios y limpiaste el evento.");
    }

    private void clearEventList(Player admin) {
        if (!pvpSnapshots.isEmpty()) {
            returnEventList(admin);
            return;
        }

        int count = pvpPlayers().size();
        resetEventState();
        admin.sendMessage(ChatColor.GREEN + "Lista de evento limpia. Jugadores quitados: " + count + ".");
    }

    private void removeEventPlayer(Player admin, String name) {
        Player target = pvpPlayerByName(name);
        if (target == null || !pvpReturnLocations.containsKey(target.getUniqueId())) {
            admin.sendMessage(ChatColor.RED + "Ese jugador no esta en la lista/evento.");
            return;
        }

        if (pvpSnapshots.containsKey(target.getUniqueId())) {
            restorePvpPlayer(target, true);
            admin.sendMessage(ChatColor.GREEN + "Sacaste a " + target.getName()
                    + " del evento y restauraste su inventario.");
            checkEventWinner();
            return;
        }

        pvpReturnLocations.remove(target.getUniqueId());
        admin.sendMessage(ChatColor.GREEN + "Quitaste a " + target.getName() + " de la lista.");
        target.sendMessage(ChatColor.YELLOW + "Un admin te quito de la lista del evento.");
    }

    private PvpSnapshot snapshotPvpPlayer(Player player) {
        PlayerInventory inv = player.getInventory();
        return new PvpSnapshot(
                pvpReturnLocations.getOrDefault(player.getUniqueId(), player.getLocation()).clone(),
                cloneContents(inv.getStorageContents()),
                cloneContents(inv.getArmorContents()),
                cloneItem(inv.getItemInOffHand()),
                player.getGameMode(),
                player.getAllowFlight(),
                player.isFlying(),
                player.getHealth(),
                player.getFoodLevel(),
                player.getSaturation(),
                player.getExp(),
                player.getLevel(),
                new ArrayList<>(player.getActivePotionEffects()));
    }

    private void prepareEventPlayer(Player player, String eventId) {
        PlayerInventory inv = player.getInventory();
        player.closeInventory();
        player.setItemOnCursor(null);
        inv.clear();
        inv.setArmorContents(new ItemStack[4]);
        inv.setItemInOffHand(null);
        for (PotionEffect effect : new ArrayList<>(player.getActivePotionEffects())) {
            player.removePotionEffect(effect.getType());
        }
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setSaturation(20.0f);
        player.setFireTicks(0);
        player.setFallDistance(0.0f);

        switch (eventId) {
            case "tntrun":
                inv.setItem(0, eventTool(Material.NETHERITE_SHOVEL, ChatColor.AQUA + "" + ChatColor.BOLD
                        + "Pala TNTRun", Enchantment.EFFICIENCY, 5));
                break;
            case "botes":
                inv.setItem(0, item(Material.OAK_BOAT, ChatColor.AQUA + "" + ChatColor.BOLD
                        + "Bote de Carrera", List.of(ChatColor.GRAY + "Llega a la meta primero.")));
                inv.setItem(1, item(Material.OAK_BOAT, ChatColor.AQUA + "" + ChatColor.BOLD
                        + "Bote de Repuesto", List.of(ChatColor.GRAY + "Por si te trabas.")));
                break;
            case "cabezas":
                inv.setItem(0, eventTool(Material.CROSSBOW, ChatColor.RED + "" + ChatColor.BOLD
                        + "Ballesta de Cabezas", Enchantment.QUICK_CHARGE, 3));
                inv.setItem(1, stack(Material.ARROW, 64));
                inv.setItem(2, stack(Material.ARROW, 64));
                break;
            case "laberinto":
                inv.setItem(0, item(Material.TORCH, ChatColor.YELLOW + "" + ChatColor.BOLD
                        + "Antorcha del Laberinto", List.of(ChatColor.GRAY + "Encuentra la salida.")));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (int) (MAZE_DURATION_MS / 50L), 0, true, false, true));
                break;
            default:
                break;
        }
        player.updateInventory();
    }

    private ItemStack eventTool(Material material, String name, Enchantment enchantment, int level) {
        ItemStack stack = item(material, name, List.of(ChatColor.DARK_GRAY + "Objeto temporal del evento."));
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.addEnchant(enchantment, level, true);
            meta.addEnchant(Enchantment.UNBREAKING, 3, true);
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    private void restorePvpPlayer(Player player, boolean teleportBack) {
        UUID uuid = player.getUniqueId();
        PvpSnapshot snapshot = pvpSnapshots.remove(uuid);
        Location returnLocation = pvpReturnLocations.remove(uuid);
        pvpEliminated.remove(uuid);

        if (snapshot == null) {
            if (teleportBack && returnLocation != null && returnLocation.getWorld() != null) {
                player.teleport(returnLocation);
            }
            return;
        }

        PlayerInventory inv = player.getInventory();
        inv.setStorageContents(cloneContents(snapshot.storage()));
        inv.setArmorContents(cloneContents(snapshot.armor()));
        inv.setItemInOffHand(cloneItem(snapshot.offhand()));
        for (PotionEffect effect : new ArrayList<>(player.getActivePotionEffects())) {
            player.removePotionEffect(effect.getType());
        }
        for (PotionEffect effect : snapshot.effects()) {
            player.addPotionEffect(effect);
        }
        player.setGameMode(snapshot.gameMode());
        player.setAllowFlight(snapshot.allowFlight());
        player.setFlying(snapshot.flying() && snapshot.allowFlight());
        player.setFoodLevel(snapshot.foodLevel());
        player.setSaturation(snapshot.saturation());
        player.setExp(snapshot.exp());
        player.setLevel(snapshot.level());
        player.setHealth(Math.min(player.getMaxHealth(), Math.max(1.0, snapshot.health())));
        player.setFireTicks(0);
        player.setFallDistance(0.0f);
        player.updateInventory();

        Location destination = returnLocation != null ? returnLocation : snapshot.returnLocation();
        if (teleportBack && destination != null && destination.getWorld() != null) {
            player.teleport(destination);
            player.sendMessage(ChatColor.GREEN + "Evento: " + ChatColor.YELLOW
                    + "volviste a tu ubicacion original y recuperaste tus cosas.");
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.8f, 0.8f);
        }
    }

    private void eliminateEventPlayer(Player player) {
        UUID uuid = player.getUniqueId();
        String eventId = runningEventId;
        if (eventId == null || !pvpSnapshots.containsKey(uuid) || pvpEliminated.contains(uuid)) {
            return;
        }

        pvpEliminated.add(uuid);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setSaturation(20.0f);
        player.setFireTicks(0);
        player.setFallDistance(0.0f);
        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Eliminado"
                + ChatColor.GRAY + " | " + ChatColor.YELLOW + "quedas en espectador hasta que termine.");
        player.playSound(player.getLocation(), Sound.ENTITY_WITHER_HURT, 0.6f, 1.3f);
        player.getWorld().spawnParticle(Particle.SMOKE, player.getLocation().add(0, 1, 0), 28, 0.35, 0.5, 0.35, 0.03);
        announceEvent(eventColor(eventId) + "[" + eventDisplayName(eventId) + "] " + ChatColor.YELLOW
                + player.getName() + ChatColor.GRAY + " fue eliminado.");
        checkEventWinner();
    }

    private void scoreHeadHunterKill(Player victim, Player killer) {
        if (!"cabezas".equals(runningEventId) || !pvpSnapshots.containsKey(victim.getUniqueId())) {
            return;
        }

        if (killer != null && !killer.getUniqueId().equals(victim.getUniqueId())
                && pvpSnapshots.containsKey(killer.getUniqueId())) {
            giveOrDrop(killer, headHunterHead(victim));
            int heads = countHeadHunterHeads(killer);
            killer.sendMessage(ChatColor.GOLD + "Conseguiste la cabeza de " + ChatColor.YELLOW
                    + victim.getName() + ChatColor.GRAY + ". Total: " + ChatColor.AQUA + heads);
            announceEvent(ChatColor.RED + "[Cazadores] " + ChatColor.YELLOW + killer.getName()
                    + ChatColor.GRAY + " elimino a " + ChatColor.YELLOW + victim.getName() + ChatColor.GRAY + ".");
        }

        victim.setHealth(victim.getMaxHealth());
        victim.setFoodLevel(20);
        victim.setSaturation(20.0f);
        victim.setFireTicks(0);
        victim.setFallDistance(0.0f);
        if (runningEventCenter != null) {
            victim.teleport(spreadAround(runningEventCenter, ThreadLocalRandom.current().nextInt(24), 24));
        }
        victim.playSound(victim.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.7f, 1.4f);
    }

    private ItemStack headHunterHead(Player victim) {
        ItemStack stack = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta instanceof SkullMeta skullMeta) {
            skullMeta.setOwningPlayer(victim);
            skullMeta.setDisplayName(ChatColor.RED + "Cabeza de " + victim.getName());
            skullMeta.setLore(List.of(ChatColor.GRAY + "Punto de Cazadores de Cabezas."));
            stack.setItemMeta(skullMeta);
        }
        return stack;
    }

    private int countHeadHunterHeads(Player player) {
        int count = 0;
        for (ItemStack item : player.getInventory().getStorageContents()) {
            if (item != null && item.getType() == Material.PLAYER_HEAD) {
                count += item.getAmount();
            }
        }
        return count;
    }

    private void checkEventWinner() {
        if (runningEventId == null || (!runningEventId.equals("pvp") && !runningEventId.equals("tntrun"))) {
            return;
        }

        List<Player> alive = eventAlivePlayers();
        if (alive.size() > 1) {
            return;
        }

        Player winner = alive.size() == 1 ? alive.get(0) : null;
        finishEventWithWinner(runningEventId, winner);
    }

    private void checkPvpWinner() {
        checkEventWinner();
    }

    private void finishEventWithWinner(String eventId, Player winner) {
        if (winner != null) {
            winner.playSound(winner.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.9f, 1.2f);
            announceEvent(ChatColor.GOLD + "" + ChatColor.BOLD + "Ganador de " + eventDisplayName(eventId)
                    + ": " + ChatColor.YELLOW + winner.getName());
        } else {
            announceEvent(ChatColor.YELLOW + eventDisplayName(eventId) + " termino sin ganador.");
        }

        for (Player player : pvpPlayers()) {
            if (pvpSnapshots.containsKey(player.getUniqueId())) {
                player.setGameMode(GameMode.SPECTATOR);
            }
        }
        runningEventId = null;
        runningEventCenter = null;
        runningEventEndsAtMs = 0L;
        pvpFightRunning = false;
        announceEvent(ChatColor.GRAY + "Admin: usa " + ChatColor.YELLOW
                + "/evento " + eventId + " volver" + ChatColor.GRAY + " para devolverlos y restaurar inventarios.");
    }

    private void finishHeadHunters() {
        if (!"cabezas".equals(runningEventId)) {
            return;
        }

        int best = -1;
        List<Player> winners = new ArrayList<>();
        for (Player player : pvpPlayers()) {
            int heads = countHeadHunterHeads(player);
            if (heads > best) {
                best = heads;
                winners.clear();
                winners.add(player);
            } else if (heads == best) {
                winners.add(player);
            }
        }

        if (winners.isEmpty() || best <= 0) {
            finishEventWithWinner("cabezas", null);
            return;
        }

        announceEvent(ChatColor.GOLD + "" + ChatColor.BOLD + "Cazadores de Cabezas termino.");
        announceEvent(ChatColor.YELLOW + "Ganador(es): " + ChatColor.AQUA + playerNames(winners)
                + ChatColor.GRAY + " con " + ChatColor.RED + best + ChatColor.GRAY + " cabeza(s).");
        for (Player winner : winners) {
            winner.playSound(winner.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.9f, 1.2f);
        }
        for (Player player : pvpPlayers()) {
            player.setGameMode(GameMode.SPECTATOR);
        }
        runningEventId = null;
        runningEventCenter = null;
        runningEventEndsAtMs = 0L;
        pvpFightRunning = false;
        announceEvent(ChatColor.GRAY + "Admin: usa " + ChatColor.YELLOW
                + "/evento cabezas volver" + ChatColor.GRAY + " para devolverlos y restaurar inventarios.");
    }

    private List<Player> eventAlivePlayers() {
        List<Player> players = new ArrayList<>();
        for (Player player : pvpPlayers()) {
            UUID uuid = player.getUniqueId();
            if (pvpSnapshots.containsKey(uuid) && !pvpEliminated.contains(uuid) && player.getGameMode() != GameMode.SPECTATOR) {
                players.add(player);
            }
        }
        return players;
    }

    private void tickEvents() {
        if (runningEventId == null) {
            return;
        }

        String eventId = runningEventId;
        if (eventId.equals("tntrun")) {
            tickTntRun();
        } else if (eventId.equals("botes")) {
            tickBoatRace();
        } else if (eventId.equals("cabezas")) {
            if (runningEventEndsAtMs > 0L && System.currentTimeMillis() >= runningEventEndsAtMs) {
                finishHeadHunters();
            }
        } else if (eventId.equals("laberinto")) {
            tickBlindMaze();
        }
    }

    private void tickTntRun() {
        if (runningEventCenter == null) {
            return;
        }
        for (Player player : eventAlivePlayers()) {
            if (player.getLocation().getWorld() == runningEventCenter.getWorld()
                    && player.getLocation().getY() < runningEventCenter.getY() - 5.0) {
                eliminateEventPlayer(player);
            }
        }
    }

    private void tickBoatRace() {
        Location finish = loadEventPoint("botes", "finish");
        if (finish != null) {
            for (Player player : eventAlivePlayers()) {
                if (sameWorld(player.getLocation(), finish) && player.getLocation().distanceSquared(finish) <= 16.0) {
                    finishEventWithWinner("botes", player);
                    return;
                }
            }
        }
        if (runningEventEndsAtMs > 0L && System.currentTimeMillis() >= runningEventEndsAtMs) {
            finishEventWithWinner("botes", null);
        }
    }

    private void tickBlindMaze() {
        Location exit = loadEventPoint("laberinto", "exit");
        if (exit != null) {
            for (Player player : eventAlivePlayers()) {
                if (sameWorld(player.getLocation(), exit) && player.getLocation().distanceSquared(exit) <= 4.0) {
                    finishEventWithWinner("laberinto", player);
                    return;
                }
            }
        }

        long remaining = runningEventEndsAtMs - System.currentTimeMillis();
        if (runningEventEndsAtMs > 0L && remaining <= 0L) {
            finishEventWithWinner("laberinto", null);
            return;
        }
        long seconds = Math.max(0L, remaining / 1000L);
        if (seconds > 0L && seconds % 30L == 0L) {
            for (Player player : eventAlivePlayers()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 60, 0, true, false, true));
                player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 0.25f, 1.8f);
            }
        }
    }

    private void handleEventBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!"tntrun".equals(runningEventId) || pvpEliminated.contains(player.getUniqueId())) {
            event.setCancelled(true);
            return;
        }

        Material type = event.getBlock().getType();
        if (type != Material.SNOW_BLOCK && type != Material.SNOW) {
            event.setCancelled(true);
            return;
        }

        event.setDropItems(false);
    }

    private boolean sameWorld(Location first, Location second) {
        return first.getWorld() != null && first.getWorld().equals(second.getWorld());
    }

    private boolean isEventBusy() {
        return openEventId != null || runningEventId != null || !pvpSnapshots.isEmpty();
    }

    private String currentEventId() {
        if (runningEventId != null) {
            return runningEventId;
        }
        if (openEventId != null) {
            return openEventId;
        }
        return "evento";
    }

    private void resetEventState() {
        openEventId = null;
        runningEventId = null;
        runningEventCenter = null;
        runningEventEndsAtMs = 0L;
        pvpFightRunning = false;
        pvpReturnLocations.clear();
        pvpSnapshots.clear();
        pvpEliminated.clear();
    }

    private int minimumPlayers(String eventId) {
        return eventId.equals("botes") || eventId.equals("laberinto") ? 1 : 2;
    }

    private long eventDurationMs(String eventId) {
        return switch (eventId) {
            case "cabezas" -> HEAD_HUNTERS_DURATION_MS;
            case "laberinto" -> MAZE_DURATION_MS;
            case "botes" -> BOAT_RACE_DURATION_MS;
            default -> 0L;
        };
    }

    private String startMessage(String eventId) {
        return switch (eventId) {
            case "pvp" -> ChatColor.RED + "" + ChatColor.BOLD + "PvP iniciado"
                    + ChatColor.GRAY + " | " + ChatColor.YELLOW + "pelea a mano limpia.";
            case "tntrun" -> ChatColor.AQUA + "" + ChatColor.BOLD + "TNTRun iniciado"
                    + ChatColor.GRAY + " | " + ChatColor.YELLOW + "rompe nieve y no caigas.";
            case "botes" -> ChatColor.AQUA + "" + ChatColor.BOLD + "Carrera de Botes"
                    + ChatColor.GRAY + " | " + ChatColor.YELLOW + "llega a la meta.";
            case "cabezas" -> ChatColor.RED + "" + ChatColor.BOLD + "Cazadores de Cabezas"
                    + ChatColor.GRAY + " | " + ChatColor.YELLOW + "10 minutos, cada kill da una cabeza.";
            case "laberinto" -> ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Laberinto a Ciegas"
                    + ChatColor.GRAY + " | " + ChatColor.YELLOW + "encuentra la salida.";
            default -> ChatColor.YELLOW + "Evento iniciado.";
        };
    }

    private String normalizeEventId(String value) {
        return switch (normalize(value)) {
            case "pvp", "peleas", "ring" -> "pvp";
            case "tntrun", "tnt", "tnt_run" -> "tntrun";
            case "botes", "barcos", "carrera_botes", "carrera_de_botes", "boat", "boats" -> "botes";
            case "cabezas", "cazadores", "cazadores_de_cabezas", "headhunters", "heads" -> "cabezas";
            case "laberinto", "laberinto_a_ciegas", "maze", "blindmaze" -> "laberinto";
            default -> null;
        };
    }

    private ChatColor eventColor(String eventId) {
        return switch (eventId) {
            case "pvp", "cabezas" -> ChatColor.RED;
            case "tntrun", "botes" -> ChatColor.AQUA;
            case "laberinto" -> ChatColor.DARK_GRAY;
            default -> ChatColor.GOLD;
        };
    }

    private String eventDisplayName(String eventId) {
        return switch (eventId) {
            case "pvp" -> "PvP";
            case "tntrun" -> "TNTRun";
            case "botes" -> "Carrera de Botes";
            case "cabezas" -> "Cazadores de Cabezas";
            case "laberinto" -> "Laberinto a Ciegas";
            default -> "Evento";
        };
    }

    private String eventJoinCommand(String eventId) {
        return switch (eventId) {
            case "pvp" -> "/pvp";
            case "tntrun" -> "/tntrun";
            case "botes" -> "/botes";
            case "cabezas" -> "/cabezas";
            case "laberinto" -> "/laberinto";
            default -> "/evento entrar";
        };
    }

    private String[] prependArg(String first, String[] args) {
        String[] result = new String[args.length + 1];
        result[0] = first;
        System.arraycopy(args, 0, result, 1, args.length);
        return result;
    }

    private void announceEvent(String message) {
        Set<UUID> sent = new HashSet<>();
        for (Player player : pvpPlayers()) {
            player.sendMessage(message);
            sent.add(player.getUniqueId());
        }
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (isEventAdmin(online) && !sent.contains(online.getUniqueId())) {
                online.sendMessage(message);
            }
        }
    }

    private ItemStack[] cloneContents(ItemStack[] contents) {
        ItemStack[] clone = new ItemStack[contents.length];
        for (int i = 0; i < contents.length; i++) {
            clone[i] = cloneItem(contents[i]);
        }
        return clone;
    }

    private ItemStack cloneItem(ItemStack item) {
        return item == null ? null : item.clone();
    }

    private Player pvpPlayerByName(String name) {
        for (Player player : pvpPlayers()) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return Bukkit.getPlayerExact(name);
    }

    private List<Player> pvpPlayers() {
        List<Player> players = new ArrayList<>();
        for (UUID uuid : new ArrayList<>(pvpReturnLocations.keySet())) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null || !player.isOnline()) {
                pvpReturnLocations.remove(uuid);
                continue;
            }
            players.add(player);
        }
        return players;
    }

    private String playerNames(List<Player> players) {
        StringBuilder builder = new StringBuilder();
        for (Player player : players) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(player.getName());
        }
        return builder.toString();
    }

    private void notifyEventAdmins(String message) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (isEventAdmin(online)) {
                online.sendMessage(message);
            }
        }
    }

    private Location spreadAround(Location center, int index, int total) {
        Location target = center.clone();
        target.setYaw(center.getYaw());
        target.setPitch(center.getPitch());
        if (total <= 1) {
            return target;
        }

        double radius = Math.min(4.0, Math.max(1.5, total * 0.35));
        double angle = (Math.PI * 2.0 * index) / total;
        target.add(Math.cos(angle) * radius, 0.0, Math.sin(angle) * radius);
        return target;
    }

    private void openWaystones(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, WAYSTONE_TITLE);
        fill(inv);
        inv.setItem(4, item(Material.RECOVERY_COMPASS, ChatColor.LIGHT_PURPLE + "Tus Waystones",
                List.of(ChatColor.GRAY + "Maximo: " + ChatColor.YELLOW + MAX_WAYSTONES,
                        ChatColor.GRAY + "Crear: " + ChatColor.YELLOW + "/waystone crear <nombre>")));

        ConfigurationSection section = waystoneSection(player);
        if (section == null || section.getKeys(false).isEmpty()) {
            inv.setItem(13, item(Material.GRAY_DYE, ChatColor.GRAY + "Sin lugares guardados",
                    List.of(ChatColor.GRAY + "Compra un Cristal de Ruta y usa:",
                            ChatColor.YELLOW + "/waystone crear casa")));
        } else {
            int[] slots = {10, 11, 12, 13, 14};
            int index = 0;
            for (String id : section.getKeys(false)) {
                if (index >= slots.length) {
                    break;
                }
                inv.setItem(slots[index], waystoneItem(player, id));
                index++;
            }
        }

        inv.setItem(18, actionItem(Material.ARROW, ChatColor.YELLOW + "Volver", List.of(), "main"));
        inv.setItem(22, actionItem(Material.BARRIER, ChatColor.RED + "Cerrar", List.of(), "close"));
        player.openInventory(inv);
    }

    private void createWaystone(Player player, String rawName) {
        String id = normalize(rawName);
        if (id.isEmpty()) {
            player.sendMessage(ChatColor.RED + "Ese nombre no sirve. Usa letras y numeros.");
            return;
        }

        ConfigurationSection section = waystoneSection(player);
        int count = section == null ? 0 : section.getKeys(false).size();
        if (count >= MAX_WAYSTONES) {
            player.sendMessage(ChatColor.RED + "Ya tienes el maximo de " + MAX_WAYSTONES + " waystones.");
            return;
        }

        String base = waystonePath(player) + "." + id;
        if (getConfig().contains(base)) {
            player.sendMessage(ChatColor.RED + "Ya tienes un waystone con ese nombre.");
            return;
        }

        if (!consumeSpecial(player, "waystone_token")) {
            player.sendMessage(ChatColor.RED + "Necesitas comprar un Cristal de Ruta en la tienda.");
            return;
        }

        Location loc = player.getLocation();
        getConfig().set(base + ".name", cleanName(rawName));
        getConfig().set(base + ".world", loc.getWorld().getName());
        getConfig().set(base + ".x", loc.getX());
        getConfig().set(base + ".y", loc.getY());
        getConfig().set(base + ".z", loc.getZ());
        getConfig().set(base + ".yaw", loc.getYaw());
        getConfig().set(base + ".pitch", loc.getPitch());
        saveConfig();

        player.sendMessage(ChatColor.LIGHT_PURPLE + "Waystone creado: " + ChatColor.YELLOW + cleanName(rawName));
        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 0.8f, 1.2f);
    }

    private void deleteWaystone(Player player, String rawName) {
        String id = normalize(rawName);
        String path = waystonePath(player) + "." + id;
        if (!getConfig().contains(path)) {
            player.sendMessage(ChatColor.RED + "No encontre ese waystone.");
            return;
        }
        getConfig().set(path, null);
        saveConfig();
        player.sendMessage(ChatColor.YELLOW + "Waystone borrado: " + cleanName(rawName));
    }

    private void teleportToWaystone(Player player, String id) {
        String base = waystonePath(player) + "." + id;
        World world = Bukkit.getWorld(getConfig().getString(base + ".world", ""));
        if (world == null) {
            player.sendMessage(ChatColor.RED + "Ese mundo ya no existe o no esta cargado.");
            return;
        }

        Location loc = new Location(
                world,
                getConfig().getDouble(base + ".x"),
                getConfig().getDouble(base + ".y"),
                getConfig().getDouble(base + ".z"),
                (float) getConfig().getDouble(base + ".yaw"),
                (float) getConfig().getDouble(base + ".pitch"));
        player.closeInventory();
        player.teleport(loc);
        player.sendMessage(ChatColor.LIGHT_PURPLE + "Teletransporte: " + ChatColor.YELLOW
                + getConfig().getString(base + ".name", id));
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.8f, 1.0f);
    }

    private void buy(Player player, String id) {
        BuyEntry entry = buyEntries.get(id);
        if (entry == null) {
            return;
        }

        if (!isBuyUnlocked(player, id)) {
            player.sendMessage(ChatColor.RED + "Ese objeto pertenece a una fase bloqueada.");
            return;
        }

        int points = getScore(player, POINTS_OBJECTIVE);
        if (points < entry.cost()) {
            player.sendMessage(ChatColor.RED + "No tienes puntos suficientes. Te faltan " + (entry.cost() - points) + " pts.");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.8f, 0.8f);
            return;
        }

        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ChatColor.RED + "Tu inventario esta lleno.");
            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_LOCKED, 0.8f, 1.0f);
            return;
        }

        addScore(player, POINTS_OBJECTIVE, -entry.cost());
        player.getInventory().addItem(entry.reward().clone());
        player.sendMessage(ChatColor.GREEN + "Compra realizada: " + ChatColor.YELLOW + entry.displayName()
                + ChatColor.GRAY + " (-" + entry.cost() + " pts)");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.7f, 1.4f);
    }

    private void sell(Player player, String id, boolean all) {
        SellEntry entry = sellEntries.get(id);
        if (entry == null) {
            return;
        }

        int amount = countMaterial(player.getInventory(), entry.material());
        if (amount <= 0) {
            player.sendMessage(ChatColor.RED + "No tienes " + entry.displayName() + " para vender.");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.8f, 0.8f);
            return;
        }

        int toSell = all ? amount : 1;
        removeMaterial(player.getInventory(), entry.material(), toSell);
        int earned = toSell * entry.price();
        addScore(player, POINTS_OBJECTIVE, earned);
        player.sendMessage(ChatColor.AQUA + "Vendiste " + ChatColor.YELLOW + toSell + "x " + entry.displayName()
                + ChatColor.GRAY + " (+" + earned + " pts)");
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8f, 1.2f);
    }

    private void giveOrDrop(Player player, ItemStack stack) {
        Map<Integer, ItemStack> leftovers = player.getInventory().addItem(stack);
        for (ItemStack leftover : leftovers.values()) {
            player.getWorld().dropItemNaturally(player.getLocation(), leftover);
        }
    }

    private boolean handleGivePoints(CommandSender sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "No tienes permiso.");
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage(ChatColor.YELLOW + "Uso: /hpgivepoints <jugador> <cantidad>");
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            sender.sendMessage(ChatColor.RED + "Cantidad invalida.");
            return true;
        }

        String targetName = args[0];
        if (!addScore(targetName, POINTS_OBJECTIVE, amount)) {
            sender.sendMessage(ChatColor.RED + "Falta el scoreboard " + POINTS_OBJECTIVE + ". Ejecuta /reload.");
            return true;
        }

        Player target = Bukkit.getPlayerExact(targetName);
        sender.sendMessage(ChatColor.GREEN + "Ajustaste " + amount + " puntos a " + targetName + ".");
        if (target != null) {
            target.sendMessage(ChatColor.GOLD + "* Tus puntos cambiaron en "
                    + ChatColor.YELLOW + amount + ChatColor.GOLD + ".");
        }
        return true;
    }

    private boolean handleGiveArmor(CommandSender sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "No tienes permiso.");
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage(ChatColor.YELLOW + "Uso: /hpgivearmor <jugador> <set>");
            sender.sendMessage(ChatColor.GRAY + "Sets: angel, " + String.join(", ", armorSets.keySet()));
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Ese jugador debe estar conectado.");
            return true;
        }

        String setId = normalize(args[1]);
        if (setId.equals("angel_caido")) {
            setId = "angel";
        }

        if (setId.equals("angel")) {
            giveOrDrop(target, angelArmor("helmet"));
            giveOrDrop(target, angelArmor("chestplate"));
            giveOrDrop(target, angelArmor("leggings"));
            giveOrDrop(target, angelArmor("boots"));
            sender.sendMessage(ChatColor.GREEN + "Entregaste la Armadura Angel Caido a " + target.getName() + ".");
            return true;
        }

        ArmorSet set = armorSets.get(setId);
        if (set == null) {
            sender.sendMessage(ChatColor.RED + "Set desconocido.");
            sender.sendMessage(ChatColor.GRAY + "Sets: angel, " + String.join(", ", armorSets.keySet()));
            return true;
        }

        for (ArmorPiece piece : ArmorPiece.values()) {
            giveOrDrop(target, armorItem(set, piece));
        }
        sender.sendMessage(ChatColor.GREEN + "Entregaste " + set.displayName() + " a " + target.getName() + ".");
        target.sendMessage(ChatColor.GOLD + "Recibiste " + set.displayName() + " para pruebas.");
        return true;
    }

    private boolean handleGiveMaterial(CommandSender sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "No tienes permiso.");
            return true;
        }
        if (args.length < 2 || args.length > 3) {
            sender.sendMessage(ChatColor.YELLOW + "Uso: /hpgivematerial <jugador> <material/item> [cantidad]");
            sender.sendMessage(ChatColor.GRAY + "IDs: " + String.join(", ", giveableItemIds()));
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Ese jugador debe estar conectado.");
            return true;
        }

        String materialId = normalize(args[1]);
        ItemStack stack = giveableItem(materialId);
        if (stack == null) {
            sender.sendMessage(ChatColor.RED + "Material/item desconocido.");
            sender.sendMessage(ChatColor.GRAY + "IDs: " + String.join(", ", giveableItemIds()));
            return true;
        }

        int amount = 1;
        if (args.length == 3) {
            try {
                amount = Math.max(1, Math.min(64, Integer.parseInt(args[2])));
            } catch (NumberFormatException ex) {
                sender.sendMessage(ChatColor.RED + "Cantidad invalida.");
                return true;
            }
        }

        if (stack.getType().getMaxStackSize() == 1 && amount > 1) {
            for (int i = 0; i < amount; i++) {
                giveOrDrop(target, stack.clone());
            }
        } else {
            stack.setAmount(amount);
            giveOrDrop(target, stack);
        }
        sender.sendMessage(ChatColor.GREEN + "Entregaste " + amount + "x "
                + giveableItemName(materialId) + " a " + target.getName() + ".");
        return true;
    }

    private List<String> giveableItemIds() {
        List<String> ids = new ArrayList<>(customMaterials.keySet());
        ids.add("revive_totem");
        ids.add("waystone_token");
        ids.addAll(DEATH_TOTEM_IDS);
        ids.addAll(BOSS_KEY_IDS);
        ids.addAll(BOSS_RELIC_IDS);
        ids.addAll(BOSS_ALTAR_IDS);
        ids.addAll(customGears.keySet());
        return ids;
    }

    private ItemStack giveableItem(String id) {
        if (customMaterials.containsKey(id)) {
            return customMaterialItem(id);
        }
        CustomGear gear = customGear(id);
        if (gear != null) {
            return gearItem(gear);
        }
        return specialItem(id);
    }

    private String giveableItemName(String id) {
        CustomMaterial material = customMaterials.get(id);
        if (material != null) {
            return material.displayName();
        }
        DeathTotemInfo deathTotem = deathTotemInfo(id);
        if (deathTotem != null) {
            return deathTotem.displayName();
        }
        BossKeyInfo key = bossKeyInfo(id);
        if (key != null) {
            return key.displayName();
        }
        BossRelicInfo relic = bossRelicInfo(id);
        if (relic != null) {
            return relic.displayName();
        }
        BossAltarInfo altar = bossAltarInfo(id);
        if (altar != null) {
            return altar.displayName();
        }
        CustomGear gear = customGear(id);
        if (gear != null) {
            return gear.type().displayName() + " " + gear.family().itemSuffix();
        }
        return switch (id) {
            case "revive_totem" -> "Totem de Resurreccion";
            case "waystone_token" -> "Cristal de Ruta";
            default -> id;
        };
    }

    private boolean handleActionClick(Player player, ItemStack clicked) {
        String action = data(clicked, actionKey);
        if (action == null) {
            return false;
        }

        switch (action) {
            case "main":
                openShopMain(player);
                return true;
            case "buy":
                openBuyShop(player);
                return true;
            case "sell":
                openSellShop(player);
                return true;
            case "profile":
                openProfile(player);
                return true;
            case "waystones":
                openWaystones(player);
                return true;
            case "close":
                player.closeInventory();
                return true;
            default:
                return false;
        }
    }

    private void placeBuy(Player player, Inventory inv, int slot, String id) {
        BuyEntry entry = buyEntries.get(id);
        if (entry == null) {
            return;
        }

        boolean unlocked = isBuyUnlocked(player, id);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + entry.description());
        lore.add("");
        if (unlocked) {
            lore.add(ChatColor.GOLD + "Costo: " + ChatColor.YELLOW + entry.cost() + " pts");
            lore.add(ChatColor.GREEN + "Click para comprar");
        } else {
            lore.add(ChatColor.RED + "Bloqueado: completa una armadura de la fase anterior.");
        }

        ItemStack icon = entry.reward().clone();
        ItemMeta meta = icon.getItemMeta();
        if (meta != null) {
            meta.setDisplayName((unlocked ? ChatColor.YELLOW : ChatColor.DARK_GRAY) + entry.displayName());
            meta.setLore(lore);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            icon.setItemMeta(meta);
        } else {
            icon = item(entry.icon(), (unlocked ? ChatColor.YELLOW : ChatColor.DARK_GRAY) + entry.displayName(), lore);
        }
        setData(icon, buyKey, id);
        inv.setItem(slot, icon);
    }

    private void placeSell(Inventory inv, int slot, String id) {
        SellEntry entry = sellEntries.get(id);
        if (entry == null) {
            return;
        }

        ItemStack icon = item(entry.material(), ChatColor.AQUA + entry.displayName(),
                List.of(ChatColor.GRAY + "Valor: " + ChatColor.YELLOW + entry.price() + " pts c/u",
                        ChatColor.GREEN + "Click: vender 1",
                        ChatColor.GREEN + "Shift + click: vender todo"));
        setData(icon, sellKey, id);
        inv.setItem(slot, icon);
    }

    private ItemStack waystoneItem(Player player, String id) {
        String base = waystonePath(player) + "." + id;
        String world = getConfig().getString(base + ".world", "?");
        int x = getConfig().getInt(base + ".x");
        int y = getConfig().getInt(base + ".y");
        int z = getConfig().getInt(base + ".z");
        ItemStack stack = item(Material.LODESTONE, ChatColor.LIGHT_PURPLE + getConfig().getString(base + ".name", id),
                List.of(ChatColor.GRAY + world + "  " + x + ", " + y + ", " + z,
                        ChatColor.YELLOW + "Click para teletransportarte",
                        ChatColor.DARK_GRAY + "Borrar: /waystone borrar " + id));
        setData(stack, waystoneKey, id);
        return stack;
    }

    private ItemStack stat(Player player, Material material, String label, String objective, String suffix, ChatColor color) {
        return item(material, color + label,
                List.of(ChatColor.GRAY + "Valor: " + ChatColor.YELLOW + getScore(player, objective) + suffix));
    }

    private ItemStack item(Material material, String name, List<String> lore) {
        ItemStack stack = new ItemStack(material, 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(lore);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    private ItemStack actionItem(Material material, String name, List<String> lore, String action) {
        ItemStack stack = item(material, name, lore);
        setData(stack, actionKey, action);
        return stack;
    }

    private ItemStack stack(Material material, int amount) {
        return new ItemStack(material, amount);
    }

    private ItemStack reviveTotem() {
        ItemStack stack = new ItemStack(Material.CARROT_ON_A_STICK, 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Totem de Resurreccion");
            meta.setLore(List.of(
                    ChatColor.GRAY + "Usalo cerca de un jugador eliminado.",
                    ChatColor.DARK_GRAY + "Objeto de HardcorePlus"));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.setItemModel(new NamespacedKey("hardcoreplus", "revive_totem"));
            meta.getPersistentDataContainer().set(specialKey, PersistentDataType.STRING, "revive_totem");
            stack.setItemMeta(meta);
        }
        return stack;
    }

    private ItemStack waystoneToken() {
        ItemStack stack = new ItemStack(Material.LODESTONE, 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Cristal de Ruta");
            meta.setLore(List.of(
                    ChatColor.GRAY + "Guarda tu ubicacion actual.",
                    ChatColor.YELLOW + "Uso: /waystone crear <nombre>",
                    ChatColor.DARK_GRAY + "Se consume al crear el lugar."));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.setItemModel(new NamespacedKey("hardcoreplus", "waystone_token"));
            meta.getPersistentDataContainer().set(specialKey, PersistentDataType.STRING, "waystone_token");
            stack.setItemMeta(meta);
        }
        return stack;
    }

    private ItemStack bossKeyItem(String id) {
        BossKeyInfo info = bossKeyInfo(id);
        if (info == null) {
            throw new IllegalArgumentException("Llave de jefe desconocida: " + id);
        }

        ItemStack stack = new ItemStack(info.material(), 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(info.color() + "" + ChatColor.BOLD + info.displayName());
            meta.setLore(List.of(
                    ChatColor.GRAY + info.description(),
                    ChatColor.YELLOW + "Click derecho en Trial Spawner o Vault.",
                    ChatColor.DARK_GRAY + "Llave de altar HardcorePlus."));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.setItemModel(new NamespacedKey("hardcoreplus", id));
            meta.getPersistentDataContainer().set(specialKey, PersistentDataType.STRING, id);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    private ItemStack bossRelicItem(String id) {
        BossRelicInfo info = bossRelicInfo(id);
        if (info == null) {
            throw new IllegalArgumentException("Reliquia de jefe desconocida: " + id);
        }

        ItemStack stack = new ItemStack(info.material(), 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(info.color() + "" + ChatColor.BOLD + info.displayName());
            meta.setLore(List.of(
                    ChatColor.GRAY + info.description(),
                    ChatColor.DARK_GRAY + "Reliquia de boss HardcorePlus."));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.setItemModel(new NamespacedKey("hardcoreplus", id));
            meta.getPersistentDataContainer().set(specialKey, PersistentDataType.STRING, id);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    private ItemStack bossAltarItem(String id) {
        BossAltarInfo info = bossAltarInfo(id);
        if (info == null) {
            throw new IllegalArgumentException("Altar de jefe desconocido: " + id);
        }

        ItemStack stack = new ItemStack(info.material(), 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(info.color() + "" + ChatColor.BOLD + info.displayName());
            meta.setLore(List.of(
                    ChatColor.GRAY + info.description(),
                    ChatColor.YELLOW + "Colocalo y usa " + bossKeyNameForBoss(info.bossId()) + ".",
                    ChatColor.DARK_GRAY + "Altar de jefe HardcorePlus."));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.setItemModel(new NamespacedKey("hardcoreplus", id));
            meta.getPersistentDataContainer().set(specialKey, PersistentDataType.STRING, id);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    private ItemStack specialItem(String id) {
        if (BOSS_KEY_IDS.contains(id)) {
            return bossKeyItem(id);
        }
        if (BOSS_RELIC_IDS.contains(id)) {
            return bossRelicItem(id);
        }
        if (BOSS_ALTAR_IDS.contains(id)) {
            return bossAltarItem(id);
        }
        return switch (id) {
            case "revive_totem" -> reviveTotem();
            case "waystone_token" -> waystoneToken();
            case "phoenix_totem", "colossus_totem", "time_totem", "celestial_totem", "totemcito" -> deathTotem(id);
            default -> null;
        };
    }

    private ItemStack deathTotem(String id) {
        DeathTotemInfo info = deathTotemInfo(id);
        if (info == null) {
            throw new IllegalArgumentException("Totem especial desconocido: " + id);
        }

        ItemStack stack = new ItemStack(Material.CARROT_ON_A_STICK, 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(info.color() + "" + ChatColor.BOLD + info.displayName());
            meta.setLore(List.of(
                    ChatColor.GRAY + info.effectLore(),
                    ChatColor.GRAY + info.extraLore(),
                    info.craftable() ? ChatColor.DARK_GRAY + "Tenerlo en la mano o secundaria al morir."
                            : ChatColor.DARK_GRAY + "Solo para eventos y pruebas."));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.setItemModel(new NamespacedKey("hardcoreplus", id));
            meta.getPersistentDataContainer().set(specialKey, PersistentDataType.STRING, id);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    private DeathTotemInfo deathTotemInfo(String id) {
        return switch (id) {
            case "phoenix_totem" -> new DeathTotemInfo(ChatColor.GOLD, "Totem del Fenix",
                    "Revive con 50% de vida y aura de fuego 10s.",
                    "Regeneracion I durante 25s.", true);
            case "colossus_totem" -> new DeathTotemInfo(ChatColor.GRAY, "Totem del Coloso",
                    "Revive con Resistencia IV y Fuerza III por 15s.",
                    "Empuja a los mobs cercanos.", true);
            case "time_totem" -> new DeathTotemInfo(ChatColor.AQUA, "Totem del Tiempo",
                    "Revive congelando mobs cercanos durante 5s.",
                    "Velocidad III durante 10s.", true);
            case "celestial_totem" -> new DeathTotemInfo(ChatColor.LIGHT_PURPLE, "Totem Celestial",
                    "Revive con la vida completa y lanza rayos cercanos.",
                    "Regeneracion III durante 10s.", true);
            case "totemcito" -> new DeathTotemInfo(ChatColor.RED, "Totemcito",
                    "Revive con 1 corazon y te teleporta hasta 50 bloques.",
                    "Da un efecto aleatorio durante 10s.", false);
            default -> null;
        };
    }

    private BossKeyInfo bossKeyInfo(String id) {
        if (id == null) {
            return null;
        }
        return switch (id) {
            case "colossus_key" -> new BossKeyInfo("colossus_key", "colossus", ChatColor.GRAY,
                    "Llave del Coloso", Material.TRIAL_KEY,
                    "Abre la ruta de jefes despues de Angel, Titanio y Leviatan.");
            case "eclipse_key" -> new BossKeyInfo("eclipse_key", "eclipse", ChatColor.YELLOW,
                    "Llave del Eclipse", Material.TRIAL_KEY,
                    "Despierta al Hombre Lobo del Eclipse.");
            case "void_key" -> new BossKeyInfo("void_key", "void", ChatColor.DARK_PURPLE,
                    "Llave del Vacio", Material.OMINOUS_TRIAL_KEY,
                    "Abre el altar de la Arana del Vacio.");
            case "phoenix_key" -> new BossKeyInfo("phoenix_key", "phoenix", ChatColor.GOLD,
                    "Llave del Fenix", Material.OMINOUS_TRIAL_KEY,
                    "Enciende el altar del Fenix Ardiente.");
            case "celestial_key" -> new BossKeyInfo("celestial_key", "celestial", ChatColor.LIGHT_PURPLE,
                    "Llave Celestial", Material.OMINOUS_TRIAL_KEY,
                    "Se forja con las reliquias del Wither, Guarden y Dragona.");
            case "infinity_key" -> new BossKeyInfo("infinity_key", "infinity", ChatColor.LIGHT_PURPLE,
                    "Llave del Infinito", Material.OMINOUS_TRIAL_KEY,
                    "Abre el ultimo altar despues de vencer a los otros jefes.");
            default -> null;
        };
    }

    private BossRelicInfo bossRelicInfo(String id) {
        if (id == null) {
            return null;
        }
        return switch (id) {
            case "chaos_star" -> new BossRelicInfo(ChatColor.DARK_RED, "Estrella del Nether Corrupta",
                    Material.NETHER_STAR, "Reliquia del Wither del Caos.");
            case "warden_heart" -> new BossRelicInfo(ChatColor.AQUA, "Corazon del Guarden",
                    Material.ECHO_SHARD, "Reliquia del Guarden del Tiempo.");
            case "dragon_heart" -> new BossRelicInfo(ChatColor.DARK_RED, "Corazon de la Dragona",
                    Material.DRAGON_BREATH, "Reliquia de la Dragona Ancestral.");
            default -> null;
        };
    }

    private BossAltarInfo bossAltarInfo(String id) {
        if (id == null) {
            return null;
        }
        return switch (id) {
            case "colossus_altar" -> new BossAltarInfo("colossus_altar", "colossus", ChatColor.GRAY,
                    "Altar del Coloso", Material.TRIAL_SPAWNER,
                    "Nucleo pesado para despertar al Coloso Ancestral.");
            case "eclipse_altar" -> new BossAltarInfo("eclipse_altar", "eclipse", ChatColor.YELLOW,
                    "Altar del Eclipse", Material.TRIAL_SPAWNER,
                    "Altar lunar para despertar al Hombre Lobo del Eclipse.");
            case "void_altar" -> new BossAltarInfo("void_altar", "void", ChatColor.DARK_PURPLE,
                    "Altar del Vacio", Material.TRIAL_SPAWNER,
                    "Altar oscuro para abrir el nido de la Arana del Vacio.");
            case "phoenix_altar" -> new BossAltarInfo("phoenix_altar", "phoenix", ChatColor.GOLD,
                    "Altar del Fenix", Material.TRIAL_SPAWNER,
                    "Brasero antiguo para invocar al Fenix Ardiente.");
            case "celestial_altar" -> new BossAltarInfo("celestial_altar", "celestial", ChatColor.LIGHT_PURPLE,
                    "Altar Celestial", Material.VAULT,
                    "Vault divino que responde a la Llave Celestial.");
            case "infinity_altar" -> new BossAltarInfo("infinity_altar", "infinity", ChatColor.LIGHT_PURPLE,
                    "Altar del Infinito", Material.VAULT,
                    "El ultimo sello para liberar al Avatar del Infinito.");
            default -> null;
        };
    }

    private CustomGear customGear(String id) {
        if (id == null) {
            return null;
        }
        return customGears.get(id);
    }

    private ItemStack gearItem(CustomGear gear) {
        ItemStack stack = new ItemStack(gear.type().material(), 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            double damage = gearDamage(gear);
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Familia: " + gear.family().color() + gear.family().displayName());
            if (gear.type().melee()) {
                lore.add(ChatColor.GRAY + "Dano base: " + ChatColor.YELLOW + formatStat(damage));
                lore.add(ChatColor.GRAY + "Velocidad: " + ChatColor.YELLOW + formatStat(gear.type().attackSpeed()));
            } else if (gear.type().projectile()) {
                lore.add(ChatColor.GRAY + "Dano extra de proyectil: " + ChatColor.YELLOW
                        + formatStat(Math.max(2.0, gear.family().power() * 0.65)));
            }
            lore.add(ChatColor.GRAY + "Rasgo: " + ChatColor.YELLOW + gear.family().traitLore());
            lore.add("");
            lore.add(ChatColor.DARK_GRAY + "Objeto legendario de HardcorePlus");

            meta.setDisplayName(gear.family().color() + "" + ChatColor.BOLD
                    + gear.type().displayName() + " " + gear.family().itemSuffix());
            meta.setLore(lore);
            meta.addEnchant(Enchantment.UNBREAKING, Math.min(8, 3 + (int) Math.floor(gear.family().power() / 3.0)), true);
            meta.addEnchant(Enchantment.MENDING, 1, true);
            addGearEnchantments(meta, gear);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            if (gear.type().melee()) {
                meta.addAttributeModifier(Attribute.ATTACK_DAMAGE,
                        new AttributeModifier(new NamespacedKey(this, gear.id() + "_attack_damage"),
                                damage, Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND));
                meta.addAttributeModifier(Attribute.ATTACK_SPEED,
                        new AttributeModifier(new NamespacedKey(this, gear.id() + "_attack_speed"),
                                gear.type().attackSpeed(), Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND));
                if (gear.type() == GearType.SPEAR) {
                    meta.addAttributeModifier(Attribute.ENTITY_INTERACTION_RANGE,
                            new AttributeModifier(new NamespacedKey(this, gear.id() + "_reach"),
                                    1.0, Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND));
                }
            }
            meta.setItemModel(new NamespacedKey("hardcoreplus", gear.id()));
            meta.getPersistentDataContainer().set(specialKey, PersistentDataType.STRING, gear.id());
            stack.setItemMeta(meta);
        }
        return stack;
    }

    private void addGearEnchantments(ItemMeta meta, CustomGear gear) {
        int level = Math.max(1, Math.min(8, (int) Math.ceil(gear.family().power() / 3.0)));
        switch (gear.type()) {
            case SWORD, SPEAR -> {
                meta.addEnchant(Enchantment.SHARPNESS, Math.min(7, 3 + level), true);
                if (gear.family().id().equals("phoenix")) {
                    meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
                }
                if (gear.family().power() >= 8.0) {
                    meta.addEnchant(Enchantment.LOOTING, 3, true);
                }
            }
            case AXE -> meta.addEnchant(Enchantment.SHARPNESS, Math.min(7, 2 + level), true);
            case BOW -> {
                meta.addEnchant(Enchantment.POWER, Math.min(8, 3 + level), true);
                if (gear.family().power() >= 5.0) {
                    meta.addEnchant(Enchantment.PUNCH, 2, true);
                }
                if (gear.family().id().equals("phoenix") || gear.family().id().equals("eclipse")) {
                    meta.addEnchant(Enchantment.FLAME, 1, true);
                }
            }
            case PICKAXE -> {
                meta.addEnchant(Enchantment.EFFICIENCY, Math.min(8, 3 + level), true);
                meta.addEnchant(Enchantment.FORTUNE, Math.min(5, 1 + level / 2), true);
            }
            case TRIDENT -> {
                meta.addEnchant(Enchantment.LOYALTY, 3, true);
                meta.addEnchant(Enchantment.IMPALING, Math.min(7, 3 + level), true);
                if (gear.family().power() >= 7.0) {
                    meta.addEnchant(Enchantment.CHANNELING, 1, true);
                }
            }
        }
    }

    private double gearDamage(CustomGear gear) {
        return gear.type().baseDamage() + gear.family().power();
    }

    private String gearId(GearFamily family, GearType type) {
        return family.id() + "_" + type.id();
    }

    private ItemStack customMaterialItem(String id) {
        CustomMaterial material = customMaterials.get(id);
        if (material == null) {
            throw new IllegalArgumentException("Material especial desconocido: " + id);
        }

        ItemStack stack = new ItemStack(material.material(), 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(material.color() + "" + ChatColor.BOLD + material.displayName());
            meta.setLore(List.of(
                    ChatColor.GRAY + material.description(),
                    ChatColor.DARK_GRAY + "Material legendario de HardcorePlus"));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.setItemModel(new NamespacedKey("hardcoreplus", id));
            meta.getPersistentDataContainer().set(specialKey, PersistentDataType.STRING, id);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    private ItemStack armorItem(ArmorSet set, ArmorPiece piece) {
        ItemStack stack = new ItemStack(piece.material(), 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            List<String> lore = new ArrayList<>();
            double totalArmor = piece.baseArmor() + set.armorBonus();
            double totalToughness = NETHERITE_TOUGHNESS + set.toughnessBonus();
            lore.add(ChatColor.GRAY + "Set: " + set.color() + set.displayName());
            lore.add(ChatColor.GRAY + "Proteccion base: " + ChatColor.AQUA + "netherita "
                    + ChatColor.YELLOW + "+" + set.armorBonus());
            lore.add(ChatColor.GRAY + "Armadura de esta pieza: " + ChatColor.YELLOW + formatStat(totalArmor));
            lore.add("");
            lore.add(ChatColor.GOLD + "Bonus 4 piezas:");
            for (String line : set.bonusLore()) {
                lore.add(ChatColor.GRAY + "- " + line);
            }

            String id = set.id() + "_" + piece.id();
            meta.setDisplayName(set.color() + "" + ChatColor.BOLD + piece.displayName() + " " + set.displayName());
            meta.setLore(lore);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.getPersistentDataContainer().set(specialKey, PersistentDataType.STRING, id);
            meta.addAttributeModifier(Attribute.ARMOR,
                    new AttributeModifier(new NamespacedKey(this, id + "_armor_total"), totalArmor, Operation.ADD_NUMBER, piece.slotGroup()));
            meta.addAttributeModifier(Attribute.ARMOR_TOUGHNESS,
                    new AttributeModifier(new NamespacedKey(this, id + "_toughness_total"), totalToughness, Operation.ADD_NUMBER, piece.slotGroup()));
            double knockbackResistance = NETHERITE_KNOCKBACK_RESISTANCE
                    + (set.id().equals("dragon") || set.id().equals("infinity") ? 0.25 : 0.0);
            meta.addAttributeModifier(Attribute.KNOCKBACK_RESISTANCE,
                    new AttributeModifier(new NamespacedKey(this, id + "_knockback_total"), knockbackResistance, Operation.ADD_NUMBER, piece.slotGroup()));
            meta.setItemModel(new NamespacedKey("hardcoreplus", id));
            EquippableComponent equippable = meta.getEquippable();
            equippable.setSlot(equipmentSlot(piece));
            equippable.setModel(new NamespacedKey("hardcoreplus", set.id()));
            meta.setEquippable(equippable);
            stack.setItemMeta(meta);
        }

        int unbreaking = set.id().equals("titanium") ? 5 : Math.min(8, 3 + Math.max(0, set.armorBonus() / 4));
        stack.addUnsafeEnchantment(Enchantment.UNBREAKING, unbreaking);
        stack.addUnsafeEnchantment(Enchantment.MENDING, 1);
        return stack;
    }

    private ItemStack angelEssence() {
        if (customMaterials.containsKey("angel_essence")) {
            return customMaterialItem("angel_essence");
        }

        ItemStack stack = new ItemStack(Material.ECHO_SHARD, 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Esencia de Angel");
            meta.setLore(List.of(
                    ChatColor.GRAY + "Material sagrado corrompido.",
                    ChatColor.GRAY + "Usado para crear la Armadura Angel Caido.",
                    ChatColor.DARK_GRAY + "Material de HardcorePlus"));
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.getPersistentDataContainer().set(specialKey, PersistentDataType.STRING, "angel_essence");
            stack.setItemMeta(meta);
        }
        return stack;
    }

    private ItemStack angelArmor(String piece) {
        Material material;
        EquipmentSlot slot;
        EquipmentSlotGroup slotGroup;
        String id;
        String name;
        double armor;
        switch (piece) {
            case "helmet":
                material = Material.DIAMOND_HELMET;
                slot = EquipmentSlot.HEAD;
                slotGroup = EquipmentSlotGroup.HEAD;
                id = "angel_helmet";
                name = "Corona del Angel Caido";
                armor = ANGEL_HELMET_ARMOR;
                break;
            case "chestplate":
                material = Material.DIAMOND_CHESTPLATE;
                slot = EquipmentSlot.CHEST;
                slotGroup = EquipmentSlotGroup.CHEST;
                id = "angel_chestplate";
                name = "Pechera del Angel Caido";
                armor = ANGEL_CHESTPLATE_ARMOR;
                break;
            case "leggings":
                material = Material.DIAMOND_LEGGINGS;
                slot = EquipmentSlot.LEGS;
                slotGroup = EquipmentSlotGroup.LEGS;
                id = "angel_leggings";
                name = "Pantalon del Angel Caido";
                armor = ANGEL_LEGGINGS_ARMOR;
                break;
            case "boots":
                material = Material.DIAMOND_BOOTS;
                slot = EquipmentSlot.FEET;
                slotGroup = EquipmentSlotGroup.FEET;
                id = "angel_boots";
                name = "Zapatillas del Angel Caido";
                armor = ANGEL_BOOTS_ARMOR;
                break;
            default:
                throw new IllegalArgumentException("Pieza angelical desconocida: " + piece);
        }

        ItemStack stack = new ItemStack(material, 1);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + name);
            meta.setLore(List.of(
                    ChatColor.GRAY + "Set: " + ChatColor.YELLOW + "Armadura Angel Caido",
                    ChatColor.GRAY + "Proteccion base: " + ChatColor.AQUA + "netherita +1",
                    ChatColor.GRAY + "Armadura de esta pieza: " + ChatColor.YELLOW + formatStat(armor),
                    "",
                    ChatColor.GOLD + "Bonus 4 piezas:",
                    ChatColor.GRAY + "- Regeneracion II",
                    ChatColor.GRAY + "- Sin daño de caida",
                    ChatColor.GRAY + "- Totem angelical cada 7 minutos"));
            meta.addEnchant(Enchantment.UNBREAKING, 3, true);
            meta.addEnchant(Enchantment.MENDING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addAttributeModifier(Attribute.ARMOR,
                    new AttributeModifier(new NamespacedKey(this, id + "_armor_total"), armor, Operation.ADD_NUMBER, slotGroup));
            meta.addAttributeModifier(Attribute.ARMOR_TOUGHNESS,
                    new AttributeModifier(new NamespacedKey(this, id + "_toughness_total"), ANGEL_TOUGHNESS, Operation.ADD_NUMBER, slotGroup));
            meta.addAttributeModifier(Attribute.KNOCKBACK_RESISTANCE,
                    new AttributeModifier(new NamespacedKey(this, id + "_knockback_total"), NETHERITE_KNOCKBACK_RESISTANCE, Operation.ADD_NUMBER, slotGroup));
            meta.setItemModel(new NamespacedKey("hardcoreplus", id));
            EquippableComponent equippable = meta.getEquippable();
            equippable.setSlot(slot);
            equippable.setModel(new NamespacedKey("hardcoreplus", "angel"));
            meta.setEquippable(equippable);
            meta.getPersistentDataContainer().set(specialKey, PersistentDataType.STRING, id);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    private EquipmentSlot equipmentSlot(ArmorPiece piece) {
        return switch (piece) {
            case HELMET -> EquipmentSlot.HEAD;
            case CHESTPLATE -> EquipmentSlot.CHEST;
            case LEGGINGS -> EquipmentSlot.LEGS;
            case BOOTS -> EquipmentSlot.FEET;
        };
    }

    private void addBuy(String id, int cost, ItemStack reward, Material icon, String displayName, String description) {
        buyEntries.put(id, new BuyEntry(id, cost, reward, icon, displayName, description));
    }

    private void addSell(String id, Material material, int price, String displayName) {
        sellEntries.put(id, new SellEntry(id, material, price, displayName));
    }

    private boolean consumeSpecial(Player player, String specialId) {
        PlayerInventory inv = player.getInventory();
        ItemStack[] contents = inv.getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack stack = contents[i];
            if (!specialId.equals(data(stack, specialKey))) {
                continue;
            }
            int amount = stack.getAmount();
            if (amount <= 1) {
                inv.setItem(i, null);
            } else {
                stack.setAmount(amount - 1);
            }
            return true;
        }
        return false;
    }

    private int countMaterial(PlayerInventory inv, Material material) {
        int total = 0;
        for (ItemStack stack : inv.getContents()) {
            if (stack != null && stack.getType() == material) {
                total += stack.getAmount();
            }
        }
        return total;
    }

    private void removeMaterial(PlayerInventory inv, Material material, int amount) {
        int remaining = amount;
        ItemStack[] contents = inv.getContents();
        for (int i = 0; i < contents.length && remaining > 0; i++) {
            ItemStack stack = contents[i];
            if (stack == null || stack.getType() != material) {
                continue;
            }
            int take = Math.min(stack.getAmount(), remaining);
            stack.setAmount(stack.getAmount() - take);
            remaining -= take;
            if (stack.getAmount() <= 0) {
                inv.setItem(i, null);
            }
        }
    }

    private int getScore(Player player, String objectiveName) {
        return getScore(player.getName(), objectiveName);
    }

    private int getScore(String entry, String objectiveName) {
        Objective objective = getObjective(objectiveName);
        if (objective == null) {
            return 0;
        }
        return objective.getScore(entry).getScore();
    }

    private void addScore(Player player, String objectiveName, int amount) {
        if (!addScore(player.getName(), objectiveName, amount)) {
            player.sendMessage(ChatColor.RED + "Falta el scoreboard " + objectiveName + ". Ejecuta /reload.");
        }
    }

    private boolean addScore(String entry, String objectiveName, int amount) {
        Objective objective = getObjective(objectiveName);
        if (objective == null) {
            return false;
        }
        objective.getScore(entry).setScore(getScore(entry, objectiveName) + amount);
        return true;
    }

    private Objective getObjective(String objectiveName) {
        Scoreboard board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        return board.getObjective(objectiveName);
    }

    private String data(ItemStack stack, NamespacedKey key) {
        if (stack == null || stack.getType().isAir()) {
            return null;
        }
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) {
            return null;
        }
        return meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
    }

    private String data(Entity entity, NamespacedKey key) {
        if (entity == null) {
            return null;
        }
        return entity.getPersistentDataContainer().get(key, PersistentDataType.STRING);
    }

    private void setData(ItemStack stack, NamespacedKey key, String value) {
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, value);
            stack.setItemMeta(meta);
        }
    }

    private void setData(Entity entity, NamespacedKey key, String value) {
        entity.getPersistentDataContainer().set(key, PersistentDataType.STRING, value);
    }

    private ConfigurationSection waystoneSection(Player player) {
        return getConfig().getConfigurationSection(waystonePath(player));
    }

    private String waystonePath(Player player) {
        return "waystones." + player.getUniqueId();
    }

    private String joinArgs(String[] args, int start) {
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(args[i]);
        }
        return builder.toString();
    }

    private String normalize(String value) {
        return cleanName(value).toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9_-]+", "_").replaceAll("_+", "_");
    }

    private String cleanName(String value) {
        String clean = value.trim();
        if (clean.length() > 24) {
            return clean.substring(0, 24);
        }
        return clean;
    }

    private String formatStat(double value) {
        if (value == Math.rint(value)) {
            return String.valueOf((int) value);
        }
        return String.format(Locale.ROOT, "%.1f", value);
    }

    private void fill(Inventory inv) {
        ItemStack pane = item(Material.BLACK_STAINED_GLASS_PANE, " ", List.of());
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, pane);
        }
    }

    private enum GearType {
        SWORD("sword", "Espada", Material.NETHERITE_SWORD, Material.NETHERITE_SWORD, 8.0, -2.4, true, false,
                " M ",
                " A ",
                " S "),
        AXE("axe", "Hacha", Material.NETHERITE_AXE, Material.NETHERITE_AXE, 10.0, -3.0, true, false,
                "MX ",
                "MA ",
                " S "),
        BOW("bow", "Arco", Material.BOW, Material.BOW, 6.0, 0.0, false, true,
                " MS",
                "A X",
                " MS"),
        PICKAXE("pickaxe", "Pico", Material.NETHERITE_PICKAXE, Material.NETHERITE_PICKAXE, 6.0, -2.8, true, false,
                "MMM",
                " A ",
                " S "),
        TRIDENT("trident", "Tridente", Material.TRIDENT, Material.TRIDENT, 9.0, -2.9, true, true,
                " M ",
                "SAS",
                " X "),
        SPEAR("spear", "Lanza", Material.NETHERITE_SWORD, Material.NETHERITE_SWORD, 9.5, -3.1, true, false,
                "  M",
                " A ",
                "S X");

        private final String id;
        private final String displayName;
        private final Material material;
        private final Material baseIngredient;
        private final double baseDamage;
        private final double attackSpeed;
        private final boolean melee;
        private final boolean projectile;
        private final String[] shape;

        GearType(String id, String displayName, Material material, Material baseIngredient,
                double baseDamage, double attackSpeed, boolean melee, boolean projectile, String... shape) {
            this.id = id;
            this.displayName = displayName;
            this.material = material;
            this.baseIngredient = baseIngredient;
            this.baseDamage = baseDamage;
            this.attackSpeed = attackSpeed;
            this.melee = melee;
            this.projectile = projectile;
            this.shape = shape;
        }

        private String id() {
            return id;
        }

        private String displayName() {
            return displayName;
        }

        private Material material() {
            return material;
        }

        private Material baseIngredient() {
            return baseIngredient;
        }

        private double baseDamage() {
            return baseDamage;
        }

        private double attackSpeed() {
            return attackSpeed;
        }

        private boolean melee() {
            return melee;
        }

        private boolean projectile() {
            return projectile;
        }

        private String[] shape() {
            return shape;
        }
    }

    private enum ArmorPiece {
        HELMET("helmet", "Casco", Material.NETHERITE_HELMET, EquipmentSlotGroup.HEAD, 3.0),
        CHESTPLATE("chestplate", "Pechera", Material.NETHERITE_CHESTPLATE, EquipmentSlotGroup.CHEST, 8.0),
        LEGGINGS("leggings", "Pantalon", Material.NETHERITE_LEGGINGS, EquipmentSlotGroup.LEGS, 6.0),
        BOOTS("boots", "Botas", Material.NETHERITE_BOOTS, EquipmentSlotGroup.FEET, 3.0);

        private final String id;
        private final String displayName;
        private final Material material;
        private final EquipmentSlotGroup slotGroup;
        private final double baseArmor;

        ArmorPiece(String id, String displayName, Material material, EquipmentSlotGroup slotGroup, double baseArmor) {
            this.id = id;
            this.displayName = displayName;
            this.material = material;
            this.slotGroup = slotGroup;
            this.baseArmor = baseArmor;
        }

        private String id() {
            return id;
        }

        private String displayName() {
            return displayName;
        }

        private Material material() {
            return material;
        }

        private EquipmentSlotGroup slotGroup() {
            return slotGroup;
        }

        private double baseArmor() {
            return baseArmor;
        }
    }

    private record CustomMaterial(String id, Material material, ChatColor color, String displayName, int cost,
            String description) {
    }

    private record ArmorSet(String id, String displayName, ChatColor color, int armorBonus, int toughnessBonus,
            String materialId, Material secondary, Material tertiary, List<String> bonusLore) {
    }

    private record GearFamily(String id, String displayName, String itemSuffix, ChatColor color, String materialId,
            Material secondary, Material tertiary, double power, String traitLore) {
    }

    private record CustomGear(String id, GearFamily family, GearType type) {
    }

    private record DeathTotemInfo(ChatColor color, String displayName, String effectLore, String extraLore,
            boolean craftable) {
    }

    private record BossDefinition(String id, String displayName, ChatColor color, BarColor barColor,
            EntityType entityType, double maxHealth, double attackDamage, double movementSpeed,
            String dropMaterialId, String nextSpecialDropId, int points, boolean altarBoss, double rewardRadius) {
    }

    private record BossKeyInfo(String id, String bossId, ChatColor color, String displayName, Material material,
            String description) {
    }

    private record BossRelicInfo(ChatColor color, String displayName, Material material, String description) {
    }

    private record BossAltarInfo(String id, String bossId, ChatColor color, String displayName, Material material,
            String description) {
    }

    private record AltarStyle(Material base, Material trim, Material core, Material crystal, Material rune) {
    }

    private record PvpSnapshot(Location returnLocation, ItemStack[] storage, ItemStack[] armor, ItemStack offhand,
            GameMode gameMode, boolean allowFlight, boolean flying, double health, int foodLevel, float saturation,
            float exp, int level, List<PotionEffect> effects) {
    }

    private record BuyEntry(String id, int cost, ItemStack reward, Material icon, String displayName, String description) {
    }

    private record SellEntry(String id, Material material, int price, String displayName) {
    }
}
