FROM openjdk:8u151-jdk
MAINTAINER mahieddine.ichir@gmail.com

RUN apt-get install -y git
ADD http://www-us.apache.org/dist/maven/maven-3/3.5.2/binaries/apache-maven-3.5.2-bin.tar.gz /tmp/
RUN mkdir -p /usr/share/maven/
RUN tar xfz /tmp/apache-maven-3.5.2-bin.tar.gz -C /usr/share/maven/
ENV MAVEN_HOME /usr/share/maven/apache-maven-3.5.2/

RUN git clone https://github.com/mahieddine-ichir/shqlserver.git /opt/shqlserver
WORKDIR /opt/shqlserver

ADD sqlserver-driver-4.0.2206.jar /tmp/
RUN ${MAVEN_HOME}/bin/mvn install:install-file -Dfile=/tmp/sqlserver-driver-4.0.2206.jar -DgroupId=com.microsoft -DartifactId=sqlserver-driver -Dversion=4.0.2206 -Dpackaging=jar
RUN ${MAVEN_HOME}/bin/mvn clean install -f /opt/shqlserver/pom.xml

RUN mkdir -p /opt/shqlserver/configuration/
ADD application.properties /opt/shqlserver/configuration/
VOLUME /opt/shqlserver/configuration/

CMD ["java -jar /opt/shqlserver/target/shqlserver-*.jar -cp /opt/shqlserver/configuration"]
