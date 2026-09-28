# Animacion global cuando un jugador queda eliminado.

execute if score @s hp.anim_elim matches 1 run title @a times 5 35 15
execute if score @s hp.anim_elim matches 1 run title @a title {text:"☠",color:"dark_red",bold:true}
execute if score @s hp.anim_elim matches 1 run title @a subtitle {text:"Un alma ha caido",color:"gray"}
execute if score @s hp.anim_elim matches 1 run playsound minecraft:entity.wither.spawn master @a ~ ~ ~ 0.7 0.7
execute if score @s hp.anim_elim matches 1 run particle minecraft:soul ~ ~1 ~ 0.6 0.8 0.6 0.04 36 force @a

execute if score @s hp.anim_elim matches 12 run title @a title {text:"ELIMINADO",color:"dark_red",bold:true}
execute if score @s hp.anim_elim matches 12 run title @a subtitle {text:"",extra:[{selector:"@s",color:"red",bold:true},{text:" ha caido definitivamente",color:"gray"}]}
execute if score @s hp.anim_elim matches 12 run playsound minecraft:entity.lightning_bolt.thunder weather @a ~ ~ ~ 0.7 0.8
execute if score @s hp.anim_elim matches 12 run particle minecraft:large_smoke ~ ~1 ~ 0.7 0.8 0.7 0.04 40 force @a

execute if score @s hp.anim_elim matches 26 run title @a title {text:"0 VIDAS",color:"dark_red",bold:true}
execute if score @s hp.anim_elim matches 26 run title @a subtitle {text:"Modo espectador",color:"gray"}
execute if score @s hp.anim_elim matches 26 run playsound minecraft:block.beacon.deactivate player @a ~ ~ ~ 0.9 0.7

scoreboard players add @s hp.anim_elim 1
execute if score @s hp.anim_elim matches 56.. run scoreboard players set @s hp.anim_elim 0
