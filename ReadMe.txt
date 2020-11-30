AES

This project will take any string as key and uses it to encrypt/decrypt the provided text. It is CLI based and instructions on operation mode as well will be provided once
the JAR is executed.


JAR Creation
From terminal, navigate to the project directory and execute the following command

./gradlew clean build

This would generate a jar in the following location
<PROJECT_ROOt>/build/libs/AES_Encryption-1.0-SNAPSHOT.jar

To execute the jar:
java -jar <PROJECT_ROOt>/build/libs/AES_Encryption-1.0-SNAPSHOT.jar