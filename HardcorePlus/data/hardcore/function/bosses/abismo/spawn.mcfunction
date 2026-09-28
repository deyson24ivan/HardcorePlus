# Invoca al Devorador del Abismo.

scoreboard players set #abismo hp.boss_state 1
bossbar set hardcore:abismo max 700
bossbar set hardcore:abismo value 700
bossbar set hardcore:abismo players @a
bossbar set hardcore:abismo visible true

summon minecraft:marker ~ ~ ~ {Tags:["hp.boss_anchor","hp.boss_abismo_anchor"]}
summon minecraft:enderman ~ ~ ~ {Tags:["hp.boss","hp.boss_abismo","hp.mob_boosted"],PersistenceRequired:1b}
execute as @e[tag=hp.boss_abismo,sort=nearest,limit=1] at @s run function hardcore:bosses/abismo/setup

title @a title {text:"EL DEVORADOR DEL ABISMO",color:"dark_aqua",bold:true}
title @a subtitle {text:"La oscuridad empieza a respirar",color:"gray"}
playsound minecraft:entity.warden.roar hostile @a ~ ~ ~ 1 0.7
tellraw @a {text:"",extra:[{text:"👑 Boss invocado: ",color:"gold",bold:true},{text:"EL DEVORADOR DEL ABISMO",color:"dark_aqua",bold:true}]}
