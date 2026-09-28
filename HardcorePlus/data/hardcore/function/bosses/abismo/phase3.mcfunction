scoreboard players set @s hp.boss_phase 3
attribute @s minecraft:movement_speed base set 0.36
effect give @s minecraft:strength 999999 1 true
effect give @s minecraft:resistance 999999 2 true
playsound minecraft:entity.warden.sonic_boom hostile @a[distance=..56] ~ ~ ~ 1 0.7
particle minecraft:sonic_boom ~ ~1.5 ~ 0 0 0 0 1 force @a[distance=..56]
tellraw @a {text:"",extra:[{text:"EL DEVORADOR DEL ABISMO",color:"dark_aqua",bold:true},{text:" entra en Fase III: grito del vacio.",color:"dark_aqua"}]}
