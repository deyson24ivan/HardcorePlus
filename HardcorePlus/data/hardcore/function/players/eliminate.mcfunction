# Elimina definitivamente al jugador de la run actual.

scoreboard players set @s hp.lives 0
scoreboard players set @s hp.anim_elim 1
tag @s add hp.eliminated
gamemode spectator @s

tellraw @a {text:"",extra:[{text:"☠ ",color:"dark_red"},{selector:"@s",color:"red",bold:true},{text:" ha caido definitivamente. 0 vidas restantes.",color:"dark_red"}]}
tellraw @s {text:"Tu alma puede volver si un companero usa un Totem de Resurreccion cerca de ti.",color:"gray"}
