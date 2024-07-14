### Service constants
``` kotlin
class RestartingService : Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * 1 метод ЖЦ. Вызывается при создании сервиса.
     */
    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    /**
     * 2 метод ЖЦ. Тут выполняется вся работа.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")
        //код сервиса выполняется в главном потоке, а значит блокирует его!
        coroutineScope.launch {
            for (i in 0 until 100) {
                delay(1000)
                log("Service is working $i in ${Thread.currentThread().name} thread")
            }
        }

        if (intent != null) {
            return when(intent.getStringExtra(START_SERVICE_CONSTANT)) {
                "START_NOT_STICKY" -> START_NOT_STICKY
                "START_STICKY" -> START_STICKY
                "START_REDELIVER_INTENT" -> START_REDELIVER_INTENT
                else -> super.onStartCommand(intent, flags, startId)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 3 метод ЖЦ. Вызывается при уничтожении сервиса.
     */
    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    /**
     * Обязателен для переопределения!
     */
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String) {
        Log.d("MyLog", "ServiceWithBackground: $message")
        val intent = Intent("BasicServiceLogs").apply {
            putExtra("log", "ServiceWithBackground: $message")
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    companion object {
        private const val START_SERVICE_CONSTANT = "START_SERVICE_CONSTANT"
        fun newIntent(context: Context, constant: String): Intent {
            return Intent(context, RestartingService::class.java).apply {
                putExtra(START_SERVICE_CONSTANT, constant)
            }
        }
    }
}
```

### Foreground Service
```kotlin
internal class ForegroundService : Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var notificationManager: NotificationManager

    @RequiresApi(Build.VERSION_CODES.ECLAIR)
    override fun onCreate() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        super.onCreate()
        log("onCreate")
        createNotificationChannel()
        // единственное, что отличает ForegroundService от обычного сервиса - необходимо вызвать startForeground:
        startForeground(NOTIFICATION_ID, createNotification("Service is working"))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")
        coroutineScope.launch {
            for (i in 0 until 100) {
                delay(1000)
                log("Timer $i")
                notificationManager.notify(
                    NOTIFICATION_ID,
                    createNotification("Service is working with ${intent?.getStringExtra(START_SERVICE_CONSTANT)}: $i")
                )
            }
            stopSelf()
        }
        if (intent != null) {
            return when (intent.getStringExtra(START_SERVICE_CONSTANT)) {
                "START_NOT_STICKY" -> START_NOT_STICKY
                "START_STICKY" -> START_STICKY
                "START_REDELIVER_INTENT" -> START_REDELIVER_INTENT
                else -> super.onStartCommand(intent, flags, startId)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyForegroundService: $message")
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification(text: String) = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Foreground Service")
        .setContentText(text)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setSilent(true)
        .build()

    companion object {

        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_name"
        private const val NOTIFICATION_ID = 1

        private const val START_SERVICE_CONSTANT = "START_SERVICE_CONSTANT"
        fun newIntent(context: Context, constant: String): Intent {
            return Intent(context, ForegroundService::class.java).apply {
                putExtra(START_SERVICE_CONSTANT, constant)
            }
        }
    }
}
```

### Activity
```kotlin
internal class ForegroundServiceActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityForegroundServiceBinding.inflate(layoutInflater)
    }

    private val constants = listOf(
        "START_STICKY",
        "START_NOT_STICKY",
        "START_REDELIVER_INTENT"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.startServiceConstantSpinner.apply {
            val arrayAdapter = ArrayAdapter(
                this@ForegroundServiceActivity,
                R.layout.simple_spinner_item,
                constants
            )
            adapter = arrayAdapter
            arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            prompt = "Выбор константы для onStartCommand"
            setSelection(0)
        }

        binding.startServiceConstantSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    binding.serviceWithConstant.text =
                        "Запустить сервис с константой: ${constants[position]}"
                    binding.foregroundService.text =
                        "Запустить Foreground Service с константой: ${constants[position]}"
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }

        binding.serviceWithConstant.text =
            "Запустить сервис с константой: ${binding.startServiceConstantSpinner.selectedItem as String}"
        binding.foregroundService.text =
            "Запустить Foreground Service с константой: ${binding.startServiceConstantSpinner.selectedItem as String}"

        binding.serviceWithConstant.setOnClickListener {
            startService(
                RestartingService.newIntent(
                    context = this@ForegroundServiceActivity,
                    constant = binding.startServiceConstantSpinner.selectedItem as String
                )
            )
        }

        binding.foregroundService.setOnClickListener {
            // startForegroundService обязывает в течение 5 секунд вызвать startForeground в сервисе, иначе приложение упадёт.
            ContextCompat.startForegroundService(
                this,
                ForegroundService.newIntent(
                    context = this@ForegroundServiceActivity,
                    constant = binding.startServiceConstantSpinner.selectedItem as String
                )
            )
        }

        binding.stopForegroundService.setOnClickListener {
            stopService(
                ForegroundService.newIntent(
                    context = this@ForegroundServiceActivity,
                    constant = ""
                )
            )
        }

        binding.markdown.loadMarkdownFile("file:///android_asset/foreground_service.md")
    }

    internal companion object {
        fun start(context: Context) =
            Intent(context, ForegroundServiceActivity::class.java).apply {
                context.startActivity(this)
            }
    }
}
```
