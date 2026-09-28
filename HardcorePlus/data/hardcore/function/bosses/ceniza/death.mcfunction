scoreboard players set #ceniza hp.boss_state 0
bossbar set hardcore:ceniza value 0
bossbar set hardcore:ceniza visible false
playsound minecraft:entity.wither_skeleton.death hostile @a ~ ~ ~ 1 0.8
particle minecraft:flame ~ ~1 ~ 1.5 1 1.5 0.1 80 force @a
tellraw @a {text:"",extra:[{text:"👑 EL REY DE CENIZA",color:"gold",bold:true},{text:" fue derrotado.",color:"gold"}]}
execute as @a[distance=..64,gamemode=!spectator] run function hardcore:stats/add_boss_silent
give @a[distance=..64,gamemode=!spectator] minecraft:blaze_rod 16
give @a[distance=..64,gamemode=!spectator] minecraft:ancient_debris 1
kill @e[tag=hp.boss_ceniza_anchor,distance=..4]
