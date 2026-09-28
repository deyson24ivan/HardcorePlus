# Da un efecto positivo aleatorio visible al mob.

execute store result score @s hp.rng run random value 1..6
execute if score @s hp.rng matches 1 run effect give @s minecraft:strength 999999 0 false
execute if score @s hp.rng matches 2 run effect give @s minecraft:resistance 999999 0 false
execute if score @s hp.rng matches 3 run effect give @s minecraft:regeneration 999999 0 false
execute if score @s hp.rng matches 4 run effect give @s minecraft:fire_resistance 999999 0 false
execute if score @s hp.rng matches 5 run effect give @s minecraft:absorption 999999 0 false
execute if score @s hp.rng matches 6 run effect give @s minecraft:speed 999999 0 false
particle minecraft:enchanted_hit ~ ~1 ~ 0.45 0.65 0.45 0.04 24 force @a[distance=..48]
