scoreboard players set @s hp.boss_phase 2
effect give @s minecraft:strength 999999 0 true
playsound minecraft:entity.warden.heartbeat hostile @a[distance=..56] ~ ~ ~ 1 0.8
particle minecraft:sculk_soul ~ ~1 ~ 1.2 1.2 1.2 0.08 70 force @a[distance=..56]
tellraw @a {text:"",extra:[{text:"EL DEVORADOR DEL ABISMO",color:"dark_aqua",bold:true},{text:" entra en Fase II: hambre profunda.",color:"gray"}]}
