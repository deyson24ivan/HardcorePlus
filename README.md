# HardcorePlus

HardcorePlus es un proyecto para Minecraft Java 26.2 que combina un datapack, un plugin de Paper y un paquete de recursos propio. Incluye progresion por armaduras y armas, materiales personalizados, totems, tienda, teletransportes, eventos, altares y una ruta de jefes.

## Contenido del repositorio

- `HardcorePlus/`: codigo fuente del datapack.
- `hardcoreplus-plugin/`: codigo fuente y compilador del plugin de Paper.
- `HardcorePlus-ResourcePack/`: texturas, modelos e iconos del paquete de recursos.
- `Esquematicos/`: estructuras y arenas guardadas para WorldEdit o Litematica.
- `reglas-servidor/`: borrador editable de las reglas del servidor.
- `tools/`: herramientas usadas para generar y mantener los recursos.
- `HardcorePlus-v0.7.1-datapack.zip`: datapack listo para instalar.
- `HardcorePlus-Custom-Armor-v0.23.zip`: paquete de recursos actual listo para instalar.
- `hardcoreplus-plugin/build/libs/HardcorePlusPlugin.jar`: plugin actual listo para instalar.

## Sistemas principales

- Tres vidas, eliminacion y resurreccion.
- Estadisticas, logros, puntos y tienda.
- Doce familias de materiales, armaduras y armas personalizadas.
- Totems especiales y sistema de teletransportes.
- Progresion inicial mediante Angel Caido, Titanio y Leviatan.
- Ruta de jefes: Coloso, Eclipse, Vacio, Fenix, Caos, Tiempo, Dragon, Celestial e Infinito.
- Llaves, corazones y altares personalizados para invocar jefes.
- Eventos administrables: PvP, TNTRun, Carrera de Botes, Cazadores de Cabezas y Laberinto a Ciegas.
- Lobby configurable y compatibilidad con WorldEdit.

## Instalacion

1. Usa un servidor Paper compatible con Minecraft Java 26.2.
2. Copia `HardcorePlusPlugin.jar` a la carpeta `plugins` del servidor.
3. Copia `HardcorePlus-v0.7.1-datapack.zip` a `world/datapacks`.
4. Coloca `HardcorePlus-Custom-Armor-v0.23.zip` en los paquetes de recursos del cliente.
5. Inicia el servidor y comprueba los sistemas con `/jefes`, `/tienda` y `/evento`.

## Desarrollo local

El script `hardcoreplus-plugin/build-plugin.ps1` compila el plugin usando el Paper y Java instalados en la carpeta local de pruebas. Esa instalacion, los mundos, las copias de seguridad y los datos de jugadores no se suben a GitHub.

El archivo [HardcorePlus/README.md](HardcorePlus/README.md) contiene la documentacion detallada del datapack y sus comandos.

