# Hands-on setup guide (Mac)

### 1. Pre-requisites

* **Git**
If required check this link to install it : [Download Git](https://git-scm.com/download/mac)

**To check** open a Terminal and type : `git --version`

**Note:** We will call `<Archive directory>` the directory where are the provided archives into the `Hands-on`. This directory is coming from the USB pen and should be copied for instance on the Desktop or the Home directory. So when you will read : `cd <Archive directory>` you will understand `cd ~/Desktop` or `cd ~/Bureau` or `cd ~` depending where you've copied the `Hands-on` directory from the USB pen.

### 2. Create `~/Hands-on`  directory 

`mkdir ~/Hands-on`

**To check** open a Terminal and type : `ls ~/Hands-on`

#### 2.1 Install Android Studio 

Install provided archive into your directory `~/Hands-on`:

Double click on `./Hands-on/Mac/android-studio-ide-145.3537739-mac.dmg`

You will see the following window :
![](img/install-studio-mac-0.1.png)

**Don't drag it into your `Applications` directory but into `~/Hands-on`** 

**To check** 
![](img/install-studio-mac-0.2.png)


#### 2.2 Install Android SDKs 

Unzip provided archive into your directory `~/Hands-on`:

```
cd <Archive directory>
cp ./Hands-on/Mac/Android.tar.gz ~/Hands-on
cd ~/Hands-on
tar zxvf Android.tar.gz
```

**To check** open a Terminal and type : `ls ~/Hands-on/Android` and you should see `Sdk` and `Plugins` directories.

#### 2.3 Hands-on project 

Unzip provided archive into your directory `~/Hands-on`:

```
cd <Archive directory>
unzip ./Hands-on/Mac/2017-handson-kotlinAndroid.zip -d ~/Hands-on/
```

**To check** open a Terminal and type : 

```
cd ~/Hands-on/2017-handson-kotlinAndroid
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

Note to use your current user instead of `laurent:staff`.

If the `/opt` directory does not exist create it with the following command : `sudo mkdir /opt`

```
cd <Archive directory>
sudo cp ./Hands-on/Mac/handson-devoxx2017-gradle.tar.gz /opt
cd /opt
sudo tar zxvf handson-devoxx2017-gradle.tar.gz 
sudo chown -R laurent:staff handson-devoxx2017/
```

**To check** open a Terminal and type : `ls -l /opt/handson-devoxx2017` and you should see `gradle` directory belonging to `laurent:staff`.


### 4. Complete Android Studio offline setup 

Double click on Android Studio icon 
![](img/install-studio-mac-0.2.png)

You will see...
![](img/install-studio-1.png)

Just ignore the message and click on `Cancel`

![](img/install-studio-2.png)

![](img/install-studio-3.png)

Select `Custom` installation

![](img/install-studio-4.png)

![](img/install-studio-5.png)

![](img/install-studio-2.1.png)

Update Android SDK location and set the path defined in chap. 2 above. Should be the absolute path of : `~/Hands-on/Android/Sdk`
![](img/install-studio-6.png)

![](img/install-studio-7.png)

![](img/install-studio-8.png)

![](img/install-studio-9.png)

Open `configure` menu and select `Preferences`

![](img/install-studio-9.2.png)

* Select `Build, Execution, Deployment | Gradle `
* Check `Offline work`
* Set service directory path to : `/opt/handson-devoxx2017/gradle`
* Then Click on `Apply` button
![](img/install-studio-12.png)

* Select `Plugins`
* Click on `Install plugin from disk...` button

![](img/install-studio-13.png)

Kotlin 1.0.6 plugin is provided along with the Android SDK archive under the `Plugins` directory :

* `~/Hands-on/Android/Plugins/kotlin-plugin-1.0.6-release-Studio2.2-1.zip`

![](img/install-studio-14.png)

![](img/install-studio-15.png)

* Then Click on `Apply` button
* Click on `Restart Android Studio` button


### 5. Import Hands-on project

![](img/install-studio-9.png)

* Click `Import project (Eclipse ADT, Gradle, etc.)` and choose `~/Hands-on/2017-handson-kotlinAndroid` directory

* Click `Add Root`
![](img/install-studio-11.png)

If there is still some errors with Gradle... Double check the following. 

* Open `File | Prefrences...` menu 
* Select `Build, Execution, Deployment | Gradle`
* Check `Offline work`
* Set service directory path to : `/opt/handson-devoxx2017/gradle`
* Then Click on `Apply` button
![](img/install-studio-12.png)

### 6. Create Virtual Device for Emulator

* Click on the  menu : `Tools | Android | AVD Manager` : ![](img/avd-manager.png)

* Click on `Create Virtual Device...` button
![](img/install-studio-16.png)

* Select `Nexus 5X`
* Click `Next`
![](img/install-studio-17.png)

* Click `Next`
![](img/install-studio-18.png)

* Click `Install Haxm`
![](img/install-studio-19.png)

![](img/install-studio-20.png)
* Click `Finish`

![](img/install-studio-21.png)


