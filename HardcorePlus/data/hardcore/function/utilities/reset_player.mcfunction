# Ejecutar como el jugador que quieres reiniciar:
# /execute as <jugador> run function hardcore:utilities/reset_player

scoreboard players set @s hp.lives 3
scoreboard players set @s hp.lastdeath 0
scoreboard players set @s hp.anim_life 0
scoreboard players set @s hp.anim_elim 0
scoreboard players set @s hp.revive_click 0
execute if score @s hp.deaths matches 0.. run scoreboard players operation @s hp.lastdeath = @s hp.deaths
gamemode survival @s
tag @s add hp.ready
tag @s remove hp.eliminated

tellraw @s {text:"Tus vidas de HardcorePlus fueron reiniciadas a ❤❤❤.",color:"green"}
