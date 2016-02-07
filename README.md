# Android Permissions Gradle Plugin
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
    public static final String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String [] REQUIRED_PERMISSIONS = {
        GET_ACCOUNTS,
        READ_PHONE_STATE
    };
    (...)
}
```
For each dangerous permission, a reference is generated with its name so you can forget where those permissions really are, and a static array containing all the dangerous permissions.
Note that you can see here only two declared permissions as the permission `android.permission.INTERNET` is a normal permission.
## Usage
In your root build.gradle add the plugin, published in jCenter:

```gradle
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        (...)
        classpath 'il.co.galex.tools.build:permissions:1.0.0'
    }
}

```

In every Android Application or Library module, apply the plugin:
```gradle
apply plugin: 'il.co.galex.permissions'
```
## Nomenclature
The generated class helper is named by default `PermissionsHelper` and the default package name depends on `android.defaultConfig.applicationId`. If the `android.defaultConfig.applicationId` is for instance `com.example.company`, the package name will be `com.example.company.helper`.
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

## License


## FAQ


## Contact

I will be happy to answer to you at alexandre.gherschon(at)gmail.com.