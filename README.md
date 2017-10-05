# Trebuchet
Code name for the Android client of BillShare (https://billshare.io)

![image](https://media0ch-a.akamaihd.net/72/68/3e5e931d91ea016a26a9dbf13cad5962.jpg)

# Summary

BillShare is a cost splitting app for use between groups and friends.

We also have a web client!

The Android client is written in Kotlin implementing an MVP - Clean architecture.

We are targetting Android sdk 26 (android 8.0, Oreo), with minimum sdk 15 (android 4.0).




# Installation
Download and install the latest JDK 1.8: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

Download and install Android Studio 3.0 Preview from: https://developer.android.com/studio/preview/index.html

Launch Android Studio and select: **Open new project from Version Control**

Configure Github integration, and select the trebuchet repository

There will be pop-up asking something about Gradle, just pick the default one and continue.

Once the editor is open, Gradle will try and sync.  This will fail a bunch of times.
Each time a message will appear with a button to fix the problem below it.
This wil entail downloading a bunch of project dependencies and specific versions of android SDK components.

The last thing I would recommend doing is switching the project view. By default the view is Android,
which hides certain files, I recommend using Project.

That's it! Once your Android Studio has finished downloading all the project dependicies you're good to go!


# Running Trebuchet
First we need to enable ADB Integration in Android Studio.

Click **Tools > Android > Enable ADB Integration**

You have 2 options for running Trebuchet:
1) Running it on your own android phone via ADB
2) Using an Android Virtual Device

I recommend testing on your own phone if you can.  It's just wayyy faster than the android VMs, and wont kill your laptop's battery.

That being said you should use AVD to test the app on different OS versions and screen sizes.

### Testing on your phone via ADB ###

**Enable Developer Settings on your Phone**
Open settings > System > About Phone

Scroll down to the bottom, and tap "Build Settings" 7 times to enable Developer Settings.
You should receive a nice little prompt letting you know developer settings are enabled.

Now you can **follow this guide** to get started: https://developer.android.com/studio/run/device.html

Once you have USB Debugging enabled, and your phone is connected to your computer,
click the play (run) button at the top of Android Studio, and you should be able to select your device
from the list below.


### Testing on an Android Virtual Device ###

Open the Android Virtual Device Manager:
    **tools > android > AVD manager**

Click: **+ Create Virtual Device**

Select a sample Nexus or Pixel device (NOTE: Pixel requires more system resources than Nexus)

Download the recommended system image.

Boot the Device.  It should now appear under "Connected Devices" when you click the run button.



