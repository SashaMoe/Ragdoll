# Ragdoll
CSSE374 Term Project

大家都是萌萌哒~ (As Cute As Ragdoll~)

## Table of contents
1. [Design of the tool](#design-of-the-tool)
2. [Who-Did-What table](#who-did-what-table)
3. [Usage / Instructions](#usage--instructions)
4. [Pictures comparisons](#pictures-comparisons)

## Design of the tool
### Design
![UML Diagram](/img/ProjectUMLManually.png "UML Diagram")
As the UML Diagram shows above, the project consists of two major parts.

The first part is designed to read code from specified java files to parse them using [asm](#http://asm.ow2.org/download/index.html).
Since asm is designed in visitor pattern, we need to implement its `visit` methods for ClassDeclarations, ClassFields and ClassMethods.
It consists of the following packages:
* ragdoll.app
* ragdoll.util
* ragdoll.asm

The second part is designed to use our own visitor pattern to output the class structure in [GraphicViz](#http://www.graphviz.org/) format.
It consists of the following packages:
* ragdoll.code.api
* ragdoll.code.impl
* ragdoll.code.visitor.api
* ragdoll.code.visitor.impl

Overall, the project uses Decorator Pattern as well as two Visitor Patterns.

### Design Principles
The project follows the following design principles:
* *Identify the aspects of your application that vary and separate them from what stays the same.* According to the above discussion, we separated the read-in functionality from the write-out functionality.
* *Program to an interface, not an implementation.* As the UML diagram shows, we are programming to various interfaces that well represents the key components of the project, such as IVisitor, ITraverser, and IClassComponents.
* *Strive for loosely coupled designs between objects that interact*. Since we are using the Visitor Pattern which has the Message Coupling, we are achieving the loose coupling in our design.
* *Classes should be open for extension, but closed for modification.* The Decorator Pattern we are using allows us to wrap up any level of visitors without modifying the existing code.
* *Each method should be either a command or a query.* We tried our best to make methods that we implemented in the project to either be a Command Method (Performs an action but has no return value) or a Query Method (Returns data but has no side effects).

## Who-Did-What table:
| *Name*         | *Who did what*                                                                              | *Time* |
|----------------|---------------------------------------------------------------------------------------------|--------|
| zxqdx          | init project(asm parser)                                                                    | 20m    |
| SashaMoe       | create project                                                                              | 5m     |
| zxqdx / yangh1 | Setup git environment w/ Yangh1                                                             | 10m    |
| Sasha          | Setup interfaces and packages for ragdoll.code                                              | 5m     |
| yangh1         | Setup packages for ragdoll.code.visitor & fix typo                                          | 10m    |
| zxqdx          | Create Class' classes                                                                       | 10m    |
| yangh1         | Create ClassDeclaration constructor and fields                                              | 10m    |
| Sasha          | Create Method constructor and fields                                                        | 10m    |
| zxqdx          | Create Field constructor and fields. Added getters for all class components                 | 10m    |
| Sasha          | Implemented one visitor setup method. Fixed interface issues.                               | 10m    |
| yangh1         | Create Ragdoll main class and added instantiation process of IClass.                        | 10m    |
| Sasha          | In asm, added IClassComponents to IClass. Implemented part of the IVisitor methods.         | 10m    |
| zxqdx          | Added visit logic for Fields and Methods.                                                   | 10m    |
| yangh1         | implement visit logic for class declaration                                                 | 10m    |
| Sasha          | implement accept logics                                                                     | 10m    |
| zxqdx          | Added main() for it to generate gv code                                                     | 10m    |
| Sasha          | Added test packages. Added test for ClassDeclaration.                                       | 10m    |
| yangh1         | Added test for Field.                                                                       | 8m     |
| zxqdx          | Added test for Method.                                                                      | 10m    |
| Sasha          | Added test for Klass.                                                                       | 10m    |
| yangh1         | Added sample classes and interface for testing asm. Added test for ClassDeclarationVisitor. | 10m    |
| zxqdx          | Added test for ClassFieldVisitor.                                                           | 10m    |
| Sasha          | Added test for ClassMethodVisitor.                                                          | 10m    |
| yangh1         | Added test cases for init/end buffer of GVOutputStream.                                     | 10m    |
| zxqdx          | Added test cases for visiting class & declaration of GVOutputStream.                        | 10m    |
| Sasha          | Added test cases for visiting fields & methods of GVOutputStream.                           | 8m     |
| yangh1         | UML diagram for the project (manually)                                                      | 20m    |
| zxqdx          | UML diagram for the project (generated)                                                     | 10m    |
| Sasha          | UML diagrams for Lab1-3 (Using zxqdx's solution for Lab1-3)                                 | 10m    |
| yangh1         | Instruction for downloading & using project.                                                | 15m    |
| zxqdx          | Description of the design of the tool.                                                      | 10m    |
| yangh1         | Description of design principles that we followed.                                          | 5m     |
| zxqdx          | Refined the discussion of design principles.                                                | 5m     |

## Usage / Instructions
### Before we start
Please make sure that you have installed [Java](https://www.java.com/en/download/).

Also please make sure that you have installed [Git](https://git-scm.com/).

### Get the code
In command line console, type:
```
git clone git@github.com:SashaMoe/Ragdoll.git
```

Now you should be able to `cd` into the folder:
```
cd
```

You should now have the entire project.

### Run the code
To run the code using `java`, type:
```
java -classpath ./bin:./lib/asm-all-5.0.4.jar ragdoll.app.Ragdoll [<package_name>, ...]
```
For `[<package_name>, ...]`, you can replace it with one or more package names, and our program will traverse your entire package and get all classes in it except classes for testing purposes.

For example, by typing:
```
java -classpath ./bin:./lib/asm-all-5.0.4.jar ragdoll.app.Ragdoll ragdoll
```

This will output the Ragdoll's generated UML textual representation in GraphicViz image format to the console.

### Generate the image
To generate the image, type:
```
java -classpath ./bin:./lib/asm-all-5.0.4.jar ragdoll.app.Ragdoll [<package_name>, ...] | dot -Tpng -o <output_png_path>
```
For `<output_png_path>`, replace it with the path of the output png file.

For example, by typing:
```
java -classpath ./bin:./lib/asm-all-5.0.4.jar ragdoll.app.Ragdoll ragdoll | dot -Tpng -o outputUML.png
```
This will generate a png image file (the UML diagram) in your current directory.

## Pictures comparisons
### Lab1-3
#### Manually created UML diagram
![UML Diagram](/img/Lab1-3UMLManually.png "")

#### Generated UML diagram
![UML Diagram](/img/Lab1-3UMLGenerated.png "")

### Project
#### Manually created UML diagram
![UML Diagram](/img/ProjectUMLManually.png "")

#### Generated UML diagram
![UML Diagram](/img/ProjectUMLGenerated.png "")