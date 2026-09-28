# Dificil II: +50% dano, +20% velocidad, 25% efecto, 50% armadura.

execute if entity @s[type=#hardcore:melee_damage_hostiles] run attribute @s minecraft:attack_damage modifier add hardcore:damage_boost 0.50 add_multiplied_total
execute if entity @s[type=#hardcore:speed_boost_hostiles] run attribute @s minecraft:movement_speed modifier add hardcore:speed_boost 0.20 add_multiplied_total
function hardcore:mobs/difficulty/special/dificil_2

execute store result score @s hp.rng run random value 1..100
execute if score @s hp.rng matches ..25 run function hardcore:mobs/difficulty/effects/apply

execute store result score @s hp.rng run random value 1..100
execute if score @s hp.rng matches ..50 if entity @s[type=#hardcore:armored_hostiles] run function hardcore:mobs/difficulty/armor/tier_dificil_2
function hardcore:mobs/difficulty/weapons/tier_dificil_2
