# +2 puntos por ancient debris minado.

scoreboard players operation @s hp.delta = @s hp.neth_raw
scoreboard players operation @s hp.delta -= @s hp.last_neth_raw
scoreboard players operation @s hp.netherite += @s hp.delta
scoreboard players operation @s hp.tmp = @s hp.delta
scoreboard players operation @s hp.tmp *= #netherite_reward hp.const
scoreboard players operation @s hp.points += @s hp.tmp
