# Bankee

Bankee is a mobile banking application that allows users to integrate and manage multiple bank accounts and credit cards in one user-friendly platform. The app ensures secure transactions and provides real-time notifications for account activities. This README file provides an overview of the project, setup instructions, and details about the various features and components.

#### Note: Adding card and money transfer is a demo, done with help of firebase as it is difficult to reach any real bank API's 

## Table of Contents

- [Features](#features)
- [Screenshots](#screenshots)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Features

- **User Authentication**: Register and login securely.
- **Account Integration**: Link and manage multiple bank accounts and credit cards.
- **Transaction Management**: View transaction history and details.
- **Notifications**: Receive real-time notifications for transactions and account activities.
- **Balance Tracking**: Check balances of all linked accounts and credit cards.
- **User Interface**: Intuitive and user-friendly design.
- **Settings**: Manage account settings and preferences.

## Screenshots
<p>
<img src="https://github.com/pronob-kumar-mondol/Bankee/blob/main/screenshots/ss-1.jpeg" alt="Screenshot 1">
<img src="https://github.com/pronob-kumar-mondol/Bankee/blob/main/screenshots/ss-2.jpeg" alt="Screenshot 2">
</p>

## Installation

### Prerequisites

- Android Studio
- A Firebase project set up with Firestore and Authentication enabled.

### Setup

1. **Clone the repository**:
    ```bash
    git clone https://github.com/pronob-kumar-mondol/Bankee.git
    cd Bankee
    ```

2. **Open in Android Studio**:
    - Open Android Studio and select `Open an existing project`.
    - Navigate to the cloned repository and open it.

3. **Firebase Configuration**:
    - Add your `google-services.json` file to the `app` directory.
    - Ensure Firestore and Authentication are enabled in your Firebase project.

4. **Dependencies**:
    - The required dependencies are listed in the `build.gradle` file. Sync the project with Gradle files to download the dependencies.

## Usage

### User Authentication

- **Register**: New users can register with their email and password.
- **Login**: Registered users can log in with their credentials.
- **Forgot Password**: Registered users can request to change their password with their credentials.

### Account Integration

- **Link Accounts:** Users can link multiple bank accounts and credit cards.
- **Unlink Accounts:** Users can unlink any previously linked accounts or cards.

### Transaction Management

- **View Transactions:** Users can view their transaction history and details for all linked accounts.
- **Filter Transactions:** Filter transactions by date, type, or account.


### Balance Tracking

- **View Balances:** Check balances for all linked bank accounts and credit cards.
- **Refresh Balances:** Manually refresh to update account balances.

### Settings

- **Manage Profile:** Update personal information and account settings.


## Contributing

I appreciate your interest in contributing to my project. Here's how you can get started:

1. **Fork** the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. **Commit** your changes (`git commit -am 'Add new feature'`).
5. **Push** to the branch (`git push origin feature-branch`).
6. Create a **Pull Request**.

Please ensure your code adheres to the coding standards and includes appropriate tests.

Thank you for helping make Bankee even better!

## License

This project is licensed under the MIT License. See the [LICENSE](https://opensource.org/license/mit) file for details.
