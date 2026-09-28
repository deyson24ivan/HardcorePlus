# +500 puntos por wither.

scoreboard players operation @s hp.delta = @s hp.kill_wither
scoreboard players operation @s hp.delta -= @s hp.last_wither
scoreboard players operation @s hp.bosses += @s hp.delta
scoreboard players operation @s hp.tmp = @s hp.delta
scoreboard players operation @s hp.tmp *= #wither_reward hp.const
scoreboard players operation @s hp.points += @s hp.tmp
tellraw @a {text:"",extra:[{text:"👑 Wither derrotado | ",color:"dark_purple",bold:true},{selector:"@s",color:"yellow"},{text:" +",color:"gray"},{score:{name:"@s",objective:"hp.tmp"},color:"yellow"},{text:" pts",color:"gray"}]}
