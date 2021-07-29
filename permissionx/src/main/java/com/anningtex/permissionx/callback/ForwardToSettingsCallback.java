package com.anningtex.permissionx.callback;

import androidx.annotation.NonNull;

import com.anningtex.permissionx.request.ForwardScope;
import com.anningtex.permissionx.request.PermissionBuilder;

import java.util.List;

/**
 * Callback for {@link PermissionBuilder#onForwardToSettings(ForwardToSettingsCallback)} method.
 *
 * @author Song
 */
public interface ForwardToSettingsCallback {

    /**
     * Called when you should tell user to allow these permissions in settings.
     *
     * @param scope      Scope to show rationale dialog.
     * @param deniedList Permissions that should allow in settings.
     */
    void onForwardToSettings(@NonNull ForwardScope scope, @NonNull List<String> deniedList);
}