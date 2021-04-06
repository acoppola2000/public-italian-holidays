# Project Title

Public Italian Holidays - *an HR selection exercise* 

## IDE

- IntelliJ 2019.1.3

### Prerequisites

- A vanilla Tomcat 9 Instance
- JDK 8 - developed and tested with JDK 8 
- Maven 3 

#### Tomcat 9 and environment configuration 

Accordingly to application development best practice, `truststore` and `server keystore` should be configured and provided externally.
Configurable `applications.properties` to be provided, too; a template is project provided, see `/configuration/application.properties`.   
 
1. Please install the project provided `/certificates/server.jks` *keystore* in Tomcat 9  
2. Please copy project provided `/truststore/cacert` *file* in a preferred location (e.g. `C:\\applicationTruststores\\cacert`)    
3. Please copy project provided `/configuration/application.properties` *file* in a preferred location (e.g. `C:\\publicItalianHolidays\\application.properties`) 
4. Please update `application.properties -> truststore.path` *property* with value from line 2; if another truststore has been installed, change `application.properties -> truststore.pass` too.  
5. Please update `application.properties -> clientkeystore.path` *property* with the `server.jks` path from line 1; if another keystore has been installed, change `application.properties -> clientkeystore.pass` too. 

Remember to run Tomcat 9 with the following JVM option 
`-Dspring.config.location = <application.properties complete Path>`
e.g. 
`-Dspring.config.location = "C:\publicItalianHolidays\application.properties"`
adding double quotes for Windows (with blanks) paths   

otherwise, create an environmental varible named `CATALINA_OPTS`, e.g.
```
CATALINA_OPTS = -DapplicationPropertiesFilePath=file:///C:/publicItalianHolidays/application.properties
```


### Run the application


Connect to https://localhost:8443/publicItalianHolidays/

```
Basic Authentication credentials: 
user: annamaria
password: coppola
```

**Currently session is *not invalidated* after each `REST request/answer`, because this is not a requirement, i.e. is not required by the exercise.**


### Application Architecture

