# Ejecutar como cada jugador que completo un evento.

scoreboard players add @s hp.events 1
scoreboard players operation @s hp.points += #event_reward hp.const
tellraw @s {text:"",extra:[{text:"Evento completado: ",color:"green"},{text:"+30 pts",color:"gold"}]}
