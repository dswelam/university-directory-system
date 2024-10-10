# Student and Faculty Directory Management System
---
This Java application manages directory information for students and faculty members, including hashed passwords for secure authentication and an admin key for privileged operations.

## Features

- **Student and Faculty Information Management**: 
  - Stores directory information for students and faculty, such as names, email addresses, and roles.
  - The system differentiates between students and faculty based on roles and offers different permissions.

- **Secure Password Handling**:
  - Passwords are securely hashed using a cryptographic hashing algorithm (SHA-256 ).
  - Users authenticate with their user ID and password, ensuring secure login to their respective accounts.

- **Admin Key**:
  - The system includes an admin key, which provides elevated permissions for administrative tasks, such as managing user data, updating the directory, or performing critical operations.
  
