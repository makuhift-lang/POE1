# POE1 Chat App - Registration & Login System

A console-based **Java application** built with **Apache Maven** and **NetBeans IDE** that handles structured user registration, custom authentication rule validation, and simulated profile logins.

## Features

The system forces strict input formatting requirements before allowing a user to register or log in:

* **Username Validation** - Must contain an underscore (`_`) and be 5 characters or fewer.
* **Password Complexity Validation** - Must be at least 8 characters long, containing at least one uppercase letter, one digit, and one special character.
* **Cell Phone Validation** - Uses regular expressions to ensure a valid international format starting with `+27` followed by up to 10 digits.
* **Login Authentication Pipeline** - Simulates secure verification matching active properties against user input.

## Technologies Used

* **Java** - Object-oriented application logic.
* **Apache Maven** - Dependency management and project compilation.
* **NetBeans IDE** - Development environment.

## Application Architecture

The project consists of two core files structured under the `com.mycompany.poe1` package layout:

* `Main.java` - Orchestrates console workflow, displays system UI layouts, manages state input loops (`Scanner`), and maps execution pipelines.
* `Login.java` - Holds properties for user records, constructor definitions, validation frameworks (`checkUserName`, `checkPasswordComplexity`, `checkCellPhoneNumber`), and authentication status returns.

## Getting Started

### Prerequisites
Make sure you have the following installed on your system:
* **Java Development Kit (JDK)** version 8 or higher.
* **NetBeans IDE** (with Maven capabilities activated).

### Installation & Execution Steps

1. **Clone the repository** to your local machine:
   ```bash
   git clone <your-repository-url>
   ```
2. Open **NetBeans IDE**.
3. Go to **File** -> **Open Project**.
4. Select the project root folder (the folder containing the `pom.xml` file) and click **Open**.
5. Right-click the project name in the left panel and select **Clean and Build**.
6. Right-click the project name again and select **Run** to execute the system console loops.
