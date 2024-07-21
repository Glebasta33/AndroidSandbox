### NetworkCallActivity 
``` kotlin 


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

```