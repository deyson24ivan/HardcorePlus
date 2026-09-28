# Entrega puntos por minerales valiosos.

function hardcore:stats/calc_mining
execute if score @s hp.dia_raw > @s hp.last_dia_raw run function hardcore:stats/rewards/diamond_delta
execute if score @s hp.neth_raw > @s hp.last_neth_raw run function hardcore:stats/rewards/netherite_delta
scoreboard players operation @s hp.last_dia_raw = @s hp.dia_raw
scoreboard players operation @s hp.last_neth_raw = @s hp.neth_raw
