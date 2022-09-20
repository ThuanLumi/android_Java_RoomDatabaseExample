package com.office.roomdatabaseexample;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

   private static final String DATABASE_NAME = "user.db";
   private static UserDatabase INSTANCE;

   public abstract UserDao userDao();

   public static synchronized UserDatabase getInstance(Context context) {
      if (INSTANCE == null) {
         INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class,
                 DATABASE_NAME).build();
      }
      return INSTANCE;
   }

   public static void destroyInstance() {
      INSTANCE = null;
   }
}
