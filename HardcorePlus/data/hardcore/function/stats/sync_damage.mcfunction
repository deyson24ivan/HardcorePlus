# Guarda el dano hecho desde que HardcorePlus empezo a medir stats.

scoreboard players add @s hp.damage_raw 0
execute if score @s hp.damage_raw > @s hp.last_dmg_raw run function hardcore:stats/rewards/damage_delta
scoreboard players operation @s hp.last_dmg_raw = @s hp.damage_raw
