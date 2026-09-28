# Apocalipsis: +100% dano, +50% velocidad, 75% efecto, 80% armadura encantada.

execute if entity @s[type=#hardcore:melee_damage_hostiles] run attribute @s minecraft:attack_damage modifier add hardcore:damage_boost 1.00 add_multiplied_total
execute if entity @s[type=#hardcore:speed_boost_hostiles] run attribute @s minecraft:movement_speed modifier add hardcore:speed_boost 0.50 add_multiplied_total
function hardcore:mobs/difficulty/special/apocalipsis

execute store result score @s hp.rng run random value 1..100
execute if score @s hp.rng matches ..75 run function hardcore:mobs/difficulty/effects/apply

execute store result score @s hp.rng run random value 1..100
execute if score @s hp.rng matches ..80 if entity @s[type=#hardcore:armored_hostiles] run function hardcore:mobs/difficulty/armor/tier_apocalipsis
function hardcore:mobs/difficulty/weapons/tier_apocalipsis
