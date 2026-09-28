# Aplica el modo global a mobs existentes y muestra el modo activo.

difficulty hard
tag @e[type=#hardcore:boosted_hostiles] remove hp.mob_boosted
execute as @e[type=#hardcore:boosted_hostiles] at @s run function hardcore:mobs/difficulty/apply
function hardcore:modes/show
