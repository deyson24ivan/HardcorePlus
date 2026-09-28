# Prepara el panel lateral de vidas y puntos.

scoreboard objectives modify hp.points displayname {"text":"✦ VIDAS Y PUNTOS","color":"gold","bold":true}

team add hp_v3 {"text":"Vidas 3","color":"green"}
team modify hp_v3 color green
team modify hp_v3 prefix {"text":"❤❤❤ ","color":"red"}
team modify hp_v3 suffix {"text":" ✦","color":"gold"}

team add hp_v2 {"text":"Vidas 2","color":"yellow"}
team modify hp_v2 color yellow
team modify hp_v2 prefix {"text":"❤❤ ","color":"gold"}
team modify hp_v2 suffix {"text":" ✦","color":"gold"}

team add hp_v1 {"text":"Ultima vida","color":"red"}
team modify hp_v1 color red
team modify hp_v1 prefix {"text":"❤ ","color":"dark_red"}
team modify hp_v1 suffix {"text":" ✦","color":"gold"}

team add hp_dead {"text":"Eliminado","color":"dark_red"}
team modify hp_dead color dark_red
team modify hp_dead prefix {"text":"☠ ","color":"dark_red"}
team modify hp_dead suffix {"text":" ✦","color":"dark_gray"}
