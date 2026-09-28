scoreboard players set @s hp.boss_phase 1
scoreboard players set @s hp.boss_cd 0
attribute @s minecraft:max_health base set 500
attribute @s minecraft:attack_damage base set 14
attribute @s minecraft:movement_speed base set 0.34
attribute @s minecraft:knockback_resistance base set 0.8
attribute @s minecraft:follow_range base set 64
attribute @s minecraft:scale base set 1.4
data merge entity @s {Health:500f}
effect give @s minecraft:resistance 999999 1 true
effect give @s minecraft:speed 999999 0 true
