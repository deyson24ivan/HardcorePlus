# HardcorePlus v0.7.1
# Se ejecuta cuando Minecraft carga o recarga el datapack.

scoreboard objectives add hp.lives dummy
scoreboard objectives add hp.deaths deathCount
scoreboard objectives add hp.lastdeath dummy
scoreboard objectives add hp.anim_life dummy
scoreboard objectives add hp.anim_elim dummy
scoreboard objectives add hp.revive_click minecraft.used:minecraft.carrot_on_a_stick
scoreboard objectives add hp.revives dummy
scoreboard objectives add hp.sleep minecraft.custom:minecraft.sleep_in_bed
scoreboard objectives add hp.lastsleep dummy
scoreboard objectives add hp.day dummy
scoreboard objectives add hp.stage dummy
scoreboard objectives add hp.rng dummy
scoreboard objectives add hp.points dummy
scoreboard objectives add hp.mobs dummy
scoreboard objectives add hp.hostiles dummy
scoreboard objectives add hp.animals dummy
scoreboard objectives add hp.elites dummy
scoreboard objectives add hp.bosses dummy
scoreboard objectives add hp.bloodmoons dummy
scoreboard objectives add hp.events dummy
scoreboard objectives add hp.diamonds dummy
scoreboard objectives add hp.netherite dummy
scoreboard objectives add hp.achievements dummy
scoreboard objectives add hp.surv_days dummy
scoreboard objectives add hp.surv_ticks dummy
scoreboard objectives add hp.mob_raw minecraft.custom:minecraft.mob_kills
scoreboard objectives add hp.last_mob_raw dummy
scoreboard objectives add hp.hostile_raw dummy
scoreboard objectives add hp.last_hostile dummy
scoreboard objectives add hp.animal_raw dummy
scoreboard objectives add hp.last_animal dummy
scoreboard objectives add hp.damage_raw minecraft.custom:minecraft.damage_dealt
scoreboard objectives add hp.damage dummy
scoreboard objectives add hp.last_dmg_raw dummy
scoreboard objectives add hp.dia_raw dummy
scoreboard objectives add hp.last_dia_raw dummy
scoreboard objectives add hp.neth_raw dummy
scoreboard objectives add hp.last_neth_raw dummy
scoreboard objectives add hp.health health
scoreboard objectives add hp.kzombie minecraft.killed:minecraft.zombie
scoreboard objectives add hp.kskeleton minecraft.killed:minecraft.skeleton
scoreboard objectives add hp.kcreeper minecraft.killed:minecraft.creeper
scoreboard objectives add hp.kspider minecraft.killed:minecraft.spider
scoreboard objectives add hp.kcspider minecraft.killed:minecraft.cave_spider
scoreboard objectives add hp.khusk minecraft.killed:minecraft.husk
scoreboard objectives add hp.kdrowned minecraft.killed:minecraft.drowned
scoreboard objectives add hp.kzvillager minecraft.killed:minecraft.zombie_villager
scoreboard objectives add hp.kstray minecraft.killed:minecraft.stray
scoreboard objectives add hp.kbogged minecraft.killed:minecraft.bogged
scoreboard objectives add hp.kwitch minecraft.killed:minecraft.witch
scoreboard objectives add hp.kenderman minecraft.killed:minecraft.enderman
scoreboard objectives add hp.kslime minecraft.killed:minecraft.slime
scoreboard objectives add hp.kmagma minecraft.killed:minecraft.magma_cube
scoreboard objectives add hp.kphantom minecraft.killed:minecraft.phantom
scoreboard objectives add hp.kpillager minecraft.killed:minecraft.pillager
scoreboard objectives add hp.kvindicator minecraft.killed:minecraft.vindicator
scoreboard objectives add hp.kevoker minecraft.killed:minecraft.evoker
scoreboard objectives add hp.kravager minecraft.killed:minecraft.ravager
scoreboard objectives add hp.kvex minecraft.killed:minecraft.vex
scoreboard objectives add hp.kblaze minecraft.killed:minecraft.blaze
scoreboard objectives add hp.kghast minecraft.killed:minecraft.ghast
scoreboard objectives add hp.kguardian minecraft.killed:minecraft.guardian
scoreboard objectives add hp.kwskel minecraft.killed:minecraft.wither_skeleton
scoreboard objectives add hp.kpiglin minecraft.killed:minecraft.piglin
scoreboard objectives add hp.kpbrute minecraft.killed:minecraft.piglin_brute
scoreboard objectives add hp.kzpiglin minecraft.killed:minecraft.zombified_piglin
scoreboard objectives add hp.khoglin minecraft.killed:minecraft.hoglin
scoreboard objectives add hp.kzoglin minecraft.killed:minecraft.zoglin
scoreboard objectives add hp.kendermite minecraft.killed:minecraft.endermite
scoreboard objectives add hp.ksilverfish minecraft.killed:minecraft.silverfish
scoreboard objectives add hp.kshulker minecraft.killed:minecraft.shulker
scoreboard objectives add hp.kbreeze minecraft.killed:minecraft.breeze
scoreboard objectives add hp.ksulfur minecraft.killed:minecraft.sulfur_cube
scoreboard objectives add hp.kcow minecraft.killed:minecraft.cow
scoreboard objectives add hp.kpig minecraft.killed:minecraft.pig
scoreboard objectives add hp.ksheep minecraft.killed:minecraft.sheep
scoreboard objectives add hp.kchicken minecraft.killed:minecraft.chicken
scoreboard objectives add hp.krabbit minecraft.killed:minecraft.rabbit
scoreboard objectives add hp.kgoat minecraft.killed:minecraft.goat
scoreboard objectives add hp.kmooshroom minecraft.killed:minecraft.mooshroom
scoreboard objectives add hp.mdia minecraft.mined:minecraft.diamond_ore
scoreboard objectives add hp.mdeepsdia minecraft.mined:minecraft.deepslate_diamond_ore
scoreboard objectives add hp.mdebris minecraft.mined:minecraft.ancient_debris
scoreboard objectives add hp.kelder minecraft.killed:minecraft.elder_guardian
scoreboard objectives add hp.kill_dragon minecraft.killed:minecraft.ender_dragon
scoreboard objectives add hp.kill_wither minecraft.killed:minecraft.wither
scoreboard objectives add hp.kill_warden minecraft.killed:minecraft.warden
scoreboard objectives add hp.last_elder dummy
scoreboard objectives add hp.last_dragon dummy
scoreboard objectives add hp.last_wither dummy
scoreboard objectives add hp.last_warden dummy
scoreboard objectives add hp.boss_raw dummy
scoreboard objectives add hp.last_boss_raw dummy
scoreboard objectives add hp.delta dummy
scoreboard objectives add hp.tmp dummy
scoreboard objectives add hp.const dummy
scoreboard objectives add hp.boss_state dummy
scoreboard objectives add hp.boss_phase dummy
scoreboard objectives add hp.boss_cd dummy
scoreboard objectives add hp.boss_hp dummy
scoreboard objectives setdisplay below_name hp.lives

