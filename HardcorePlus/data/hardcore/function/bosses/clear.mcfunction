# Cancela todos los bosses de prueba activos.

scoreboard players set #titan hp.boss_state 0
scoreboard players set #bruja hp.boss_state 0
scoreboard players set #ceniza hp.boss_state 0
scoreboard players set #abismo hp.boss_state 0
scoreboard players set #dragona hp.boss_state 0

bossbar set hardcore:titan value 0
bossbar set hardcore:titan visible false
bossbar set hardcore:bruja value 0
bossbar set hardcore:bruja visible false
bossbar set hardcore:ceniza value 0
bossbar set hardcore:ceniza visible false
bossbar set hardcore:abismo value 0
bossbar set hardcore:abismo visible false
bossbar set hardcore:dragona value 0
bossbar set hardcore:dragona visible false

kill @e[tag=hp.boss]
kill @e[tag=hp.boss_anchor]
kill @e[tag=hp.boss_minion]
tellraw @a {text:"Bosses de prueba limpiados.",color:"gray"}
