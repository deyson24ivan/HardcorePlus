execute if score #ceniza hp.boss_state matches 1 run tellraw @s {text:"El Rey de Ceniza ya esta activo.",color:"gray"}
execute unless score #ceniza hp.boss_state matches 1 run function hardcore:bosses/ceniza/spawn
