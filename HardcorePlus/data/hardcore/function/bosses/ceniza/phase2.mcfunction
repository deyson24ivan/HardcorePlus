scoreboard players set @s hp.boss_phase 2
effect give @s minecraft:strength 999999 1 true
playsound minecraft:block.fire.ambient hostile @a[distance=..48] ~ ~ ~ 1 0.7
particle minecraft:flame ~ ~1 ~ 1.2 1.2 1.2 0.08 80 force @a[distance=..48]
tellraw @a {text:"",extra:[{text:"EL REY DE CENIZA",color:"gold",bold:true},{text:" entra en Fase II: corona ardiente.",color:"red"}]}
