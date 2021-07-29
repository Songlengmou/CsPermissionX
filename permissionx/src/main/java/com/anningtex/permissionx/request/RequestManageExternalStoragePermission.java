package com.anningtex.permissionx.request;

import android.os.Build;
import android.os.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for request android.permission.MANAGE_EXTERNAL_STORAGE.
 *
 * @author Song
 */
public class RequestManageExternalStoragePermission extends BaseTask {

    /**
     * Define the const to compat with system lower than R.
     */
    public static final String MANAGE_EXTERNAL_STORAGE = "android.permission.MANAGE_EXTERNAL_STORAGE";

    RequestManageExternalStoragePermission(PermissionBuilder permissionBuilder) {
        super(permissionBuilder);
    }

    @Override
    public void request() {
        if (pb.shouldRequestManageExternalStoragePermission()
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                // MANAGE_EXTERNAL_STORAGE permission has already granted, we can finish this task now.
                finish();
                return;
            }
            if (pb.explainReasonCallback != null || pb.explainReasonCallbackWithBeforeParam != null) {
                List<String> requestList = new ArrayList<>();
                requestList.add(MANAGE_EXTERNAL_STORAGE);
                if (pb.explainReasonCallbackWithBeforeParam != null) {
                    // callback ExplainReasonCallbackWithBeforeParam prior to ExplainReasonCallback
                    pb.explainReasonCallbackWithBeforeParam.onExplainReason(getExplainScope(), requestList, true);
                } else {
                    pb.explainReasonCallback.onExplainReason(getExplainScope(), requestList);
                }
            } else {
                // No implementation of explainReasonCallback, we can't request
                // MANAGE_EXTERNAL_STORAGE permission at this time, because user won't understand why.
                finish();
            }
            return;
        }
        // shouldn't request MANAGE_EXTERNAL_STORAGE permission at this time, so we call finish()
        // to finish this task.
        finish();
    }

    @Override
    public void requestAgain(List<String> permissions) {
        // don't care what the permissions param is, always request WRITE_SETTINGS permission.
        pb.requestManageExternalStoragePermissionNow(this);
    }
}