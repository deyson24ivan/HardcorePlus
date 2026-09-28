scoreboard players set #titan hp.boss_state 0
bossbar set hardcore:titan value 0
bossbar set hardcore:titan visible false
playsound minecraft:entity.ravager.death hostile @a ~ ~ ~ 1 0.8
particle minecraft:explosion ~ ~1 ~ 1.5 1 1.5 0.08 30 force @a
tellraw @a {text:"",extra:[{text:"👑 EL TITAN DE HIERRO",color:"gray",bold:true},{text:" fue derrotado.",color:"gold"}]}
execute as @a[distance=..64,gamemode=!spectator] run function hardcore:stats/add_boss_silent
give @a[distance=..64,gamemode=!spectator] minecraft:iron_block 4
give @a[distance=..64,gamemode=!spectator] minecraft:diamond 2
kill @e[tag=hp.boss_titan_anchor,distance=..4]
