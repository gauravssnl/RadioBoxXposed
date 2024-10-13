package com.gauravssnl.radiobox

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.Objects

class Xposed : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpParam: XC_LoadPackage.LoadPackageParam) {
        val packageName = "com.finallevel.radiobox"
        XposedBridge.log("Trying to find the package $packageName & hook methods")
        if (!lpParam.packageName.equals(packageName)) return
        val clazz: Class<*> =
            XposedHelpers.findClassIfExists("$packageName.MainActivity", lpParam.classLoader)
        val hashMap = hashMapOf<String, Any>("noAd" to true)
        val methodName = "L0"
        // Example Method signature :  public final HashMap<String, Object> m()
        val methods = clazz.declaredMethods
        var filteredMethods = methods.filter {
            it.parameterCount == 0 && Modifier.isPublic(
                it.modifiers
            ) && Modifier.isFinal(it.modifiers)
                    && it.returnType == HashMap::class.java
        }
        filteredMethods = filteredMethods.filter { methodName == it.name }
        if (filteredMethods.size == 1) {
            val method = filteredMethods[0]
            XposedBridge.log("Found the method for hooking :: $method")
            XposedBridge.hookMethod(method, XC_MethodReplacement.returnConstant(hashMap))
        }
        XposedBridge.log("All hooking completed for the package $packageName")
    }

}