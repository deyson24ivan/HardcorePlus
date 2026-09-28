# Inicializa estadisticas compactas sin borrar el progreso existente.

scoreboard players add @s hp.points 0
scoreboard players add @s hp.mobs 0
scoreboard players add @s hp.hostiles 0
scoreboard players add @s hp.animals 0
scoreboard players add @s hp.elites 0
scoreboard players add @s hp.bosses 0
scoreboard players add @s hp.bloodmoons 0
scoreboard players add @s hp.events 0
scoreboard players add @s hp.diamonds 0
scoreboard players add @s hp.netherite 0
scoreboard players add @s hp.achievements 0
scoreboard players add @s hp.surv_days 0
scoreboard players add @s hp.surv_ticks 0
scoreboard players add @s hp.revives 0
scoreboard players add @s hp.damage 0

scoreboard players add @s hp.mob_raw 0
scoreboard players operation @s hp.last_mob_raw = @s hp.mob_raw
scoreboard players add @s hp.damage_raw 0
scoreboard players operation @s hp.last_dmg_raw = @s hp.damage_raw

function hardcore:stats/calc_hostiles
scoreboard players operation @s hp.last_hostile = @s hp.hostile_raw
function hardcore:stats/calc_animals
scoreboard players operation @s hp.last_animal = @s hp.animal_raw
function hardcore:stats/calc_mining
scoreboard players operation @s hp.last_dia_raw = @s hp.dia_raw
scoreboard players operation @s hp.last_neth_raw = @s hp.neth_raw

scoreboard players add @s hp.kelder 0
scoreboard players add @s hp.kill_dragon 0
scoreboard players add @s hp.kill_wither 0
scoreboard players add @s hp.kill_warden 0
scoreboard players operation @s hp.last_elder = @s hp.kelder
scoreboard players operation @s hp.last_dragon = @s hp.kill_dragon
scoreboard players operation @s hp.last_wither = @s hp.kill_wither
scoreboard players operation @s hp.last_warden = @s hp.kill_warden

tag @s add hp.stats_ready
tag @s add hp.stats_v2
