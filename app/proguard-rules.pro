# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/winnerawan/Library/Android/sdk/tools/proguard/proguard-android.txt
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

# view res/layout/abc_screen_simple_overlay_action_mode.xml #generated:23
-keep class android.support.v7.internal.widget.FitWindowsFrameLayout { <init>(...); }

