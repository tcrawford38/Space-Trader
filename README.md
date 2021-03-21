# Space Trader Remix

# About

Java/Javafx Game based on the old game Space Trader. For more information about the original game, visit http://www.spronck.net/spacetrader/STFrames.html

# How to run on Visual Studio

Step 1: Make sure files are under 1 folder. Sometimes when downloading, it will create nested folders which can cause problems when running.

Step 2: Go into extensions in Visual Studio Preferences and make sure Java Extension Pack is installed.

Step 3: For Javafx environment, watch this video in its entirety: https://www.youtube.com/watch?v=H67COH9F718. It contains lots of individual steps which are very important. The folder should already contain javafx SDK, but I recommend just following the video and download the SDK in your own preferred location.

Please email me at tcrawford38@gatech.edu if you have any questions.

# How to run using Maven (Command Line)

IDEs like [Eclipse](https://www.lagomframework.com/documentation/1.6.x/java/EclipseMavenInt.html) and [IntelliJ](https://www.jetbrains.com/help/idea/maven-support.html#maven_import_project_start) already have Maven fully integrated. VSCode also has a Maven extension which can abstract the need to access the command line. If you want to run commands through the command line then you need to download Maven from [here](https://maven.apache.org/download.cgi). The [installation page](http://maven.apache.org/install.html) may also be helpful. Note that running `mvn -v` is a good way to test if mvn is installed.

1. Change directories into the root folder and run the following two commands to compile and then run the program.

```
mvn compile
mvn exec:java
```

Note: There are other ways of building and running the project, but I've found that directly executing the program through maven is the easiest and fastest way.

Please email ckhiddeewu@gatech.edu if you have any questions.
