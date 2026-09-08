<h1 align="center">TeethKids</h1>

<p align="center"><em>Two mobile apps over a single Firebase backend: one for the person requesting emergency dental help, another for the dentist who answers the call.</em></p>

<p align="center">
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-1.8.0-7F52FF?logo=kotlin&logoColor=white">
  <img alt="Android" src="https://img.shields.io/badge/Android-SDK%2033-3DDC84?logo=android&logoColor=white">
  <img alt="Flutter" src="https://img.shields.io/badge/Flutter-Dart%202.19-02569B?logo=flutter&logoColor=white">
  <img alt="Firebase" src="https://img.shields.io/badge/Firebase-BoM%2032.0.0-FFCA28?logo=firebase&logoColor=black">
  <img alt="TypeScript" src="https://img.shields.io/badge/Cloud%20Functions-TypeScript-3178C6?logo=typescript&logoColor=white">
</p>

<p align="center"><a href="README.md">Português</a> · <b>English</b></p>

---

## 📸 Preview

The screenshots below are from the **Dentista** app, captured on an Android 33 emulator with the data layer replaced by demo values — the original Firebase project is no longer online. The **Socorrista** app builds, but has no screenshots in this version of the README.

### Dentista

| Sign in | Profile | Emergencies list |
| :---: | :---: | :---: |
| <img src="docs/screenshots/01-dentista-login.png" width="240" alt="Sign-in screen"> | <img src="docs/screenshots/02-dentista-perfil.png" width="240" alt="Dentist profile"> | <img src="docs/screenshots/03-dentista-lista-emergencias.png" width="240" alt="Emergencies list"> |

| Emergency detail | Emergency in progress | Reputation |
| :---: | :---: | :---: |
| <img src="docs/screenshots/04-dentista-detalhe-emergencia.png" width="240" alt="Emergency detail"> | <img src="docs/screenshots/05-dentista-emergencia-andamento.png" width="240" alt="Emergency in progress"> | <img src="docs/screenshots/06-dentista-reputacao.png" width="240" alt="Dentist reputation"> |

| Reviews received |
| :---: |
| <img src="docs/screenshots/07-dentista-feedbacks.png" width="240" alt="Reviews list"> |

### Socorrista

No screenshots in this version. The app's code lives in `Socorrista/`, and the implemented screens are listed under [Architecture](#️-architecture).

---

## 📌 Context and Motivation

TeethKids was a project for the **Mobile Application Development** course, built between April and June 2023. The subject — emergency dental care for children — came ready-made in the assignment brief; the product itself was not the point of the work.

What was at stake was technical command of the platform. The brief called for **two distinct apps talking to each other through the same backend**, and that is where the learning was: modelling a flow in which an action in one app has to reach the other, on another device, without the two knowing each other directly. That meant working with Firestore as a shared source of truth, Cloud Messaging to notify the opposite side, and Cloud Functions to concentrate the rules neither client should run on its own.

On the Android side, the focus was the structure of a native Kotlin app: a single Activity with the Navigation Component, navigation between Fragments, ViewBinding, RecyclerView with custom adapters, and integration with CameraX and Google Maps.

I am recording here what the repository actually is, including the organisational decisions that did not age well — the Socorrista folder structure, described further down, is one of them.

---

## 🏗️ Architecture

The two apps are independent clients of the same Firebase project. Neither calls the other: communication happens through Firestore and the Cloud Functions, which fire FCM notifications to the app on the opposite side.

```
   ┌──────────────────────────┐         ┌──────────────────────────┐
   │        Socorrista        │         │         Dentista         │
   │      Flutter / Dart      │         │     Android / Kotlin     │
   │                          │         │                          │
   │  · opens the call        │         │  · lists the emergencies │
   │  · sends injury photos   │         │  · accepts or declines   │
   │  · follows up and rates  │         │  · shares their location │
   └────────────┬─────────────┘         └────────────┬─────────────┘
                │                                    │
                └────────────────┬───────────────────┘
                                 │
              ┌──────────────────▼───────────────────┐
              │               Firebase               │
              │  Auth · Firestore · Storage · FCM    │
              ├──────────────────────────────────────┤
              │      Cloud Functions (TypeScript)    │
              │      region southamerica-east1       │
              │                                      │
              │  emergencyListener · setActions      │
              │  sendMessage · sendMessageToDentist  │
              │  sendRTS · sendRating · sendLocation │
              │  setUser · setToken · setAddress     │
              └──────────────────────────────────────┘
```

**Main flow.** The requester opens a call with photos and details of the accident, which go to Storage and to the `emergencies` collection. A Cloud Function watches that collection and notifies available dentists over FCM. In the Dentista app the call shows up in the emergencies list; on acceptance, the action is recorded by the `setActions` function and the requester is notified. During the appointment the dentist shares their location, and once the call is concluded a notification opens the rating screen in the requester's app. Those ratings feed the reputation shown on the dentist's profile.

