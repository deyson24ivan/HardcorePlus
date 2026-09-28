scoreboard players set @s hp.boss_phase 1
scoreboard players set @s hp.boss_cd 0
attribute @s minecraft:max_health base set 700
attribute @s minecraft:attack_damage base set 22
attribute @s minecraft:movement_speed base set 0.30
attribute @s minecraft:knockback_resistance base set 1
attribute @s minecraft:follow_range base set 56
attribute @s minecraft:scale base set 1.15
data merge entity @s {Health:700f}
effect give @s minecraft:resistance 999999 1 true
