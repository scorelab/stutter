Stutter Aid is an android project where it's aim is to help people who stutter by Delayed Auditory Feedback. Future implementations will also feature frequency shift + Delayed audio feedback.

####Building the stutter-android app

* You need to have your Android ndk and sdk downloaded and setup correctly.
* Add your sdk and ndk paths to your system variables
* Open the app in Android studio and go to File->Project Structure->Add your ndk path and sdk paths correctly
* If you have correctlty set these up, your paths will show in the local.properties file as below 

**For Windows**
```
ndk.dir=MYPATH\\AppData\\Local\\Android\\android-ndk-r10e
sdk.dir=MYPATH\\AppData\\Local\\Android\\sdk
```

**For OS X ** 
```
ndk.dir=/Users/usr/Library/Android/sdk/ndk-bundle
sdk.dir=/Users/usr/Library/Android/sdk
```

* You can even run the ` ndk-build ` command through a terminal from the ` stutter-android ` project folder and build the jni files

If the ndk-build command does not work, you need to do this: 
Go to 
```
project -> app -> build.gradle 
```

There, you will see something like this: 

``` 
commandLine 'ndk-build', '-B', '-C', file('src/main/jni').absolutePath
``` 
or 
```
commandLine 'ndk-build.cmd', '-B', '-C', file('src/main/jni').absolutePath
``` 

You have to change ` ndk-build ` to your ndk path in your system variable. Add a .cmd in the end only if you're on Windows.
So finally it will look like: 

```
commandLine '/Users/usr/Library/Android/sdk/ndk-bundle/ndk-build', '-B', '-C', file('src/main/jni').absolutePath
``` 
