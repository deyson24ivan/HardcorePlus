# Dia 1-10: modo base, pero los mobs ya pegan 10% mas.

execute if entity @s[type=#hardcore:melee_damage_hostiles] run attribute @s minecraft:attack_damage modifier add hardcore:damage_boost 0.10 add_multiplied_total
function hardcore:mobs/difficulty/special/default
