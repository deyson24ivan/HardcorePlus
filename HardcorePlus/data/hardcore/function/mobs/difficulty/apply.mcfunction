# Aplica dificultad a cada mob hostil una sola vez al aparecer.

tag @s add hp.mob_boosted
function hardcore:mobs/difficulty/reset
execute if score #stage hp.stage matches 0 run function hardcore:mobs/difficulty/stages/default
execute if score #stage hp.stage matches 1 run function hardcore:mobs/difficulty/stages/dificil_1
execute if score #stage hp.stage matches 2 run function hardcore:mobs/difficulty/stages/dificil_2
execute if score #stage hp.stage matches 3 run function hardcore:mobs/difficulty/stages/pesadilla
execute if score #stage hp.stage matches 4 run function hardcore:mobs/difficulty/stages/apocalipsis
execute if score #stage hp.stage matches 5 run function hardcore:mobs/difficulty/stages/masacre_total
