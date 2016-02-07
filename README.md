# Android Permissions Gradle Plugin
## Introduction
Since Android Marshmallow, developers have to [request permissions](http://developer.android.com/guide/topics/security/permissions.html) the Android SDK considers dangerous, aka [Dangerous Permissions](http://developer.android.com/guide/topics/security/permissions.html#normal-dangerous).

This plugin generates a class to help with that task. It parses the Android Manifest on build and generates a helper class with the list of the Dangerous Permissions plus a few utility methods.
## Generated Code
If your manifest contains those three permissions:
```xml
    <uses-permission android:name="android.permission.GET_ACCOUNTS" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
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
        classpath 'il.co.galex.permissions:permissions:1.0.0'
    }
}

```

In every Android Application or Library module, apply the plugin:
```gradle
apply plugin: 'il.co.galex.permissions'
```
## Nomenclature
The default generated class will be named `PermissionsHelper`.
The default package of name depends on the applicationId. If the applicationId is `com.example.myapp`, the package name will be `com.example.myapp.helper`.
## Configuration
You can change the package name and the class name to suit your needs.
Add this at the root of your app or library build.gradle:
```gradle
android {
    (...)
}
permissions {
    helperPackage applicationId + ".util"
    helperClasName "PermissionsUtils"
}
```