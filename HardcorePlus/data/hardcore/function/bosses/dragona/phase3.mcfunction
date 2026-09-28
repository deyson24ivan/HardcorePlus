scoreboard players set @s hp.boss_phase 3
attribute @s minecraft:movement_speed base set 0.45
effect give @s minecraft:strength 999999 1 true
playsound minecraft:entity.ender_dragon.growl hostile @a[distance=..56] ~ ~ ~ 1 0.6
particle minecraft:reverse_portal ~ ~0.5 ~ 1.2 1.2 1.2 0.05 60 force @a[distance=..56]
tellraw @a {text:"",extra:[{text:"LA DRAGONA CORRUPTA",color:"light_purple",bold:true},{text:" entra en Fase III: tormenta del End.",color:"light_purple"}]}
