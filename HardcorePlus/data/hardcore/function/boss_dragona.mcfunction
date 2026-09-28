execute if score #dragona hp.boss_state matches 1 run tellraw @s {text:"La Dragona Corrupta ya esta activa.",color:"gray"}
execute unless score #dragona hp.boss_state matches 1 run function hardcore:bosses/dragona/spawn
