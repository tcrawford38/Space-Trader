# Space Trader Remix

Java game based on the old game Space Trader. For more information about the original game, visit http://www.spronck.net/spacetrader/STFrames.html

## How to run the project

1. Download exe from updated releases on github page.

2. Libgdx has a [guide](https://libgdx.com/dev/import-and-running/) which explains how to import gradle projects into your 
IDE (Eclipse, Intellij, etc) or run the game through the command line. Note that you need to download 
[gradle](https://gradle.org/install) to run the project through the command line. The project currently only supports
desktop.

Additional note: You might need to append `--illegal-access=permit` as a jvm flag (VM option in IntelliJ). 
The libgdx engine uses a lot of internal reflection which is disabled by default in versions past java 8.

Please email tcrawford38@gatech.edu if you have any questions about setup.
