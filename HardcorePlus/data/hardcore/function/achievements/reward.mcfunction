# Recompensa comun por desbloquear un logro.

scoreboard players add @s hp.achievements 1
scoreboard players operation @s hp.points += #achievement_reward hp.const
playsound minecraft:entity.player.levelup player @s ~ ~ ~ 0.8 1.25
