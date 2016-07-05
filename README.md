# AsciiPanel

[![Build Status](https://travis-ci.org/roddy/MavenizedAsciiPanel.svg)](https://travis-ci.org/roddy/MavenizedAsciiPanel)

AsciiPanel simulates a [code page 437](https://en.wikipedia.org/wiki/Code_page_437) ASCII terminal display. It supports all 256 characters of codepage 437, arbitrary foreground colors, arbitrary background colors, and arbitrary terminal sizes.

The default terminal size is 80x24 characters and default colors matching the Windows Command Prompt are provided.

This should be useful to roguelike developers.

## Usage

AsciiPanel is a [Maven](https://maven.apache.org/) project, compatible with Maven 2 and Maven 3. It can be built using the following command:

```
mvn install
```

This will build the project, run the unit tests, and copy the resulting jar into your local Maven repository. Once the jar is deployed to your repository, you can include it in your projects by including the following dependency in your pom:

```xml
<dependency>
  <groupId>net.trystan</groupId>
  <artifactId>ascii-panel</artifactId>
  <version>1.0</version>
</dependency>
```

## Notes

This project is built with Java 8. However the code itself does not *require* Java 8. If you are supporting a project running an earlier version of Java, you can change the pom file and rebuild the jar using your chosen version of Java without having to modify the code.