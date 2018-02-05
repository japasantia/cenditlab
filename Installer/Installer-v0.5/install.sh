#!/bin/bash

installDir="/opt/cenditlab"

if [ -e "$installDir" ]
then
	rm -R $installDir/*
else
	mkdir "$installDir"
fi

cp App.jar $installDir
cp Core.jar $installDir
cp Systems.jar $installDir
cp UI.jar $installDir
cp UI-Base.jar $installDir

chmod +x $installDir/App.jar

cp cendit-logo.jpg $installDir

echo "[Desktop Entry]
Type=Application
Name=CenditLab
Comment=Lanzador para CenditLab
Exec=java -jar $installDir/App.jar
Terminal=false
Icon=$installDir/cendit-logo.jpg
Categories=Application;TestAndMeasurement" > CenditLab.desktop

desktopDir=$(xdg-user-dir DESKTOP)
echo $desktopDir

mv CenditLab.desktop $desktopDir
chmod +x $desktopDir/CenditLab.desktop

echo "CenditLab se ha instalado de manera exitosa"

