tag @s add hp.ach_survivor
function hardcore:achievements/reward
tellraw @a {text:"",extra:[{text:"🏆 SOBREVIVIENTE",color:"gold",bold:true},{text:" | ",color:"dark_gray"},{selector:"@s",color:"yellow"},{text:" llego a 100 dias sobrevividos. +100 pts",color:"gray"}]}
