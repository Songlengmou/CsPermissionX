package com.anningtex.permissionx.callback;

import androidx.annotation.NonNull;

import com.anningtex.permissionx.request.ExplainScope;
import com.anningtex.permissionx.request.PermissionBuilder;

import java.util.List;

/**
 * Callback for {@link PermissionBuilder#onExplainRequestReason(ExplainReasonCallbackWithBeforeParam)} method.
 *
 * @author Song
 */
public interface ExplainReasonCallbackWithBeforeParam {

    /**
     * Called when you should explain why you need these permissions.
     *
     * @param scope         Scope to show rationale dialog.
     * @param deniedList    Permissions that you should explain.
     * @param beforeRequest Indicate it's before or after permission request. Work with {@link PermissionBuilder#explainReasonBeforeRequest()}
     */
    void onExplainReason(@NonNull ExplainScope scope, @NonNull List<String> deniedList, boolean beforeRequest);
}