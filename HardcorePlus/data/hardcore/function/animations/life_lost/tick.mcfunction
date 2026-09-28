# Animacion global cuando un jugador pierde una vida.

execute if score @s hp.anim_life matches 1 run title @a times 5 22 10
execute if score @s hp.anim_life matches 1 run title @a title {text:"❤",color:"red",bold:true}
execute if score @s hp.anim_life matches 1 run title @a subtitle {text:"",extra:[{selector:"@s",color:"yellow"},{text:" ha muerto",color:"gray"}]}
execute if score @s hp.anim_life matches 1 run playsound minecraft:entity.player.hurt player @a ~ ~ ~ 0.9 0.75
execute if score @s hp.anim_life matches 1 run particle minecraft:damage_indicator ~ ~1 ~ 0.35 0.45 0.35 0.05 12 force @a

execute if score @s hp.anim_life matches 8 run title @a title {text:"💔",color:"dark_red",bold:true}
execute if score @s hp.anim_life matches 8 run title @a subtitle {text:"",extra:[{selector:"@s",color:"yellow"},{text:" ha perdido una vida",color:"gray"}]}
execute if score @s hp.anim_life matches 8 run playsound minecraft:block.glass.break player @a ~ ~ ~ 1 0.65
execute if score @s hp.anim_life matches 8 run particle minecraft:poof ~ ~1 ~ 0.45 0.6 0.45 0.05 24 force @a

execute if score @s hp.anim_life matches 18 if score @s hp.lives matches 2 run title @a title {text:"Vidas restantes",color:"gold",bold:true}
execute if score @s hp.anim_life matches 18 if score @s hp.lives matches 2 run title @a subtitle {text:"❤❤",color:"red",bold:true}
execute if score @s hp.anim_life matches 18 if score @s hp.lives matches 1 run title @a title {text:"ULTIMA VIDA",color:"dark_red",bold:true}
execute if score @s hp.anim_life matches 18 if score @s hp.lives matches 1 run title @a subtitle {text:"❤",color:"red",bold:true}
execute if score @s hp.anim_life matches 18 run playsound minecraft:block.note_block.bass player @a ~ ~ ~ 0.8 0.8

scoreboard players add @s hp.anim_life 1
execute if score @s hp.anim_life matches 42.. run scoreboard players set @s hp.anim_life 0
