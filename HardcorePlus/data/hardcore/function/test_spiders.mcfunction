# Invoca aranas para comprobar velocidad y dano por modo.

tellraw @s {text:"Invocando aranas de prueba. En modos altos deberian moverse mas rapido y pegar mas fuerte.",color:"gold"}
summon minecraft:spider ~2 ~ ~ {Tags:["hp.test_mob"]}
summon minecraft:spider ~3 ~ ~ {Tags:["hp.test_mob"]}
summon minecraft:spider ~4 ~ ~ {Tags:["hp.test_mob"]}
summon minecraft:cave_spider ~5 ~ ~ {Tags:["hp.test_mob"]}
summon minecraft:cave_spider ~6 ~ ~ {Tags:["hp.test_mob"]}
