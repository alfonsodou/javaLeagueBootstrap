for f in *.jar;
do
	echo "Firmando $f ..";
	echo "jarsigner -keystore javaleague.keystore -storepass j4v4H1sp4n0 $f javaLeague";
	jarsigner -storepass j4v4H1sp4n0 $f javaleague;
done;
