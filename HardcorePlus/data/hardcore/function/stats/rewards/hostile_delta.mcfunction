# +2 puntos por mob hostil.

scoreboard players operation @s hp.delta = @s hp.hostile_raw
scoreboard players operation @s hp.delta -= @s hp.last_hostile
scoreboard players operation @s hp.hostiles += @s hp.delta
scoreboard players operation @s hp.mobs += @s hp.delta
scoreboard players operation @s hp.tmp = @s hp.delta
scoreboard players operation @s hp.tmp *= #hostile_reward hp.const
scoreboard players operation @s hp.points += @s hp.tmp
