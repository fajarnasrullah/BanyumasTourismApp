package com.jer.banyumastourismapp.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.jer.banyumastourismapp.data.local.DaoDestination
import com.jer.banyumastourismapp.data.local.DaoItinerary
import com.jer.banyumastourismapp.data.local.DaoTicket
import com.jer.banyumastourismapp.data.local.DaoUser
import com.jer.banyumastourismapp.data.remote.TourismPagingSource
import com.jer.banyumastourismapp.data.remote.retrofit.RetrofitClient
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.domain.model.Itinerary
import com.jer.banyumastourismapp.domain.model.ItineraryWithPlanCards
import com.jer.banyumastourismapp.domain.model.Story
import com.jer.banyumastourismapp.domain.model.Plan
import com.jer.banyumastourismapp.domain.model.PlanCardData
import com.jer.banyumastourismapp.domain.model.Ticket
import com.jer.banyumastourismapp.domain.model.TransactionRequest
import com.jer.banyumastourismapp.domain.model.TransactionResponse
import com.jer.banyumastourismapp.domain.model.User
import com.jer.banyumastourismapp.domain.repository.TourismRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TourismRepositoryImpl(
    private val db: FirebaseDatabase,
    private val daoDestination: DaoDestination,
    private val daoItinerary: DaoItinerary,
    private val daoUser: DaoUser,
    private val daoTicket: DaoTicket,
    private val auth: FirebaseAuth,
): TourismRepository {
    override fun getDestinations(): Flow<PagingData<Destination>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                TourismPagingSource(db)
            }
        ).flow
    }

    override suspend fun getDestination(id: Int): Destination? {
        return daoDestination.getDestination(id)
    }

    override suspend fun getDestinationByTitle(title: String): List<Destination> = suspendCoroutine { continuation ->
        val listData = mutableListOf<Destination>()
        val ref = db.getReference("destinations")
        val query: Query = ref.orderByChild("title").equalTo(title)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    val destination = childSnapshot.getValue(Destination::class.java)
                    val lists = mutableListOf<Destination>()
                    if (destination != null) {
                        lists.add(destination)
                    }
                    listData.addAll(lists)
                }
                Log.d("TourismRepositoryImpl", "Success get Destination By Title: $listData")

                continuation.resume(listData)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TourismRepositoryImpl", "Error fetching data Destination By Title: ${error.message}")
                continuation.resume(emptyList())
            }

        })




    }

    override suspend fun insertDestination(destination: Destination) {
        return daoDestination.insert(destination)
    }

    override suspend fun deleteDestination(destination: Destination) {
        return daoDestination.delete(destination)
    }

    override fun selectDestinations(): Flow<List<Destination>> {
        return daoDestination.getDestinations()
    }

    override suspend fun getDestinationsForMaps(): List<Destination> {
        val snapshot = db.getReference("destinations").get().await()
//            snapshot.getValue(Destination::class.java)
//            totalDestiCount += snapshot.childrenCount.toInt()
        Log.d("TourismRepositoryImpl","Succeed to get Destination From Realtime Database")
        val listDestinations = mutableListOf<Destination>()
        for (destinationSnapshot in snapshot.children) {
            val destination = destinationSnapshot.getValue(Destination::class.java)
            destination?.let {
                val tempDestination = Destination(
                    id = listDestinations.size,
                    title = it.title,
                    imageUrl = it.imageUrl,
                    imageList = it.imageList,
                    category = it.category,
                    description = it.description,
                    location = it.location,
                    latitude = it.latitude,
                    longitude = it.longitude,
                    cost = it.cost,
                    timeOpen = it.timeOpen,
                    rating = it.rating,
                    ig = it.ig,
                    igUrl = it.igUrl,
                    facility = it.facility,
                    route = it.route,
                    accessibility = it.accessibility,
                    tips = it.tips

                )
                listDestinations.add(tempDestination)
            }
        }
        return listDestinations
    }


    override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser?> {
        return try {
            val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)

            val authResult = auth.signInWithCredential(credential).await()
            auth.currentUser?.let {
                saveUserData(it, "google")
            }
            Result.success(authResult.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun saveUserData(user: FirebaseUser?, provider: String) {
        user?.let {
            val userData = User(
                uid = user.uid,
                name = user.displayName,
                email = user.email,
                provider = provider

            )
            db.getReference("users").child(it.uid).setValue(userData)
        }
    }

    override fun updateUserData(name: String, desc: String) {
        val user = auth.currentUser
        user?.let {
            val userData = hashMapOf<String, Any>(
                "name" to name,
                "desc" to desc
            )
            db.getReference("users").child(it.uid).updateChildren(userData).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TourismRepository", "User data updated successfully: $name, $desc")
                } else {
                    Log.e("TourismRepository", "Failed to update user data: ${task.exception}")
                }
            }
        }
    }



    override suspend fun insertItinerary(itinerary: Itinerary): Long {
        return daoItinerary.insertItinerary(itinerary)
    }

    override suspend fun insertPlanCard(planCard: PlanCardData): Long {
        return daoItinerary.insertPlanCard(planCard)
    }

    override suspend fun insertPlan(plan: Plan) {
        daoItinerary.insertPlan(plan)
    }

    override suspend fun deleteItinerary(itinerary: Itinerary) {
        daoItinerary.deleteItinerary(itinerary)
    }

    override suspend fun deletePlanCard(planCard: PlanCardData) {
        daoItinerary.deletePlanCard(planCard)
    }

    override suspend fun deleteListPlan(planCardDataId: Int) {
        daoItinerary.deleteListPlan(planCardDataId)
    }

    override suspend fun getItinerary(uid: String): Itinerary? {
        return daoItinerary.getItinerary(uid)
    }

    override suspend fun getItineraryWithPlanCards(uid: String): ItineraryWithPlanCards? {
        return daoItinerary.getItineraryWithPlanCards(uid)
    }

    override suspend fun insertUser(user: User) {
        return daoUser.insertUser(user)
    }

    override suspend fun deleteUser(user: User) {
        return daoUser.deleteUser(user)
    }

    override suspend fun getUser(uid: String): User? {
        return daoUser.getUser(uid)
    }

    override suspend fun createTransaction(request: TransactionRequest): Response<TransactionResponse> {
        val response = RetrofitClient.instance.createTransaction(request)
        return response
    }

    override suspend fun insertTicket(ticket: Ticket) {
        return daoTicket.insertTicket(ticket)
    }

    override suspend fun deleteTicket(ticket: Ticket) {
        return daoTicket.deleteTicket(ticket)
    }

    override suspend fun getTicket(uid: String): Ticket {
        return daoTicket.getTicket(uid)
    }

    override suspend fun getTicketHistory(uid: String): List<Ticket> {
        return daoTicket.getTicketHistory(uid)
    }

    override suspend fun insertStory(story: Story) {

        val dbStoryRef = db.reference.child("stories")
        dbStoryRef.push().setValue(story)
    }

    override suspend fun getStory(): List<Story> {
        val snapshot = db.getReference("stories").get().await()
        val listStories = mutableListOf<Story>()

        for (storySnapshot in snapshot.children) {
            val story = storySnapshot.getValue(Story::class.java)
            val lists = mutableListOf<Story>()
            if (story != null ) {
                lists.add(story)
            }
            listStories.addAll(lists)
        }

        return listStories
    }


}