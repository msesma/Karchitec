package com.paradigmadigital.karchitect.domain.db

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.util.SparseIntArray
import com.paradigmadigital.karchitect.domain.entities.DomContributor
import com.paradigmadigital.karchitect.domain.entities.DomRepo
import com.paradigmadigital.karchitect.domain.entities.RepoSearchResult
import java.util.*

@Dao
abstract class RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg repos: DomRepo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertContributors(contributors: List<DomContributor>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repositories: List<DomRepo>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createRepoIfNotExists(domRepo: DomRepo): Long

    @Query("SELECT * FROM DomRepo WHERE owner_login = :p0 AND name = :p1")
    abstract fun load(login: String, name: String): LiveData<DomRepo>

    @Query("SELECT login, avatarUrl, contributions FROM DomContributor "
            + "WHERE repoName = :p1 AND repoOwner = :p0 "
            + "ORDER BY contributions DESC")
    abstract fun loadContributors(owner: String, name: String): LiveData<List<DomContributor>>

    @Query("SELECT * FROM DomRepo "
            + "WHERE owner_login = :p0 "
            + "ORDER BY stars DESC")
    abstract fun loadRepositories(owner: String): LiveData<List<DomRepo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(result: RepoSearchResult)

    @Query("SELECT * FROM RepoSearchResult WHERE query = :p0")
    abstract fun search(query: String): LiveData<RepoSearchResult>

    fun loadOrdered(repoIds: List<Int>): LiveData<List<DomRepo>> {
        val order = SparseIntArray()
        var index = 0
        for (repoId in repoIds) {
            order.put(repoId, index++)
        }
        return Transformations.map(loadById(repoIds)) { repositories ->
            Collections.sort(repositories) { r1, r2 ->
                val pos1 = order.get(r1.id)
                val pos2 = order.get(r2.id)
                pos1 - pos2
            }
            repositories
        }
    }

    @Query("SELECT * FROM DomRepo WHERE id in (:p0)")
    protected abstract fun loadById(repoIds: List<Int>): LiveData<List<DomRepo>>

    @Query("SELECT * FROM RepoSearchResult WHERE query = :p0")
    abstract fun findSearchResult(query: String): RepoSearchResult
}
