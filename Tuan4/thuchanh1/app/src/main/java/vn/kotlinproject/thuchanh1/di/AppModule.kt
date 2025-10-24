package vn.kotlinproject.thuchanh1.di

import com.google.firebase.firestore.FirebaseFirestore
import vn.kotlinproject.thuchanh1.data.remote.FirestoreDataSource
import vn.kotlinproject.thuchanh1.data.repository.FirestoreLibraryRepository
import vn.kotlinproject.thuchanh1.domain.repository.LibraryRepository

object AppModule {
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val remote: FirestoreDataSource by lazy { FirestoreDataSource(firestore) }

    val repository: LibraryRepository by lazy { FirestoreLibraryRepository(remote) }
}
