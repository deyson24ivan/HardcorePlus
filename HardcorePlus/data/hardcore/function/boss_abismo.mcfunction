execute if score #abismo hp.boss_state matches 1 run tellraw @s {text:"El Devorador del Abismo ya esta activo.",color:"gray"}
execute unless score #abismo hp.boss_state matches 1 run function hardcore:bosses/abismo/spawn
