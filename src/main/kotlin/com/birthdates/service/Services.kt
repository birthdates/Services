package com.birthdates.service

import com.birthdates.service.impl.CoreServiceManager
import com.sun.org.apache.xerces.internal.utils.ObjectFactory

class Services {
    companion object {
        private val serviceManager: ServiceManager = CoreServiceManager()

        fun load() {
            serviceManager.registerAllServices()
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