execute if score #bruja hp.boss_state matches 1 run tellraw @s {text:"La Bruja Carmesi ya esta activa.",color:"gray"}
execute unless score #bruja hp.boss_state matches 1 run function hardcore:bosses/bruja/spawn
