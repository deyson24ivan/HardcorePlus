# Muestra el modo global activo.

execute if score #stage hp.stage matches 0 run tellraw @a {text:"HardcorePlus | Modo: Predeterminado (+10% dano mobs)",color:"green"}
execute if score #stage hp.stage matches 1 run tellraw @a {text:"HardcorePlus | Modo: Dificil I (+25% dano, +10% velocidad)",color:"yellow"}
execute if score #stage hp.stage matches 2 run tellraw @a {text:"HardcorePlus | Modo: Dificil II (+50% dano, +20% velocidad)",color:"gold"}
execute if score #stage hp.stage matches 3 run tellraw @a {text:"HardcorePlus | Modo: Pesadilla (+75% dano, +30% velocidad)",color:"red"}
execute if score #stage hp.stage matches 4 run tellraw @a {text:"HardcorePlus | Modo: Apocalipsis (+100% dano, +50% velocidad)",color:"dark_red"}
execute if score #stage hp.stage matches 5 run tellraw @a {text:"HardcorePlus | Modo: Masacre Total (+150% dano, +50% velocidad)",color:"dark_purple"}
