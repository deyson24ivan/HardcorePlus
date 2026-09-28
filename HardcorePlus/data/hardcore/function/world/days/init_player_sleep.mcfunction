# Evita que suenos antiguos del jugador cuenten al actualizar el datapack.

scoreboard players set @s hp.lastsleep 0
execute if score @s hp.sleep matches 0.. run scoreboard players operation @s hp.lastsleep = @s hp.sleep
tag @s add hp.sleep_ready
