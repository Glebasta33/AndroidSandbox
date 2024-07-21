### CustomObservableActivity 
``` kotlin 


/**
 * RxJava - библиотека для асинхронного программирования,
 * которая работает на основе реактивных потоков
 * (последовательность данных, на которую можно подписаться и реагировать).
 *
 * RxJava основана на паттерне "наблюдатель".
 * Основные сущности:
 * - Observable - источник данных.
 * - Subscriber - потребитель данных.
 *
 * Observable эмитит данные и завершает работу (успешно или с ошибкой).
 * Для каждого Subscriber, подписанного на Observable,
 * вызывается метод Subscriber.onNext() для каждого элемента
 * потока данных, после которого может быть вызван
 * как Subscriber.onComplete(), так и Subscriber.onError().
 */
class CustomObservableActivity : ComponentActivity() {

    private val disposables = CompositeDisposable()
    private val simpleObservable = Observable.create<String> { emmiter ->
        repeat(5) { i ->
            emmiter.onNext("Hello, RxJava $i!")
            Thread.sleep(1000)
        }

        emmiter.onComplete()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarkdownFrom(fileName = "custom_observable.md", modifier = Modifier.fillMaxSize())
            val disposable = simpleObservable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { string ->
                        toast("onNext: $string")
                    },
                    { error ->
                        toast("onError: ${error.message}")
                    },
                    {
                        toast("onComplete")
                    }
                )

            disposables.add(disposable)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        fun start(context: Context) {
            Intent(context, CustomObservableActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this@CustomObservableActivity, text, Toast.LENGTH_SHORT).show()
    }
}

```