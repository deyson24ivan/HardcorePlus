# Prueba visual forzada del modo actual.

tellraw @s {text:"Prueba visual del modo actual: zombie, esqueleto, aranas, creeper, pillager, ghast y sulfur cube.",color:"gold"}
summon minecraft:zombie ~2 ~ ~ {Tags:["hp.test_forced"]}
summon minecraft:skeleton ~4 ~ ~ {Tags:["hp.test_forced"]}
summon minecraft:spider ~6 ~ ~ {Tags:["hp.test_forced"]}
summon minecraft:cave_spider ~8 ~ ~ {Tags:["hp.test_forced"]}
summon minecraft:creeper ~10 ~ ~ {Tags:["hp.test_forced"]}
summon minecraft:pillager ~12 ~ ~ {Tags:["hp.test_forced"]}
summon minecraft:ghast ~14 ~4 ~ {Tags:["hp.test_forced"]}
summon minecraft:sulfur_cube ~16 ~ ~ {Tags:["hp.test_forced"]}

execute as @e[tag=hp.test_forced,type=#hardcore:boosted_hostiles] at @s run function hardcore:mobs/difficulty/apply

execute as @e[tag=hp.test_forced,type=#hardcore:armored_hostiles] at @s if score #stage hp.stage matches 1 run function hardcore:mobs/difficulty/armor/tier_dificil_1
execute as @e[tag=hp.test_forced,type=#hardcore:armored_hostiles] at @s if score #stage hp.stage matches 2 run function hardcore:mobs/difficulty/armor/tier_dificil_2
execute as @e[tag=hp.test_forced,type=#hardcore:armored_hostiles] at @s if score #stage hp.stage matches 3 run function hardcore:mobs/difficulty/armor/tier_pesadilla
execute as @e[tag=hp.test_forced,type=#hardcore:armored_hostiles] at @s if score #stage hp.stage matches 4 run function hardcore:mobs/difficulty/armor/tier_apocalipsis
execute as @e[tag=hp.test_forced,type=#hardcore:armored_hostiles] at @s if score #stage hp.stage matches 5 run function hardcore:mobs/difficulty/armor/tier_masacre_total

execute as @e[tag=hp.test_forced,type=#hardcore:boosted_hostiles] at @s unless score #stage hp.stage matches 0 run function hardcore:mobs/difficulty/effects/apply

tag @e[tag=hp.test_forced] remove hp.test_forced
tellraw @s {text:"Listo. En una partida normal la armadura y efectos siguen usando probabilidad; esta prueba los fuerza para revisar el modo.",color:"yellow"}
