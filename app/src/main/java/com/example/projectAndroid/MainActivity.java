package com.example.projectAndroid;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projectAndroid.data.Profil;
import com.example.projectAndroid.data.ProfilDao;
import com.example.projectAndroid.data.UserDatabase;
import com.example.seminar_5.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    public ListView lvUsers;
    public ProfilAdapter adaptor;
    public List<Profil> users = new ArrayList<>();
    public Profil selectedUser;
    public Profil profil;
    public ActivityResultLauncher<Intent> addNewUserLauncher;
    public List<Profil> listFinal = new ArrayList<>();
    public List<Profil> listaSortDescending = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        UserDatabase userDatabase = UserDatabase.getInstance(getApplicationContext());

        ProfilDao profilDao = userDatabase.getProfilDao();

        addNewUserLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Profil newUser = (Profil) data.getSerializableExtra("newUser");
                            if (newUser != null) {
                                // Adding the new user to the local list
                                users.add(newUser);
                                // Notify the adapter about the change
                                // Add the new user to the database
                                if(profilDao != null)
                                {
                                    profilDao.insert(newUser);
                                }
                                adaptor.notifyDataSetChanged();
                                listFinal.clear();
                                listFinal.addAll(users);
                            }
                        }
                    }
                });

        //app compact widget toolbar
        Toolbar  toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvUsers = findViewById(R.id.lv_users);

        //adaptor = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,users);
        adaptor = new ProfilAdapter(this,users);

        lvUsers.setAdapter(adaptor);

        lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedUser = users.get(position);
                Intent intent = new Intent(MainActivity.this, ProfileDetails.class);
                intent.putExtra("user", selectedUser);
                startActivity(intent);
            }
        });

        lvUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Deleting an element from the list
                // Long press to delete an item
                users.remove(position);
                adaptor.notifyDataSetChanged();
                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddUsers.class);
            addNewUserLauncher.launch(intent);
        });

        List<Profil> usersFromDatabase = profilDao.getAll();

        users.clear();
        users.addAll(usersFromDatabase);
        adaptor.notifyDataSetChanged();

        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        boolean runOnce = pref.getBoolean("run", false);

        if (!runOnce) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("run", true);
            editor.apply();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        String lastAccess = simpleDateFormat.format(new Date());
        SharedPreferences.Editor editorU = pref.edit();
        editorU.putString("last", lastAccess);
        editorU.apply();

        String output = pref.getString("last", "data");
        Toast.makeText(MainActivity.this, "Last accessed: " + output, Toast.LENGTH_SHORT).show();

        List<Profil> user1 = profilDao.getAll();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Profil newUser = (Profil) data.getSerializableExtra("newUser");
            if (newUser != null) {
                users.add(newUser);
                adaptor.notifyDataSetChanged();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.functions_for_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        UserDatabase userDatabase = UserDatabase.getInstance(getApplicationContext());

        ProfilDao profilDao = userDatabase.getProfilDao();

        if (id == R.id.list_A_Z) {
            // Users are sorted alphabetically
            Collections.sort(users, new Comparator<Profil>() {
                @Override
                public int compare(Profil user1, Profil user2) {
                    return user1.getName().compareTo(user2.getName());
                }
            });

            // Updating the adapter
            adaptor.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.list_Z_A) {
            // The list is sorted in descending order by name
            // We create a new list that takes values from users because using reversed directly didn't work
            Collections.sort(users, new Comparator<Profil>() {
                @Override
                public int compare(Profil profil1, Profil profil2) {
                    return profil1.getName().compareTo(profil2.getName());
                }
            });

            listaSortDescending.clear();
            listaSortDescending = users.stream().sorted(Comparator.comparing(Profil::getName).reversed()).collect(Collectors.toList());

            users.clear();
            users.addAll(listaSortDescending);

            adaptor.notifyDataSetChanged();
            return true;
        }
        if(id==R.id.listUnordered)
        {
            users.clear();

            List<Profil> usersFromDatabase = profilDao.getAll();

            users.clear();

            users.addAll(usersFromDatabase);

            adaptor.notifyDataSetChanged();


            // We add the new user to the end of the list (if there is a new user)
            if (listFinal.size() > users.size()) {
                users.add(listFinal.get(listFinal.size() - 1));
            }

            adaptor.notifyDataSetChanged();
        }

        if(id==R.id.listusersolder21)
        {
            users.clear();
            List<Profil> usersOver21 = profilDao.getUsersOlderThan21();
            users.addAll(usersOver21);
            adaptor.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}