# Inicializa a cada jugador la primera vez que entra con el datapack activo.

scoreboard players set @s hp.lives 3
scoreboard players set @s hp.lastdeath 0
execute if score @s hp.deaths matches 0.. run scoreboard players operation @s hp.lastdeath = @s hp.deaths
tag @s add hp.ready
function hardcore:stats/init

tellraw @s {text:"",extra:[{text:"HardcorePlus",color:"gold",bold:true},{text:" iniciado. Tienes ",color:"gray"},{text:"❤❤❤",color:"red"},{text:" vidas.",color:"gray"}]}
title @s title {text:"HardcorePlus",color:"gold",bold:true}
title @s subtitle {text:"3 vidas",color:"red"}
