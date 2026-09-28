# +1 punto por mineral de diamante minado.

scoreboard players operation @s hp.delta = @s hp.dia_raw
scoreboard players operation @s hp.delta -= @s hp.last_dia_raw
scoreboard players operation @s hp.diamonds += @s hp.delta
scoreboard players operation @s hp.tmp = @s hp.delta
scoreboard players operation @s hp.tmp *= #diamond_reward hp.const
scoreboard players operation @s hp.points += @s hp.tmp
