# evolutionary-mastermind
**Author:** Hubert Mazur
### How to build the project using Maven
In order to build executable run:
```
mvn clean package -Dmaven.test.skip
```
This will produce two files: `Mastermind.jar` and `Mastermind.exe` which is a wrapper for `.jar` file.
In order to run `.exe` file the `JAVA_HOME` variable must be correctly defined.

Additionally, if one wants to sing the executable, they should provide a JKS and modify values for properties:
`keystore.path`, `keystore.alias` and `keystore.passwd`.
Then run:
```
mvn clean package verify -Dmaven.test.skip
```
### Requirements
In order to build and/or run the application one has to have:
+ Java 18 SDK