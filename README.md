JHazm
=====

A Java version of [Hazm](https://github.com/sobhe/hazm) (Python library for digesting Persian text)

+ Text cleaning
+ Sentence and word tokenizer
+ Word lemmatizer
+ POS tagger
+ Dependency parser
+ Corpus readers for [Hamshahri](http://ece.ut.ac.ir/dbrg/hamshahri/) and [Bijankhan](http://ece.ut.ac.ir/dbrg/bijankhan/)

* You can download [pre-trained tagger and parser models](http://dl.dropboxusercontent.com/u/90405495/resources.zip) for persian and put these models in the `core/src/main/resources` folder of your project.

# Installation and Using

To make a single jar file run this codes:

mvn clean compile assembly:single

To run and see the help:
```shell
java -jar jhazm-jar-with-dependencies.jar
```

For example to do POS Tag on bundled sample file use:
```
java -jar jhazm-jar-with-dependencies.jar -a partOfSpeechTagging -o test.txt
```

Or to run on any other file:
```
java -jar jhazm-jar-with-dependencies.jar -a partOfSpeechTagging -o test.txt -i input.txt
```

Or on sample text:
```
java -jar jhazm-jar-with-dependencies.jar -a partOfSpeechTagging -o test.txt -t "سلام من خوب هستم!"
```
Good Luck!
