package il.co.galex.permissions.model
/**
 * Created by galex on 06/02/16.
 */
enum Permission {

    INTERNET("android.permission.INTERNET", "Manifest.permission.INTERNET")

    // represents a constant value of a permission, ex: "android.permission.INTERNET"
    final String constant
    // represents where it is stored in the Android SDK ex: "Manifest.permission.INTERNET"
    final String sdk

    private Permission(String constant, String sdk) {
        this.constant = constant;
        this.sdk = sdk;
    }

    public String toString() {

        return constant + " -> " + sdk
    }
}