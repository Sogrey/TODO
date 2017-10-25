# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志

-dontwarn android.support.**
-dontwarn org.dom4j.**
-dontwarn com.google.gson.**
-dontwarn org.apache.**
-dontwarn org.apache.log4j.**
-dontwarn org.apache.commons.logging.**
-dontwarn org.apache.commons.codec.binary.**
#-Xlint:deprecation
#-Xlint:unchecked

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法

-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆

#####################在此之间写新加入的##################
#保持 xutils 包不被混淆
-keep public class * extends org.apache.http.client.methods.HttpRequestBase        # 保持哪些类不被混淆
#-keep public class * org.apache.http.HttpEntityEnclosingRequest        # 保持哪些类不被混淆
-keep public class com.lidroid.xutils.http.client.HttpRequest    # 保持哪些类不被混淆
-keep public class org.apache.http.HttpEntityEnclosingRequest    # 保持哪些类不被混淆

-keep class com.lidroid.**{ *; }
-keep class org.apache.http.**{ *; }
-keep class com.lidroid.xutils.**{*;}
#保持 ksoap2-android-assembly-2.5.4-jar-with-dependencies.jar 包不被混淆
-keep class org.kxml2.**{*;}
-keep class org.xmlpull.v1.**{*;}
-keep class org.ksoap2.**{*;}
-keep class org.kobjects.**{*;}
-keep class org.hibernate.**{*;}
-dontwarn  org.kxml2.**
-dontwarn  org.xmlpull.v1.**
-dontwarn  org.ksoap2.**
-dontwarn  org.kobjects.**
-keep class com.baidu.** {*;}


-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;}
-keep interface com.squareup.okhttp.** { *; }

-dontwarn okio.**



#腾讯地图 2D sdk
#-libraryjars libs/TencentMapSDK_Raster_v1.2.4_4af1d6f.jar
-keep class com.tencent.mapsdk.**{*;}
-keep class com.tencent.tencentmap.**{*;}

#腾讯地图检索sdk
#-libraryjars libs/TencentSearch1.1.3.jar
-keep class com.tencent.lbssearch.**{*;}
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-dontwarn com.tencent.lbssearch.**

#腾讯地图定位dk
#-libraryjars libs/TencentLocationSDK_v4.5.8.jar
-keep class com.tencent.map.geolocation.**{*;}

-keepattributes *Annotation*
-keepclassmembers class ** {
    public void on*Event(...);
}
-keepclasseswithmembernames class * {
    native <methods>;
}
-dontwarn  org.eclipse.jdt.annotation.**

# sdk版本小于18时需要以下配置, 建议使用18或以上版本的sdk编译
-dontwarn  android.location.Location
-dontwarn  android.net.wifi.WifiManager

-dontnote ct.**



# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }


 #Orm混淆建议：
 # 1. 给你要持久化的每一个Java（Model）类设置一个表名：即为类添加@Table("table_name")注解。
 # 2. 给你要持久化的每一个属性（成员变量）设置一个列名： 即为属性加@Column("column_name")注解。
 # 满足1、2则可以将你要持久化的类和者属性随意混淆；
 # 反之，则需要将你要持久化的类和属性keep住，不可混淆。

 #另外，枚举和注解、签名要keep：
 # 使用注解
 -keepattributes *Annotation*,Signature,Exceptions

 # For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
 -keepclassmembers enum * {
     **[] $VALUES;
     public *;
 }

 #使用1.5.3mini以及更新的版本请注意：
 #因为混淆压缩的问题，内部枚举类无法使用，导致1.5.3以及以后版本将枚举类从内部类调整出来成为独立的类，因此你的代码里如果用到里PrimaryKey.AssignType, Mapping.Relation, Conflict.Strategy，那么package包名需要重新引入一下，带来不便，敬请见谅。


#　ButterKnife的ProGuard设置

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#####################在此之间写新加入的##################


#-keep class * extends java.lang.annotation.Annotation { *; }

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}