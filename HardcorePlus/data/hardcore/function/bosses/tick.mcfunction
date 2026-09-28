# Actualiza bosses activos.

execute if score #titan hp.boss_state matches 1 if entity @e[tag=hp.boss_titan,limit=1] run function hardcore:bosses/titan/tick
execute if score #titan hp.boss_state matches 1 unless entity @e[tag=hp.boss_titan,limit=1] at @e[tag=hp.boss_titan_anchor,limit=1] run function hardcore:bosses/titan/death

execute if score #bruja hp.boss_state matches 1 if entity @e[tag=hp.boss_bruja,limit=1] run function hardcore:bosses/bruja/tick
execute if score #bruja hp.boss_state matches 1 unless entity @e[tag=hp.boss_bruja,limit=1] at @e[tag=hp.boss_bruja_anchor,limit=1] run function hardcore:bosses/bruja/death

execute if score #ceniza hp.boss_state matches 1 if entity @e[tag=hp.boss_ceniza,limit=1] run function hardcore:bosses/ceniza/tick
execute if score #ceniza hp.boss_state matches 1 unless entity @e[tag=hp.boss_ceniza,limit=1] at @e[tag=hp.boss_ceniza_anchor,limit=1] run function hardcore:bosses/ceniza/death

execute if score #abismo hp.boss_state matches 1 if entity @e[tag=hp.boss_abismo,limit=1] run function hardcore:bosses/abismo/tick
execute if score #abismo hp.boss_state matches 1 unless entity @e[tag=hp.boss_abismo,limit=1] at @e[tag=hp.boss_abismo_anchor,limit=1] run function hardcore:bosses/abismo/death

execute if score #dragona hp.boss_state matches 1 if entity @e[tag=hp.boss_dragona,limit=1] run function hardcore:bosses/dragona/tick
execute if score #dragona hp.boss_state matches 1 unless entity @e[tag=hp.boss_dragona,limit=1] at @e[tag=hp.boss_dragona_anchor,limit=1] run function hardcore:bosses/dragona/death
