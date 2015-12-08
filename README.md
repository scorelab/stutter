Stutter Aid is an android project where it's aim is to help people who stutter by Delayed Auditory Feedback. Future implementations will also feature frequency shift + Delayed audio feedback.

####Building the stutter-android app

* You need to have your Android ndk and sdk downloaded and setup correctly.
* Add your sdk and ndk paths to your system variables
* Open the app in Android studio and go to File->Project Structure->Add your ndk path and sdk paths correctly
* If you have correctlty set these up, your paths will show in the local.properties file as below
```
ndk.dir=MYPATH\\AppData\\Local\\Android\\android-ndk-r10e
sdk.dir=MYPATH\\AppData\\Local\\Android\\sdk
```
* You can even run the ` ndk-bulid ` command through a terminal from the ` stutter-android ` project folder and build the jni files
