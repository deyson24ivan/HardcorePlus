scoreboard players set #dragona hp.boss_state 0
bossbar set hardcore:dragona value 0
bossbar set hardcore:dragona visible false
playsound minecraft:entity.ender_dragon.death hostile @a ~ ~ ~ 1 0.8
particle minecraft:dragon_breath ~ ~1 ~ 1.5 1 1.5 0.1 90 force @a
tellraw @a {text:"",extra:[{text:"👑 LA DRAGONA CORRUPTA",color:"light_purple",bold:true},{text:" fue derrotada.",color:"gold"}]}
execute as @a[distance=..64,gamemode=!spectator] run function hardcore:stats/add_boss_silent
give @a[distance=..64,gamemode=!spectator] minecraft:dragon_breath 4
give @a[distance=..64,gamemode=!spectator] minecraft:ender_pearl 16
kill @e[tag=hp.boss_dragona_anchor,distance=..4]
