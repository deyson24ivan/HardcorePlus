# +250 puntos por elder guardian.

scoreboard players operation @s hp.delta = @s hp.kelder
scoreboard players operation @s hp.delta -= @s hp.last_elder
scoreboard players operation @s hp.bosses += @s hp.delta
scoreboard players operation @s hp.tmp = @s hp.delta
scoreboard players operation @s hp.tmp *= #elder_reward hp.const
scoreboard players operation @s hp.points += @s hp.tmp
tellraw @a {text:"",extra:[{text:"👑 Anciano del templo derrotado | ",color:"gold",bold:true},{selector:"@s",color:"yellow"},{text:" +",color:"gray"},{score:{name:"@s",objective:"hp.tmp"},color:"yellow"},{text:" pts",color:"gray"}]}
