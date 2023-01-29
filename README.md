# Bee md

## What is this project for?
If you hold many projects at GitHub, then you may want to create nice **md** files.
Unless you have a good md editor, you have no idea how a final md file will look.
The project fills the gap.

## How to run it

It is a Java web application packaged as a **war** file. You can drop it in any web server auto deploy directory.
I use [TJWS](https://github.com/drogatkin/TJWS2). It is recommended to use [7Bee](https://github.com/drogatkin/7Bee) for
building **war** file, but any other tool will work too. The name of the application is **beemd**. You can rename it to any other name.

### 3rd party dependency
The project uses 1 dependency [commonmark](https://github.com/commonmark/commonmark-java.git). Checkout the project, and then copy
[bee-java.rb](bee-java.rb) there and run **[rb](https://github.com/drogatkin/rust_utilities)**. Modify [env.xml](env.xml) here to specify the dependency jar location. If you do not
want to build the *jar*, then you can take it from [Maven Central](https://search.maven.org/search?q=g:org.commonmark).

The project utilizes also WebBee and Aldan3 frameworks for a rapid development SPA web applications. Jar files for the frameworks have to be built, since Maven versions are not available yet.
 Check out [Aldan3-jdo](https://github.com/drogatkin/aldan3-jdo),  [Aldan3](https://github.com/drogatkin/aldan3), and [WebBee](https://github.com/drogatkin/Webbee). Run 7Bee to build
*jar* files. And then correct path to them in [env.xml](env.xml).