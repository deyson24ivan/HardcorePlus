# Un dia sobrevivido equivale a 24000 ticks vivo.

scoreboard players remove @s hp.surv_ticks 24000
scoreboard players add @s hp.surv_days 1
scoreboard players operation @s hp.points += #day_reward hp.const
execute if score @s hp.surv_days matches 100.. unless entity @s[tag=hp.ach_survivor] run function hardcore:achievements/grant/sobreviviente
