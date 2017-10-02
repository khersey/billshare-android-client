# Trebuchet
Code name for the Android client of BillShare (https://billshare.io)

![image](https://media0ch-a.akamaihd.net/72/68/3e5e931d91ea016a26a9dbf13cad5962.jpg)

# Summary

BillShare is a cost splitting app for use between groups and friends.

We also have a web client!

The Android client is written in Kotlin implementing an MVP - Clean architecture.

We are targetting Android sdk 26 (android 8.0, Oreo), with minimum sdk 15 (android 4.0).




# Installation
Download Android Studio 3.0 Beta from: https://developer.android.com/studio/preview/index.html




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



