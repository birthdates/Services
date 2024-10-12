package com.birthdates.service.impl

import com.birthdates.service.register.Depends
import com.birthdates.service.Service
import com.birthdates.service.ServiceManager
import com.birthdates.service.register.Registers
import java.util.concurrent.CompletableFuture

class CoreServiceManager : ServiceManager {
    private val services = mutableMapOf<Class<*>, Service>()
    private val waitingForLoad = mutableMapOf<Class<*>, List<CompletableFuture<Void>>>().withDefault { mutableListOf() }

    override fun <T : Service> getService(service: Class<T>): T {
        if (!doesServiceExist(service)) {
            throw IllegalArgumentException("Service ${service.simpleName} does not exist")
        }
        return services[service] as T
    }

    override fun doesServiceExist(service: Class<out Service>): Boolean {
        return services.containsKey(service)
    }

    override fun <T : Service> registerService(serviceType: Class<T>, serviceImplementation: Class<out T>) {
        if (doesServiceExist(serviceType)) {
            throw IllegalArgumentException("Service ${serviceType.simpleName} already exists")
        }

        val depends = serviceImplementation.getAnnotation(Depends::class.java)
        if (depends == null) {
            innerRegisterService(serviceType, serviceImplementation)
            return
        }

        var futuresArray = ArrayList<CompletableFuture<Void>>(depends.services.size)
        depends.services.forEach {
            val serviceClass = it.java
            if (!doesServiceExist(serviceClass)) {
                val future = CompletableFuture<Void>()
                waitingForLoad[serviceClass] = waitingForLoad.getValue(serviceClass) + future
                futuresArray += future
            }
        }
        CompletableFuture.allOf(*futuresArray.toTypedArray()).thenRun { innerRegisterService(serviceType, serviceImplementation) }
    }

    fun <T : Service> innerRegisterService(service: Class<T>, serviceImplementation: Class<out T>) {
        try {
            val instance = serviceImplementation.constructors[0].newInstance() as Service
            services[service] = instance
            instance.load()
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to load service ${serviceImplementation.simpleName} for ${service.simpleName}", e)
        }

        waitingForLoad.remove(service)?.forEach { it.complete(null) }
    }

    override fun registerAllServices() {
        Registers.getTypeRegisters(Service::class.java).forEach {
            registerService(Registers.getRegisterInterface(it), it as Class<out Service>)
        }
    }
}