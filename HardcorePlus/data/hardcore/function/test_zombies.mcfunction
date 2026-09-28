# Invoca varios zombies para probar la dificultad actual.

tellraw @s {text:"Invocando zombies de prueba. Espera 1 segundo para que HardcorePlus les aplique la dificultad actual.",color:"gold"}
summon minecraft:zombie ~2 ~ ~ {Tags:["hp.test_mob"]}
summon minecraft:zombie ~3 ~ ~ {Tags:["hp.test_mob"]}
summon minecraft:zombie ~4 ~ ~ {Tags:["hp.test_mob"]}
summon minecraft:zombie ~5 ~ ~ {Tags:["hp.test_mob"]}
summon minecraft:zombie ~6 ~ ~ {Tags:["hp.test_mob"]}
summon minecraft:zombie ~7 ~ ~ {Tags:["hp.test_mob"]}
summon minecraft:zombie ~8 ~ ~ {Tags:["hp.test_mob"]}
summon minecraft:zombie ~9 ~ ~ {Tags:["hp.test_mob"]}
