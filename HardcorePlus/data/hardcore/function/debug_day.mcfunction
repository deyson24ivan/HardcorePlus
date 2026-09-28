# Muestra valores internos del modo de dificultad.

tellraw @s {text:"--- HardcorePlus debug dificultad ---",color:"gold"}
tellraw @s {text:"",extra:[{text:"Stage interno: ",color:"gray"},{score:{name:"#stage",objective:"hp.stage"},color:"yellow"}]}
function hardcore:modes/show
tellraw @s {text:"Mobs nuevos sin procesar se marcan automaticamente. Al cambiar modo, los mobs existentes se reprocesan.",color:"gray"}
