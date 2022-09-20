package com.office.roomdatabaseexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   private EditText edtUsername;
   private EditText edtAddress;
   private Button btnAddUser;
   private RecyclerView rcvUser;

   private UserAdapter userAdapter;
   private List<User> mListUser;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      initUI();
      userAdapter = new UserAdapter();
      mListUser = new ArrayList<>();

      userAdapter.setData(mListUser);
      rcvUser.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
      rcvUser.setAdapter(userAdapter);

      btnAddUser.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            LoadData loadData = new LoadData();
            loadData.execute();
         }
      });
   }

   private void initUI() {
      edtUsername = findViewById(R.id.edt_username);
      edtAddress = findViewById(R.id.edt_address);
      btnAddUser = findViewById(R.id.btn_add_user);
      rcvUser = findViewById(R.id.rcv_user);
   }

   public class LoadData extends AsyncTask<Void, Void, Void> {

      @Override
      protected void onPreExecute() {
         super.onPreExecute();
      }

      @Override
      protected Void doInBackground(Void... voids) {
         addUser();

         return null;
      }

      @Override
      protected void onPostExecute(Void unused) {
         super.onPostExecute(unused);

         edtUsername.setText("");
         edtAddress.setText("");

         hideSoftKeyboard();

         userAdapter.setData(mListUser);
      }
   }

   private void addUser() {
      String strUsername = edtUsername.getText().toString().trim();
      String strAddress = edtAddress.getText().toString().trim();

      if (TextUtils.isEmpty(strUsername) || TextUtils.isEmpty(strAddress)) {
         return;
      }

      User user = new User(strUsername, strAddress);

      UserDatabase.getInstance(this).userDao().insertUser(user);

      mListUser = UserDatabase.getInstance(MainActivity.this).userDao().getListUser();

   }

   public void hideSoftKeyboard() {
      try {
         InputMethodManager inputMethodManager =
                 (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
         inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}