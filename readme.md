# Online Exam System

## Project Overview
The **Online Exam System** is a Java-based desktop application designed to facilitate online examinations over a local network. It utilizes **JavaFX** for UI, **Threading** for handling multiple clients, **Networking** for communication, and **File IO** for file uploads and downloads.

### Tech Stack
- **JavaFX** – UI Development
- **Threading** – Concurrency for multiple clients
- **Networking** – Server-client communication
- **File IO** – Upload and download of exam-related files

## Features
### User Roles
1. **Teacher**
    - Can log in using credentials:
        - ID: `teacher`, Password: `789`
    - Upload questions in `.txt` format.
    - Set exam timers.
    - Download all submitted answer scripts as a `.zip` file.

2. **Student**
    - Can log in using credentials:
        - ID: `win`, Password: `123`
        - ID: `nayem`, Password: `456`
    - Download the exam question file.
    - View the answer submission deadline.
    - Upload answer scripts in `.txt` format.

### Additional Features
- Secure login system for both teachers and students.
- Server-client architecture for efficient communication.
- Students can only submit their answers before the deadline.
- Both teachers and students can log out after the exam.

[//]: # (## Installation & Setup)

[//]: # (1. Clone the repository:)

[//]: # (   ```sh)

[//]: # (   git clone https://github.com/your-repo/online-exam-system.git)

[//]: # (   cd online-exam-system)

[//]: # (   ```)

[//]: # (2. Compile and run the server:)

[//]: # (   ```sh)

[//]: # (   javac Server.java)

[//]: # (   java Server)

[//]: # (   ```)

[//]: # (3. Compile and run the client:)

[//]: # (   ```sh)

[//]: # (   javac Client.java)

[//]: # (   java Client)

[//]: # (   ```)

## License
This project is licensed under the MIT License.

## Contributors
Contributions are welcome! Feel free to submit a pull reque