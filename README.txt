                                       Perfumeria Marlen ERP Project.
                                       _____________________________


A) Requirements.
   0.- Environment 
       * )prefered Unix / Linux ( In Windows used CyGWin ( + make tools ) ) 
       * )preferred Encoding UTF-8
       * )preferred Line-FeeD: UNIX Style
   1.- Jdk-6 
   2.- Maven 2.x
   3.- MySQL 5.x ( root password)
   4.- Apache-Tomcat-6-0-x (optional, just for deployment )
   
B) IDE's Compatibility

   1.- NetBeans 6.9.+  : Import or Checkout from svn repo.
   2.- Eclipse Helios +  ( + Maven plugin + SVN plugin ) : Import or Checkout from svn repo.

C) Building with Maven 2.x

    1.- Run first to set up the local mysql database enviromnet for TEST profile.

    make -C pmarlen-jpa-entity/db_resources init_DB_TEST MYSQL_ROOT_PASSWORD=xxxxxxxxx

    2.- Add your Tomcat 6-0-X to maven config.


    Add these lines to $(M2_HOME)/config/config.xml

    <server>
      <id>tomcat-6-0-X</id>
      <username>admin_user_for_deploy</username>
      <password>secret_password_for_admin</password>
    </server>

    3.1 - Run compile test(or skip) deploy in tomcat.

	mvn clean tomcat:deploy -Dmaven.test.skip -P devinjob

	mvn clean tomcat:deploy -P preprod

    3.2 - Explore development database

	mysql --default-character-set=utf8 -u PMARLEN_TEST -pPMARLEN_TEST_PASSWORD PMARLEN_DB_TEST

