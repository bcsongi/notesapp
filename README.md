# NotesApp -  individual project
![N|Solid](https://i.imgur.com/ASsNoos.png)

## Developer documentation
Role of ActivityMainfest.xml:
 - every app has to contain an ActivityMainfest.xml file in the root folder beacuse it provides necessery information,
 - it names the name of the java packages ,
 - describe the name of the app components (activities, services, broadcast receivers and content providers),
 - define the min version of API which neccessary to run the app (android:minSdkVersion=8),
 - seting up the launcher activity (<action android:name="android.intent.action.MAIN"/>),
 - seting up the app icon (<category android:name="android.intent.category.LAUNCHER"/>).

Role of ActivityMain.java:
- every app has to contain an ActivityMain.java file which has to extends Activity base class
- because I used fregment in this app, it had to implement FragmentActivity
### Fragment properties:
- fragments provides the UI development
- a big advantage is that we can separate fragment in the activity
- the fragment operations is handled by FragmentManager class, it use transaction to operate with it, it's important in case of memory or resource issue: ```java (fm.beginTransaction(),commit()).```
- fragments can open other fragments, in this case have to save the order of the fragments: ```java (fm.addToStack()).```


## Usage documentation
The NotesApp provides user data quick save, and to send it via email. Besides that, the app provides to add GPS coordinates into the note and saves these information.

In the left side optional menu you can choose from the following features:
- Create new note
- Delete all note

In the case of "Create a new note" appears a new window where you can set up the following information:
- Title
- Piority
- Due date
- Description

After that, the GUI lists the note items in the list format under each other with their title, date and a colorful circle which means the status of the note:
- red: high priority, very important
- yellow: low priority, medium important
- green: low priority, not important

