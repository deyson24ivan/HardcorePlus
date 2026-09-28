# Ajustes por tipo para el modo predeterminado.

execute if entity @s[type=minecraft:creeper] run data merge entity @s {ExplosionRadius:3b,Fuse:30s}
execute if entity @s[type=minecraft:ghast] run data merge entity @s {ExplosionPower:1}
