execute if score #titan hp.boss_state matches 1 run tellraw @s {text:"El Titan de Hierro ya esta activo.",color:"gray"}
execute unless score #titan hp.boss_state matches 1 run function hardcore:bosses/titan/spawn
