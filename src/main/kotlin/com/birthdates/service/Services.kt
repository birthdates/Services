package com.birthdates.service

import com.birthdates.service.impl.CoreServiceManager

class Services {
    companion object {
        private val serviceManager: ServiceManager = CoreServiceManager()

        fun load() {
            serviceManager.registerAllServices()
        }

        inline fun <reified T : Service> fetch(): T {
            return get(T::class.java)
        }

        fun <T : Service> get(serviceType: Class<T>): T {
            return serviceManager.getService(serviceType)
        }

        fun <T : Service> register(serviceType: Class<T>, serviceImplementation: Class<out T>) {
            serviceManager.registerService(serviceType, serviceImplementation)
        }

        fun unload() {
            serviceManager.unload()
        }
    }
}