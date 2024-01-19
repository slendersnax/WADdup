# WADdup
A custom GZDoom launcher made with Java

### build

I have added Maven support, so if you have it installed you can just execute the `mvn package` command from the main directory. This also generates a `.jar` file, which you can execute with:

```
java -jar target/sl-gzdoom-wad-picker-1.1.jar
```

You can also pass a directory path as a parameter, which will set it to be the default directory when choosing files. Otherwise the file chooser will open in the home directory. This is handy if you have a folder full of WADs somewhere and you don't want to navigate there everytime you start the launcher.
For example:
```
java -jar target/sl-gzdoom-wad-picker-1.1.jar ~/Documents/WADS
```

If for some reason this command doesn't work, you can try
```
java -cp target/sl-gzdoom-wad-picker-1.1.jar Main
```

Tested on Arch Linux with Java 17 and 21.

### screenshot(s)

![screenshot 1](screenshots/sl_gzdoom_launcher_1.png?)
![screenshot 2](screenshots/sl_gzdoom_launcher_2.png?)
![screenshot 3](screenshots/sl_gzdoom_launcher_3.png?)

### features and reasons

I don't like drag-and-drop that much and I don't really use the other options from the official GZDoom launcher either, so I made a small launcher that makes it easier to select WADs. It is...

- able to select PWADs without having to drag-and-drop them from your filesystem (you can select multiple in the file selection window by holding down the `ctrl` or `shift` keys)
- able to remove PWADs or change their loading order
- able to save and load WAD configurations

### todo

- [ ] test on other platforms
	- [ ] Windows
	- [ ] general Linux build?
- [x] create, save, load configs
- [ ] create file filter to only show `wad`, `pk3`, and `deh` files 
- [ ] make it prettier
- [x] add build and install script
	- kind of done? Maven builds an executable `jar` file, but maybe we could add a bash wrapper and desktop icon
- [ ] maybe add other GZDoom launch options (Vulkan vs. OpenGL, Fullscreen, etc)