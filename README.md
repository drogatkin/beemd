# Bee md

## What is this project for?
If you hold many projects at GitHub, then you may want to create nice **md** files.
Unless you have a good md editor, you have no idea how finally the md file will look.
The project fills the gap.

## How to run it

It is a Java web application packaged as a **war** file. You can drop it in any web server auto deploy directory.
I use [TJWS](https://sourceforge.net/projects/tjws/files/). It is recommended to use [7Bee](https://github.com/drogatkin/7Bee) for
building a **war** file, but any other tool for the purpose will work too. The name of the application is **beemd**. You can rename it to any other name.

### 3rd party dependencies
The project uses 1 dependency [commonmark](https://github.com/commonmark/commonmark-java.git). Checkout the project, and then copy
[bee-java.rb](bee-java.rb) there and run **[rb](https://gitlab.com/tools6772135/rusthub/-/tree/master)**. It will build **commonmark.jar** in directory *build*.

Specify its location in  [env.xml](env.xml) of this project. If you do not
want to build the *jar*, then you can take it from [Maven Central](https://search.maven.org/search?q=g:org.commonmark).

The project utilizes also WebBee and Aldan3 frameworks for a rapid development SPA web applications. Jar files for the frameworks have to be built, since Maven versions are not available yet.
 Check out [Aldan3-jdo](https://github.com/drogatkin/aldan3-jdo),  [Aldan3](https://github.com/drogatkin/aldan3), and [WebBee](https://github.com/drogatkin/Webbee). Run 7Bee to build
*jar* files, or take them from release distributions. And then correct path to them in [env.xml](env.xml). WebBee is required to be present to build a final war file, since some resources are taken from it. 

The newer version of the viewer adds an instant update of the md view when the
.md file has been changed. [Buzzbee](https://github.com/drogatkin/Buzzbee) is used for the functionality. You can build the library using
[7Bee](https://github.com/drogatkin/7Bee) or [RustBee](https://sourceforge.net/projects/seven-bee/files/Release%201.3.1/). Since a js file from the
library is used, the library should be available in a source form for a **war** file creation.

## Screenshot
![beemd](doc/beemd.png?format=raw){width=640 height=480}