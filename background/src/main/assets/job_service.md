### Job service
JobService и JobScheduler позволяют выполнять задачи в фоне 
и при этом не показывать никаких уведомлений пользователю.

``` kotlin
/**
 * JobService может выполняться без показа уведомления.
 * На JobService накладываются ограничения, при каких условиях сервис
 * будет выполняться (например, при подключении к WiFi, или при зарядке).
 *
 * Для работы JobService нужно добавить резрешение
 * в тег сервиса в манифесте:
 * <service android:name=".services.job_dispatcher.MyJobService"
 *   android:permission="android.permission.BIND_JOB_SERVICE"/>
 */
class MyJobService : JobService() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        createLoggingNotificationChannel()
        log("onCreate")
    }

    /**
     * Тут выполняется вся работа сервиса.
     * Код onStartJob выполняется в главном потоке.
     * onStartJob: Boolean говорит о том, выполняется ли ещё работа.
     * - true - нужно возращать при выполнении асинхронной работы (как правило).
     * - false - сервис завершил работу (синхронно как правило).
     *
     * jobFinished - для ручного завершения сервиса (при асинхронной работе).
     * Второй boolean-параметр - нужно ли перезапускать сервис.
     */
    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartJob")
        coroutineScope.launch {
            for (page in 0 until 10) {
                for (i in 0 until 10) {
                    delay(1000)
                    log("JobService: page $page loading ${i * 10} %")
                }
            }
            jobFinished(params, true)
        }
        return true
    }

    /**
     * onStopJob - вызывается, когда система убивает сервис,
     * но не когда вручную вызывается jobFinished.
     * onStopJob: Boolean - нужно ли перезапустить сервис
     * после того, как его убила система.
     */
    override fun onStopJob(params: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("MyLog", "MyJobService: $message")
        notifyLog(11, message)
    }

    companion object {
        const val JOB_ID = 111
    }
}

class JobServiceWithParam : JobService() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        createLoggingNotificationChannel()
        log("onCreate")
    }

    /**
     * Если запустить JobService с новыми параметрами чезез schedule,
     * старый сервис отменится и запустится новый.
     */
    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartJob")
        val page = params?.extras?.getInt(PAGE) ?: 0

        coroutineScope.launch {
            for (i in 0 until 10) {
                delay(1000)
                log("JobService: page $page loading ${i * 10} %")
            }
            jobFinished(params, true)
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("MyLog", "MyJobService: $message")
        notifyLog(11, message)
    }

    companion object {
        const val JOB_ID = 112
        private const val PAGE = "page"

        fun newBundle(page: Int) = PersistableBundle().apply {
            putInt(PAGE, page)
        }
    }
}

class ScheduledJobService : JobService() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        createLoggingNotificationChannel()
        log("onCreate")
    }

    /**
     * Если запустить JobService с новыми параметрами чезез schedule,
     * старый сервис отменится и запустится новый.
     */
    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartJob")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            coroutineScope.launch {
                var workItem = params?.dequeueWork() // берём первый элемент очереди
                // проходимся по очереди
                while (workItem != null) {
                    val page = workItem.intent.getIntExtra(PAGE, 0)
                    for (i in 0 until 10) {
                        delay(1000)
                        log("JobService: page $page loading ${i * 10} %")
                    }
                    params?.completeWork(workItem) //завершение сервиса из очереди
                    workItem = params?.dequeueWork() // берём следующий элемент очереди
                }
            }
            jobFinished(params, true) //завершение сервиса вцелом
        }

        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("MyLog", "ScheduledJobService: $message")
        notifyLog(13, message)
    }

    companion object {
        const val JOB_ID = 113
        private const val PAGE = "page"

        fun newIntent(page: Int) = Intent().apply {
            putExtra(PAGE, page)
        }
    }
}

internal class JobServiceActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityJobServiceBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.jobService.setOnClickListener {
            val componentName = ComponentName(this, MyJobService::class.java)

            /**
             * JobInfo - объект с ограничениями для JobService
             */
            val jobInfo = JobInfo.Builder(MyJobService.JOB_ID, componentName)
                // устройство должно заряжаться:
                .setRequiresCharging(true)
                // необхоимо подключение к wifi
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                // перезапускать после выключения устройства
                .setPersisted(true)
                .build()

            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.schedule(jobInfo)
        }

        binding.buttonSavePages.setOnClickListener {
            binding.jobServiceParams.text = "Start JobService with param: ${binding.editTextPagesInput.text}"
            binding.scheduledJobService.text = "Start ScheduledJobService with param: ${binding.editTextPagesInput.text}"
            binding.editTextPagesInput.clearFocus()
        }

        binding.jobServiceParams.setOnClickListener {
            val componentName = ComponentName(this, JobServiceWithParam::class.java)

            val jobInfo = JobInfo.Builder(JobServiceWithParam.JOB_ID, componentName)
                // передача параметров
                .setExtras(JobServiceWithParam.newBundle(binding.editTextPagesInput.text.toString().toInt()))
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .build()

            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.schedule(jobInfo)
        }

        binding.scheduledJobService.setOnClickListener {
            val componentName = ComponentName(this, ScheduledJobService::class.java)

            val jobInfo = JobInfo.Builder(ScheduledJobService.JOB_ID, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
//                .setPersisted(true) - нельзя использовать для очереди сервисов
                .build()

            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            val page = binding.editTextPagesInput.text.toString().toInt()

            /**
             * С Android 8 (API 26) можно выполнять JobService последовательно
             * друг за другом через метод enqueue.
             *
             * При API 26 аналог - IntentService.
             * 
             * JobIntent делает эту проверку под капотом.
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val intent = ScheduledJobService.newIntent(page)
                jobScheduler.enqueue(jobInfo, JobWorkItem(intent))
            } else {
                startService(MyIntentService2.newIntent(this, page))
            }
        }

        binding.markdown.loadMarkdownFile("file:///android_asset/job_service.md")
    }

    internal companion object {
        fun start(context: Context) =
            Intent(context, JobServiceActivity::class.java).apply {
                context.startActivity(this)
            }
    }

}
```