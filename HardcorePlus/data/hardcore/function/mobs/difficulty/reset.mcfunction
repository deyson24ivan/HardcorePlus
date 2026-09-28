# Limpia boosts anteriores antes de aplicar el modo actual.

attribute @s minecraft:attack_damage modifier remove hardcore:damage_boost
attribute @s minecraft:movement_speed modifier remove hardcore:speed_boost
execute if entity @s[type=minecraft:creeper] run data merge entity @s {ExplosionRadius:3b,Fuse:30s}
execute if entity @s[type=minecraft:ghast] run data merge entity @s {ExplosionPower:1}
