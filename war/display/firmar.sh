for f in *.jar;
do
	echo "Firmando $f ..";
	jarsigner -keystore javaleague.keystore -storepass j4v4H1sp4n0 $f javaLeague;
done;
