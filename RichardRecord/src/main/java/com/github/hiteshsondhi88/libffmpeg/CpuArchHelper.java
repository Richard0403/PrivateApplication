package com.github.hiteshsondhi88.libffmpeg;

import android.os.Build;
import android.util.Log;

class CpuArchHelper {

    static CpuArch getCpuArch() {
        // check if device is x86
    	Log.i("cpu", "cpu"+Build.CPU_ABI);
        if (Build.CPU_ABI.equals(getx86CpuAbi())) {
            return CpuArch.x86;
        }else if(Build.CPU_ABI.equals(getArm64v8CpuAbi())){
        	return CpuArch.ARMv8;
        } 
        else {
            // check if device is armeabi
            if (Build.CPU_ABI.equals(getArmeabiv7CpuAbi())) {
                ArmArchHelper cpuNativeArchHelper = new ArmArchHelper();
                String archInfo = cpuNativeArchHelper.cpuArchFromJNI();
                // check if device is arm v7
                if (cpuNativeArchHelper.isARM_v7_CPU(archInfo)) {
                    // check if device is neon
                    if (cpuNativeArchHelper.isNeonSupported(archInfo)) {
                        return CpuArch.ARMv7_NEON;
                    }
                    return CpuArch.ARMv7;
                }
            }
        }
        return CpuArch.NONE;
    }

    static String getx86CpuAbi() {
        return "x86";
    }

    static String getArmeabiv7CpuAbi() {
        return "armeabi-v7a";
    }
    static String getArm64v8CpuAbi() {
        return "arm64-v8a";
    }
}