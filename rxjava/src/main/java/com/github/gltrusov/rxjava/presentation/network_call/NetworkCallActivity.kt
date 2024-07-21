package com.github.gltrusov.rxjava.presentation.network_call

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.MutableLiveData
import com.github.gltrusov.rxjava.data.ApiFactory
import com.github.gradle_sandbox.Markdown
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

@Markdown("rxjava_network.md")
class NetworkCallActivity : ComponentActivity() {

    private val compositeDisposable = CompositeDisposable()
    private val tasksLiveData = MutableLiveData<TasksScreenState>(TasksScreenState.Loading)

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        asyncLoadTasks()
        setContent {
            val state = tasksLiveData.observeAsState()
            state.value?.let { TasksScreen(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()

    }

    private fun asyncLoadTasks() {
        val disposable = ApiFactory.apiService.getListOfTasks()
            .subscribeOn(Schedulers.io()) // поток чтения данных
            .doOnEach {
                Thread.sleep(3000) // для загрузки
            }
            .observeOn(AndroidSchedulers.mainThread()) // ui-поток Android
            .doOnError {
                tasksLiveData.postValue(TasksScreenState.Error(it.message.toString()))
            }
            .subscribe { tasks ->
                val loadedState = TasksScreenState.Loaded(tasks)
                tasksLiveData.postValue(loadedState)
            }

        compositeDisposable.add(disposable)
    }

    internal companion object {
        fun start(context: Context): Intent =
            Intent(context, NetworkCallActivity::class.java).apply {
                context.startActivity(this)
            }
    }

}