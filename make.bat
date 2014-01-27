RMDIR /S /Q build
DEL /S /Q ElectromagneticFieldHockey.jar
IF %1==clean EXIT
MKDIR build
javac -source 1.6 -target 1.6 -deprecation -sourcepath src\main\java -d build -implicit:class -g:none src\main\java\thebombzen\emfieldhockey\ElectromagneticFieldHockey.java
jar -cmf jar_manifest.mf ElectromagneticFieldHockey.jar -C build thebombzen

