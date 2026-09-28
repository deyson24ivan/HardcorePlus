# Armadura asegurada y brutal.

execute store result score @s hp.rng run random value 1..2
execute if score @s hp.rng matches 1 run function hardcore:mobs/difficulty/armor/sets/enchanted_netherite_protection
execute if score @s hp.rng matches 2 run function hardcore:mobs/difficulty/armor/sets/enchanted_netherite_thorns
