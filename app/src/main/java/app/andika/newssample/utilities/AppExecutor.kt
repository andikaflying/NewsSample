package app.andika.newssample.utilities

import java.util.concurrent.Executors

public val IO_EXECUTOR = Executors.newSingleThreadExecutor()

//To run operation on different thread (name : background/worker thread) from UI thread. So it will not interfere main thread (alias UI thread)
fun runOnBackgroundThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}