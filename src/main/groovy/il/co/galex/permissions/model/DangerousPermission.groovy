package il.co.galex.permissions.model
/**
 * DangerousPermission represents the fixed list of Dangerous permissions to this date.
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


    public String constantName(){
        return "android.permission." + this.name();
    }

    public String sdkName(){
        return "Manifest.permission." + this.name();
    }

    public static DangerousPermission get(String str){

        for(DangerousPermission permission : values()){
            if(permission.name().equals(str)) return permission
        }
        return null
    }
}