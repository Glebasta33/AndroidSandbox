### BasicServiceWithBackground``` kotlin 


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