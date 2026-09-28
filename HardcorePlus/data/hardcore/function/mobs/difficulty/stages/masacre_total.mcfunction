# Masacre Total: +150% dano, +50% velocidad, 100% efecto, 100% armadura.

execute if entity @s[type=#hardcore:melee_damage_hostiles] run attribute @s minecraft:attack_damage modifier add hardcore:damage_boost 1.50 add_multiplied_total
execute if entity @s[type=#hardcore:speed_boost_hostiles] run attribute @s minecraft:movement_speed modifier add hardcore:speed_boost 0.50 add_multiplied_total
function hardcore:mobs/difficulty/special/masacre_total

function hardcore:mobs/difficulty/effects/apply
execute if entity @s[type=#hardcore:armored_hostiles] run function hardcore:mobs/difficulty/armor/tier_masacre_total
function hardcore:mobs/difficulty/weapons/tier_masacre_total
