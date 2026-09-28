# Muestra las vidas actuales sin ocupar el chat.

execute if score @s hp.lives matches 3.. run title @s actionbar {text:"",extra:[{text:"Vidas: ❤❤❤",color:"red"},{text:" | ✦ ",color:"gold"},{score:{name:"@s",objective:"hp.points"},color:"yellow"},{text:" pts",color:"gold"}]}
execute if score @s hp.lives matches 2 run title @s actionbar {text:"",extra:[{text:"Vidas: ❤❤",color:"red"},{text:" | ✦ ",color:"gold"},{score:{name:"@s",objective:"hp.points"},color:"yellow"},{text:" pts",color:"gold"}]}
execute if score @s hp.lives matches 1 run title @s actionbar {text:"",extra:[{text:"Vidas: ❤",color:"red"},{text:" | ✦ ",color:"gold"},{score:{name:"@s",objective:"hp.points"},color:"yellow"},{text:" pts",color:"gold"}]}
execute if score @s hp.lives matches ..0 run title @s actionbar {text:"",extra:[{text:"ELIMINADO",color:"dark_red",bold:true},{text:" | ✦ ",color:"gold"},{score:{name:"@s",objective:"hp.points"},color:"yellow"},{text:" pts",color:"gold"}]}
