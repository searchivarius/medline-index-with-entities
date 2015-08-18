#/bin/bash
bash_cmd="mvn compile exec:java -Dexec.mainClass=edu.cmu.lti.oaqa.bio.index.medline.annotated.apps.SimpleQueryApp -Dexec.args='$@'"
bash -c "$bash_cmd"
