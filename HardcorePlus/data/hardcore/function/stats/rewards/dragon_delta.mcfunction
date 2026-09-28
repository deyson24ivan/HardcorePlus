# +1000 puntos por ender dragon.

scoreboard players operation @s hp.delta = @s hp.kill_dragon
scoreboard players operation @s hp.delta -= @s hp.last_dragon
scoreboard players operation @s hp.bosses += @s hp.delta
scoreboard players operation @s hp.tmp = @s hp.delta
scoreboard players operation @s hp.tmp *= #dragon_reward hp.const
scoreboard players operation @s hp.points += @s hp.tmp
tellraw @a {text:"",extra:[{text:"👑 Dragona derrotada | ",color:"light_purple",bold:true},{selector:"@s",color:"yellow"},{text:" +",color:"gray"},{score:{name:"@s",objective:"hp.tmp"},color:"yellow"},{text:" pts",color:"gray"}]}
