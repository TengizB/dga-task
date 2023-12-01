# JAVA-DGA-TASK
გავაკეთოთ მარტივი პროექტი contact book: აპლიკაცია სადაც მომხმარებელი
ლოგინდება (ანუ აქვს ავტორიზაციის მხარდაჭერა) და შეჰყავს თავისი სასურველი
საკოტაქტო ინფორმაცია (პიროვნება, მობილური, ემაილი, მისამართი). დალოგინებულ მომხმარებელს ხვდება ცხრილი თავის დამატებული საკონტაქტო
ინფორმაციის paging-ითა და ძებნით და შეუძლია მოძებნოს, დაარედაქტიროს ან
წაშალოს ჩანაწერი. სასურველი იქნება postman ან swagger იყოს მომზადებული.

# PROJECT REQUIREMENTS
  - Java version 17
  - Gradle-8.3
  - Postgresql (Database must exist on project execution for test data to be generated of disable this feature in             application.properties spring.jpa.defer-datasource-initialization=false)
    
# PROJECT FEATURES
  - JWT authentication
  - Spring security by roles
  - Unit tests for all controllers
  - Swagger (http://localhost:8080/swagger-ui/index.html)
    - Authentication in swagger required for most services
  - CRUD operation for Users
  - CRUD operations for ContactInfo
  - Search with paging by User firstname, lastname and contact info using criteria builder
  - H2 database for tests
  - Test data and schema (data.sql, schema.sql)

# CONFIGURATION
  - Default admin user:
    - Username(email): tbregvadze@mail.com
    - Password: 12345
  - Application Profiles (Default dev):
    - dev: For debugging
    - test: For running test (is not required to be changed manually)

