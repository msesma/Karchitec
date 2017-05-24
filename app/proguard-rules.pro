-ignorewarnings
-renamesourcefileattribute SourceFile
-keepattributes Exceptions,SourceFile,LineNumberTable,*Annotation*,Signature

-keep public class * extends android.app.IntentService
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends com.google.inject.AbstractModule
-keep public class com.android.vending.licensing.ILicensingService
-keep class com.google.inject.** { *; }
-keep class javax.inject.** { *; }
-keep class javax.annotation.** { *; }

-keepclasseswithmembers class * {
    public <init> (android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init> (android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers,allowobfuscation class * {
    @org.codehaus.jackson.annotate.JsonProperty <init>(...);
    @org.codehaus.jackson.annotate.JsonIgnoreProperties <init>(...);
    @com.google.inject.Inject <init>(...);
    @com.google.inject.InjectResource <init>(...);
    @com.google.inject.Inject <fields>;

    public <methods>;
}

-dontwarn android.support.**

#retrofit
-keep class retrofit.** { *; }
-keep class es.tid.gconnect.api.** { *; }

### Google Play Services
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

#Remove logs
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** i(...);
    public static *** v(...);
    public static *** w(...);
    public static *** e(...);
    public static *** wtf(...);
    public static *** println(...);
}

#ButterKnife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# Retrolambda
-dontwarn java.lang.invoke.*