# +100 puntos por boss vanilla derrotado.

scoreboard players operation @s hp.delta = @s hp.boss_raw
scoreboard players operation @s hp.delta -= @s hp.last_boss_raw
scoreboard players operation @s hp.bosses += @s hp.delta
scoreboard players operation @s hp.tmp = @s hp.delta
scoreboard players operation @s hp.tmp *= #boss_reward hp.const
scoreboard players operation @s hp.points += @s hp.tmp
tellraw @a {text:"",extra:[{text:"👑 Boss derrotado | ",color:"gold",bold:true},{selector:"@s",color:"yellow"},{text:" +",color:"gray"},{score:{name:"@s",objective:"hp.tmp"},color:"yellow"},{text:" pts",color:"gray"}]}
