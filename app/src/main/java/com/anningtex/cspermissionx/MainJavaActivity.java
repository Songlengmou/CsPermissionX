package com.anningtex.cspermissionx;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anningtex.cspermissionx.databinding.ActivityMainJavaBinding;
import com.anningtex.permissionx.other.PermissionX;

/**
 * @author Song
 * desc:PermissionX的使用
 */
public class MainJavaActivity extends AppCompatActivity {
    private ActivityMainJavaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainJavaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.makeRequestBtn.setOnClickListener(view ->
                PermissionX.init(MainJavaActivity.this)
                        .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .explainReasonBeforeRequest()
                        .onExplainRequestReason((scope, deniedList, beforeRequest) -> {
                            //即将申请的权限是程序必须依赖的权限
//                    CustomDialog customDialog = new CustomDialog(MainJavaActivity.this, "PermissionX needs following permissions to continue", deniedList);
//                    scope.showRequestReasonDialog(customDialog);
                            scope.showRequestReasonDialog(deniedList, "PermissionX needs following permissions to continue", "Allow");
                        })
                        .onForwardToSettings((scope, deniedList) -> {
                            //您需要去应用程序设置当中手动开启权限
                            scope.showForwardToSettingsDialog(deniedList, "Please allow following permissions in settings", "Allow");
                        })
                        .request((allGranted, grantedList, deniedList) -> {
                            if (allGranted) {
                                //所有申请的权限都已通过
                                Toast.makeText(MainJavaActivity.this, "All permissions are granted", Toast.LENGTH_SHORT).show();
                            } else {
                                //您拒绝了如下权限
                                Toast.makeText(MainJavaActivity.this, "The following permissions are denied：" + deniedList, Toast.LENGTH_SHORT).show();
                            }
                        }));
    }

    /**
     * 若找不到要跳转的界面，可以先把用户引导到系统设置页面
     */
    public void toSelfSetting() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }
}