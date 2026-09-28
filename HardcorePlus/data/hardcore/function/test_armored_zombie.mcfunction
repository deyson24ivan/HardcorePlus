# Prueba visual forzada: crea un zombie y le aplica armadura segun la etapa actual.

summon minecraft:zombie ~2 ~ ~ {Tags:["hp.force_test"]}
execute as @e[type=minecraft:zombie,tag=hp.force_test,sort=nearest,limit=1] at @s run function hardcore:mobs/difficulty/apply
execute as @e[type=minecraft:zombie,tag=hp.force_test,sort=nearest,limit=1] at @s if score #stage hp.stage matches 1 run function hardcore:mobs/difficulty/armor/tier_dificil_1
execute as @e[type=minecraft:zombie,tag=hp.force_test,sort=nearest,limit=1] at @s if score #stage hp.stage matches 2 run function hardcore:mobs/difficulty/armor/tier_dificil_2
execute as @e[type=minecraft:zombie,tag=hp.force_test,sort=nearest,limit=1] at @s if score #stage hp.stage matches 3 run function hardcore:mobs/difficulty/armor/tier_pesadilla
execute as @e[type=minecraft:zombie,tag=hp.force_test,sort=nearest,limit=1] at @s if score #stage hp.stage matches 4 run function hardcore:mobs/difficulty/armor/tier_apocalipsis
execute as @e[type=minecraft:zombie,tag=hp.force_test,sort=nearest,limit=1] at @s if score #stage hp.stage matches 5 run function hardcore:mobs/difficulty/armor/tier_masacre_total
tag @e[type=minecraft:zombie,tag=hp.force_test,sort=nearest,limit=1] remove hp.force_test
tellraw @s {text:"Zombie de prueba creado con armadura forzada segun el modo actual.",color:"gold"}
