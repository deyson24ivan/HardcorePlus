# Ejecutar como el jugador que derroto un boss custom.

scoreboard players add @s hp.bosses 1
scoreboard players operation @s hp.points += #boss_reward hp.const
tellraw @a {text:"",extra:[{text:"👑 Boss derrotado | ",color:"gold",bold:true},{selector:"@s",color:"yellow"},{text:" +100 pts",color:"gray"}]}
