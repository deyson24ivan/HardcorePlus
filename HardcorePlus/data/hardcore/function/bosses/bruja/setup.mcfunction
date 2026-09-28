scoreboard players set @s hp.boss_phase 1
scoreboard players set @s hp.boss_cd 0
attribute @s minecraft:max_health base set 450
attribute @s minecraft:movement_speed base set 0.36
attribute @s minecraft:knockback_resistance base set 0.7
attribute @s minecraft:follow_range base set 48
attribute @s minecraft:scale base set 1.2
data merge entity @s {Health:450f}
effect give @s minecraft:resistance 999999 0 true
effect give @s minecraft:regeneration 999999 0 true
