ExploreDB:
	
	mvn exec:java -Dexec.mainClass=com.pmarlen.client.ExploreDB
RunApp:

	mvn exec:java -Dexec.mainClass=com.pmarlen.client.Main -P devinjob -e

	mvn clean package exec:java -Dmaven.test.skip=true -Dexec.mainClass=com.pmarlen.client.Main -P preprod -Ddevelopment_host=perfumeriamarlen.dyndns.org -Ddevelopment_port=1080

	mvn clean package -Dmaven.test.skip=true -Dexec.mainClass=com.pmarlen.client.Main -P devinjob
