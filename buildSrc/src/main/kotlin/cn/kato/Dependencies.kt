package cn.kato

object Versions {
    const val coreKtx = "1.3.2"
    const val appCompat = "1.2.0"
    const val materialDesign = "1.3.0"
    const val constraintLayout = "2.0.4"
    const val viewModel = "2.4.0"
    const val liveData = "2.4.0"
    const val activityKtx = "1.4.0"
    const val swiperefreshlayout = "1.1.0"
}

@Suppress("unused")
object Deps {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModel}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveData}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
}