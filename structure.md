# v1

org.slendersnax.waddup
│
├── Main.java
│
├── ui
│   ├── window
│   │   └── AppWindow.java
│   │
│   ├── picker
│   │   └── PickerPanel.java
│   │
│   ├── options
│   │   └── OptionsPanel.java
│   │
│   ├── config
│   │   ├── LoadConfigPanel.java
│   │   └── SaveConfigPanel.java
│   │
│   └── components
│       ├── IWADLabel.java
│       ├── WADPanel.java
│       ├── ItemPanel.java
│       └── VerticalBtnPanel.java
│
├── model
│   ├── WADModel.java
│   ├── WadSession.java        ← new
│   └── Settings.java          ← new
│
├── service
│   ├── LauncherService.java
│   └── GZDoomLauncher.java
│
├── infrastructure
│   ├── PropWrapper.java
│   └── SlenderConstants.java
│
└── controller
└── PickerController.java  ← new

# v2

org.slendersnax.waddup
├── Main.java
├── ui
│   ├── window/AppWindow.java
│   ├── picker/PickerPanel.java
│   ├── options/OptionsPanel.java
│   ├── config/LoadConfigPanel.java
│   ├── config/SaveConfigPanel.java
│   └── components/
│       ├── IWADLabel.java
│       ├── WADPanel.java
│       ├── ItemPanel.java
│       └── VerticalBtnPanel.java
├── model
│   ├── WADModel.java
│   ├── WadSession.java
│   └── Settings.java
├── service
│   └── GZDoomLauncher.java
└── infrastructure
    ├── PropWrapper.java
    └── SlenderConstants.java

