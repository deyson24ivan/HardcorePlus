# Reparto justo entre hierro, oro y diamante.

execute store result score @s hp.rng run random value 1..3
execute if score @s hp.rng matches 1 run function hardcore:mobs/difficulty/armor/sets/iron
execute if score @s hp.rng matches 2 run function hardcore:mobs/difficulty/armor/sets/gold
execute if score @s hp.rng matches 3 run function hardcore:mobs/difficulty/armor/sets/diamond
