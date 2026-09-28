scoreboard players set @s hp.boss_phase 3
attribute @s minecraft:movement_speed base set 0.40
effect give @s minecraft:strength 999999 2 true
playsound minecraft:entity.blaze.shoot hostile @a[distance=..48] ~ ~ ~ 1 0.6
particle minecraft:lava ~ ~1 ~ 1.2 1.2 1.2 0.05 35 force @a[distance=..48]
tellraw @a {text:"",extra:[{text:"EL REY DE CENIZA",color:"gold",bold:true},{text:" entra en Fase III: fuego final.",color:"dark_red"}]}
