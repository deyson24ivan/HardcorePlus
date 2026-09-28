# Muestra logros en una sola linea.

tellraw @s {text:"",extra:[{text:"🏆 Logros: ",color:"gold"},{score:{name:"@s",objective:"hp.achievements"},color:"yellow",bold:true},{text:"/4",color:"gray"},{text:" | ✦ ",color:"gold"},{score:{name:"@s",objective:"hp.points"},color:"yellow"},{text:" pts | detalle: ",color:"gray"},{text:"/function hardcore:logros_detalle",color:"aqua"}]}
