package com.anningtex.permissionx.request;

import android.Manifest;
import android.os.Build;

import com.anningtex.permissionx.other.PermissionX;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for request ACCESS_BACKGROUND_LOCATION permission.
 *
 * @author Song
 */
public class RequestBackgroundLocationPermission extends BaseTask {

    /**
     * Define the const to compat with system lower than Q.
     */
    public static final String ACCESS_BACKGROUND_LOCATION = "android.permission.ACCESS_BACKGROUND_LOCATION";

    RequestBackgroundLocationPermission(PermissionBuilder permissionBuilder) {
        super(permissionBuilder);
    }

    @Override
    public void request() {
        if (pb.shouldRequestBackgroundLocationPermission()) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                // If app runs under Android Q, there's no ACCESS_BACKGROUND_LOCATION permissions.
                // We remove it from request list, but will append it to the request callback as denied permission.
                pb.specialPermissions.remove(ACCESS_BACKGROUND_LOCATION);
                pb.permissionsWontRequest.add(ACCESS_BACKGROUND_LOCATION);
            }
            if (PermissionX.isGranted(pb.activity, ACCESS_BACKGROUND_LOCATION)) {
                // ACCESS_BACKGROUND_LOCATION has already granted, we can finish this task now.
                finish();
                return;
            }
            boolean accessFindLocationGranted = PermissionX.isGranted(pb.activity, Manifest.permission.ACCESS_FINE_LOCATION);
            boolean accessCoarseLocationGranted = PermissionX.isGranted(pb.activity, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (accessFindLocationGranted || accessCoarseLocationGranted) {
                if (pb.explainReasonCallback != null || pb.explainReasonCallbackWithBeforeParam != null) {
                    List<String> requestList = new ArrayList<>();
                    requestList.add(ACCESS_BACKGROUND_LOCATION);
                    if (pb.explainReasonCallbackWithBeforeParam != null) {
                        // callback ExplainReasonCallbackWithBeforeParam prior to ExplainReasonCallback
                        pb.explainReasonCallbackWithBeforeParam.onExplainReason(getExplainScope(), requestList, true);
                    } else {
                        pb.explainReasonCallback.onExplainReason(getExplainScope(), requestList);
                    }
                } else {
                    // No implementation of explainReasonCallback, so we have to request ACCESS_BACKGROUND_LOCATION without explanation.
                    requestAgain(null);
                }
                return;
            }
        }
        // Shouldn't request ACCESS_BACKGROUND_LOCATION at this time, so we call finish() to finish this task.
        finish();
    }

    @Override
    public void requestAgain(List<String> permissions) {
        // Don't care what the permissions param is, always request ACCESS_BACKGROUND_LOCATION.
        pb.requestAccessBackgroundLocationNow(this);
    }
}