# Ejecutar como el jugador que derroto un Elite.

scoreboard players add @s hp.elites 1
scoreboard players operation @s hp.points += #elite_reward hp.const
execute unless entity @s[tag=hp.ach_first_blood] run function hardcore:achievements/grant/primera_sangre
tellraw @s {text:"",extra:[{text:"Elite derrotado: ",color:"dark_purple"},{text:"+50 pts",color:"gold"}]}
