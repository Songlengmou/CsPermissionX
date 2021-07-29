package com.anningtex.cspermissionx;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anningtex.cspermissionx.databinding.ActivityMainJavaBinding;
import com.anningtex.permissionx.other.PermissionX;

/**
 * @author Song
 */
public class MainJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainJavaBinding binding = ActivityMainJavaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView(binding);
    }

    private void initView(ActivityMainJavaBinding binding) {
        binding.makeRequestBtn.setOnClickListener(view -> PermissionX.init(MainJavaActivity.this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .explainReasonBeforeRequest()
                .onExplainRequestReason((scope, deniedList, beforeRequest) -> {
//                    CustomDialog customDialog = new CustomDialog(MainJavaActivity.this, "PermissionX needs following permissions to continue", deniedList);
//                    scope.showRequestReasonDialog(customDialog);
                    scope.showRequestReasonDialog(deniedList, "PermissionX needs following permissions to continue", "Allow");
                })
                .onForwardToSettings((scope, deniedList) -> {
                    scope.showForwardToSettingsDialog(deniedList, "Please allow following permissions in settings", "Allow");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        Toast.makeText(MainJavaActivity.this, "All permissions are granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainJavaActivity.this, "The following permissions are denied：" + deniedList, Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}