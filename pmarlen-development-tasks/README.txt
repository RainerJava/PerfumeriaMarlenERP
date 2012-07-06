PERFUMERIA AMRLEN DEVELOPMENT TASKS

1) IMOPORT OR CREATE IMAGES FOR PRODUCTO:

    1.1 FROM DIRECTORY

    mvn clean compile exec:java -Dexec.mainClass=com.pmarlen.dev.task.CrearImagenesDePruebaEnMultimedio -Dexec.args="-u jdbc:mysql://localhost/PMARLEN_DB_TEST?characterEncoding=UTF-8 PMARLEN_TEST PMARLEN_TEST_PASSWORD ../../pmarlen_imgs @PRODUCTO_ID@.JPG"

    1.2 : CREATING DUMMY IMAGES:

    mvn clean compile exec:java -Dexec.mainClass=com.pmarlen.dev.task.CrearImagenesDePruebaEnMultimedio -Dexec.args="-u jdbc:mysql://localhost/PMARLEN_DB_TEST?characterEncoding=UTF-8 PMARLEN_TEST PMARLEN_TEST_PASSWORD"

2) IMPORTADOR DE PRODUCTOS

    mvn clean compile exec:java -Dexec.mainClass=com.pmarlen.dev.task.ImportProductoRelatedEntitiesFromXLSX -Dexec.args="../../excel_import/Base_de_productos_ultima_definitiva.xlsx 3 524 ./o1.sql"

3) IMPORTAR SEPOMEX

    mvn clean compile exec:java -Dexec.mainClass=com.pmarlen.dev.task.ParseSepomex -Dexec.args="CPdescarga.xml sepomex.sql"

    mvn clean compile exec:java -Dexec.mainClass=com.pmarlen.dev.task.ParseSepomex -Dexec.args=/home/alfredo/Descargas/CPdescarga.xml sepomex.txt false

4) BUILD EXECUTABLE JAR

   mvn clean package -P Build4Run
