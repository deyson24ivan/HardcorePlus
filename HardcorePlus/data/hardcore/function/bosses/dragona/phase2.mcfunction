scoreboard players set @s hp.boss_phase 2
effect give @s minecraft:strength 999999 0 true
playsound minecraft:entity.ender_dragon.flap hostile @a[distance=..56] ~ ~ ~ 1 0.8
particle minecraft:dragon_breath ~ ~0.5 ~ 1.2 1 1.2 0.05 70 force @a[distance=..56]
tellraw @a {text:"",extra:[{text:"LA DRAGONA CORRUPTA",color:"light_purple",bold:true},{text:" entra en Fase II: aliento corrupto.",color:"dark_purple"}]}
