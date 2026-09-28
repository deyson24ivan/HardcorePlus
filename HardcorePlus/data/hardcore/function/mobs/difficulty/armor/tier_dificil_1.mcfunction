# Reparto justo entre cota de malla, cobre e hierro.

execute store result score @s hp.rng run random value 1..3
execute if score @s hp.rng matches 1 run function hardcore:mobs/difficulty/armor/sets/chainmail
execute if score @s hp.rng matches 2 run function hardcore:mobs/difficulty/armor/sets/copper
execute if score @s hp.rng matches 3 run function hardcore:mobs/difficulty/armor/sets/iron
