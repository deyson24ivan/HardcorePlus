bossbar set hardcore:bruja players @a
bossbar set hardcore:bruja visible true
execute at @e[tag=hp.boss_bruja,limit=1] run tp @e[tag=hp.boss_bruja_anchor,limit=1] ~ ~ ~
execute store result bossbar hardcore:bruja value run data get entity @e[tag=hp.boss_bruja,limit=1] Health 1
execute as @e[tag=hp.boss_bruja,limit=1] store result score @s hp.boss_hp run data get entity @s Health 1
execute as @e[tag=hp.boss_bruja,limit=1] run scoreboard players add @s hp.boss_cd 1
execute as @e[tag=hp.boss_bruja,limit=1,scores={hp.boss_phase=1,hp.boss_hp=..300}] at @s run function hardcore:bosses/bruja/phase2
execute as @e[tag=hp.boss_bruja,limit=1,scores={hp.boss_phase=2,hp.boss_hp=..150}] at @s run function hardcore:bosses/bruja/phase3
execute as @e[tag=hp.boss_bruja,limit=1,scores={hp.boss_cd=70}] at @s run function hardcore:bosses/bruja/attack_curse
execute as @e[tag=hp.boss_bruja,limit=1,scores={hp.boss_cd=140}] at @s run function hardcore:bosses/bruja/attack_spiders
execute as @e[tag=hp.boss_bruja,limit=1,scores={hp.boss_cd=200..}] run scoreboard players set @s hp.boss_cd 0
execute at @e[tag=hp.boss_bruja,limit=1] run particle minecraft:witch ~ ~1.1 ~ 0.7 0.8 0.7 0.02 10 force @a[distance=..48]
