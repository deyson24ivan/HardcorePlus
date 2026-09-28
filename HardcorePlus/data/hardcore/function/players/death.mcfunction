# Se ejecuta una vez por cada muerte detectada.

scoreboard players operation @s hp.lastdeath = @s hp.deaths
execute if score @s hp.lives matches 1.. run scoreboard players remove @s hp.lives 1

execute if score @s hp.lives matches 2 run function hardcore:players/messages/death_two_lives
execute if score @s hp.lives matches 1 run function hardcore:players/messages/death_one_life
execute if score @s hp.lives matches ..0 run function hardcore:players/eliminate
