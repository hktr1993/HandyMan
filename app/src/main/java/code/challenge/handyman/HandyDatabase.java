package code.challenge.handyman;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import code.challenge.handyman.models.HandyMan;

@Database(entities = {HandyMan.class}, exportSchema = false, version = 1)
@TypeConverters({Converter.class})
public abstract class HandyDatabase extends RoomDatabase {
    public abstract HandyDao handyDao();

    private static volatile HandyDatabase INSTANCE;

    public static HandyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HandyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HandyDatabase.class, "handyman_database.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
