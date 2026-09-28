# Cuenta bosses especiales con recompensas propias.

scoreboard players add @s hp.kelder 0
scoreboard players add @s hp.kill_dragon 0
scoreboard players add @s hp.kill_wither 0
scoreboard players add @s hp.kill_warden 0
execute if score @s hp.kelder > @s hp.last_elder run function hardcore:stats/rewards/elder_delta
execute if score @s hp.kill_dragon > @s hp.last_dragon run function hardcore:stats/rewards/dragon_delta
execute if score @s hp.kill_wither > @s hp.last_wither run function hardcore:stats/rewards/wither_delta
execute if score @s hp.kill_warden > @s hp.last_warden run function hardcore:stats/rewards/warden_delta
scoreboard players operation @s hp.last_elder = @s hp.kelder
scoreboard players operation @s hp.last_dragon = @s hp.kill_dragon
scoreboard players operation @s hp.last_wither = @s hp.kill_wither
scoreboard players operation @s hp.last_warden = @s hp.kill_warden
