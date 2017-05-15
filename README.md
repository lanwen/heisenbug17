# Heisenbug 2017

Codegen полезняшки. Набор инструментов, которые пробовал в процессе работы над разными проектами. Некоторые сильно дублируют друг-друга.


## JAXB & плагины

- The most advanced JAXB2 Maven Plugin for XML Schema compilation.
    Именно об этом плагине речь в докладе. Очень мощные возможности для быстрого наведения порядка в дата-классах
    https://github.com/highsource/maven-jaxb2-plugin

- Jaxb плагин для вставки доп методов
https://github.com/mklemm/jaxb-delegate-plugin

- XJC / JAXB plugin for generation of Bean Validation Annotations (JSR-303) and replacing primitive types
Позволяет сразу генерировать простые аннотации для валидации полей.
https://github.com/krasa/krasa-jaxb-tools

- Add arbitrary annotations to JAXB classes.
Позволяет развешивать аннотации в любом месте
https://github.com/highsource/jaxb2-annotate-plugin

- JAXB @XmlElementWrapper Plugin
Упрощает сгенерированные классы
https://github.com/dmak/jaxb-xew-plugin


## Annotation processors

- Reducing Boilerplate Code with Project Lombok
Очень сильно позволяет уменьшить количество пустого кода. Несколько магический.
https://projectlombok.org

- An annotation processor for generating type-safe bean mappers http://mapstruct.org/
Чтобы одни бины превращать в другие без рефлексии и ухищрений
https://github.com/mapstruct/mapstruct

- Annotation processor to create immutable objects and builders.
https://github.com/immutables/immutables

- Java Annotation Processor which allows to simplify development
Местами дублирует возможности ломбока, но не переписывает результирующие классы
https://github.com/vbauer/jackdaw

- A Java Code Generator for Pojo Builders
Генерирует билдеры для классов
https://github.com/mkarneim/pojobuilder

- Generates Hamcrest's Feature Matchers for every field annotated with selected annotation
Генерирует матчеры для полей
https://github.com/yandex-qatools/hamcrest-pojo-matcher-generator

- A collection of source code generators for Java.
Солянка разных
https://github.com/google/auto

## Шаблонизаторы

- Logic-less and semantic Mustache templates with Java
Удобные, без логики, довольно куцые шаблоны. Очень легко воспринимаются. Отлично для старта.
https://github.com/jknack/handlebars.java

- Apache FreeMarker is a template engine: a Java library to generate text output
Один из самых распространенных в java. Умеет очень много, довольно быстрый.
Сильно полноценнее mustache, но и сложнее для освоения и восприятия.
http://freemarker.org

- StringTemplate is a java template engine (with ports for C#, Objective-C, JavaScript, Scala) for generating source code, web pages, emails, or any other formatted text output.
Несколько маргинальная либа для шаблонизации, но много умеет и входит в состав ANTLR тулчейна (популярная либа для грамматик по обработке текста). Очень быстрая.
http://www.stringtemplate.org

- Velocity is a Java-based template engine. It permits anyone to use a simple yet powerful template language to reference objects defined in Java code.
По возможностям близко к freemarker, но ооочень давно не обновляется и сильно устарела по api, удобству, скорости итд. Здесь чтобы просто быть.
http://velocity.apache.org

## Для тестирования

- **!** Testing tools for javac and annotation processors
Самый удобный тул для тестирования кодогенераторов в вакууме
https://github.com/google/compile-testing

- Custom assertions generator
По идее похож на генерацию матчеров. Только генерирует сразу ассерты.
Не всегда удобно использовать с глубокой вложенностью
https://github.com/joel-costigliola/assertj-assertions-generator

- Rest-Assured RAML Codegen - Generates test http client, based on Rest-Assured with help of RAML spec
Гибкий клиент для тестирования. rest-assured под капотом
https://github.com/qameta/rarc


## Бины, протоколы

- Protocol buffers are Google's language-neutral, platform-neutral, extensible
mechanism for serializing structured data – think XML, but smaller, faster, and simpler.
Не только бины, но и протокол сериализации. Не всегда прозрачно ложится на текущую инфраструктуру.
Внедрить подход через JAXB или jsonschema сильно проще, принцип но такой же как у протобуфов.
Нечеловекочитаемый результат сериализации. Очень экономно передает данные.
Используется, например, большинством браузеров для синхронизации данных с облаком.
https://developers.google.com/protocol-buffers/

- Maven Plugin that executes the Protocol Buffers (protoc) compiler
Позволяет процессить схемы во время билда и не коммитить сотни тысяч строк сгенерированного кода.
https://github.com/xolstice/protobuf-maven-plugin

- jsonschema2pojo generates Java types from JSON Schema (or example JSON) and can annotate those types for data-binding with Jackson 1.x, Jackson 2.x or Gson.
Похож на JAXB, только работает с json и json-схемой. Очень гибкий и простой для внедрения. Есть мавен, гредл и cli виды.
https://github.com/joelittlejohn/jsonschema2pojo

- swagger-codegen contains a template-driven engine to generate documentation, API clients and server stubs
Генерирует клиента по сваггер схеме. Код получается уродливый. Кастомизируется тяжело.
Зато очень много языков из коробки. Отличный пример как *не надо* работать с результатами кодогенерации.
Ребята коммитят всё прямо рядом с исходниками кодогенератора. Из-за этого в репозитории каша, а каждый PR это месиво из тысяч строк.
Возможно из-за этого и сам код кодогенератора - месиво.
https://github.com/swagger-api/swagger-codegen

- The Apache Thrift software framework, for scalable cross-language services development,
combines a software stack with a code generation engine to build services that work efficiently and seamlessly between C++,
Java, Python, PHP, Ruby, Erlang, Perl, Haskell, C#, Cocoa, JavaScript, Node.js, Smalltalk, OCaml and Delphi and other languages.
Как и протобуфы дает еще и протокол сериализации. Помимо этого еще и сервер и клиентов.
http://thrift.apache.org

- Maven plugin for generating Java client RESTful code based on RAML protocol.
Несложные клиенты по raml схеме. Уступает swagger-генератору по поддержаным возможностям
https://github.com/aureliano/cgraml-maven-plugin


## Генерация сорцов, байткода

- A Java API for generating .java source files.
Лучшая на мой взгляд библиотека для императивного способа генерации сорцов
https://github.com/square/javapoet

### Ближе к байткоду

- cglib - Byte Code Generation Library is high level API to generate and transform Java byte code.
https://github.com/cglib/cglib

- Runtime code generation for the Java virtual machine.
https://github.com/raphw/byte-buddy

- Maven plugin that will apply Javassist bytecode transformations during build time.
https://github.com/icon-Systemhaus-GmbH/javassist-maven-plugin


## Общее

- Native bindings generator for JNA / BridJ / Node.js
Парсит заголовочные файлы для генерации биндингов.
https://github.com/nativelibs4java/JNAerator

- Distributed code search and refactoring for Java
https://github.com/Netflix-Skunkworks/rewrite

- Среди java-awesome подборки
https://github.com/akullpp/awesome-java#code-generators


## Golang

- GO codegen
Аналог javapoet, только на go
https://github.com/dave/jennifer

- json-schemas generator based on Go types
Похож на jsonschema2pojo
https://github.com/mcuadros/go-jsonschema-generator






























