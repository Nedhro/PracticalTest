1.git clone https://github.com/shofiq4744/PracticalTest.git or download as zip
2.Navigate project folder
3.start mysql 
4.create a database name practicaltest
3.gradlew clean build
you will see message Build success
4. run the application using gradlew bootRun command/or use any ide 
5.navigate http://localhost:8090 you will see a login screen 
default user and password are bellow
username:admin@admin.com
password:admin
6.For REST API to get employee list (GET /api/v1/employee) i made it 
public of easy acces if you want autenticate please
remove /api/v1/employee on security config antMatchers then 
you must pass AccesToken on Authorization header.You will get AccesToken
on Post call http://localhost:8090/auth using username and password.

Description:This a spring boot web application backend develop using spring boot and front-end using angularjS.
I have use token base autentication using spring-security-jwt for make the system scalable and secure.