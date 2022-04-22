package com.example.hsereddit.network


import ServiceBuilder
import android.util.Log
import com.example.hsereddit.data.model.*
import com.example.hsereddit.data.paging.PostPagingTitleKeywordsSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.RuntimeException


class RestApiService {
    var authorizedData: AuthorizedData? = null
    var accountVerified: Boolean = true

    fun decorateToken(token: String): String {
        return "Bearer $token"
    }

    // comments-controller
    //
    fun createComment(
        data: Comment,
        onResult: (
            Void?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.createComment(data).enqueue(
            object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult(response.body())
                }
            }
        )
    }

    fun getAllCommentsForPost(
        postId: Int,
        onResult: (
            List<Comment>?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getAllCommentsForPost(postId).enqueue(
            object : Callback<List<Comment>> {
                override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<List<Comment>>,
                    response: Response<List<Comment>>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult(response.body())
                }
            }
        )
    }

    // posts-controller
    //
    fun createPost(
        data: Requests.CreatePostRequest,
        onResult: (
            Void?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.createPost(data).enqueue(
            object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult(response.body())
                }
            }
        )
    }

    fun updatePostContent(
        postId: Int,
        content: String,
        onResult: (
            Post?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.updatePostContent(postId, content).enqueue(
            object : Callback<Post> {
                override fun onFailure(call: Call<Post>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Post>,
                    response: Response<Post>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult(response.body())
                }
            }
        )
    }

    fun updatePostTitle(
        postId: Int,
        postTitle: String,
        onResult: (
            Post?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.updatePostTitle(postId, postTitle).enqueue(
            object : Callback<Post> {
                override fun onFailure(call: Call<Post>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Post>,
                    response: Response<Post>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult(response.body())
                }
            }
        )
    }

//    fun updatePostSection(
//        postId: Int,
//        sectionName: String,
//        onResult: (
//            Post?
//        ) -> Unit
//    ) {
//        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
//        retrofit.updatePost(null, postId, null, sectionName).enqueue(
//            object : Callback<Post> {
//                override fun onFailure(call: Call<Post>, t: Throwable) {
//                    Log.i("ERROR", t.message.toString())
//                    onResult(null)
//                }
//
//                override fun onResponse(
//                    call: Call<Post>,
//                    response: Response<Post>
//                ) {
//                    Log.i("CODE", response.code().toString())
//                    Log.i("SUCCESS", response.body().toString())
//                    onResult(response.body())
//                }
//            }
//        )
//    }

    fun getAllPosts(
        pageId: Int,
        onResult: (
            List<Post>?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getAllPosts(pageId).enqueue(
            object : Callback<List<Post>> {
                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<List<Post>>,
                    response: Response<List<Post>>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult(response.body())
                }
            }
        )
    }

    fun getPost(
        postId: Int,
        onResult: (
            Post?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getPost(postId).enqueue(
            object : Callback<Post> {
                override fun onFailure(call: Call<Post>, t: Throwable) {
                    Log.i("get post ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Post>,
                    response: Response<Post>
                ) {
                    Log.i("get post code", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult(response.body())
                }
            }
        )
    }

    fun deletePost(
        postId: Int,
        onResult: (
            Void?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.deletePost(postId).enqueue(
            object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult(response.body())
                }
            }
        )
    }

    fun getPostsBySection(
        sectionId: Int,
        pageId: Int,
        onResult: (
            List<Post>
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getPostsBySection(sectionId, pageId).enqueue(
            object : Callback<List<Post>> {
                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(emptyList())
                }

                override fun onResponse(
                    call: Call<List<Post>>,
                    response: Response<List<Post>>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    response.body()?.let { onResult(it) }
                }
            }
        )
    }

    fun getSubscriptionPosts(
        pageId: Int,
        onResult: (
            List<Post>
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getSubscriptionPosts(pageId).enqueue(
            object : Callback<List<Post>> {
                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(emptyList())
                }

                override fun onResponse(
                    call: Call<List<Post>>,
                    response: Response<List<Post>>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    response.body()?.let { onResult(it) }
                }
            }
        )
    }

    fun getPostsByKeywordInTitle(
        keywords: String,
        pageId: Int,
        onResult: (
            List<Post>?
        ) -> Unit
    ) {
        Log.i("TRY KW", keywords)
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getPostsByKeywordInTitle(keywords, pageId).enqueue(
            object : Callback<List<Post>> {
                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<List<Post>>,
                    response: Response<List<Post>>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult(response.body())
                }
            }
        )
    }

    fun getPostsByUser(
        username: String,
        pageId: Int,
        onResult: (
            List<Post>?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getPostsByUser(username, pageId).enqueue(
            object : Callback<List<Post>> {
                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(emptyList())
                }

                override fun onResponse(
                    call: Call<List<Post>>,
                    response: Response<List<Post>>
                ) {
                    val body = response.body()
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult(body)
                }
            }
        )
    }

    // reaction-controller
    //
    fun vote(
        data: Requests.VoteRequest,
        onResult: (
            Void?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.vote(data).enqueue(
            object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult(response.body())
                }
            }
        )
    }

    // section-controller
    //
    fun createSection(
        token: String,
        data: Requests.CreateSectionRequest,
        onResult: (
            Section?
        ) -> Unit
        ) {
            val retrofit = ServiceBuilder.buildService(RestApi::class.java)
            val call = retrofit.createSection(
//                token,
                data
            )
        Log.i("SEND", call.request().body().toString())
        call.enqueue(
            object : Callback<Section> {
                override fun onFailure(call: Call<Section>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Section>,
                    response: Response<Section>
                ) {
                    val body = response.body()
                    Log.i("CODE", response.headers().toString())
                    Log.i("SUCCESS", "theme created ${body?.name}")
                    onResult(body)
                }
            }
        )
    }

    fun getAllSection(
        pageId: Int,
        onResult: (
            List<Section>?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getAllSection(pageId).enqueue(
            object : Callback<List<Section>> {
                override fun onFailure(call: Call<List<Section>>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<List<Section>>,
                    response: Response<List<Section>>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult(response.body())
                }
            }
        )
    }

    fun getSection(
        sectionId: Int,
        onResult: (
            Section?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getSection(sectionId).enqueue(
            object : Callback<Section> {
                override fun onFailure(call: Call<Section>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Section>,
                    response: Response<Section>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult(response.body())
                }
            }
        )
    }

    fun getSectionByKeywordsInTitle(
        keywords: String,
        pageId: Int,
        onResult: (
            List<Section>?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getSectionByKeywordsInTitle(keywords, pageId).enqueue(
            object : Callback<List<Section>> {
                override fun onFailure(call: Call<List<Section>>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<List<Section>>,
                    response: Response<List<Section>>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult(response.body())
                }
            }
        )
    }

    fun subscribeToSection(
        postId: Int,
        onResult: (
            String?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.subscribeToSection(postId).enqueue(
            object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult("OK")
                }
            }
        )
    }

    // user-controller
    fun verifyAccount(
        token: String,
        onResult: (
            String?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.verifyAccount(token).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    val body = response.body()
                    onResult(body)
                }
            }
        )
    }

    fun login(
        data: Requests.LoginRequest,
        onResult: (AuthorizedData?) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        Log.i("AUTH", "${data.username} ${data.password}")
        retrofit.login(data).enqueue(object : Callback<AuthorizedData> {
            override fun onResponse(
                call: Call<AuthorizedData>,
                response: Response<AuthorizedData>
            ) {
                val code = response.code()
                val auth = response.body()
                authorizedData = auth
                Log.i("CODE", response.code().toString())
                Log.i("SUCCESS", response.body().toString())
                onResult(auth)
            }

            override fun onFailure(call: Call<AuthorizedData>, t: Throwable) {
                Log.i("ERROR", t.message.toString())
                onResult(null)

            }
        })
    }

    fun logout(
        data: Requests.LogoutRequest,
        onResult: (
            String?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.logout(data).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult("OK")
                }
            }
        )
    }

    fun uploadProfilePicture() {
        // TODO
    }

    fun getProfilePicture() {
        // TODO
    }

    fun refreshToken(
        data: Requests.RefreshTokenRequest,
        onResult: (
            AuthorizedData?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.refreshToken(data).enqueue(
            object : Callback<AuthorizedData> {
                override fun onFailure(call: Call<AuthorizedData>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<AuthorizedData>,
                    response: Response<AuthorizedData>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult(response.body())
                }
            }
        )

    }

    fun signup(
        data: Requests.SignupRequest,
        onResult: (
            String?
        ) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.signup(data).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult("OK")
                }
            }
        )

    }

    fun updatePassword(
        data: Requests.UpdatePasswordRequest,
        onResult: (String?) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.updatePassword(data).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i("ERROR", t.message.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    Log.i("CODE", response.code().toString())
                    Log.i("SUCCESS", response.body().toString())
                    onResult("OK")
                }
            }
        )
    }
}
