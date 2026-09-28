# Actualiza estadisticas personales y logros.

execute if score @s hp.lives matches 1.. unless entity @s[tag=hp.eliminated] run function hardcore:stats/survival_tick
function hardcore:stats/sync_mobs
function hardcore:stats/sync_damage
function hardcore:stats/sync_bosses
function hardcore:stats/sync_mining
function hardcore:achievements/check
