scoreboard players set @s hp.boss_phase 3
effect give @s minecraft:strength 999999 1 true
effect give @s minecraft:resistance 999999 1 true
playsound minecraft:entity.witch.throw hostile @a[distance=..48] ~ ~ ~ 1 0.6
particle minecraft:damage_indicator ~ ~1 ~ 1 1 1 0.05 30 force @a[distance=..48]
tellraw @a {text:"",extra:[{text:"LA BRUJA CARMESI",color:"dark_red",bold:true},{text:" entra en Fase III: sangre negra.",color:"dark_red"}]}
