# HardcorePlus v0.7.1

Datapack base para un servidor Minecraft Java con sistema de 3 vidas, animaciones de muerte, resurreccion y dificultad manual progresiva.

## Version objetivo

- Minecraft Java: 26.2
- Servidor recomendado: Paper 26.2 o la build disponible equivalente en tu host
- Datapack format: 107.1

## Que incluye

- Cada jugador nuevo recibe 3 vidas.
- Al morir pierde 1 vida.
- Con 2 o 1 vidas se muestra un mensaje al servidor.
- Al llegar a 0 vidas se cambia a modo espectador.
- Las vidas se muestran en la actionbar.
- Las vidas numericas tambien aparecen debajo del nombre.
- Animacion global cuando alguien pierde una vida.
- Animacion global cuando alguien queda eliminado.
- Sistema de resurreccion con Totem de Resurreccion.
- Modos de dificultad elegidos por comando.
- Boost automatico a mobs hostiles nuevos y mobs ya existentes al cambiar modo.
- Ajustes especiales para creepers y mobs a distancia.
- Armadura de cobre real para Dificil I en 26.2.
- Efectos visibles en mobs cuando les toca el bonus.
- Estadisticas compactas por jugador.
- Logros propios de HardcorePlus.
- Puntos de progresion preparados para la futura tienda.
- Panel lateral bonito con vidas y puntos de todos.
- Cinco bosses prototipo con bossbar, fases, ataques, loot y puntos.
- Nombres flotantes de bosses y minions ocultos; el nombre del boss queda en la bossbar.
- Funcion de utilidad para reiniciar un jugador.

## Instalacion local rapida

1. Crea o abre un mundo de pruebas en Minecraft Java 26.2.
2. Abre la carpeta del mundo.
3. Copia esta carpeta `HardcorePlus` dentro de:

```text
world/datapacks/
```

4. Entra al mundo o ejecuta:

```text
/reload
```

5. Comprueba que aparece:

```text
HardcorePlus v0.7.1 cargado: nombres flotantes de bosses ocultos.
```

## Pruebas basicas

Para probar rapido:

```text
/kill <tu_jugador>
```

Resultado esperado:

- Primera muerte: quedan 2 vidas.
- Segunda muerte: queda 1 vida.
- Tercera muerte: eliminado y modo espectador.

Para reiniciar a un jugador:

```text
/execute as <tu_jugador> run function hardcore:utilities/reset_player
```

## Dificultad manual

HardcorePlus ya no sube dificultad por dias ni por dormir. El modo global se elige por comando para que todos avancen a la par.

Modos actuales:

| Modo | Mobs |
| --- | --- |
| Predeterminado | +10% dano cuerpo a cuerpo |
| Dificil I | +25% dano, +10% velocidad, 10% efecto, 50% armadura entre cota de malla, cobre e hierro |
| Dificil II | +50% dano, +20% velocidad, 25% efecto, 50% armadura entre hierro, oro y diamante |
| Pesadilla | +75% dano, +30% velocidad, 50% efecto, 50% armadura diamante/netherite encantada |
| Apocalipsis | +100% dano, +50% velocidad, 75% efecto, 80% armadura encantada |
| Masacre Total | +150% dano, +50% velocidad, 100% efecto, 100% armadura encantada |

Para ver el modo actual:

```text
/function hardcore:show_mode
```

Para cambiar el modo:

```text
/function hardcore:normal
/function hardcore:dificil_uno
/function hardcore:dificil_dos
/function hardcore:pesadilla
/function hardcore:apocalipsis
/function hardcore:masacre_total
```

Al cambiar modo, HardcorePlus vuelve a procesar los mobs hostiles existentes y tambien procesa los nuevos que aparezcan despues.

Importante: en la carpeta `datapacks` debe quedar activa solo una version de HardcorePlus. Si hay dos zips, Minecraft puede cargar etiquetas duplicadas y ejecutar el tick mas de una vez.

Para probar mobs:

```text
/function hardcore:test_zombies
/function hardcore:test_skeletons
/function hardcore:test_spiders
/function hardcore:test_creepers
```

Recuerda que la armadura es probabilidad, no garantia, excepto en Masacre Total. Si quieres una prueba visual forzada del set de armadura actual:

```text
/function hardcore:test_armored_zombie
```

Para revisar todo el modo actual sin depender de la suerte:

```text
/function hardcore:test_modo
```

Notas de funcionamiento:

- Zombies, aranas, slimes, hoglins, ravagers y otros mobs de golpe directo reciben el aumento por atributo de dano.
- Zombies, esqueletos, pillagers, piglins y variantes que pueden usar equipo reciben armadura segun la probabilidad del modo.
- Esqueletos, strays, bogged y pillagers reciben armas encantadas, porque su dano real viene del arco o crossbow.
- Creepers suben por radio de explosion y menor fuse, porque `attack_damage` no cambia su explosion.
- Ghasts suben por potencia de explosion.
- Los efectos son visibles y pueden ser fuerza, resistencia, regeneracion, resistencia al fuego, absorcion o velocidad.

Para ver valores internos si algo no cuadra:

```text
/function hardcore:debug_mode
```

El boost se aplica a mobs hostiles nuevos una sola vez, y tambien se vuelve a aplicar a los mobs existentes cada vez que cambias el modo.

## Estadisticas, logros y puntos

La actionbar muestra tus vidas y puntos sin ocupar el chat. El panel lateral derecho muestra a todos los jugadores con sus vidas y puntos.

Panel lateral:

```text
/function hardcore:panel
/function hardcore:panel_off
```

En el panel, los corazones salen junto al nombre y el numero de la derecha son los puntos:

