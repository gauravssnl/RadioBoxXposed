package com.gauravssnl.radiobox

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class Xposed : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpParam: XC_LoadPackage.LoadPackageParam) {
        val packageName = "com.finallevel.radiobox"
        XposedBridge.log("Trying to find the package $packageName & hook methods")
        if (!lpParam.packageName.equals(packageName)) return
        val clazz: Class<*> =
            XposedHelpers.findClassIfExists("$packageName.MainActivity", lpParam.classLoader)
        val hashMap = hashMapOf<String, Any>("noAd" to true)
        val methodName = "L0"
        XposedHelpers.findAndHookMethod(clazz, methodName, XC_MethodReplacement.returnConstant(hashMap))
        XposedBridge.log("All hooking completed for the package $packageName")
    }

}