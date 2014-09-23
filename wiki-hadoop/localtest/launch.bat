set HADOOP_CLASSPATH=C:\Wiki\wiki-hadoop\localtest\wiki-hadoop-0.1-SNAPSHOT.jar
::set HADOOP_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8999"
::hadoop com.magg.wiki.mapreduce.WikiLinksDriver -conf hadoop-local.xml in/wikiPage.xml out/single
::hadoop com.magg.wiki.mapreduce.WikiOutLinksDriver in/WikiCollection.xml out/outwards/
hadoop com.magg.wiki.mapreduce.WikiInLinksDriver in/WikiCollection.xml out/inwards
