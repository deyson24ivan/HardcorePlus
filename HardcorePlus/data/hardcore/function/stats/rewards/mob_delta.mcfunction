# +1 punto por mob derrotado.

scoreboard players operation @s hp.delta = @s hp.mob_raw
scoreboard players operation @s hp.delta -= @s hp.last_mob_raw
scoreboard players operation @s hp.mobs += @s hp.delta
scoreboard players operation @s hp.points += @s hp.delta
