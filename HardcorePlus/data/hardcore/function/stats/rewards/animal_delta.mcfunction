# +1 punto por animal de granja.

scoreboard players operation @s hp.delta = @s hp.animal_raw
scoreboard players operation @s hp.delta -= @s hp.last_animal
scoreboard players operation @s hp.animals += @s hp.delta
scoreboard players operation @s hp.mobs += @s hp.delta
scoreboard players operation @s hp.tmp = @s hp.delta
scoreboard players operation @s hp.tmp *= #animal_reward hp.const
scoreboard players operation @s hp.points += @s hp.tmp
