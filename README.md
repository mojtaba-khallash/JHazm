JHazm
=====
[![Build Status](https://travis-ci.org/mojtaba-khallash/JHazm.png)](https://travis-ci.org/mojtaba-khallash/JHazm)
[![codecov](https://codecov.io/gh/mojtaba-khallash/JHazm/branch/master/graph/badge.svg)](https://codecov.io/gh/mojtaba-khallash/JHazm)
[![Dependency Status](https://www.versioneye.com/user/projects/563374b036d0ab0016001e4d/badge.svg?style=flat)](https://www.versioneye.com/user/projects/563374b036d0ab0016001e4d) 

[![GitHub release](https://img.shields.io/github/release/mojtaba-khallash/JHazm.svg)](https://github.com/mojtaba-khallash/JHazm/releases)
[![License](http://img.shields.io/:license-mit-blue.svg)](http://badges.mit-license.org)

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

* You can download [pre-trained tagger](https://www.dropbox.com/s/rfbo13u11wkh0yu/resources.zip?dl=0) and [parser models](https://www.dropbox.com/s/vuchhc4tlriiudk/resources-extra.zip?dl=0) for persian and put these models in the `JHazm/resources` folder of your project.

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