scoreboard players add #world hp.day 0
scoreboard players add #daytime hp.day 0
scoreboard players add #last_daytime hp.day 0
scoreboard players add #target_day hp.day 0
scoreboard players add #last hp.day 0
scoreboard players add #day_base_set hp.day 0
scoreboard players add #sleep_cooldown hp.day 0
scoreboard players add #sleep_counted hp.day 0
scoreboard players add #stage hp.stage 0
scoreboard players set #boss_reward hp.const 100
scoreboard players set #hostile_reward hp.const 2
scoreboard players set #animal_reward hp.const 1
scoreboard players set #achievement_reward hp.const 100
scoreboard players set #elite_reward hp.const 50
scoreboard players set #bloodmoon_reward hp.const 100
scoreboard players set #event_reward hp.const 30
scoreboard players set #revive_reward hp.const 100
scoreboard players set #day_reward hp.const 5
scoreboard players set #diamond_reward hp.const 1
scoreboard players set #netherite_reward hp.const 2
scoreboard players set #elder_reward hp.const 250
scoreboard players set #wither_reward hp.const 500
scoreboard players set #dragon_reward hp.const 1000
scoreboard players set #warden_reward hp.const 1500
scoreboard players add #titan hp.boss_state 0
scoreboard players add #bruja hp.boss_state 0
scoreboard players add #ceniza hp.boss_state 0
scoreboard players add #abismo hp.boss_state 0
scoreboard players add #dragona hp.boss_state 0

function hardcore:ui/sidebar_setup
function hardcore:ui/sidebar_on
function hardcore:bosses/load

tellraw @a {text:"HardcorePlus v0.7.1 cargado: nombres flotantes de bosses ocultos.",color:"gold"}
function hardcore:modes/apply_change
