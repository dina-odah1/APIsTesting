# MobiquityAPIsTesting

## Overview:
This project makes an initial Skelton for APIs testing framework using rest assured as the core library and Java as programming language.
The project tries to follow best coding practices such as **SOLID**, **Dry**, **KISS**, etc to the possible extent. Reusable methods were implemented 
to be used throughout the project to avoid redundancy, duplication and to reduce the effort required for maintaining and updating the code 
in future. Annotations are also used across the implemented tests along with BDD Test cases to enhance the readability of the code.

## Main technologies used:
- Rest Assured.
- Maven
- TestNG
- IntelliJ as an IDE 
- Github.
- Source Tree.
- Allure.
- CircleCI

## Scenarios Covered:
#### Main flow:
The test coverage so far includes a main flow that involves 3 main APIs (Users, Posts and Comments) to search for a specific username, find the posts made by that user along with the comments made on each post. And finally verify whether the email address linked to each comment of the is valid.

#### Additional flows around the flow covered:
- POST a new post with the same user and confirm the post is actually posted.
- Updated an existing post the user has previously made and confirm it is actually updated.
- Delete a post the user made and confirm whether it is actually deleted.

#### Negative Scenarios Covered:
- Query Posts API with a post that doesn't exist.
- Query Users API with invalid user.
- Query Users API with invalid parameter.
- Post a post with an empty body.

**Note**: For more information, APIs documentation can be found here [https://jsonplaceholder.typicode.com/guide/
](https://docs.qameta.io/allure/#_installing_a_commandline)
## Bugs Found:
- Newly posted posts are not really added to the list of user's posts.
- Updated posts are not actually being updated.
- Deleted posts are not really being deleted.
- Query an API with invalid request parameter returns the whole response.
- Posting a post with empty body is allowed.

## Project Structure:
#### Main Setup folders and paths:
- `/src/Main` has ison.model that defines objects of types users, comments and posts. It also has a package for a test listener and logger classes and finally an xml file that defines the project listeners and classes to be excited.

- `/src/test` has the main tests along with additional negative tests under main.apis.tests package. Apis.calls package defines the reusable methods across the three different APIs. Finally global variables.properties resources file has the global variables used across the project and the properties consumption is configured under utils.configuration package.

## In order to run the project:
- Download the project or Clone it from GitHub through this repository link [https://github.com/dina-student/APIsFramework.git](https://docs.qameta.io/allure/#_installing_a_commandline)

- Make sure you have allure installed on your machine, if not, it can be download through [this link](https://docs.qameta.io/allure/#_installing_a_commandline).

- To initiate a clean run, open your terminal and make sure you change the directory to where the project was downloaded.

- Execute this command `mvn clean test` on your terminal.

- To lunch the allure report, execute this command allure `serve allure-results on the terminal`

## Demo: 
![This is an image](https://github.com/dina-student/MobiquityAPIsTesting/blob/master/TestSuits.png)
![This is an image](https://github.com/dina-student/MobiquityAPIsTesting/blob/master/Coverage.png)
 
