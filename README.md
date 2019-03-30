Description:The application developed with spring-boot as backend and front-end using angularJS.
I have used token base authentication using spring-security-jwt for make the system scalable and secure.Please follow 
the instruction if needed.

1.<b>git clone https://github.com/shofiq4744/PracticalTest.git</b> or download as zip</br>
2.Navigate project folder (cd practicaltest)</br>
3.start mysql and create a database name practicaltest</br>
3.<b>gradlew clean build</b> you will see message Build success</br>
4. run the application using <b>gradlew bootRun</b> command/or use any ide </br>
5.navigate http://localhost:8090 you will see a login screen </br>
default user and password are bellow you also find it on properties</br>
username:admin@admin.com</br>
password:admin</br>
6.For REST API to get employee list (GET /api/v1/employee) i have made it 
public of easy access. if you want authenticate please
remove /api/v1/employee on security config antMatchers then 
you must pass AccesToken on Authorization header.You will get AccesToken
on Post call http://localhost:8090/auth using username and password.
