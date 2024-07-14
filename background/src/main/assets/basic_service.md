### Basic Service

``` kotlin
class BasicService : Service() {

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
        for (i in 0 until 100) {
            Thread.sleep(1000)
            log("Service is working $i in ${Thread.currentThread().name} thread")
        }
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 3 метод ЖЦ. Вызывается при уничтожении сервиса.
     */
    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    /**
     * Обязателен для переопределения!
     */
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String) {
        Log.d("MyLog", "BasicService: $message")
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, BasicService::class.java)
        }
    }
}
```

### Service with background
```kotlin
class BasicServiceWithBackground : Service() {

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
        fun newIntent(context: Context): Intent {
            return Intent(context, BasicServiceWithBackground::class.java)
        }
    }
}
```

### Activity
```kotlin
internal class ServiceActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityServiceBinding.inflate(layoutInflater)
    }

    private val logsReceiver = object : BroadcastReceiver() {
        private val logs = StringBuilder()
        override fun onReceive(context: Context, intent: Intent) {
            val log = intent.getStringExtra("log") ?: "empty"
            logs.append(log).append("\n")
            binding.logs.text = logs.toString()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        LocalBroadcastManager.getInstance(this).registerReceiver(logsReceiver, IntentFilter("BasicServiceLogs"))
        binding.blockingService.setOnClickListener {
            startService(BasicService.newIntent(this))
        }
        binding.notBlockingService.setOnClickListener {
            startService(BasicServiceWithBackground.newIntent(this))
        }
        binding.logs.movementMethod = ScrollingMovementMethod()
    }

    internal companion object {
        fun start(context: Context) =
            Intent(context, ServiceActivity::class.java).apply {
                context.startActivity(this)
            }
    }

}
```