As public holidays days change rarely, in order to optimize application performance (and not to draw too much on [Nager.Date](https://date.nager.at/Api) service),
a local cache for holidays has been created. 
Cache is loaded during application startup and then periodically updated every five minutes (time interval can be made configurable). 
The Http client (used to query [Nager.Date](https://date.nager.at/Api) service) is rebuild every time (every five minutes), to overcome *connection lost* issues.
Cache content is made available for Rest Controller methods.
Inspired by a lean approach, a simple Http client is used.
Please consider other http clients for more sofisticated requirements.     

Concurrency issues are managed in a non blocking way, using references and iterators local to query methods, 
while each update rebuild the holiday list (manual concurrency tests done in IDE).
Basic Authentication security is granted by Spring Security framework.

Currently session is *not invalidated* after each `REST request/answer`, because this is not a requirement, i.e. is not required by the exercise.

### About dependencies minimization

Only essential Spring `standard packages` are referred in pom.xml. No further attempt has been done to reduce dependencies.
A better result can be obtained, but a deeper understanding of the whole Spring dependency management (particularly regarding thir `load on runtime` rules) is required.
Besides, the unbundling of `standard packages` can impact on maintainability of the application and on future developments.
So a strong motivation should act to force this kind of effort.     


### About [Nager.Date](https://date.nager.at/Api) Certificate validation

To verify the [Nager.Date](https://date.nager.at/Api) Certificate a truststore is needed.
One option could be to load the external JDK provided cacert file, for example `C:\Program Files\Java\jdk1.8.0_181\jre\\lib\security\cacerts`, but it works only for JSK 8 (JDK 9 cacert file is empty).
Only for the exercise purpouse, I made a copy of this cacert file and added it to the project.
File is referenced by a parametrized path value. 
If a new CA Authority should be born in future, and if next [Nager.Date](https://date.nager.at/Api) certificate shuold be issued by this new CA Authority, 
this cacert file should be updated.       

     
### Suggested improvements (not implemented) 

##### A) Define a strategy for REST-Clients when *Retrieve data errors* happen
As for REST-clients, choose between:
1. Shield REST-clients from *Retrieve data errors* (no advise for delay updateTime, return empty list, etc.) and leave the error notification to a side-channel (preferred, see B advice) 
2. Advice REST-clients about prolonged *Retrieve data errors*

In this example the first strategy was implemented.

##### B) Define a strategy for application monitoring 

To deal with *Retrieve data errors*, following actions should be taken   
1. Add a logging service for fatal errors
2. Implement a notification system (i.e. email or webhook) on filtered fatal errors
 

##### C) Make some values configurable 

Following values should be configurable:
- [Nager.Date](https://date.nager.at/Api) API `url`
- `Periodic update interval`
- HTTPUrlConnection `timeouts`
- `country code` (not only 'IT')

there params should be added to `application.properties` external file     


##### D) More unit and Integration tests to be added



## Running the tests

Tests are automatically run by Maven.

Tests are inspired by [Unit Testing](https://www.manning.com/books/unit-testing) (**Khorikov**) book guidelines.

To improve readability, [AssertJ]() library has been adopted.

## Deployment

An *automatic* Tomcat 9 configuration (i.e. `https connector` enabled) could be done only if Tomcat 9 was *mavenized*, but this was not allowed by exercise requirements:
>maven project that it produces a war file – it will be deployed into a vanilla Tomcat 9 instance

`Https connector` should be enable in Tomcat 9, that's to say, in file `server.xml`:
```
    ...
    <Connector port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol"
               maxThreads="150" SSLEnabled="true">
        <SSLHostConfig>
            <Certificate certificateKeystoreFile="conf/server.jks"
					     certificateKeystorePassword="miapassword"
						 certificateKeyAlias="server"
                         type="RSA" />
        </SSLHostConfig>
    </Connector>
    ... 
```


## Built With

* [Spring](http://www.dropwizard.io/1.0.2/docs/) - Spring
* [Jackson](https://github.com/FasterXML/jackson/) - Jackson
* [Maven](https://maven.apache.org/) - Dependency Management
* [JUnit 5](https://junit.org/junit5) - Testing framework

## Authors

* **Annamaria Coppola** - *Initial work* - [PublicItalianHolidays](https://github.com/acoppola2000/public-italian-holidays)


## License

This project is licensed under the MIT License

## Acknowledgments

* [Baeldung](https://www.baeldung.com/) - helpful website for Spring and more
* [Granella](https://gist.github.com/granella/01ba0944865d99227cf080e97f4b3cb6) - CA and Self Signed Certificates
* [PurpleBooth](https://gist.github.com) - A template to make good README.md
 
## Create the SSL Certificate issued by a Self Signed CA, and the CA itself 

This certificate is used for ssl connections in `PublicItalianHolidays Server` and `client` connection towards [Nager.Date](https://date.nager.at/Api) API.

[KeyTool](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/keytool.html) was used to generate keystore, keys, X.509 certificate chains, etc.
[Keystore Explorer](https://keystore-explorer.org/index.html) was used to verify keystore. 

##### Note for `-ext` option
>`-ext {name{:critical} {=value}}` denotes an X.509 certificate extension. 
The option can be used in -genkeypair and -gencert to embed extensions into the certificate generated, 
or in -certreq to show what extensions are requested in the certificate request. 
The option can appear multiple times. 
The name argument can be a supported extension name (see Named Extensions) or an arbitrary OID number. 
The value argument, when provided, denotes the argument for the extension. 
When value is omitted, that means that the default value of the extension or the extension requires no argument. 
The :critical modifier, when provided, means the extension's isCritical attribute is true; otherwise, it is false. 
You can use :c in place of :critical.


#### 1. Generate private keys for root, CA and server
>`-genkeypair` generates a key pair (a public key and associated private key). 
Wraps the public key into an X.509 v3 self-signed certificate, which is stored as a single-element certificate chain. 
This certificate chain and the private key are stored in a new keystore entry identified by alias.

>`-ext bc:c` stands for `X.509 extension`
```
keytool -genkeypair -alias root -dname "CN=Annamaria Coppola,OU=Test Public Italian Holidays,O=Root self unit,L=LATINA,ST=LT,C=IT" -validity 365 -keyalg RSA -keysize 2048 -ext bc:c -keystore root.jks -keypass miapassword -storepass miapassword
keytool -genkeypair -alias ca -dname "CN=Annamaria Coppola,OU=Test Public Italian Holidays,O=CA self unit,L=LATINA,ST=LT,C=IT" -validity 365 -keyalg RSA -keysize 2048 -ext bc:c -keystore ca.jks -keypass miapassword -storepass miapassword
keytool -genkeypair -alias server -dname "CN=Annamaria Coppola,OU=Test Public Italian Holidays,O=Server self unit,L=LATINA,ST=LT,C=IT" -validity 365 -keyalg RSA -keysize 2048 -keystore server.jks -keypass miapassword -storepass miapassword
```

#### 2. Generate certificate for root
>`-exportcert` reads from the keystore the certificate associated with alias and stores it in the cert_file file.
When no file is specified, the certificate is output to stdout. The certificate is by default output in binary encoding. 
If the -rfc option is specified, then the output in the printable encoding format defined by the Internet RFC 1421 Certificate Encoding Standard.
```
keytool -exportcert -rfc -keystore root.jks -alias root -storepass miapassword > root.pem
```

#### 3. Generate a Certificate Signing Request for CA 
>`-certreq` generates a Certificate Signing Request (CSR) using the PKCS #10 format.
A Certificate Signing Request is intended to be sent to a certificate authority (CA). 
The CA authenticates the certificate requestor (usually off-line) and will return a certificate or certificate chain, 
used to replace the existing certificate chain (which initially consists of a self-signed certificate) in the keystore.
The private key associated with alias is used to create the PKCS #10 certificate request. 
The sigalg value specifies the algorithm that should be used to sign the Certificate Signing Request.
The Certificate Signing Request is stored in the file specified. If no file is specified, then the CSR is output to stdout.
```
keytool -certreq -keystore ca.jks -alias ca -storepass miapassword -file ca_req.p10
```
#### 4. Generate a Certificate for CA, signed by root
>`-gencert` generates a certificate as a response to a certificate request file (which can be created by the keytool -certreq command). 
The command reads the request from infile (if omitted, from the standard input), signs it using alias's private key, 
and outputs the X.509 certificate into outfile (if omitted, to the standard output). 
When -rfc is specified, the output format is Base64-encoded PEM; otherwise, a binary DER is created.
The ext value shows what X.509 extensions will be embedded in the certificate. 
```
keytool -gencert -keystore root.jks -storepass miapassword -alias root -ext bc=0 -rfc -infile ca_req.p10 -outfile ca.pem
```

#### 5. Import CA Certificate chain into ca.jks
>`-importcert` reads the certificate or certificate chain 
(where the latter is supplied in a PKCS#7 formatted reply or a sequence of X.509 certificates) from the file cert_file, 
and stores it in the keystore entry identified by alias. 
If no file is specified, then the certificate or certificate chain is read from stdin.
The keytool command can import X.509 v1, v2, and v3 certificates, and PKCS#7 formatted certificate chains consisting of certificates of that type. The data to be imported must be provided either in binary encoding format or in printable encoding format (also known as Base64 encoding) as defined by the Internet RFC 1421 standard. In the latter case, the encoding must be bounded at the beginning by a string that starts with -----BEGIN, and bounded at the end by a string that starts with -----END.
You import a certificate for two reasons: To add it to the list of trusted certificates, and to import a certificate reply received from a certificate authority (CA) as the result of submitting a Certificate Signing Request to that CA (see the -certreq option in Commands).
Which type of import is intended is indicated by the value of the -alias option. If the alias does not point to a key entry, then the keytool command assumes you are adding a trusted certificate entry. In this case, the alias should not already exist in the keystore. If the alias does already exist, then the keytool command outputs an error because there is already a trusted certificate for that alias, and does not import the certificate. If the alias points to a key entry, then the keytool command assumes you are importing a certificate reply.
```
keytool -importcert -keystore ca.jks -storepass miapassword -alias root -trustcacerts -noprompt -file root.pem  
keytool -importcert -keystore ca.jks -storepass miapassword -alias ca -file ca.pem 
```

#### 6. Generate a Certificate Signing Request for Server, to be signed by fake CA
```
keytool -certreq -keystore server.jks -alias server -storepass miapassword -file server_req.p10
```

#### 7. Generate a certificate for Server, signed by CA (root -> CA -> server)
**`-ext eku=sa,ca`** stands for`ExtendedKeyUsage for serverAuth, clientAuth`

**`-ext ku:c=dig,keyEnc`** stands for `KeyUsage for digitalSignature, keyEncipherment`
```
keytool -gencert -keystore ca.jks -storepass miapassword -alias ca -ext ku:c=dig,keyEnc -ext eku=sa,ca -rfc -infile server_req.p10 -outfile server.pem
```
#### 8. Import Server Certificate chain into server.jks
```
keytool -importcert -keystore server.jks -storepass miapassword -alias root -trustcacerts -noprompt -file root.pem   
keytool -importcert -keystore server.jks -storepass miapassword -alias ca -file ca.pem 
keytool -importcert -keystore server.jks -storepass miapassword -alias server -file server.pem
```
