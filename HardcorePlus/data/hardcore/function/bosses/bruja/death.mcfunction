scoreboard players set #bruja hp.boss_state 0
bossbar set hardcore:bruja value 0
bossbar set hardcore:bruja visible false
playsound minecraft:entity.witch.death hostile @a ~ ~ ~ 1 0.8
particle minecraft:witch ~ ~1 ~ 1.5 1 1.5 0.1 80 force @a
tellraw @a {text:"",extra:[{text:"👑 LA BRUJA CARMESI",color:"dark_red",bold:true},{text:" fue derrotada.",color:"gold"}]}
execute as @a[distance=..64,gamemode=!spectator] run function hardcore:stats/add_boss_silent
give @a[distance=..64,gamemode=!spectator] minecraft:golden_apple 2
give @a[distance=..64,gamemode=!spectator] minecraft:redstone 16
kill @e[tag=hp.boss_bruja_anchor,distance=..4]
