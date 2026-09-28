# Suma kills de animales de granja.

scoreboard players set @s hp.animal_raw 0
scoreboard players add @s hp.kcow 0
scoreboard players add @s hp.kpig 0
scoreboard players add @s hp.ksheep 0
scoreboard players add @s hp.kchicken 0
scoreboard players add @s hp.krabbit 0
scoreboard players add @s hp.kgoat 0
scoreboard players add @s hp.kmooshroom 0
scoreboard players operation @s hp.animal_raw += @s hp.kcow
scoreboard players operation @s hp.animal_raw += @s hp.kpig
scoreboard players operation @s hp.animal_raw += @s hp.ksheep
scoreboard players operation @s hp.animal_raw += @s hp.kchicken
scoreboard players operation @s hp.animal_raw += @s hp.krabbit
scoreboard players operation @s hp.animal_raw += @s hp.kgoat
scoreboard players operation @s hp.animal_raw += @s hp.kmooshroom
