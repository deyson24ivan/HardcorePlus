bossbar set hardcore:abismo players @a
bossbar set hardcore:abismo visible true
execute at @e[tag=hp.boss_abismo,limit=1] run tp @e[tag=hp.boss_abismo_anchor,limit=1] ~ ~ ~
execute store result bossbar hardcore:abismo value run data get entity @e[tag=hp.boss_abismo,limit=1] Health 1
execute as @e[tag=hp.boss_abismo,limit=1] store result score @s hp.boss_hp run data get entity @s Health 1
execute as @e[tag=hp.boss_abismo,limit=1] run scoreboard players add @s hp.boss_cd 1
execute as @e[tag=hp.boss_abismo,limit=1,scores={hp.boss_phase=1,hp.boss_hp=..460}] at @s run function hardcore:bosses/abismo/phase2
execute as @e[tag=hp.boss_abismo,limit=1,scores={hp.boss_phase=2,hp.boss_hp=..230}] at @s run function hardcore:bosses/abismo/phase3
execute as @e[tag=hp.boss_abismo,limit=1,scores={hp.boss_cd=90}] at @s run function hardcore:bosses/abismo/attack_darkness
execute as @e[tag=hp.boss_abismo,limit=1,scores={hp.boss_cd=170}] at @s run function hardcore:bosses/abismo/attack_endermites
execute as @e[tag=hp.boss_abismo,limit=1,scores={hp.boss_cd=240..}] run scoreboard players set @s hp.boss_cd 0
execute at @e[tag=hp.boss_abismo,limit=1] run particle minecraft:sculk_soul ~ ~1.2 ~ 0.8 1 0.8 0.03 18 force @a[distance=..56]
