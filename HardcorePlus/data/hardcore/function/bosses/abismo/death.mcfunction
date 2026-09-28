scoreboard players set #abismo hp.boss_state 0
bossbar set hardcore:abismo value 0
bossbar set hardcore:abismo visible false
playsound minecraft:entity.warden.death hostile @a ~ ~ ~ 1 0.8
particle minecraft:sculk_soul ~ ~1 ~ 1.5 1 1.5 0.1 80 force @a
tellraw @a {text:"",extra:[{text:"👑 EL DEVORADOR DEL ABISMO",color:"dark_aqua",bold:true},{text:" fue derrotado.",color:"gold"}]}
execute as @a[distance=..64,gamemode=!spectator] run function hardcore:stats/add_boss_silent
give @a[distance=..64,gamemode=!spectator] minecraft:echo_shard 4
give @a[distance=..64,gamemode=!spectator] minecraft:sculk_catalyst 1
kill @e[tag=hp.boss_abismo_anchor,distance=..4]
