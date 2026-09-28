# Invoca a la Bruja Carmesi.

scoreboard players set #bruja hp.boss_state 1
bossbar set hardcore:bruja max 450
bossbar set hardcore:bruja value 450
bossbar set hardcore:bruja players @a
bossbar set hardcore:bruja visible true

summon minecraft:marker ~ ~ ~ {Tags:["hp.boss_anchor","hp.boss_bruja_anchor"]}
summon minecraft:witch ~ ~ ~ {Tags:["hp.boss","hp.boss_bruja","hp.mob_boosted"],PersistenceRequired:1b}
execute as @e[tag=hp.boss_bruja,sort=nearest,limit=1] at @s run function hardcore:bosses/bruja/setup

title @a title {text:"LA BRUJA CARMESI",color:"dark_red",bold:true}
title @a subtitle {text:"El aire se vuelve veneno",color:"red"}
playsound minecraft:entity.witch.celebrate hostile @a ~ ~ ~ 1 0.75
tellraw @a {text:"",extra:[{text:"👑 Boss invocado: ",color:"gold",bold:true},{text:"LA BRUJA CARMESI",color:"dark_red",bold:true}]}
