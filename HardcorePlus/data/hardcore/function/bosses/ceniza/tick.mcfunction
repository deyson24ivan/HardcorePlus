bossbar set hardcore:ceniza players @a
bossbar set hardcore:ceniza visible true
execute at @e[tag=hp.boss_ceniza,limit=1] run tp @e[tag=hp.boss_ceniza_anchor,limit=1] ~ ~ ~
execute store result bossbar hardcore:ceniza value run data get entity @e[tag=hp.boss_ceniza,limit=1] Health 1
execute as @e[tag=hp.boss_ceniza,limit=1] store result score @s hp.boss_hp run data get entity @s Health 1
execute as @e[tag=hp.boss_ceniza,limit=1] run scoreboard players add @s hp.boss_cd 1
execute as @e[tag=hp.boss_ceniza,limit=1,scores={hp.boss_phase=1,hp.boss_hp=..360}] at @s run function hardcore:bosses/ceniza/phase2
execute as @e[tag=hp.boss_ceniza,limit=1,scores={hp.boss_phase=2,hp.boss_hp=..180}] at @s run function hardcore:bosses/ceniza/phase3
execute as @e[tag=hp.boss_ceniza,limit=1,scores={hp.boss_cd=70}] at @s run function hardcore:bosses/ceniza/attack_flames
execute as @e[tag=hp.boss_ceniza,limit=1,scores={hp.boss_cd=150}] at @s run function hardcore:bosses/ceniza/attack_blazes
execute as @e[tag=hp.boss_ceniza,limit=1,scores={hp.boss_cd=220..}] run scoreboard players set @s hp.boss_cd 0
execute at @e[tag=hp.boss_ceniza,limit=1] run particle minecraft:flame ~ ~1.2 ~ 0.7 1 0.7 0.03 14 force @a[distance=..48]
