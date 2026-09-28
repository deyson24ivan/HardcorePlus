# Invoca a la Dragona Corrupta.

scoreboard players set #dragona hp.boss_state 1
bossbar set hardcore:dragona max 500
bossbar set hardcore:dragona value 500
bossbar set hardcore:dragona players @a
bossbar set hardcore:dragona visible true

summon minecraft:marker ~ ~ ~ {Tags:["hp.boss_anchor","hp.boss_dragona_anchor"]}
summon minecraft:phantom ~ ~6 ~ {Tags:["hp.boss","hp.boss_dragona","hp.mob_boosted"],PersistenceRequired:1b,Size:8}
execute as @e[tag=hp.boss_dragona,sort=nearest,limit=1] at @s run function hardcore:bosses/dragona/setup

title @a title {text:"LA DRAGONA CORRUPTA",color:"light_purple",bold:true}
title @a subtitle {text:"El cielo se abre sobre el End",color:"dark_purple"}
playsound minecraft:entity.ender_dragon.growl hostile @a ~ ~ ~ 1 0.8
tellraw @a {text:"",extra:[{text:"👑 Boss invocado: ",color:"gold",bold:true},{text:"LA DRAGONA CORRUPTA",color:"light_purple",bold:true}]}
