
# TechDon

A full-stack webapp made as part of my BSc CS degree in 2021/2022.

The assignment was to create a website that addresses one or more of UN's SDGs.

Large fractions of the project, which were non-functional, or entirely written not by me, were retroactivly removed. (using git-filter-repo)

The code is licensed under GNU v3.0.

![image](https://user-images.githubusercontent.com/73062879/155819954-c5e89ee2-647b-44cb-a412-aa33dbf19d70.png)

---

### Group Members

Name | Student ID | Feature Contribution |
-----|------------|--------------|
Philipp ***** | ******* | Back-end: REST API documentation, DB interface, queue system, all student / donor specific conrollers & endpoints, majority of system and object design, API unit testing, component testing; Front-end: preferences page, view & remove listed devices, view & accept & decline offered devices, back-end interfaces & integration |
******* | ******* | ******* |

---

## Project Idea

### UN Sustainable development goal number: 4 (education) & 10 (reduced inequalities)

### Motivation: To help as many students get a better quality of education

### Features:
- Students, organizations, and individuals can create an account
- Students can enter required personal information & select which electronics they need
- Organizations and individuals can list technological items for donation
- A queue system to allow a fair wait time for laptops on a first come first serve basis
- Students are informed per email once a device has been offered to them
- A person can only apply once and the app will tell us if they received their device or not


## Technologies

1. Back-end: Spring
2. Front-end: ReactJS
3. Database: MongoDB
4. Testing: Junit, Manual, Postman

## Deployment
- Create a MongoDB deployment, local or in the cloud
- Install a Java JDK with Maven support and NodeJS
- Build and run the back-end, which will create a file at `~/.techdon/pref.json`
- Fill in valid MongoDB and SMTP Server credentials
- Start the server again, which should now succeed
- Go to the `front-end` folder in a terminal and run `npm install`
- You can now run `npm start`, which should launch the front-end on `localhost:3000`
- If you chose to deloy the instance, route port 3000 to 8000 and attach an A record to your domain's DNS
