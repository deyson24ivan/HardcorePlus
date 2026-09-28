# Mantiene a cada jugador en la linea visual correcta del panel.

team join hp_v3 @a[tag=hp.ready,scores={hp.lives=3..},team=!hp_v3]
team join hp_v2 @a[tag=hp.ready,scores={hp.lives=2},team=!hp_v2]
team join hp_v1 @a[tag=hp.ready,scores={hp.lives=1},team=!hp_v1]
team join hp_dead @a[tag=hp.ready,scores={hp.lives=..0},team=!hp_dead]
