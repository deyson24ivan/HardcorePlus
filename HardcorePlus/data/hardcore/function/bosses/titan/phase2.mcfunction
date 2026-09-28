scoreboard players set @s hp.boss_phase 2
effect give @s minecraft:strength 999999 1 true
playsound minecraft:entity.iron_golem.repair hostile @a[distance=..48] ~ ~ ~ 1 0.7
particle minecraft:poof ~ ~1 ~ 1 0.8 1 0.05 50 force @a[distance=..48]
tellraw @a {text:"",extra:[{text:"EL TITAN DE HIERRO",color:"gray",bold:true},{text:" entra en Fase II: armadura viva.",color:"gold"}]}
