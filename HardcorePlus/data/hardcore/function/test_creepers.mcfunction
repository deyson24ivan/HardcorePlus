# Invoca creepers para comprobar radio/fuse por modo.

tellraw @s {text:"Invocando creepers de prueba. En modos altos deberian explotar mas fuerte y mas rapido.",color:"gold"}
summon minecraft:creeper ~2 ~ ~ {Tags:["hp.test_mob"]}
summon minecraft:creeper ~4 ~ ~ {Tags:["hp.test_mob"]}
summon minecraft:creeper ~6 ~ ~ {Tags:["hp.test_mob"]}
summon minecraft:creeper ~8 ~ ~ {Tags:["hp.test_mob"]}
