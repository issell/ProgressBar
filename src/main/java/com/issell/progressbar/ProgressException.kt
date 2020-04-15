package com.issell.progressbar

import java.io.PrintWriter
import java.io.StringWriter
import java.lang.Exception

class ProgressException(message:String):Exception(message)
fun stackTraceToString(message:String):String{
    val exception = ProgressException(message)
    val stringWriter = StringWriter()
    val printWriter = PrintWriter(stringWriter)
    exception.printStackTrace(printWriter)
    return printWriter.toString()
}
fun stackTraceToString(exception: Exception):String{
    val stringWriter = StringWriter()
    val printWriter = PrintWriter(stringWriter)
    exception.printStackTrace(printWriter)
    return printWriter.toString()
}