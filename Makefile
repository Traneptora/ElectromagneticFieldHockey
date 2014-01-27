all: ElectromagneticFieldHockey.jar

ElectromagneticFieldHockey.jar: build/thebombzen/emfieldhockey/ElectromagneticFieldHockey.class
	jar -cmf jar_manifest.mf ElectromagneticFieldHockey.jar -C build/ thebombzen/

build/thebombzen/emfieldhockey/ElectromagneticFieldHockey.class:
	mkdir -p build/
	javac -source 1.6 -target 1.6 -deprecation -sourcepath src/main/java -d build/ -implicit:class -g:none src/main/java/thebombzen/emfieldhockey/ElectromagneticFieldHockey.java

clean: 
	rm -rf build/
	rm -f ElectromagneticFieldHockey.jar

