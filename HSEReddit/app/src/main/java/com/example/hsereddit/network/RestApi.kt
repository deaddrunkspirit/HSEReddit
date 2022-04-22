package com.example.hsereddit.network

import android.database.Observable
import com.example.hsereddit.data.model.AuthorizedData
import com.example.hsereddit.data.model.Comment
import com.example.hsereddit.data.model.Post
import com.example.hsereddit.data.model.Section
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*

interface RestApi {
    // comments-controller
    //
    @POST("comment")
    fun createComment(@Body body: Comment): Call<Void>

    @GET("comment/byPost")
    fun getAllCommentsForPost(@Query("id") id: Int): Call<List<Comment>>

    // posts-controller
    //
    @POST("post/create")
    fun createPost(@Body body: Requests.CreatePostRequest): Call<Void>

    @PUT("post")
    fun updatePostTitle(
        @Query("postId") postId: Int,
        @Query("postTitle") postTitle: String?,
    ) : Call<Post>

    @PUT("post")
    fun updatePostContent(
        @Query("postId") postId: Int,
        @Query("content") content: String?,
    ) : Call<Post>

    @GET("post/all")
    fun getAllPosts(@Query("page") page: Int): Call<List<Post>>

    @GET("post")
    fun getPost(@Query("id") id: Int): Call<Post>

    @DELETE("post/delete")
    fun deletePost(@Query("id") id: Int): Call<Void>

    @GET("post/bySection")
    fun getPostsBySection(
        @Query("id") id: Int,
        @Query("page") page: Int
    ) : Call<List<Post>>

    @GET("post/bySubscriptions")
    fun getSubscriptionPosts(
        @Query("page") page: Int
    ) : Call<List<Post>>

    @GET("post/byTitle")
    fun getPostsByKeywordInTitle(
        @Query("keywords") keywords: String,
        @Query("page") page: Int
    ) : Call<List<Post>>

    @GET("post/byUser")
    fun getPostsByUser(
        @Query("username") username: String,
        @Query("page") page: Int
    ) : Call<List<Post>>

    // reaction-controller
    //
    @POST("reaction")
    fun vote(@Body body: Requests.VoteRequest): Call<Void>

    // section-controller
    //
    @POST("section")
    fun createSection(
        @Body body: Requests.CreateSectionRequest
    ): Call<Section>

    @GET("section/all")
    fun getAllSection(@Query("page") page: Int): Call<List<Section>>

    @GET("section")
    fun getSection(@Query("id") id: Int): Call<Section>

    @GET("section/search?keywords={keywords}&page={page}")
    fun getSectionByKeywordsInTitle(
        @Path("keywords") keywords: String,
        @Path("page") page: Int
    ) : Call<List<Section>>

    @POST("section/subscribe")
    fun subscribeToSection(@Query("id") id: Int): Call<Void>

    // user-controller
    @GET("user/accountVerifiction/{token}")
    fun verifyAccount(@Path("token") token: String): Call<String>

    @POST("user/login")
    fun login(@Body body: Requests.LoginRequest): Call<AuthorizedData>

    @POST("user/logout")
    fun logout(@Body body: Requests.LogoutRequest): Call<String>

    @POST("user/profile-picture")
    fun uploadProfilePicture(@Body body: String): Call<Void>

    @GET("user/profile-picture/{id}")
    fun getProfilePicture(@Path("id") id: Int): Call<String>

    @POST("user/refresh/token")
    fun refreshToken(@Body body: Requests.RefreshTokenRequest): Call<AuthorizedData>

    @POST("user/signup")
    fun signup(@Body body: Requests.SignupRequest): Call<String>

    @PATCH("user/updatePassword")
    fun updatePassword(@Body body: Requests.UpdatePasswordRequest): Call<String>

    companion object {
        var BASE_URL = "https://hse-forum-backend.herokuapp.com/api/"

        fun create() : RestApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).build()

            return retrofit.create(RestApi::class.java)
        }
    }
}