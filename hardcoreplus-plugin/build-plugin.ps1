$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$server = Join-Path (Split-Path -Parent $root) "paper-local-26.2"
$jdk = Get-ChildItem -LiteralPath (Join-Path $server "runtime") -Directory -Filter "jdk-25*" | Sort-Object Name -Descending | Select-Object -First 1

if (-not $jdk) {
  throw "No se encontro Java 25 en paper-local-26.2/runtime."
}

$java = Join-Path $jdk.FullName "bin/java.exe"
$javac = Join-Path $jdk.FullName "bin/javac.exe"
$jar = Join-Path $jdk.FullName "bin/jar.exe"
$libraryJars = Get-ChildItem -LiteralPath (Join-Path $server "libraries") -Recurse -File -Filter "*.jar" | ForEach-Object { $_.FullName }
if (-not $libraryJars -or $libraryJars.Count -eq 0) {
  throw "No se encontro paper-api en paper-local-26.2/libraries. Arranca Paper una vez para descargar librerias."
}
$classpath = ($libraryJars -join [IO.Path]::PathSeparator)
$classes = Join-Path $root "build/classes"
$outDir = Join-Path $root "build/libs"
$outJar = Join-Path $outDir "HardcorePlusPlugin.jar"
$sourcesFile = Join-Path $root "build/sources.txt"

Remove-Item -LiteralPath $classes -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item -LiteralPath $outDir -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path $classes | Out-Null
New-Item -ItemType Directory -Force -Path $outDir | Out-Null

$sources = Get-ChildItem -LiteralPath (Join-Path $root "src/main/java") -Recurse -Filter "*.java" | ForEach-Object { $_.FullName }
$sources | ForEach-Object { $_.Replace("\", "/") } | Set-Content -LiteralPath $sourcesFile -Encoding ASCII
& $javac -encoding UTF-8 -cp $classpath -d $classes "@$sourcesFile"
if ($LASTEXITCODE -ne 0) {
  throw "javac fallo con codigo $LASTEXITCODE."
}

$resources = Join-Path $root "src/main/resources"
if (Test-Path -LiteralPath $resources) {
  Copy-Item -Path (Join-Path $resources "*") -Destination $classes -Recurse -Force
}
& $jar --create --file $outJar -C $classes .
if ($LASTEXITCODE -ne 0) {
  throw "jar fallo con codigo $LASTEXITCODE."
}

Copy-Item -LiteralPath $outJar -Destination (Join-Path $server "plugins/HardcorePlusPlugin.jar") -Force

& $java -version
Write-Host "Plugin compilado e instalado: $outJar"
