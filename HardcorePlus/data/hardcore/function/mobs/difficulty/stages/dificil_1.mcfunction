# Dificil I: +25% dano, +10% velocidad, 10% efecto, 50% armadura.

execute if entity @s[type=#hardcore:melee_damage_hostiles] run attribute @s minecraft:attack_damage modifier add hardcore:damage_boost 0.25 add_multiplied_total
execute if entity @s[type=#hardcore:speed_boost_hostiles] run attribute @s minecraft:movement_speed modifier add hardcore:speed_boost 0.10 add_multiplied_total
function hardcore:mobs/difficulty/special/dificil_1

execute store result score @s hp.rng run random value 1..100
execute if score @s hp.rng matches ..10 run function hardcore:mobs/difficulty/effects/apply

execute store result score @s hp.rng run random value 1..100
execute if score @s hp.rng matches ..50 if entity @s[type=#hardcore:armored_hostiles] run function hardcore:mobs/difficulty/armor/tier_dificil_1
function hardcore:mobs/difficulty/weapons/tier_dificil_1
