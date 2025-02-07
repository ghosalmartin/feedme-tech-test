package com.cba.feedmetechtest.producer.messaging

import java.io.Reader
import javax.net.SocketFactory

class Consumer(
    private val reader: Reader =
        SocketFactory
            .getDefault()
            .createSocket(System.getenv("PROVIDER_IP"), System.getenv("PROVIDER_PORT").toInt())
            .getInputStream()
            .bufferedReader()
) {
    fun consume(listener: (String) -> Unit) {
        reader.forEachLine {
            listener.invoke(it)
        }
    }
}