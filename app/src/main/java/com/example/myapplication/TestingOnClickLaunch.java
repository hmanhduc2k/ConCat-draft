package com.example.myapplication;

import android.app.Activity;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;

public class TestingOnClickLaunch extends Activity {

    private HashMap<Integer, ApplicationInfo> storage;

    public TestingOnClickLaunch() {

    }

    @Override
    public void onCreate(Bundle instanceState) {
        super.onCreate(instanceState);
        //  setContentView(R.layout.activity_main);
        PackageManager packageManager = getPackageManager();

        /**
         * Get a list of all installed applications on the Android phone
         */
        List<ApplicationInfo> appLists = packageManager.getInstalledApplications(0);
        this.storage = new HashMap<>();
        Button button = findViewById(R.id.button2);

        /*
        If there is nothing on the button yet, then click to choose application
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Hold to choose app", Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                /**
                 * Creating a Pop-up Menu to choose application
                 * We might need to change this to ListView or other kinds of menu
                 * so that a search function is enabled and better view is ensured
                 */
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
                /*
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                If create a menu on the layout files, then use this to retrieve the menu
                 */
                /**
                 * Get the menu and add items to the menu - in this case the items are the
                 * application info saved on the Android phone
                 * For each application added, the hash function will map the respective
                 * item id to the app info for later retrieval
                 */
                for (int i = 0; i < appLists.size(); i++) {
                    popupMenu.getMenu().add(Menu.NONE, i, i,
                            packageManager.getApplicationLabel(appLists.get(i)));
                    storage.put(i, appLists.get(i));
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    /**
                     * When clicking on the item, the system will retrieve the item id and get
                     * the Application Info hashed from the id itself. From thereon, app launcher
                     * is settled
                     * @param item
                     * @return
                     */
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        ApplicationInfo temp = storage.get(id);
                        if (temp == null) {
                            return false;
                        }
                        button.setText(packageManager.getApplicationLabel(temp));
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                launchApp(temp);
                            }
                        });
                        return true;
                    }
                });
                popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu menu) {
                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    /**
     * Launch the specify app from the application info
     * @param appInfo information of the application, including package name
     */
    private void launchApp(ApplicationInfo appInfo) {
        Toast.makeText(getApplicationContext(), "Launching app...", Toast.LENGTH_SHORT).show();
        Intent intent = getPackageManager().getLaunchIntentForPackage(appInfo.packageName);
        if (intent == null || appInfo == null) {
            Toast.makeText(getApplicationContext(), "Cannot find package", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Activity not found!", Toast.LENGTH_SHORT).show();
        }
    }
}
