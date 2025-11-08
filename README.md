FairThread Android Application
OPSC6312 – Open Source Coding (Intermediate) Final POE Submission

Overview
FairThread is a Kotlin-based Android app developed for the OPSC6312 module. It is designed for secure user interaction in a clothing or community platform. The app uses Firebase for authentication, data storage, messaging, and crash reporting.

Core Features
User Authentication Users can register, log in, reset passwords, and manage their settings. All authentication is handled through Firebase Auth, with encrypted password handling and UID tracking for session control.

Form Validation The app includes full validation for login, signup, and password reset. It checks for empty fields, invalid email formats, and weak passwords before submission.
Email Verification with AbstractAPI The AbstractAPI Email Validator is integrated to confirm that email addresses are real and active before registration. This improves data quality and prevents fake signups.
Jetpack Compose Navigation Navigation is handled using a NavGraph and NavController. Screens include Register, Login, Forgot Password, and Home. Transitions are smooth and intuitive.

Modern UI The interface is built with Jetpack Compose and Material 3. A custom FairThreadTheme.kt defines a clean, minimal design system.
Real-Time Notifications Firebase Cloud Messaging (FCM) is used to deliver real-time alerts and updates to users.
Offline Sync The app supports offline interaction. Data is automatically synced with Firebase Firestore when the device reconnects.

Multilingual Support FairThread supports English, isiZulu, and Afrikaans using localized strings.xml resources.
Firebase Firestore & Storage Firestore is used for storing user data. Firebase Storage handles profile image uploads and other media.

Development & Testing
Version Control The project is version-controlled using Git and hosted on GitHub. All commits are tracked and tagged.

CI/CD Pipeline GitHub Actions is configured to run automated tests and build the app on every push.
Testing The app includes unit tests for core logic and UI tests for navigation and validation. Tests are executed via GitHub Actions to ensure cross-environment reliability.

Deployment
Signed APK The final build is signed and ready for Play Store submission.

Play Store Preparation All required assets, screenshots, and metadata have been prepared for publishing.
Video Demonstration A full walkthrough video with voice-over is included in the repository. Watch the demo Video Showcase: https://www.youtube.com/watch?v=HYwaT-nAfSw

Release Notes
Integrated Firebase Cloud Messaging for real-time alerts

Added offline sync using Firestore’s local persistence
Implemented isiZulu and Afrikaans language support
Hardened Gradle configuration and resolved all dependency issues

Final UI polish and accessibility improvements
GitHub Actions pipeline added for automated testing and builds

https://github.com/Boomch1ka/FairThread

