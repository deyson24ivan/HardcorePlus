tag @s add hp.ach_not_this_time
function hardcore:achievements/reward
tellraw @a {text:"",extra:[{text:"🏆 NO ESTA VEZ",color:"gold",bold:true},{text:" | ",color:"dark_gray"},{selector:"@s",color:"yellow"},{text:" sobrevivio con medio corazon. +100 pts",color:"gray"}]}
