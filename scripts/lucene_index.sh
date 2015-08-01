#/bin/bash
export MAVEN_OPTS="-Xms15500m -Xmx15500m"
tr=`mktemp run_lucene_index.XXX`
echo "mvn compile exec:java -Dexec.mainClass=edu.cmu.lti.oaqa.bio.index.medline.annotated.CreateBioConceptLuceneIndex -Dexec.args='$@' " > $tr
. $tr
rm $tr
