# Android Permissions Gradle Plugin (1.0.6)
## Introduction
Since Android Marshmallow, developers have to [request permissions](http://developer.android.com/guide/topics/security/permissions.html) the Android SDK considers dangerous, aka [Dangerous Permissions](http://developer.android.com/guide/topics/security/permissions.html#normal-dangerous).

This plugin generates a class to help with that task. It parses the Android Manifest on build and generates a helper class with the list of the Dangerous Permissions plus a few utility methods.
## Generated Code
If your manifest contains those three permissions:
```xml
<manifest package="com.example.company" xmlns:android="http://schemas.android.com/apk/res/android">
    <uses-permission android:name="android.permission.GET_ACCOUNTS" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
</manifest>
```
The plugin will generate this helper class named PermissionsHelper:

```java
public class PermissionsHelper {
    public static final String [] REQUIRED_PERMISSIONS = {
        Manifest.permission.GET_ACCOUNTS,
        Manifest.permission.READ_PHONE_STATE
    };
    (...)
}
```
The generated class contains a static String [] of all the dangerous permissions from the manifest.
Note that you can see here only two declared permissions as the permission `android.permission.INTERNET` is a not a dangerous permission.
## Usage
In your root build.gradle add the plugin, published in jCenter:

```gradle
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        (...)
        classpath 'il.co.galex.tools.build:permissions:1.0.6'
    }
}

```

In every Android Application or Library module, apply the plugin:
```gradle
apply plugin: 'il.co.galex.permissions'
```
## Nomenclature
The generated class helper is named by default `PermissionsHelper` and the default package name depends on `variant.applicationId`.
If `variant.applicationId` is for instance `com.example.company`, the package name will be `com.example.company.helper`.
## Configuration
You can change the package name and the class name to suit your needs.
For instance if you prefer the 'utils' convention:
```gradle
android {
    (...)
}
permissions {
    helperPackage android.defaultConfig.applicationId + ".util"
    helperClassName "PermissionsUtils"
}
```
## Utility Methods
The class contains utility methods to ease the work with the [System Permissions API](http://developer.android.com/guide/topics/security/permissions.html).
More documentation on this subject will be added soon.

## License
This plugin is available under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

(c) All rights reserved Alexander Gherschon