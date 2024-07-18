### ServiceActivity``` kotlin 


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

        //TODO: Сделать ListView + Adapter для отрисовки списка из MarkdownView
        binding.markdown1.loadMarkdownFile("file:///android_asset/basic_service.md")
        binding.markdown2.loadMarkdownFile("file:///android_asset/basic_service_with_background.md")
        binding.markdown3.loadMarkdownFile("file:///android_asset/service_activity.md")
    }

    internal companion object {
        fun start(context: Context) =
            Intent(context, ServiceActivity::class.java).apply {
                context.startActivity(this)
            }
    }

}

```