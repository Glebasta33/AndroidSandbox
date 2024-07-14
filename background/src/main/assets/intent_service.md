### Intent service

``` kotlin
/**
 * Минусы обычного сервиса:
 * - По дефолту выполняется в главном потоке.
 * - Нужно самостоятельно останавливать.
 * - При повторном запуске экземпляры сервиса дублируются.
 *
 * IntentService решает все эти проблемы.
 */
internal class MyIntentService : IntentService("MyIntentService") {

    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        super.onCreate()
        log("onCreate")
        setIntentRedelivery(true) // true - аналог STRAT_REDELIVER_INTENT, false - START_NOT_STICKY
        createNotificationChannel()
        // IntentService может быть запущен как Foreground
        startForeground(NOTIFICATION_ID, createNotification("Service is working"))
    }

    /**
     * onHandleIntent вместо onStartCommand.
     * После выполнения onHandleIntent IntentService будет автоматически остановлен.
     * Каждый запуск onHandleIntent будет выполняться ПОСЛЕДОВАТЕЛЬНО друг за другом!
     */
    override fun onHandleIntent(intent: Intent?) {
        log("onHandleIntent")
        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("IntentService is working $i")
            notificationManager.notify(
                NOTIFICATION_ID,
                createNotification("IntentService is working $i")
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("MyLog", "MyForegroundService: $message")
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
        .setContentTitle("Intent Service")
        .setContentText(text)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setSilent(true)
        .build()

    companion object {

        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_name"
        private const val NOTIFICATION_ID = 1

        fun newIntent(context: Context): Intent {
            return Intent(context, MyIntentService::class.java)
        }
    }
}
```