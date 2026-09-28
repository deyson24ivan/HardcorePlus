# Detecta el uso del Totem de Resurreccion.

scoreboard players set @s hp.revive_click 0
execute unless entity @a[tag=hp.eliminated,distance=..6,limit=1] run function hardcore:revive/no_soul
execute if entity @a[tag=hp.eliminated,distance=..6,limit=1] run function hardcore:revive/cast
