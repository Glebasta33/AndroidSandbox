### Bound Service

``` kotlin
internal class BoundService : Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val notificationManager: NotificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }
    private val notificationBuilder by lazy {
        createNotificationBuilder("Service is working")
    }
    var onProgressChanged: ((Int) -> Unit)? = null

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")
        coroutineScope.launch {
            for (i in 0..100 step 5) {
                delay(1000)
                val notification = notificationBuilder
                    .setProgress(100, i, false)
                    .build()
                notificationManager.notify(NOTIFICATION_ID, notification)
                onProgressChanged?.invoke(i)
                log("Timer $i")
            }
            stopSelf()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    inner class LocalBinder : Binder() {
        fun getService() = this@BoundService
    }

    override fun onBind(intent: Intent?): IBinder {
        log("onBind")
        return LocalBinder()
    }

    private fun log(message: String) {
        Log.d("MyLog", "BoundService: $message")
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

    private fun createNotificationBuilder(text: String) = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Bound Service")
        .setContentText(text)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setOnlyAlertOnce(true)
        .setProgress(100, 0, false)

    companion object {

        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_name"
        private const val NOTIFICATION_ID = 1

        fun newIntent(context: Context): Intent {
            return Intent(context, BoundService::class.java)
        }
    }
}

internal class BoundServiceActivity : AppCompatActivity() {

    private val binding by lazy {
        AcrivityBoundServiceBinding.inflate(layoutInflater)
    }

    /**
     * ServiceConnection реализует подписку на сервис
     */
    private val serviceConnection = object : ServiceConnection {
        /**
         * onServiceConnected вызывается при подписке на сервис.
         * В service: IBinder прилетит экземпляр Binder, который
         * мы возвращаем из onBind сервиса.
         */
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = (service as? BoundService.LocalBinder) ?: return
            val boundService = binder.getService()
            boundService.onProgressChanged = { progress ->
                binding.progressBar.progress = progress
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("MyLog", "ServiceConnection: onServiceDisconnected")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.boundService.setOnClickListener {
            startService(BoundService.newIntent(this))
        }
    }

    internal companion object {
        fun start(context: Context): Intent =
            Intent(context, BoundServiceActivity::class.java).apply {
                context.startActivity(this)
            }
    }

    /**
     * Подписка на сервис осуществляется методом bindService,
     * в который нужно передать экземпляр ServiceConnection
     */
    override fun onStart() {
        super.onStart()
        bindService(
            BoundService.newIntent(this),
            serviceConnection,
            0
        )
    }

    override fun onStop() {
        super.onStop()
        unbindService(serviceConnection)
    }
}
```