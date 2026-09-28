tag @s add hp.ach_sacrifice
function hardcore:achievements/reward
tellraw @a {text:"",extra:[{text:"🏆 SACRIFICIO",color:"gold",bold:true},{text:" | ",color:"dark_gray"},{selector:"@s",color:"yellow"},{text:" revivio a un companero. +100 pts",color:"gray"}]}
