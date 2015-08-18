#/bin/bash
bash_cmd="mvn compile exec:java -Dexec.mainClass=edu.cmu.lti.oaqa.annographix.solr.SimpleQueryApp -Dexec.args='$@'"
bash -c "$bash_cmd"