**Dentista** — native Gradle project, a single Activity (`SignInActivity`) hosting a `NavHostFragment`. The screens are Fragments coordinated by a single `nav_graph.xml`:

- Access: `SignInFragment`, `SignUpFragment`, `ResumeFragment`
- Emergencies: `EmergenciesListFragment`, `EmergencyDetailFragment`, `EmergencyInProgressFragment`
- Profile and reputation: `ProfileFragment`, `EditProfileFragment`, `ReputationProfileFragment`, `FeedbacksListFragment`
- Addresses: `AddressesListSignUpFragment`, `AddressesListProfileFragment`, `AddressRegisterSignUpFragment`, `AddressRegisterProfileFragment`, `EditAddressSignUpFragment`, `EditAddressProfileFragment`
- Device features: `MapsFragment`, `CameraPreviewFragment`

The domain lives in `model/` (`Emergency`, `Address`, `Action`, `Dentist`, `Feedback`), with `AddressesDao` for addresses, `ImageHelper` for photo handling, and `DefaultMessageService` extending `FirebaseMessagingService` to receive notifications.

**Socorrista** — Flutter app. The interface is concentrated in `lib/main.dart`, with the screens `PermissionScreen`, `AbrirChamado`, `Identificacao`, `MyCustomForm`, `TakePictureScreen`, `EmergenciesList`, `DetalhesScreen` and `Avaliacao`.

