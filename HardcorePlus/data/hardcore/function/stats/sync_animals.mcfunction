# Entrega +1 punto por animal de granja derrotado.

function hardcore:stats/calc_animals
execute if score @s hp.animal_raw > @s hp.last_animal run function hardcore:stats/rewards/animal_delta
scoreboard players operation @s hp.last_animal = @s hp.animal_raw
