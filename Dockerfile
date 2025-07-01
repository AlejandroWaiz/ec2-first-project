FROM tomcat:10.1.23-jre21
MAINTAINER admin_sucursal
EXPOSE 8080
COPY target/vehiculosBuild.war  /usr/local/tomcat/webapps/vehiculosBuild.war 
