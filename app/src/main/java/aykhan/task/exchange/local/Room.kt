package aykhan.task.exchange.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface ExchangeDao {

    @Query("select * from exchangeRates")
    fun getExchangeRates(): LiveData<List<ExchangeRate>>

    @Insert(onConflict = REPLACE)
    fun insert(exchangeRate: ExchangeRate)

    @Insert(onConflict = REPLACE)
    fun insertAll(vararg exchangeRate: ExchangeRate)

    @Query("delete from exchangeRates where code like :code")
    fun delete(code: String)

    @Query("update exchangeRates set refreshTrigger = refreshTrigger * (-1)")
    fun refreshData()

}

@Database(entities = [ExchangeRate::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val exchangeRateDao: ExchangeDao
}

private const val DATABASE_NAME = "ExchangeRates"
private lateinit var INSTANCE: AppDatabase

fun getDatabase(context: Context): AppDatabase {
    synchronized(AppDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
    return INSTANCE
}