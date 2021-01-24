package com.bigdogz.productmanager.config

import org.slf4j.LoggerFactory

interface Log {
    fun logger() = LoggerFactory.getLogger(this.javaClass)
}
