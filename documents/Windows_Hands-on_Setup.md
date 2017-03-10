# Hands-on setup guide (Windows)

### 1. Pre-requisites

* **Git**
If required check this link to install it : [Download Git](https://git-scm.com/download/win)

**To check** open a Git Bash Terminal and type : `git --version`

**Note:** All provided command will have to be issued in the Git Bash Terminal.

**Note:** We will call `<Archive directory>` the directory where are the provided archives into the `Hands-on`. This directory is coming from the USB pen and should be copied for instance on the Desktop or the Home directory. So when you will read : `cd <Archive directory>` you will understand `cd ~/Desktop` or `cd ~/Bureau` or `cd ~` depending where you've copied the `Hands-on` directory from the USB pen.

### 2. Create `c:\Hands-on`  directory 

`mkdir /c/Hands-on`

**To check** open a Git Bash Terminal and type : `ls /c/Hands-on`

#### 2.1 Install Android SDKs 

Unzip provided archive into your directory `c:\Hands-on`:

Open a Git Bash Terminal and type :

```
cd <Archive directory>
unzip ./Hands-on/Windows/Android.zip -d /c/Hands-on/
```

**To check** open a Git Bash Terminal and type : `ls /c/Hands-on/Android` and you should see `Sdk` and `Plugins` directories.

#### 2.2 Install Android Studio

Install provided archive into your directory `c:\Hands-on`:

Double click on `<Archive directory>\Hands-on\Windows\android-studio-bundle-145.3537739-windows.exe`

You will see the following window :
![](img/install-studio-win-0.1.png)

* Click `Next`
* Uncheck `Android SDK`
* Uncheck `Android Virtual Device`

![](img/install-studio-win-0.2.png)

* Click `Next`

![](img/install-studio-win-0.2.1.png)

* Click `I Agree`

* Set existing Android SDK path to : `c:\Hands-on\Android\Sdk`

![](img/install-studio-win-0.3.png)

* Configure installation directoriy to : `c:\Hands-on\Android Studio` for Android Studio

![](img/install-studio-win-0.3.1.png)

* Click `Next`

![](img/install-studio-win-0.4.png)

* Click `Install`

Wait for the installation finish.

![](img/install-studio-win-0.5.png)

* Click `Next`

![](img/install-studio-win-0.6.png)

* Uncheck `Start Android Studio`
* Click `Next`

#### 2.3 Hands-on project 

Unzip provided archive into your directory `c:\Hands-on`:

Open a Git Bash Terminal and type :

```
cd <Archive directory>
unzip ./Hands-on/Windows/2017-handson-kotlinAndroid.zip -d /c/Hands-on/
```

**To check** open a Git Bash Terminal and type : 

```
cd /c/Hands-on/2017-handson-kotlinAndroid
git lg
``` 

you should read :

```
* 9b50a75 (origin/solution, solution) Exercise 2.5 : Kotlin & Rx
* a911fe9 Exercise 2.4 : Lambda
* a7911d8 Exercise 2.3 : Function extensions
* 8112ad2 Exercise 2.2 : Kotlin extensions
* acbd955 Exercise 2.1 : Prepare MainActivity
* 5e9f1f6 (tag: End-Part1) Exercise 1.4 : Lateinit & Companion Object
* 1d67724 Exercise 1.3 : Collections
* 15a64ab Exercise 1.2 : When
* 76f22bc Exercise 1.1 : Data class kotlin
* 9003149 (HEAD -> master, origin/master, origin/HEAD) Mode offline (#1)
* 24d0724 Initial commit
```

Create your working branch: 

```
git checkout -b mywork
git lg
``` 

you should read :

```
* 9b50a75 (origin/solution, solution) Exercise 2.5 : Kotlin & Rx
* a911fe9 Exercise 2.4 : Lambda
* a7911d8 Exercise 2.3 : Function extensions
* 8112ad2 Exercise 2.2 : Kotlin extensions
* acbd955 Exercise 2.1 : Prepare MainActivity
* 5e9f1f6 (tag: End-Part1) Exercise 1.4 : Lateinit & Companion Object
* 1d67724 Exercise 1.3 : Collections
* 15a64ab Exercise 1.2 : When
* 76f22bc Exercise 1.1 : Data class kotlin
* 9003149 (HEAD -> mywork, origin/master, origin/HEAD, master) Mode offline (#1)
* 24d0724 Initial commit
```

### 3. Install Gradle cache 

**/!\ Warning: Due to Gradle open issue ([Gradle's cache stores the native OS absolute path](https://github.com/gradle/gradle/issues/1338)), you have to create exactly the same directory ! /!\\**


Unzip provided archive into your directory `C:\Hands-on`:

Open a Git Bash Terminal and type :

```
cd <Archive directory>
unzip ./Hands-on/Windows/gradle.zip -d /c/Hands-on/
```

**To check** open a Terminal and type : `ls /c/Hands-on/gradle` and you should see `gradle` directories.


### 4. Complete Android Studio offline setup 

Double click on Android Studio 
`c:\Hands-on\Android Studio\bin\studio64.exe` 

You will see...
![](img/install-studio-1-win.png)

Just ignore the message and click on `Cancel`

![](img/install-studio-2-win.png)

![](img/install-studio-3-win.png)

Select `Custom` installation

![](img/install-studio-4-win.png)

![](img/install-studio-5-win.png)


Update Android SDK location and set the path defined in chap. 2 above. Should be the absolute path of : `c:\Hands-on\Android\Sdk`

![](img/install-studio-6-win.png)

![](img/install-studio-7-win.png)

![](img/install-studio-8-win.png)

![](img/install-studio-9-win.png)

Open `configure` menu and select `Settings`

![](img/install-studio-9.2-win.png)

* Select `Build, Execution, Deployment | Gradle `
* Check `Offline work`
* Set service directory path to : `c:\Hands-on\gradle`
* Then Click on `Apply` button
![](img/install-studio-12-win.png)

* Select `Plugins`
* Click on `Install plugin from disk...` button

![](img/install-studio-13-win.png)

Kotlin 1.0.6 plugin is provided along with the Android SDK archive under the `Plugins` directory :

* `c:\Hands-on\Android\Plugins\kotlin-plugin-1.0.6-release-Studio2.2-1.zip`

![](img/install-studio-14-win.png)

![](img/install-studio-15-win.png)

* Then Click on `Apply` button
* Click on `Restart Android Studio` button


### 5. Import Hands-on project

![](img/install-studio-9-win.png)

* Click `Import project (Eclipse ADT, Gradle, etc.)` and choose `c:\Hands-on\2017-handson-kotlinAndroid` directory
* Click `Add Root`


If there is still some errors with Gradle... Double check the following. 

* Open `File | Prefrences...` menu 
* Select `Build, Execution, Deployment | Gradle`
* Check `Offline work`
* Set service directory path to : `c:\Hands-on\gradle`
* Then Click on `Apply` button
![](img/install-studio-12-win.png)

### 6. Create Virtual Device for Emulator

* Click on the  menu : `Tools | Android | AVD Manager` : ![](img/avd-manager-win.png)

* Click on `Create Virtual Device...` button
![](img/install-studio-16-win.png)

* Select `Nexus 5X`
* Click `Next`
![](img/install-studio-17-win.png)

* Click `Next`
![](img/install-studio-18-win.png)

* Click `Install Haxm`
![](img/install-studio-19-win.png)

![](img/install-studio-20-win.png)

* Click `Finish`

![](img/install-studio-21-win.png)

* Click `Finish`

![](img/install-studio-22-win.png)


