# Revive al jugador eliminado mas cercano. En v0.2 el objeto activable es carrot_on_a_stick.

clear @s minecraft:carrot_on_a_stick 1
tag @s add hp.revive_caster
scoreboard players add @s hp.revives 1
function hardcore:stats/rewards/revive
execute unless entity @s[tag=hp.ach_sacrifice] run function hardcore:achievements/grant/sacrificio

title @a times 5 35 15
title @a title {text:"✦ RESURRECCION ✦",color:"gold",bold:true}
title @a subtitle {text:"El mas alla abre sus puertas",color:"yellow"}
playsound minecraft:item.totem.use master @a ~ ~ ~ 1 1
particle minecraft:totem_of_undying ~ ~1 ~ 0.8 1 0.8 0.15 80 force @a

execute as @p[tag=hp.eliminated,distance=..6,sort=nearest] run function hardcore:revive/revive_target
tag @s remove hp.revive_caster
