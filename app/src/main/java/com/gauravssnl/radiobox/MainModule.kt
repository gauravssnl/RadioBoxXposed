package com.gauravssnl.radiobox

import io.github.libxposed.api.XposedInterface
import io.github.libxposed.api.XposedInterface.BeforeHookCallback
import io.github.libxposed.api.XposedModule
import io.github.libxposed.api.XposedModuleInterface
import io.github.libxposed.api.XposedModuleInterface.ModuleLoadedParam
import io.github.libxposed.api.annotations.BeforeInvocation
import io.github.libxposed.api.annotations.XposedHooker

const val PACKAGE_NAME = "com.finallevel.radiobox"
const val METHOD_NAME = "L0"
const val MAP_KEY = "noAd"

private lateinit var module: MainModule

class MainModule(base: XposedInterface, param: ModuleLoadedParam) : XposedModule(base, param) {
    init {
        log("MainModule at :: " + param.processName)
        module = this
    }

    override fun onPackageLoaded(param: XposedModuleInterface.PackageLoadedParam) {
        super.onPackageLoaded(param)
        if (param.isFirstPackage) {
            log("onPackageLoaded :: ${param.packageName}")
            log("Try finding classes & hooking methods")
            val clazz: Class<*> = param.classLoader.loadClass("$PACKAGE_NAME.MainActivity")
            val methods = clazz.declaredMethods
            val filteredMethods = methods.filter { METHOD_NAME == it.name }
            if (filteredMethods.size == 1) {
                val method = filteredMethods[0]
                log("Found the method for hooking :: $method")
                hook(method, MyHooker::class.java)
            }
            log("All hooking completed for the package $PACKAGE_NAME")
        }
    }

    @XposedHooker
    class MyHooker : XposedInterface.Hooker {
        companion object {
            @JvmStatic
            @BeforeInvocation
            fun beforeInvocation(callback: BeforeHookCallback): MyHooker {
                if (callback.member.name == METHOD_NAME) {
                    callback.returnAndSkip(hashMapOf(MAP_KEY to true))
                }
                return MyHooker()
            }
        }
    }
}