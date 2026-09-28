# Cuenta dias personales por tiempo vivo. No depende de dormir ni del dia del mundo.

scoreboard players add @s hp.surv_ticks 1
execute if score @s hp.surv_ticks matches 24000.. run function hardcore:stats/day_complete
