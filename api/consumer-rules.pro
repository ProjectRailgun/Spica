-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt # core serialization annotations

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}

-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keepclasseswithmembers class co.railgun.spica.api.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep class * implements co.railgun.spica.api.model.** {
    <fields>;
    <init>(...);
}

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class co.railgun.spica.api.**$$serializer { *; }
-keepclassmembers class co.railgun.spica.api.** {
    *** Companion;
}
-keepclasseswithmembers class co.railgun.spica.api.** {
    kotlinx.serialization.KSerializer serializer(...);
}
