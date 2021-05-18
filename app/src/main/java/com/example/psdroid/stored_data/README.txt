

README for Storing Data of your application

/////
** Android uses a file system that's similar to disk-based file systems on other platforms. **

The system provides several options for you to save your app data:

App-specific storage: Store files that are meant for your app's use only, either in dedicated directories within an internal storage
volume or different dedicated directories within external storage. Use the directories within internal storage to save sensitive
information that other apps shouldn't access.

1) Shared storage: Store files that your app intends to share with other apps, including media, documents, and other files.
2) Shared Preferences: Store private, primitive data in key-value pairs(like int,string,boolean,double,float).
3) Databases: Store structured data in a private database using the Room persistence library.(Usually SQL Lite)


** We can also use external libraries like ROOM, GSON , TinyDB & others for storing data quickly & easily **

ROOM : Room is a persistence library, part of the Android Jetpack. Room is now considered as a better approach for data
       persistence than SQLiteDatabase. It makes it easier to work with SQLiteDatabase objects in your app, decreasing the amount of
       boilerplate code and verifying SQL queries at compile time.
       https://medium.com/mindorks/using-room-database-android-jetpack-675a89a0e942

GSON :GSON is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a
      JSON string to an equivalent Java object. GSON can work with arbitrary Java objects including pre-existing objects that you do
      not have source-code of.
      There are a few open-source projects that can convert Java objects to JSON. However, most of them require that you place Java
      annotations in your classes; something that you can not do if you do not have access to the source-code. Most also do not fully
      support the use of Java Generics. GSON considers both of these as very important design goals.
      https://github.com/google/gson

TinyDB : Its a github based library which is very simple to use & is similar to GSON as it requires installation of GSON dependency
         https://github.com/kcochibili/TinyDB--Android-Shared-Preferences-Turbo

////