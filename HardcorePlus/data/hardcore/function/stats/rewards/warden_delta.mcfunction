# +1500 puntos por warden.

scoreboard players operation @s hp.delta = @s hp.kill_warden
scoreboard players operation @s hp.delta -= @s hp.last_warden
scoreboard players operation @s hp.bosses += @s hp.delta
scoreboard players operation @s hp.tmp = @s hp.delta
scoreboard players operation @s hp.tmp *= #warden_reward hp.const
scoreboard players operation @s hp.points += @s hp.tmp
tellraw @a {text:"",extra:[{text:"👑 Warden derrotado | ",color:"dark_aqua",bold:true},{selector:"@s",color:"yellow"},{text:" +",color:"gray"},{score:{name:"@s",objective:"hp.tmp"},color:"yellow"},{text:" pts",color:"gray"}]}
