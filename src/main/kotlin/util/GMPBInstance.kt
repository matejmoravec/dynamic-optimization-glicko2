package util

enum class GMPBInstance(val config: GMPBConfig) {
    F1(GMPBConfig(31, 100, 5, 5, 5000, 1, 100)),
    F2(GMPBConfig(31, 100, 10, 5, 5000, 1, 100)),
    F3(GMPBConfig(31, 100, 25, 5, 5000, 1, 100)),
    F4(GMPBConfig(31, 100, 50, 5, 5000, 1, 100)),
    F5(GMPBConfig(31, 100, 100, 5, 5000, 1, 100)),
    F6(GMPBConfig(31, 100, 10, 5, 2500, 1, 100)),
    F7(GMPBConfig(31, 100, 10, 5, 1000, 1, 100)),
    F8(GMPBConfig(31, 100, 10, 5, 500, 1, 100)),
    F9(GMPBConfig(31, 100, 10, 10, 5000, 1, 100)),
    F10(GMPBConfig(31, 100, 10, 20, 5000, 1, 100)),
    F11(GMPBConfig(31, 100, 10, 5, 5000, 2, 100)),
    F12(GMPBConfig(31, 100, 10, 5, 5000, 5, 100));
}