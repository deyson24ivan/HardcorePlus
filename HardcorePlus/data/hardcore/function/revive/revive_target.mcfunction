# Esta funcion se ejecuta como el jugador eliminado que va a revivir.

scoreboard players set @s hp.lives 1
scoreboard players operation @s hp.lastdeath = @s hp.deaths
scoreboard players set @s hp.anim_life 0
scoreboard players set @s hp.anim_elim 0
tag @s remove hp.eliminated

gamemode survival @s
tp @s @a[tag=hp.revive_caster,limit=1,sort=nearest]
effect give @s minecraft:regeneration 8 1 true
effect give @s minecraft:resistance 8 1 true
effect give @s minecraft:fire_resistance 8 0 true

tellraw @a {text:"",extra:[{text:"✦ ",color:"gold"},{selector:"@s",color:"yellow",bold:true},{text:" ha regresado del mas alla con ",color:"gray"},{text:"❤",color:"red"},{text:" 1 vida.",color:"gray"}]}
title @s title {text:"REVIVIDO",color:"gold",bold:true}
title @s subtitle {text:"Has vuelto con ❤ 1 vida",color:"red"}
playsound minecraft:block.beacon.activate player @s ~ ~ ~ 1 1.2
particle minecraft:heart ~ ~1 ~ 0.5 0.8 0.5 0.05 18 force @a
