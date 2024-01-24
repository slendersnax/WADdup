# WADdup
A custom GZDoom launcher made with Java

### prerequisites

In order to run the program you need Java 17 (or a newer version) installed. If you want to build it from source, [Maven](https://maven.apache.org/download.cgi) is the best option.

### installation

Head over to the [releases](https://github.com/slendersnax/WADdup/releases/) page and download the latest `waddup-x.x.jar` file. This is an executable file so you can just double click on it and it will launch (at the moment it's suggest to run it from the command line however).

### build and run

You can build it using Maven or import it into your IDE of choice and build it from there.

Using Maven: open the command line and execute the `mvn package` command from the main directory. This will generate a `waddup-x.x.jar` file in the `target` folder (where `x.x` is the version number). You can execute this file by double clicking on it or from the command line like this (substituting `x.x` with your version):

```
java -jar target/waddup-x.x.jar
```

In the command line you can also pass a directory path as a parameter, which will set it to be the default directory when choosing files for the current session. Otherwise the file chooser will open in the home directory. This is handy if you have a folder full of WADs somewhere and you don't want to navigate there everytime you start the launcher.
For example:
```
java -jar target/waddup-x.x.jar ~/Documents/WADS
```

### screenshot(s)

![screenshot 1](screenshots/sl_gzdoom_launcher_1.png?)
![screenshot 2](screenshots/sl_gzdoom_launcher_2.png?)
![screenshot 3](screenshots/sl_gzdoom_launcher_3.png?)

### features and reasons

I don't like drag-and-drop that much and I don't really use the other options from the official GZDoom launcher either, so I made a small launcher that makes it easier to select WADs. It is...

- able to select PWADs without having to drag-and-drop them from your filesystem (you can select multiple in the file selection window by holding down the `ctrl` or `shift` keys)
- able to remove PWADs or change their loading order
- able to save and load WAD configurations

### notes

As of right now:
- the latest version is still optimised for Linux so I don't suggest running it on Windows (yet).
- it's suggested to run it from the command line with the command found in the **build and run** section above

### todo

- [ ] test on other platforms
	- [ ] Windows
	- [ ] general Linux build?
- [x] create, save, load configs
	- [x] configs are now created in the same place on Linux
	- [ ] test this on other platforms
- [ ] options to set GZDoom executable and GZDoom config location
- [ ] create file filter to only show `wad`, `pk3`, and `deh` files 
- [ ] make it prettier
- [x] add build and install script
- ~~maybe add other GZDoom launch options (Vulkan vs. OpenGL, Fullscreen, etc)?~~
	- you can set these from inside GZDoom so this isn't needed