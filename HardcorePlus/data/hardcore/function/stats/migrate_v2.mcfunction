# Prepara jugadores existentes para la economia nueva sin dar puntos retroactivos.

scoreboard players add @s hp.hostiles 0
scoreboard players add @s hp.animals 0
scoreboard players add @s hp.diamonds 0
scoreboard players add @s hp.netherite 0

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

tag @s add hp.stats_v2
