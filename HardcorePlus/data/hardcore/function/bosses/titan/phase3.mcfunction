scoreboard players set @s hp.boss_phase 3
attribute @s minecraft:movement_speed base set 0.42
effect give @s minecraft:resistance 999999 2 true
playsound minecraft:entity.ravager.stunned hostile @a[distance=..48] ~ ~ ~ 1 0.5
particle minecraft:explosion ~ ~1 ~ 1 1 1 0.05 12 force @a[distance=..48]
tellraw @a {text:"",extra:[{text:"EL TITAN DE HIERRO",color:"gray",bold:true},{text:" entra en Fase III: furia de metal.",color:"red"}]}
