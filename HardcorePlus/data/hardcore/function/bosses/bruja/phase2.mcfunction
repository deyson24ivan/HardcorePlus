scoreboard players set @s hp.boss_phase 2
effect give @s minecraft:speed 999999 0 true
playsound minecraft:entity.witch.drink hostile @a[distance=..48] ~ ~ ~ 1 0.8
particle minecraft:witch ~ ~1 ~ 1 1 1 0.08 60 force @a[distance=..48]
tellraw @a {text:"",extra:[{text:"LA BRUJA CARMESI",color:"dark_red",bold:true},{text:" entra en Fase II: maldicion abierta.",color:"red"}]}
