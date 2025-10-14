# FairThread
OPSEC project

🧵 FairThread Android App 📖 Overview
FairThread is a Kotlin-based Android application designed to create a smooth and secure user experience for an online clothing or community platform. The app focuses on user authentication, API integration, and form validation, built using Jetpack Compose and Firebase Authentication.

This project is part of the OPSC Part 1 module and demonstrates Android development best practices, RESTful API integration, and validation logic within a modern Kotlin framework.

🚀 Features

🔐 User Authentication using Firebase Auth
Email/Password login
Registration and password reset
Firebase UID tracking for session management

✅ Form Validation
Validates all signup, login, and forgot-password inputs before Firebase submission
Prevents empty or invalid email formats and weak passwords
Integration ready for an external email validation API

🌐 External API Integration
Email verification via a third-party Email Validation API (to confirm real/active addresses before Firebase sign-up)

🧭 Jetpack Compose Navigation
Navigation handled through a NavGraph using a NavController
Smooth transitions between Register, Login, Forgot Password, and Main/Home screens

🎨 Modern UI
Built with Jetpack Compose and Material 3 components
Clean, minimalistic theme defined in FairThreadTheme.kt


