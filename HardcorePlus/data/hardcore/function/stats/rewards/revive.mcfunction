# +100 puntos por revivir a un companero.

scoreboard players operation @s hp.points += #revive_reward hp.const
tellraw @s {text:"",extra:[{text:"+100 pts",color:"gold",bold:true},{text:" por revivir a un companero.",color:"gray"}]}
