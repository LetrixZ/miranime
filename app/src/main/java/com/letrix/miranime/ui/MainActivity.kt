package com.letrix.miranime.ui

import GetAnimeQuery
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.letrix.miranime.R
import com.letrix.miranime.data.Data
import com.letrix.miranime.data.network.ApiService
import dagger.hilt.android.AndroidEntryPoint
import io.github.wax911.library.model.body.GraphContainer
import io.github.wax911.library.model.request.QueryContainerBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var apolloClient: ApolloClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retrofitGraphQl()
    }

    fun retrofitGraphQl() {
        CoroutineScope(Dispatchers.IO).launch {
            val query = QueryContainerBuilder()
            val anime = apiService.getHome(query)
            Timber.d(anime.errorBody()?.string())
        }
    }

    fun apollo() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = try {
                apolloClient.query(GetAnimeQuery(idMal = 40060)).await()
            } catch (e: ApolloException) {
                Timber.d(e)
                return@launch
            }

            val launch = response.data
            if (launch == null || response.hasErrors()) {
                // handle application errors
                return@launch
            }

            // launch now contains a typesafe model of your data
            Timber.d(launch.toString())
        }
    }

    fun retrofit() {
        CoroutineScope(Dispatchers.IO).launch {
            val jsonObject = JsonObject()
            jsonObject.addProperty(
                "query",
                "{anime(idMal: 40060) { idMal idAl title synopsis synonyms genres type state poster banner year season episodes })"
            )
            jsonObject.addProperty("variables", "{}")
            apiService.getRetro(jsonObject)
        }
    }

    fun okhttp() {
        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient.Builder().build()
            val request = Request.Builder().url("http://192.168.1.40:4000/graphql")
                .post(
                    RequestBody.create(
                        MediaType.parse("application/json"),
                        query
                    )
                )
                .build()
            val call = client.newCall(request)
            val response = call.execute()
            if (response.isSuccessful) {
                val anime: GraphContainer<Data> = Gson().fromJson(
                    response.body()?.string(),
                    object : TypeToken<GraphContainer<Data>>() {}.type
                )
                Timber.d(anime.toString())
            }
        }
    }

    companion object {
        val query =
            "{\"query\":\"query (\$idMal: Int) {\\n  anime(idMal: \$idMal) { idMal idAl title synopsis synonyms genres type state poster banner year season episodes }\\n}\\n\",\"variables\":{\"idMal\": 40060}}"
    }
}