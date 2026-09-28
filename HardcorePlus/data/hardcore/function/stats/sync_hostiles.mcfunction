# Entrega +2 puntos por mob hostil derrotado.

function hardcore:stats/calc_hostiles
execute if score @s hp.hostile_raw > @s hp.last_hostile run function hardcore:stats/rewards/hostile_delta
scoreboard players operation @s hp.last_hostile = @s hp.hostile_raw
