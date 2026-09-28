# Cerebro principal del datapack. Mantener este archivo pequeno y delegar en modulos.

execute as @a[tag=!hp.ready] run function hardcore:players/init
execute as @a[tag=hp.ready,tag=!hp.stats_ready] run function hardcore:stats/init
execute as @a[tag=hp.ready,tag=hp.stats_ready,tag=!hp.stats_v2] run function hardcore:stats/migrate_v2
execute as @a[tag=hp.ready,tag=hp.stats_ready] run function hardcore:stats/tick
function hardcore:ui/sidebar_tick
function hardcore:bosses/tick
execute as @a[tag=hp.ready] if score @s hp.deaths > @s hp.lastdeath run function hardcore:players/death
execute as @a[tag=hp.ready] run function hardcore:players/actionbar
execute as @a[tag=hp.ready,tag=!hp.eliminated,scores={hp.lives=..0}] run function hardcore:players/eliminate
execute as @a[tag=hp.ready,scores={hp.lives=..0},gamemode=!spectator] run gamemode spectator @s
execute as @a[tag=hp.ready,scores={hp.anim_life=1..}] at @s run function hardcore:animations/life_lost/tick
execute as @a[tag=hp.ready,scores={hp.anim_elim=1..}] at @s run function hardcore:animations/eliminated/tick
execute as @a[tag=hp.ready,scores={hp.revive_click=1..}] at @s run function hardcore:revive/use_totem
execute as @e[type=#hardcore:boosted_hostiles,tag=!hp.mob_boosted] at @s run function hardcore:mobs/difficulty/apply
