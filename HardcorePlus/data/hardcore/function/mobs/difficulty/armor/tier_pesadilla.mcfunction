# Diamante o netherita con encantamientos utiles.

execute store result score @s hp.rng run random value 1..4
execute if score @s hp.rng matches 1 run function hardcore:mobs/difficulty/armor/sets/enchanted_diamond_protection
execute if score @s hp.rng matches 2 run function hardcore:mobs/difficulty/armor/sets/enchanted_diamond_thorns
execute if score @s hp.rng matches 3 run function hardcore:mobs/difficulty/armor/sets/enchanted_netherite_protection
execute if score @s hp.rng matches 4 run function hardcore:mobs/difficulty/armor/sets/enchanted_netherite_thorns
