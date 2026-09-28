# Recompensa individual por boss custom derrotado.

scoreboard players add @s hp.bosses 1
scoreboard players operation @s hp.points += #boss_reward hp.const
tellraw @s {text:"",extra:[{text:"+100 pts",color:"gold",bold:true},{text:" por derrotar un boss.",color:"gray"}]}
