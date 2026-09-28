# Revisa logros automaticos. Cada logro se entrega una sola vez.

execute if score @s hp.health matches 1 if score @s hp.lives matches 1.. unless entity @s[tag=hp.eliminated] unless entity @s[tag=hp.ach_not_this_time] run function hardcore:achievements/grant/no_esta_vez
execute if score @s hp.elites matches 1.. unless entity @s[tag=hp.ach_first_blood] run function hardcore:achievements/grant/primera_sangre
execute if score @s hp.revives matches 1.. unless entity @s[tag=hp.ach_sacrifice] run function hardcore:achievements/grant/sacrificio
execute if score @s hp.surv_days matches 100.. unless entity @s[tag=hp.ach_survivor] run function hardcore:achievements/grant/sobreviviente
