# Invoca al Rey de Ceniza.

scoreboard players set #ceniza hp.boss_state 1
bossbar set hardcore:ceniza max 550
bossbar set hardcore:ceniza value 550
bossbar set hardcore:ceniza players @a
bossbar set hardcore:ceniza visible true

summon minecraft:marker ~ ~ ~ {Tags:["hp.boss_anchor","hp.boss_ceniza_anchor"]}
summon minecraft:wither_skeleton ~ ~ ~ {Tags:["hp.boss","hp.boss_ceniza","hp.mob_boosted"],PersistenceRequired:1b}
execute as @e[tag=hp.boss_ceniza,sort=nearest,limit=1] at @s run function hardcore:bosses/ceniza/setup

title @a title {text:"EL REY DE CENIZA",color:"gold",bold:true}
title @a subtitle {text:"El Nether reclama la arena",color:"red"}
playsound minecraft:entity.wither_skeleton.ambient hostile @a ~ ~ ~ 1 0.6
tellraw @a {text:"",extra:[{text:"👑 Boss invocado: ",color:"gold",bold:true},{text:"EL REY DE CENIZA",color:"gold",bold:true}]}
