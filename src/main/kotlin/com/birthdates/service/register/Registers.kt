package com.birthdates.service.register

import org.reflections.Reflections
import org.reflections.scanners.Scanners
import kotlin.collections.get

class Registers {
    companion object {
        private val REFLECTIONS = Reflections(Scanners.MethodsAnnotated, Scanners.TypesAnnotated)

        fun <T> getRegisterInterface(register: Class<*>): Class<T> {
            print("")
            return register.interfaces[0] as Class<T>
        }

        fun <T> getTypeRegisters(superInterface: Class<T>? = null): List<Class<T>> {
            val filtered = REFLECTIONS.getTypesAnnotatedWith(Register::class.java).filter {
                superInterface == null || !it.interfaces.isEmpty() && superInterface.isAssignableFrom(it.interfaces[0])
            }
            return filtered as List<Class<T>>
        }
    }
}