**functions/** — Firebase project configuration (`firebase.json`, `firestore.rules`, `firestore.indexes.json`, `storage.rules`) and the Cloud Functions in TypeScript, all registered in the `southamerica-east1` region.

---

## 🧰 Technology Stack

### Dentista (native Android)

| Technology | Version |
| --- | --- |
| Kotlin | 1.8.0 |
| Android Gradle Plugin | 7.4.2 |
| Gradle | 7.5 |
| compileSdk / targetSdk | 33 |
| minSdk | 29 |
| Firebase BoM | 32.0.0 |
| google-services | 4.3.15 |
| Navigation (fragment / ui-ktx / safe-args) | 2.5.3 |
| Material Components | 1.8.0 |
| AppCompat | 1.6.1 |
| ConstraintLayout | 2.1.4 |
| CameraX (camera2 / lifecycle / view) | 1.2.3 |
| Glide | 4.12.0 |
| FirebaseUI Storage | 8.0.0 |
| play-services-maps | 17.0.0 |
| play-services-location | 18.0.0 |
| secrets-gradle-plugin | 2.0.0 |

Firebase services used by the app: Authentication, Firestore, Storage, Cloud Messaging and Cloud Functions.

### Socorrista (Flutter)

| Technology | Version |
| --- | --- |
| Dart SDK | >=2.19.6 <3.0.0 |
| Android Gradle Plugin | 7.2.0 |
| Kotlin (Android host) | 1.7.10 |
| minSdk / targetSdk | 21 / 31 |
| firebase_core | ^2.13.1 |
| firebase_auth | ^4.6.2 |
| cloud_firestore | ^4.8.0 |
| firebase_storage | ^11.2.2 |
| firebase_messaging | ^14.6.2 |
| cloud_functions | ^4.3.3 |
| camera | ^0.10.5 |
| image_picker | ^0.8.3 |
| image_gallery_saver | ^1.7.1 |
| geolocator | ^9.0.2 |
| permission_handler | ^10.3.0 |
| flutter_rating_bar | ^4.0.1 |
| url_launcher | ^6.1.11 |
| path_provider | ^2.0.15 |

### Backend

| Technology | Version |
| --- | --- |
| Node.js (functions runtime) | 16 |
| firebase-functions | ^4.2.0 |
| firebase-admin | ^11.5.0 |
| TypeScript | ^4.9.0 |
| Region | southamerica-east1 |

---

## ⚙️ Environment Setup

### Dentista

The Gradle 7.5 used by the project **requires JDK 17**. Newer JDKs are not accepted by that Gradle version and the build fails before compiling.

- **JDK 17** (Temurin or equivalent)
- **Android SDK** with `platform-tools`, `platforms;android-33` and `build-tools;33.0.2`
- To run on an emulator: `system-images;android-33;google_apis;arm64-v8a` (or `x86_64`, depending on the machine)

Create the `Dentista/local.properties` file — it is not versioned:

```properties
sdk.dir=/path/to/Android/sdk
GOOGLE_MAPS_API_KEY=your-google-maps-key
```

The Maps key is read by `secrets-gradle-plugin` and injected into `AndroidManifest.xml`. Without a valid key the app still compiles and runs, but `MapsFragment` does not render the map.

### Socorrista

- **Flutter SDK** compatible with Dart 2.19 (stable channel, 3.7–3.10 line)
- The same Android SDK from the previous item

### Reconnecting your own Firebase

The versioned `google-services.json` files point to the original Firebase project from the course, **which no longer responds**. To get the apps working you need to point them at a new project:

1. Create a project in the [Firebase console](https://console.firebase.google.com/) and enable Authentication (email/password), Firestore, Storage and Cloud Messaging.
2. Register both Android apps with their respective `applicationId` values and replace each project's `google-services.json` with the new ones.
3. Publish the rules and indexes from `functions/`:
   ```bash
   cd functions
   firebase use --add           # select the project you created
   firebase deploy --only firestore,storage
   ```
4. Install and deploy the Cloud Functions:
   ```bash
   cd functions/functions
   npm install
   cd ..
   firebase deploy --only functions
   ```
5. The functions are pinned to the `southamerica-east1` region, and so are the clients. If you deploy to another region, adjust the `Firebase.functions("...")` calls in Dentista and `FirebaseFunctions.instanceFor(region: ...)` in Socorrista.

Using Cloud Functions requires the Blaze plan on the Firebase account.

---

## ▶️ Running the Project

> The original Firebase project is offline. Without going through "Reconnecting your own Firebase", the apps compile, install and open, but the screens that depend on remote data stay empty.

### Dentista

```bash
git clone https://github.com/guiazevedo17/TeethKids.git
cd TeethKids/Dentista

# point at JDK 17 and the Android SDK
export JAVA_HOME=/path/to/jdk-17
printf 'sdk.dir=/path/to/Android/sdk\nGOOGLE_MAPS_API_KEY=your-key\n' > local.properties

# build the debug APK
./gradlew assembleDebug

# install on a connected emulator or device
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Socorrista

```bash
cd TeethKids/Socorrista/Socorrista/teethsocorrista/TeethSocorrista/TeethSocorrista/teethsocorrista

flutter pub get
flutter run          # or: flutter build apk --debug
```

### Cloud Functions

```bash
cd TeethKids/functions/functions
npm install
npm run build        # compiles the TypeScript
cd ..
firebase emulators:start --only functions,firestore
```

---

## 📁 Folder Structure

```
TeethKids/
├── Dentista/                          # native Android app (Gradle project root)
│   ├── app/
│   │   └── src/main/
│   │       ├── java/com/kids/teeth/dentista/
│   │       │   ├── activity/          # SignInActivity
│   │       │   ├── dao/               # AddressesDao
│   │       │   ├── fragment/          # every screen + ImageHelper
│   │       │   ├── messaging/         # DefaultMessageService (FCM)
│   │       │   ├── model/             # Emergency, Address, Action, Dentist, Feedback
│   │       │   └── recyclerview/adapter/
│   │       ├── res/navigation/nav_graph.xml
│   │       └── AndroidManifest.xml
│   ├── build.gradle
│   └── gradle/wrapper/                # Gradle 7.5
│
├── Socorrista/                        # Flutter app
│   └── Socorrista/
│       ├── app/                       # Android source tree with no Gradle files
│       └── teethsocorrista/
│           └── TeethSocorrista/
│               └── TeethSocorrista/
│                   └── teethsocorrista/   # ← actual Flutter project root
│                       ├── lib/
│                       │   ├── main.dart
│                       │   └── firebase_options.dart
│                       ├── android/
│                       ├── ios/
│                       └── pubspec.yaml
│
└── functions/                         # Firebase configuration and backend
    ├── firebase.json
    ├── firestore.rules
    ├── firestore.indexes.json
    ├── storage.rules
    └── functions/
        ├── src/index.ts               # all the Cloud Functions
        ├── package.json
        └── tsconfig.json
```

Two notes about Socorrista, for anyone navigating the repository:

- The actual Flutter project root sits six levels below `Socorrista/`, at `Socorrista/Socorrista/teethsocorrista/TeethSocorrista/TeethSocorrista/teethsocorrista/`. The repetition came from commits that brought the project in along with the folders containing it, and it was never corrected during the course. That is the directory to open in an editor and to use with the `flutter` commands.
- `Socorrista/Socorrista/app/` holds an Android source tree (Kotlin and layouts) with no `build.gradle` and no manifest. It is a leftover from an earlier version of the app and is not part of any build.

---

## 👥 Credits

Group project for the Mobile Application Development course, between April and June 2023, built with:

- [@blemess](https://github.com/blemess)
- [@Jzanholo](https://github.com/Jzanholo)
- [@lanzaaaa](https://github.com/lanzaaaa)

---

## ⚖️ Rights

Academic project, published for study and portfolio purposes. It has no connection to any real dental service and is not intended for production use.
