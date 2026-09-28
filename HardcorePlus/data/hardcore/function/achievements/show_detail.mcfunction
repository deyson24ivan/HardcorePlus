# Muestra el detalle de logros.

tellraw @s {text:"",extra:[{text:"🏆 Logros: ",color:"gold"},{score:{name:"@s",objective:"hp.achievements"},color:"yellow",bold:true},{text:"/4",color:"gray"},{text:" | ✦ ",color:"gold"},{score:{name:"@s",objective:"hp.points"},color:"yellow"},{text:" pts",color:"gray"}]}
execute if entity @s[tag=hp.ach_first_blood] run tellraw @s {text:"✓ PRIMERA SANGRE",color:"green"}
execute unless entity @s[tag=hp.ach_first_blood] run tellraw @s {text:"□ PRIMERA SANGRE",color:"dark_gray"}
execute if entity @s[tag=hp.ach_not_this_time] run tellraw @s {text:"✓ NO ESTA VEZ",color:"green"}
execute unless entity @s[tag=hp.ach_not_this_time] run tellraw @s {text:"□ NO ESTA VEZ",color:"dark_gray"}
execute if entity @s[tag=hp.ach_sacrifice] run tellraw @s {text:"✓ SACRIFICIO",color:"green"}
execute unless entity @s[tag=hp.ach_sacrifice] run tellraw @s {text:"□ SACRIFICIO",color:"dark_gray"}
execute if entity @s[tag=hp.ach_survivor] run tellraw @s {text:"✓ SOBREVIVIENTE",color:"green"}
execute unless entity @s[tag=hp.ach_survivor] run tellraw @s {text:"□ SOBREVIVIENTE",color:"dark_gray"}
