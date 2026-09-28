# Armas para mobs a distancia en Masacre Total.

execute if entity @s[type=minecraft:skeleton] run item replace entity @s weapon.mainhand with minecraft:bow[minecraft:enchantments={"minecraft:power":5,"minecraft:punch":2,"minecraft:flame":1}]
execute if entity @s[type=minecraft:stray] run item replace entity @s weapon.mainhand with minecraft:bow[minecraft:enchantments={"minecraft:power":5,"minecraft:punch":2,"minecraft:flame":1}]
execute if entity @s[type=minecraft:bogged] run item replace entity @s weapon.mainhand with minecraft:bow[minecraft:enchantments={"minecraft:power":5,"minecraft:punch":2,"minecraft:flame":1}]
execute if entity @s[type=minecraft:pillager] run item replace entity @s weapon.mainhand with minecraft:crossbow[minecraft:enchantments={"minecraft:quick_charge":3,"minecraft:multishot":1}]
execute if entity @s[type=#hardcore:ranged_hostiles] run data modify entity @s HandDropChances set value [0.0f,0.0f]
