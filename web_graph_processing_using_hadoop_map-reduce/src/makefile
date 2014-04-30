compile:
	javac -classpath /usr/local/hadoop/hadoop-1.0.4/hadoop-core-1.0.4.jar:./webgraph_classes -d webgraph_classes WebGraph.java
	jar -cvf webgraph.jar -C webgraph_classes/ .

run: compile 
	hadoop dfs -rmr /user/hduser/output
	hadoop jar webgraph.jar WebGraph input/ output/
	hadoop dfs -cat /user/hduser/output/*
part2:
	javac -classpath /usr/local/hadoop/hadoop-1.0.4/hadoop-core-1.0.4.jar:./part2_classes -d part2_classes Source.java
	jar -cvf part2.jar -C part2_classes/ .
	hadoop dfs -rmr /user/hduser/output2
	hadoop jar part2.jar Source input/ output2/
	hadoop dfs -cat /user/hduser/output2/p* > output2.txt	
