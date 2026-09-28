scoreboard players set @s hp.boss_phase 1
scoreboard players set @s hp.boss_cd 0
attribute @s minecraft:max_health base set 550
attribute @s minecraft:attack_damage base set 16
attribute @s minecraft:movement_speed base set 0.32
attribute @s minecraft:knockback_resistance base set 0.9
attribute @s minecraft:follow_range base set 48
attribute @s minecraft:scale base set 1.35
data merge entity @s {Health:550f}
effect give @s minecraft:fire_resistance 999999 0 true
effect give @s minecraft:resistance 999999 1 true
item replace entity @s weapon.mainhand with minecraft:netherite_sword[minecraft:enchantments={"minecraft:fire_aspect":2,"minecraft:sharpness":3}]
item replace entity @s armor.head with minecraft:netherite_helmet[minecraft:enchantments={"minecraft:protection":2}]
