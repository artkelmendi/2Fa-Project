# Two-Factor Authentication (2FA) Using Email in Android

This project demonstrates a simple Android application that implements Two-Factor Authentication (2FA) using email. The app allows users to log in using their username and password, validates the credentials, and sends a One-Time Password (OTP) to the registered email address for additional security.

## Features

- **User Login**: Users can log in with their username and password.
- **Database Integration**: Validates user credentials using a local SQLite database.
- **OTP Generation**: A 6-digit OTP is generated randomly.
- **Email Delivery**: Sends the OTP to the user's registered email using Gmail's SMTP service.
- **Asynchronous OTP Sending**: Ensures email sending is handled in the background using `AsyncTask`.
- **Error Handling**: Provides error messages and logs detailed information for debugging.
- **Modular Code Design**: Separate classes handle different responsibilities (e.g., email sending, database operations).

## Code Overview

### 1. **MainActivity**
- Handles the user login process.
- Validates user credentials through the `DatabaseHelper` class.
- Generates a random 6-digit OTP for authenticated users.
- Uses the `SendOtpTask` class (an `AsyncTask`) to send the OTP to the registered email.

### 2. **EmailActivity2FA**
- Responsible for sending emails using Gmail's SMTP server.
- Configured with:
  - **SMTP Host**: `smtp.gmail.com`
  - **Port**: `587` (with STARTTLS enabled).
  - **Email Authentication**: Uses the app-specific password from Gmail.
- Prepares and sends an email containing the OTP to the recipient.

### 3. **DatabaseHelper**
- Provides methods to validate user credentials and retrieve user data (like email) from a local SQLite database.

### 4. **OTP Verification**
- After receiving the OTP via email, the user will be prompted to verify it (this part can be implemented in a `VerifyActivity`).

## Requirements

### Software
- Android Studio (latest version recommended)
- Android SDK (API Level 21 or above)

### Gmail Configuration
1. If **2-Factor Authentication (2FA)** is enabled for your Gmail account:
   - Generate an [App Password](https://support.google.com/accounts/answer/185833).
   - Use the generated app password in `EmailActivity2FA`.

2. If **2-Factor Authentication is not enabled**:
   - Enable "Less Secure App Access" in your [Google Account Security Settings](https://myaccount.google.com/security).

## How to Set Up

### 1. Clone the Repository
```bash
git clone https://github.com/artkelmendi/2Fa-Project.git
cd 2Fa-Project
