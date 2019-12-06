package com.cba.feedmetechtest.producer.messaging

import java.io.BufferedReader
import java.net.Socket
import javax.net.SocketFactory

class Consumer(
    socket: Socket = SocketFactory.getDefault().createSocket("localhost", 8282),
    private val bufferedReader: BufferedReader = socket.getInputStream().bufferedReader()
) {
    fun consume(listener: (String) -> Unit){
        bufferedReader.useLines { sequence ->
            sequence.iterator().forEach { listener.invoke(it) }
        }
    }
}