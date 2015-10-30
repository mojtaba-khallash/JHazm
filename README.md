JHazm
=====
[![Build Status](https://travis-ci.org/mojtaba-khallash/JHazm.png)](https://travis-ci.org/mojtaba-khallash/JHazm) [![Dependency Status](https://www.versioneye.com/user/projects/563374b036d0ab0016001e4d/badge.svg?style=flat)](https://www.versioneye.com/user/projects/563374b036d0ab0016001e4d)

A Java version of [Hazm](https://github.com/sobhe/hazm) (Python library for digesting Persian text)

+ Text cleaning
+ Sentence and word tokenizer
+ Word lemmatizer
+ POS tagger
+ Dependency parser
+ Corpus readers for:
   * [Hamshahri](http://ece.ut.ac.ir/dbrg/hamshahri/)
   * [Bijankhan](http://ece.ut.ac.ir/dbrg/bijankhan/)
   * [Persica](https://sourceforge.net/projects/persica/)
   * [Verb Valency](http://dadegan.ir/catalog/pervallex)

## Requirements

* You can download [pre-trained tagger](http://dl.dropboxusercontent.com/u/90405495/resources-extra.zip) and [parser models](http://dl.dropboxusercontent.com/u/90405495/resources-extra.zip) for persian and put these models in the `JHazm/resources` folder of your project.

## Installation and Using

To make a single jar file run this codes:

```bash
mvn clean compile assembly:single
```
For using this project as library in maven just use:
```bash
mvn clean install
```

To run and see the help:
```bash
java -jar jhazm-jar-with-dependencies.jar
```

For example to do POS Tag on bundled sample file use:
```bash
java -jar jhazm-jar-with-dependencies.jar -a partOfSpeechTagging -o test.txt
```

Or to run on any other file:
```bash
java -jar jhazm-jar-with-dependencies.jar -a partOfSpeechTagging -o test.txt -i input.txt
```

Or on some piece of text:
```bash
java -jar jhazm-jar-with-dependencies.jar -a partOfSpeechTagging -o test.txt -t "سلام من خوب هستم!"
```
Good Luck!
