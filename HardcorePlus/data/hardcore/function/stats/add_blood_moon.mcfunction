# Ejecutar como cada jugador que sobrevivio una Blood Moon.

scoreboard players add @s hp.bloodmoons 1
scoreboard players operation @s hp.points += #bloodmoon_reward hp.const
tellraw @s {text:"",extra:[{text:"Blood Moon sobrevivida: ",color:"dark_red"},{text:"+100 pts",color:"gold"}]}
