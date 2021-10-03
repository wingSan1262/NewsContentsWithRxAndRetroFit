package vanrrtech.app.retrofitandrx.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vanrrtech.app.retrofitandrx.datamodels.NewsItemDataModelForJSON

@Database(entities = [NewsItemDataModelForJSON::class], exportSchema = false, version = 1)
public abstract class ArticleDataBase : RoomDatabase(){
    companion object{
        val DB_NAME :  String = "article-db"
        private var instance : ArticleDataBase? = null

        fun getInstance (context: Context) : ArticleDataBase?{
            if (instance == null){
                instance = Room.databaseBuilder(context.applicationContext, ArticleDataBase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return instance
        }
    }

    public abstract fun articlerDao() : ArticleDao
}