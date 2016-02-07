# Android Permissions Gradle Plugin

## Introduction

Since Android Marshmallow, developers have to [request permissions](http://developer.android.com/guide/topics/security/permissions.html) the Android SDK considers dangerous, aka [Dangerous Permissions](http://developer.android.com/guide/topics/security/permissions.html#normal-dangerous).

## Goal

This plugin generates a class to help with that task. It parses the Android Manifest on build and generates a helper class with the list of the Dangerous Permissions plus a few utility methods.

## Example

If your manifest contains those three permissions:

```java
public static class PermissionsHelper {

}
```