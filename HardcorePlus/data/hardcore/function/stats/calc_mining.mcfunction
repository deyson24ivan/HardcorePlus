# Suma minerales valiosos minados.

scoreboard players set @s hp.dia_raw 0
scoreboard players set @s hp.neth_raw 0
scoreboard players add @s hp.mdia 0
scoreboard players add @s hp.mdeepsdia 0
scoreboard players add @s hp.mdebris 0
scoreboard players operation @s hp.dia_raw += @s hp.mdia
scoreboard players operation @s hp.dia_raw += @s hp.mdeepsdia
scoreboard players operation @s hp.neth_raw += @s hp.mdebris
