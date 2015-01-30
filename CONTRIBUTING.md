# How to Contribute

We welcome community contributions! You can help by reporting bugs, fixing code, adding new features and/or better documentation.

We use the common fork, feature branch, pull request workflow.  If you would like to contribute to the project, fork the project, create a feature branch, make your changes and then issue a pull request to our master branch.  See [Using pull requests](https://help.github.com/articles/using-pull-requests/)

## How to run this project 

Before running this project locally copy the application-local.conf to application.conf:

```
cp conf/application-local.conf conf/application.conf
```

## How to publish this library 

### locally for local testing

1. set the version you want in `version.sbt` 
2. from an sbt shell (to cross-compile against multiple versions of scala, prepend with a plus sign):
```sbt
+publishLocal
```

### to bintray.com

1. Make sure you have an account on [bintray.com]() and have been added to the yetu organization (with write permissions)
2. Make sure your git directory is clean and you have no uncommitted or untracked files
3. Publish a release version (interactive, will ask for version number):
```sbt
release cross 
```
if you want the default settings, you can use 
```
release cross with-defaults
```

#### Not recommended:

Alternatively you may also set the version you want in `version.sbt` and execute
```sbt
+publish
```
But this will not tag the git commit. Also, it may not work for SNAPSHOT versions.



## Authors

* [Alex Migutsky](https://github.com/mr-mig)
* [Joé Schaul](https://github.com/jschaul)
* [Artem Zadorozhnyi](https://github.com/azadorozhniy)