# Se ejecuta una vez cuando el mundo entra en un dia nuevo.

scoreboard players operation #last hp.day = #world hp.day
title @a times 10 50 20
playsound minecraft:block.beacon.ambient player @a ~ ~ ~ 0.6 1.35

execute if score #stage hp.stage matches 0 run function hardcore:world/days/announce/default
execute if score #stage hp.stage matches 1 run function hardcore:world/days/announce/dificil_1
execute if score #stage hp.stage matches 2 run function hardcore:world/days/announce/dificil_2
execute if score #stage hp.stage matches 3 run function hardcore:world/days/announce/pesadilla
execute if score #stage hp.stage matches 4 run function hardcore:world/days/announce/apocalipsis
execute if score #stage hp.stage matches 5 run function hardcore:world/days/announce/masacre_total
