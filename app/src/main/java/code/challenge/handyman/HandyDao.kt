package code.challenge.handyman

import androidx.room.*
import code.challenge.handyman.models.HandyMan

@Dao
interface HandyDao {

    @Query("SELECT * FROM handymanentity ORDER BY visitOrder DESC")
    fun getOrderedHandyMen(): List<HandyMan>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(job: HandyMan)

    @Query("DELETE FROM handymanentity WHERE identifier = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM handymanentity WHERE identifier = :id")
    fun findItem(id: Int) : Boolean


}