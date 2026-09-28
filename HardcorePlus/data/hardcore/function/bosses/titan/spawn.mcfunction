# Invoca al Titan de Hierro.

scoreboard players set #titan hp.boss_state 1
bossbar set hardcore:titan max 600
bossbar set hardcore:titan value 600
bossbar set hardcore:titan players @a
bossbar set hardcore:titan visible true

summon minecraft:marker ~ ~ ~ {Tags:["hp.boss_anchor","hp.boss_titan_anchor"]}
summon minecraft:ravager ~ ~ ~ {Tags:["hp.boss","hp.boss_titan","hp.mob_boosted"],PersistenceRequired:1b}
execute as @e[tag=hp.boss_titan,sort=nearest,limit=1] at @s run function hardcore:bosses/titan/setup

title @a title {text:"EL TITAN DE HIERRO",color:"gray",bold:true}
title @a subtitle {text:"La tierra tiembla bajo sus pasos",color:"gold"}
playsound minecraft:entity.ravager.roar hostile @a ~ ~ ~ 1 0.7
tellraw @a {text:"",extra:[{text:"👑 Boss invocado: ",color:"gold",bold:true},{text:"EL TITAN DE HIERRO",color:"gray",bold:true}]}
