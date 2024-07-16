### Work Manager

``` kotlin
/**
 * Worker + WorkManager - это замена сервисам
 * из библиотеки JetPack.
 *
 * Внутри Worker нет доступа к контексту,
 * Context передаётся в конструктор.
 *
 * WorkerParameters содержит inputData, в котором как
 * в Bundle храняться данные парами ключ-значение.
 *
 * При использовании WorkManager ничего не нужно
 * регистрировать в манифесте (это делается автоматически).
 */
internal class MyWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
) : Worker(context, workerParams) {

    /**
     * Вся работа выполняется в методе doWork (обязателен для переопределения).
     * Код в doWork выполняется в другом потоке (как в IntentService).
     *
     * doWork может вернуть 3 значения:
     * - Result.success() - работа завершена успешно.
     * - Result.failure() - ошибка, Worker не перезапустится.
     * - Result.retry() - ошибка, Worker перезапустится.
     */
    override fun doWork(): Result {
        log(context, "doWork")
        val page = workerParams.inputData.getInt(PAGE, 0)
        for (i in 0 until 5) {
            Thread.sleep(1000)
            log(context,"page $page loading ${i * 10} %")
        }
        return Result.success()
    }

    companion object {

        private const val PAGE = "page"

        const val WORK_NAME = "work name"

        fun makeRequest(page: Int): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<MyWorker>().apply {
                setInputData(workDataOf(PAGE to page))
                setConstraints(makeConstraints())
            }.build()
        }

        private fun makeConstraints() = Constraints.Builder()
            .setRequiresCharging(true)
            .build()
    }

    private fun log(context: Context, message: String) {
        val text = "Worker: $message"
        Log.d("MyLog", text)
        context.notifyLog(15, text)
    }
}

class WorkManagerActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityWorkManagerBinding.inflate(layoutInflater)
    }

    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        createLoggingNotificationChannel()

        val workManager = WorkManager.getInstance(applicationContext)

        binding.workManager.setOnClickListener {
            workManager.enqueueUniqueWork(
                MyWorker.WORK_NAME,
                ExistingWorkPolicy.APPEND,
                MyWorker.makeRequest(page++)
            )
        }
        
        binding.markdown.loadMarkdownFile("file:///android_asset/job_service.md")
    }

    internal companion object {
        fun start(context: Context) =
            Intent(context, WorkManagerActivity::class.java).apply {
                context.startActivity(this)
            }
    }

}
```