package com.birthdates.service

interface ServiceManager {
    fun <T : Service> getService(service: Class<T>): T
    fun doesServiceExist(service: Class<out Service>): Boolean
    fun <T : Service> registerService(service: Class<T>, serviceImplementation: Class<out T>)
    fun registerAllServices()
    fun unload() {}
}