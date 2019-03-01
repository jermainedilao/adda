package ph.sym.adda.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.content.ContextCompat


class PermissionsUtil {
    companion object {
        val PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE
        // val PERMISSION_MODIFY_PHONE_STATE = Manifest.permission.MODIFY_PHONE_STATE
        val PERMISSION_SEND_SMS = Manifest.permission.SEND_SMS
        val PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE
        val PERMISSION_NOTIFICATION_POLICY = Manifest.permission.ACCESS_NOTIFICATION_POLICY

        fun hasCompletePermissions(context: Context): Boolean {
            return getNonGrantedPermissionsList(context).isEmpty()
        }

        fun getNonGrantedPermissionsList(context: Context): List<String> {
            val readPhoneStatePermission = ContextCompat.checkSelfPermission(
                    context,
                    PERMISSION_READ_PHONE_STATE
            )

//            val modifyPhoneStatePermission = ContextCompat.checkSelfPermission(
//                    context,
//                    PERMISSION_MODIFY_PHONE_STATE
//            )

            val sendSmsPermission = ContextCompat.checkSelfPermission(
                    context,
                    PERMISSION_SEND_SMS
            )

            val callPhonePermission = ContextCompat.checkSelfPermission(
                    context,
                    PERMISSION_CALL_PHONE
            )

            val notificationPolicyPermission = ContextCompat.checkSelfPermission(
                    context,
                    PERMISSION_NOTIFICATION_POLICY
            )

            val permissionList: MutableList<String> = mutableListOf()

            if (readPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(PERMISSION_READ_PHONE_STATE)
            }

//            if (modifyPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
//                permissionList.add(PERMISSION_MODIFY_PHONE_STATE)
//            }

            if (sendSmsPermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(PERMISSION_SEND_SMS)
            }

            if (callPhonePermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(PERMISSION_CALL_PHONE)
            }

            if (notificationPolicyPermission != PackageManager.PERMISSION_GRANTED &&
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                permissionList.add(PERMISSION_NOTIFICATION_POLICY)
            }

            return permissionList
        }
    }
}