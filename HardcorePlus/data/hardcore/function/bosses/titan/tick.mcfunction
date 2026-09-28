bossbar set hardcore:titan players @a
bossbar set hardcore:titan visible true
execute at @e[tag=hp.boss_titan,limit=1] run tp @e[tag=hp.boss_titan_anchor,limit=1] ~ ~ ~
execute store result bossbar hardcore:titan value run data get entity @e[tag=hp.boss_titan,limit=1] Health 1
execute as @e[tag=hp.boss_titan,limit=1] store result score @s hp.boss_hp run data get entity @s Health 1
execute as @e[tag=hp.boss_titan,limit=1] run scoreboard players add @s hp.boss_cd 1
execute as @e[tag=hp.boss_titan,limit=1,scores={hp.boss_phase=1,hp.boss_hp=..400}] at @s run function hardcore:bosses/titan/phase2
execute as @e[tag=hp.boss_titan,limit=1,scores={hp.boss_phase=2,hp.boss_hp=..200}] at @s run function hardcore:bosses/titan/phase3
execute as @e[tag=hp.boss_titan,limit=1,scores={hp.boss_cd=80}] at @s run function hardcore:bosses/titan/attack_quake
execute as @e[tag=hp.boss_titan,limit=1,scores={hp.boss_cd=160}] at @s run function hardcore:bosses/titan/attack_minions
execute as @e[tag=hp.boss_titan,limit=1,scores={hp.boss_cd=220..}] run scoreboard players set @s hp.boss_cd 0
execute at @e[tag=hp.boss_titan,limit=1] run particle minecraft:crit ~ ~1.2 ~ 0.8 0.8 0.8 0.02 8 force @a[distance=..48]
