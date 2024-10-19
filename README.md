# Student and Faculty Directory Management System
---

This Java application is designed to securely manage directory information for both students and faculty members. The system includes features for securely handling passwords, differentiating roles, and providing administrative privileges through an admin key for critical operations.

## Features

### 1. Student and Faculty Information Management:
- Stores and manages directory information such as names, email addresses, and user roles.
- Differentiates between students and faculty members, providing appropriate access and permissions based on their roles.

### 2. Secure Password Handling:
- Passwords are securely hashed using the **SHA-256 cryptographic hashing algorithm**.
- Users authenticate by providing their user ID and password, ensuring only authorized access to their accounts.

### 3. Admin Key:
- The system incorporates an admin key, granting elevated permissions for administrators.
- The admin can perform critical operations such as managing user data, updating the directory, or maintaining system integrity.

## Installation
To run this project, you will need the following:
1. **Java Development Kit (JDK)** installed on your machine.
2. A code editor such as **Eclipse** or **IntelliJ IDEA**.
3. The project source files, which can be cloned or downloaded from this repository.

### Steps to run:
1. Clone the repository to your local machine:
   ```
   git clone https://github.com/yourusername/your-repo-name.git
   ```
2. Open the project in your preferred Java IDE.
3. Compile and run the application.

## Usage
Once the application is running, users will be able to:
1. **Login** with their user ID and hashed password.
2. **Access directory information** based on their roles (students or faculty).
3. **Admins** can use the admin key to perform elevated operations, such as updating user details, resetting passwords, or managing the directory.

## Requirements
- **Java 8 or later**
- **IDE** for running the Java application
- **Libraries**: Built-in Java libraries for cryptographic hashing and basic I/O operations.

