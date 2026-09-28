# Servidor local de pruebas

Esta carpeta es para montar un Paper local cuando quieras probar sin subir a Aternos cada cambio.

Tu PC actualmente tenia Java 8 instalado cuando se preparo esta carpeta. Paper moderno para Minecraft 26.2 necesita Java moderno, asi que antes de correr el servidor local instala Java 21 o superior.

Cuando Java este listo, el flujo sera:

1. Descargar Paper 26.2 o la build disponible equivalente.
2. Colocar `paper.jar` en esta carpeta.
3. Aceptar el EULA en `eula.txt`.
4. Copiar `HardcorePlus` a `world/datapacks/`.
5. Iniciar el servidor.

Comando base:

```text
java -Xms2G -Xmx4G -jar paper.jar nogui
```
