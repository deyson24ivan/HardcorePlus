bossbar set hardcore:dragona players @a
bossbar set hardcore:dragona visible true
execute at @e[tag=hp.boss_dragona,limit=1] run tp @e[tag=hp.boss_dragona_anchor,limit=1] ~ ~ ~
execute store result bossbar hardcore:dragona value run data get entity @e[tag=hp.boss_dragona,limit=1] Health 1
execute as @e[tag=hp.boss_dragona,limit=1] store result score @s hp.boss_hp run data get entity @s Health 1
execute as @e[tag=hp.boss_dragona,limit=1] run scoreboard players add @s hp.boss_cd 1
execute as @e[tag=hp.boss_dragona,limit=1,scores={hp.boss_phase=1,hp.boss_hp=..330}] at @s run function hardcore:bosses/dragona/phase2
execute as @e[tag=hp.boss_dragona,limit=1,scores={hp.boss_phase=2,hp.boss_hp=..160}] at @s run function hardcore:bosses/dragona/phase3
execute as @e[tag=hp.boss_dragona,limit=1,scores={hp.boss_cd=80}] at @s run function hardcore:bosses/dragona/attack_lift
execute as @e[tag=hp.boss_dragona,limit=1,scores={hp.boss_cd=150}] at @s run function hardcore:bosses/dragona/attack_swarm
execute as @e[tag=hp.boss_dragona,limit=1,scores={hp.boss_cd=220..}] run scoreboard players set @s hp.boss_cd 0
execute at @e[tag=hp.boss_dragona,limit=1] run particle minecraft:dragon_breath ~ ~0.5 ~ 0.8 0.8 0.8 0.02 12 force @a[distance=..56]