```text
✦ VIDAS Y PUNTOS
❤❤❤ Deyson ✦    125
❤ Ana ✦          42
☠ Luis ✦          8
```

Perfil compacto:

```text
/function hardcore:stats
/function hardcore:estadisticas
```

Puntos solamente:

```text
/function hardcore:puntos
```

Logros:

```text
/function hardcore:logros
/function hardcore:logros_detalle
```

Recompensas actuales:

| Accion | Puntos |
| --- | ---: |
| Mob hostil derrotado | +2 |
| Animal de granja derrotado | +1 |
| Dia personal sobrevivido | +5 |
| Revivir a un companero | +100 |
| Elite derrotado | +50 |
| Blood Moon sobrevivida | +100 |
| Evento completado | +30 |
| Boss custom derrotado | +100 |
| Logro desbloqueado | +100 |
| Mineral de diamante minado | +1 |
| Ancient debris minado | +2 |
| Anciano del templo derrotado | +250 |
| Wither derrotado | +500 |
| Dragona derrotada | +1000 |
| Warden derrotado | +1500 |

Los dias sobrevividos son personales: cuentan 24000 ticks vivo y no dependen de dormir ni del dia del mundo.

Los animales de granja contados por ahora son vaca, cerdo, oveja, gallina, conejo, cabra y mooshroom.

La "netherita" minada se cuenta como `ancient_debris`, porque ese es el bloque real que se mina antes de convertirlo en netherite.

Logros actuales:

| Logro | Condicion |
| --- | --- |
| PRIMERA SANGRE | Derrotar un Elite |
| NO ESTA VEZ | Sobrevivir con medio corazon |
| SACRIFICIO | Revivir a un companero |
| SOBREVIVIENTE | Llegar a 100 dias personales sobrevividos |

Funciones listas para conectar con fases futuras:

```text
/execute as <jugador> run function hardcore:stats/add_elite
/execute as <jugador> run function hardcore:stats/add_boss
/execute as <jugador> run function hardcore:stats/add_blood_moon
/execute as <jugador> run function hardcore:stats/add_event
```

## Bosses prototipo

Estos bosses ya tienen gameplay base. Por ahora usan mobs vanilla como cuerpo visible temporal; despues se conectan a modelos 3D del resource pack.

Lista:

| Boss | Base temporal | Estilo |
| --- | --- | --- |
| El Titan de Hierro | Ravager | Tanque, terremotos, guardias |
| La Bruja Carmesi | Witch | Veneno, maldiciones, aranas |
| El Rey de Ceniza | Wither Skeleton | Fuego, blazes, presion cuerpo a cuerpo |
| El Devorador del Abismo | Enderman | Oscuridad, control, endermites |
| La Dragona Corrupta | Phantom gigante | Vuelo, levitacion, criaturas del End |

Comandos:

```text
/function hardcore:bosses
/function hardcore:boss_titan
/function hardcore:boss_bruja
/function hardcore:boss_ceniza
/function hardcore:boss_abismo
/function hardcore:boss_dragona
/function hardcore:boss_clear
```

Al derrotar un boss custom:

- Los jugadores cercanos reciben +100 puntos.
- Cuenta en `hp.bosses`.
- Suelta loot simple de prueba.
- Oculta automaticamente su bossbar.

Nota: no invoques el mismo boss dos veces a la vez. El datapack bloquea duplicados por boss, pero puedes tener varios bosses distintos activos si quieres hacer pruebas locas.

## Paper y plugins

HardcorePlus funciona como datapack, pero esta preparado para conectarse con plugins de Paper usando scoreboards.

Scoreboards principales:

```text
hp.points
hp.lives
hp.hostiles
hp.animals
hp.elites
hp.bosses
hp.diamonds
hp.netherite
hp.achievements
```

Un plugin puede leer o modificar estos valores directamente, por ejemplo para una tienda GUI, NPCs, misiones, menus, rangos o recompensas especiales.

Ejemplos utiles para plugins:

```text
/scoreboard players add <jugador> hp.points 100
/execute as <jugador> run function hardcore:stats/add_event
/execute as <jugador> run function hardcore:stats/add_blood_moon
```

La tienda quedaria mejor como plugin o con un plugin de menus, porque el datapack puede hacer compras, pero una interfaz GUI bonita es mucho mas comoda desde Paper.

## Resurreccion

Desde v0.2, el jugador eliminado representa su propia `Alma del Caido` en modo espectador.

Para revivirlo:

1. El jugador eliminado se acerca en espectador a un companero vivo.
2. El companero usa un `Totem de Resurreccion` a 6 bloques o menos.
3. El jugador eliminado vuelve en survival con 1 vida.

Como todavia no tenemos resource pack, el Totem de Resurreccion usa internamente:

```text
carrot_on_a_stick
```

Receta:

```text
Ancient Debris | Nether Star       | Ancient Debris
Echo Shard     | Totem of Undying  | Echo Shard
Ancient Debris | Diamond           | Ancient Debris
```

Para pruebas rapidas:

```text
/execute as <jugador_vivo> run function hardcore:revive/give_totem
```

## Subir a Aternos

1. Entra al panel de Aternos.
2. Usa Paper con la misma version objetivo del datapack.
3. Abre `Mundos` o `Files`, segun como tengas configurado el servidor.
4. Sube la carpeta o un `.zip` cuyo contenido tenga `pack.mcmeta` en la raiz.
5. Reinicia el servidor.
6. Ejecuta `/datapack list` y revisa que `HardcorePlus` aparezca activo.

## Siguiente fase

La siguiente version natural es la tienda de puntos o mobs elite y legendarios.
