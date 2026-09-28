# Guarda el dano acumulado sin dar puntos por cada golpe.

scoreboard players operation @s hp.delta = @s hp.damage_raw
scoreboard players operation @s hp.delta -= @s hp.last_dmg_raw
scoreboard players operation @s hp.damage += @s hp.delta
