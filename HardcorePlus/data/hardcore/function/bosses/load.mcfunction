# Prepara bossbars y estados de los bosses prototipo.
# Limpia nombres flotantes de bosses/minions viejos si se recarga con una pelea activa.
execute as @e[tag=hp.boss] if data entity @s CustomName run data remove entity @s CustomName
execute as @e[tag=hp.boss] if data entity @s CustomNameVisible run data remove entity @s CustomNameVisible
execute as @e[tag=hp.boss_minion] if data entity @s CustomName run data remove entity @s CustomName
execute as @e[tag=hp.boss_minion] if data entity @s CustomNameVisible run data remove entity @s CustomNameVisible

bossbar add hardcore:titan {"text":"EL TITAN DE HIERRO","color":"gray","bold":true}
bossbar set hardcore:titan name {"text":"EL TITAN DE HIERRO","color":"gray","bold":true}
bossbar set hardcore:titan color white
bossbar set hardcore:titan style notched_10
bossbar set hardcore:titan max 600
bossbar set hardcore:titan value 0
bossbar set hardcore:titan visible false

bossbar add hardcore:bruja {"text":"LA BRUJA CARMESI","color":"dark_red","bold":true}
bossbar set hardcore:bruja name {"text":"LA BRUJA CARMESI","color":"dark_red","bold":true}
bossbar set hardcore:bruja color red
bossbar set hardcore:bruja style notched_10
bossbar set hardcore:bruja max 450
bossbar set hardcore:bruja value 0
bossbar set hardcore:bruja visible false

bossbar add hardcore:ceniza {"text":"EL REY DE CENIZA","color":"gold","bold":true}
bossbar set hardcore:ceniza name {"text":"EL REY DE CENIZA","color":"gold","bold":true}
bossbar set hardcore:ceniza color yellow
bossbar set hardcore:ceniza style notched_10
bossbar set hardcore:ceniza max 550
bossbar set hardcore:ceniza value 0
bossbar set hardcore:ceniza visible false

bossbar add hardcore:abismo {"text":"EL DEVORADOR DEL ABISMO","color":"dark_aqua","bold":true}
bossbar set hardcore:abismo name {"text":"EL DEVORADOR DEL ABISMO","color":"dark_aqua","bold":true}
bossbar set hardcore:abismo color blue
bossbar set hardcore:abismo style notched_10
bossbar set hardcore:abismo max 700
bossbar set hardcore:abismo value 0
bossbar set hardcore:abismo visible false

bossbar add hardcore:dragona {"text":"LA DRAGONA CORRUPTA","color":"light_purple","bold":true}
bossbar set hardcore:dragona name {"text":"LA DRAGONA CORRUPTA","color":"light_purple","bold":true}
bossbar set hardcore:dragona color purple
bossbar set hardcore:dragona style notched_10
bossbar set hardcore:dragona max 500
bossbar set hardcore:dragona value 0
bossbar set hardcore:dragona visible false
