# Scribbles

Scribbles aims to be a collection of tools, although currently it contains only one.

## Features

### New Service

A service is considered to be a group of gradle modules which separates the _api_ from the _implementation_.

Scribbles can generate for you the following structure of gradle modules:
```
:newService
    :newService-api
    :newService-implementation
    :newService-test
``` 

It adds them to `settings.gradle`

It also adds `build.gradle` files for each module and optionally an `AndroidManifest.xml`

> Note: It only supports gradle.kts

### Rename Package

Renames the directories that represent the package name of the app. 

For example, it can change: `com/alexgabor/template` to `com/new/project`  

## How to install 

1. Extract the zip for your platform
2. Add the folder to your path
3. Run `scribbles` from the terminal

## Run

Scribbles uses by default the directory it was started from as the project directory. You can change it using `-p <path-to-project>`. 

## Contribute

Run with `gradle app:run`