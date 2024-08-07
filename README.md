# WADdup
A custom GZDoom launcher made with Java

### prerequisites

In order to run the program you need Java 8 (or a newer version) installed. If you want to build it from source, [Maven](https://maven.apache.org/download.cgi) is the best option.

Most people have a version of Java installed, and it's usually Java 8. If you're unsure if you have it installed, or what version you have installed, check out these guides for:
- Linux: https://phoenixnap.com/kb/check-java-version-linux
- Windows 10: https://www.howtogeek.com/717330/how-to-check-your-java-version-on-windows-10/
- Windows 11: https://www.howtogeek.com/838703/how-to-check-your-java-version-on-windows-11/

On Linux you can install it using your preferred package manager. On Windows, head over to the [Java download page](https://www.java.com/en/download/) and follow the guide to install it.

### installation

Head over to the [releases](https://github.com/slendersnax/WADdup/releases/) page and download the latest `waddup-x.x.jar` file. This is an executable file so you can just double click on it and it will launch.

### build

You can build it using Maven or import it into your IDE of choice and build it from there.

Using Maven: open the command line and execute the `mvn package` command from the main directory. This will generate a `waddup-x.x.jar` file in the `target` folder (where `x.x` is the version number).

### run

As of a few versions ago the classic method of double clicking on it is now the recommended method (yay). When running it for the first time it's recommended to go to "Settings" and select your main WAD folder (if you have one). This will make it easier to select the WADS since the file selector will always open in that directory from then on.

On Windows you **have to** go to "Settings" -> "Windows" and set the location of the executable.

If you did either of the above, press "Save" to save those settings, otherwise it's not going to work.

If you want to run it from the command line, you can use:
```
java -jar waddup-x.x.jar
```

### screenshot(s)

![screenshot 1](screenshots/waddup_1.png?)
![screenshot 2](screenshots/waddup_2.png?)
![screenshot 3](screenshots/waddup_3.png?)
![screenshot 4](screenshots/waddup_4.png?)

### features and reasons

I don't like drag-and-drop that much and I don't really use the other options from the official GZDoom launcher either, so I made a small launcher that makes it easier to select WADs. It is...

- able to select PWADs (without drag-and-drop - you can select multiple in the file selection window by holding down the `ctrl` or `shift` keys like you would in a normal folder)
- able to remove PWADs or change their loading order
- able to save and load WAD configurations
- able to run GZDoom using Wine on Linux

### tips

- in order for the changes made in "Settings" to take effect, you **must** press the "Save" button. This applies to the current session as well.
- please note that the location of the native Linux GZDoom saved games and the Wine GZDoom saved games is different
	- nothing is stopping you from copying your games over though :D: on Linux they can most often be found in `~/.config/gzdoom/savegames/`, and you can copy them to the Wine Prefix that you're using: `[your wine prefix]/drive_c/users/[your user]/Saved Games/GZDoom/`
		- your default Wine Prefix is `~/.wine`
	- you can also change what Prefix Wine uses to load the game

- as of v0.95 when resizing the window the new size is saved automatically
	- **important**: when resizing to a larger size, some elements may not get resized until you restart the program 

### todo

- [x] test on other platforms
	- [x] Windows
	- ~~general Linux build?~~
		- it's made in Java so barring any exceptions where the basic home folder structure is different, this is fine
- [x] create, save, load configs
	- [x] configs are now created in the same place on Linux
	- [x] test this on Windows
- [x] options to set GZDoom executable
- [x] create file filter to only show `wad`, `pk3` (also `zip` I guess), and `deh` files 
- [ ] make it prettier
- [ ] add tests
- [x] add build and install script
- [ ] add option to add custom command line options
- [ ] add drag-and-drop
- [ ] setup wizard
	- [ ] Windows: select exe path
	- [ ] general: select WAD folder path
- [ ] make explorer a bit more convenient to use
	- [ ] copy paste directory paths directly