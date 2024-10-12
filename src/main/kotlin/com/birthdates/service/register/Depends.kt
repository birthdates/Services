package com.birthdates.service.register

import com.birthdates.service.Service
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class Depends(val services: Array<KClass<out Service>>)