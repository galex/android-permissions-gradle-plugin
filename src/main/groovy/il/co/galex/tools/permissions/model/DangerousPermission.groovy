package il.co.galex.tools.permissions.model
/**
 * List of Dangerous permissions in the Android SDK.
 *
 * @author Alexander Gherschon
 */
enum DangerousPermission {

    READ_CALENDAR,
    WRITE_CALENDAR,
    CAMERA,
    READ_CONTACTS,
    WRITE_CONTACTS,
    GET_ACCOUNTS,
    ACCESS_FINE_LOCATION,
    ACCESS_COARSE_LOCATION,
    RECORD_AUDIO,
    READ_PHONE_STATE,
    CALL_PHONE,
    READ_CALL_LOG,
    WRITE_CALL_LOG,
    ADD_VOICEMAIL,
    USE_SIP,
    PROCESS_OUTGOING_CALLS,
    BODY_SENSORS,
    SEND_SMS,
    RECEIVE_SMS,
    READ_SMS,
    RECEIVE_WAP_PUSH,
    RECEIVE_MMS,
    READ_EXTERNAL_STORAGE,
    WRITE_EXTERNAL_STORAGE

    /**
     * 'Manifest.permission' + enum name
     * @return the name of the variable containing this Permission in the Android SDK
     */
    public String getSdkName(){

        return "Manifest.permission." + this.name();
    }

    /**
     * Get enum by its string value, if exists.
     * @param str
     * @return enum if the parameter str has an enum equivalent, and if not returns null
     */
    public static DangerousPermission get(String value){

        for(DangerousPermission permission : values()){
            if(permission.name().equals(value)) return permission
        }
        return null
    }
}