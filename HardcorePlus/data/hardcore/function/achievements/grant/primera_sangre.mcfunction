tag @s add hp.ach_first_blood
function hardcore:achievements/reward
tellraw @a {text:"",extra:[{text:"🏆 PRIMERA SANGRE",color:"gold",bold:true},{text:" | ",color:"dark_gray"},{selector:"@s",color:"yellow"},{text:" mato un Elite. +100 pts",color:"gray"}]}
