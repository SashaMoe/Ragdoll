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
* ragdoll.asm.*

The second part is designed to use our own visitor pattern to output the class structure in either [GraphicViz](#http://www.graphviz.org/) format, or [SDEdit](#http://sdedit.sourceforge.net/) format.
It consists of the following packages:
* ragdoll.code.*.api
* ragdoll.code.*.impl
* ragdoll.code.visitor.api
* ragdoll.code.visitor.impl

Overall, the project uses Decorator Pattern as well as two aspects of Visitor Patterns.

### Evolution of the design
**Note**: In Milestone 1, we considered possible needs in the future to draw more types of arrows. Thus, we modified a little bit of Visitor Pattern in `GVOutputStream` class and allow it to visit `String`s. Therefore, in Milestone 2, we don't see a strong demand of completely redesigning of our design patterns. We choose to keep using our existing one.

In Milestone 3, we recognized the need to properly extend our project's structure. We went through a brainstorm activity during the meeting, and came with the following reasonings:
* This milestone is about to build an entirely different tool.
* At the first glance, we thought we may share some data structure or common classes with the existing one. Indeed, we figured out that the following classes and interfaces could be shared:
 * ragdoll.app.Ragdoll
 * ragdoll.code.visitor.api.IVisitor
 * ragdoll.code.visitor.impl.AOutputStream
 * Some test cases
* For some data structure, such as `ragdoll.code.uml.api.IMethod`, we firstly tended to reuse it in SD tool. However, it added a level of confusion in implementing `Method` class because it doesn't make sense to let a UML drawing tool to have getter and setter to deal with method's depth. It also doesn't make sense to let a SD drawing tool to keep track of method's access level and exceptions, because as long as the code compiles, we will just traverse the compiled code and form the [Call Graph](#https://en.wikipedia.org/wiki/Call_graph).
Therefore, we kept a low level of coupling between these two drawing tools. These are indicated by their own package names.

### Design Principles
The project follows the following design principles:
* *Identify the aspects of your application that vary and separate them from what stays the same.* According to the above discussion, we separated the read-in functionality from the write-out functionality.
* *Program to an interface, not an implementation.* As the UML diagram shows, we are programming to various interfaces that well represents the key components of the project, such as IVisitor, ITraverser, and IClassComponents.
* *Strive for loosely coupled designs between objects that interact*. Since we are using the Visitor Pattern which has the Message Coupling, we are achieving the loose coupling in our design.
* *Classes should be open for extension, but closed for modification.* The Decorator Pattern we are using allows us to wrap up any level of visitors without modifying the existing code.
* *Each method should be either a command or a query.* We tried our best to make methods that we implemented in the project to either be a Command Method (Performs an action but has no return value) or a Query Method (Returns data but has no side effects).

## Who-Did-What table:
| *Name*         | *Who did what*                                                                                                        | *Time* |
|----------------|-----------------------------------------------------------------------------------------------------------------------|--------|
| zxqdx          | init project(asm parser)                                                                                              | 20m    |
| SashaMoe       | create project                                                                                                        | 5m     |
| zxqdx / yangh1 | Setup git environment w/ Yangh1                                                                                       | 10m    |
| Sasha          | Setup interfaces and packages for ragdoll.code                                                                        | 5m     |
| yangh1         | Setup packages for ragdoll.code.visitor & fix typo                                                                    | 10m    |
| zxqdx          | Create Class' classes                                                                                                 | 10m    |
| yangh1         | Create ClassDeclaration constructor and fields                                                                        | 10m    |
| Sasha          | Create Method constructor and fields                                                                                  | 10m    |
| zxqdx          | Create Field constructor and fields. Added getters for all class components                                           | 10m    |
| Sasha          | Implemented one visitor setup method. Fixed interface issues.                                                         | 10m    |
| yangh1         | Create Ragdoll main class and added instantiation process of IClass.                                                  | 10m    |
| Sasha          | In asm, added IClassComponents to IClass. Implemented part of the IVisitor methods.                                   | 10m    |
| zxqdx          | Added visit logic for Fields and Methods.                                                                             | 10m    |
| yangh1         | implement visit logic for class declaration                                                                           | 10m    |
| Sasha          | implement accept logics                                                                                               | 10m    |
| zxqdx          | Added main() for it to generate gv code                                                                               | 10m    |
| Sasha          | Added test packages. Added test for ClassDeclaration.                                                                 | 10m    |
| yangh1         | Added test for Field.                                                                                                 | 8m     |
| zxqdx          | Added test for Method.                                                                                                | 10m    |
| Sasha          | Added test for Klass.                                                                                                 | 10m    |
| yangh1         | Added sample classes and interface for testing asm. Added test for ClassDeclarationVisitor.                           | 10m    |
| zxqdx          | Added test for ClassFieldVisitor.                                                                                     | 10m    |
| Sasha          | Added test for ClassMethodVisitor.                                                                                    | 10m    |
| yangh1         | Added test cases for init/end buffer of GVOutputStream.                                                               | 10m    |
| zxqdx          | Added test cases for visiting class & declaration of GVOutputStream.                                                  | 10m    |
| Sasha          | Added test cases for visiting fields & methods of GVOutputStream.                                                     | 8m     |
| yangh1         | UML diagram for the project (manually)                                                                                | 20m    |
| zxqdx          | UML diagram for the project (generated)                                                                               | 10m    |
| Sasha          | UML diagrams for Lab1-3 (Using zxqdx's solution for Lab1-3)                                                           | 10m    |
| yangh1         | Instruction for downloading & using project.                                                                          | 15m    |
| zxqdx          | Description of the design of the tool.                                                                                | 10m    |
| yangh1         | Description of design principles that we followed.                                                                    | 5m     |
| zxqdx          | Refined the discussion of design principles.                                                                          | 5m     |
| zxqdx          | Changed class list to hashmap. Added parameter support. Experimented with VisitMethodInsn.                            | 10m    |
| Sasha          | Add output for use arrows                                                                                             | 10m    |
| yangh1         | Fixed display issue and fixed incomplete use arrows.                                                                  | 20m    |
| zxqdx          | Added output for association arrows.                                                                                  | 15m    |
| Sasha & yangh1 | Added support for nested types in association.                                                                        | 20m    |
| zxqdx          | Fixed an issue where it displays association arrow and use arrow at the same time.                                    | 5m     |
| Sasha          | Minor bug fix; Create UML for PizzaFactory                                                                            | 10m    |
| yangh1         | Updated manually created UML diagram for project.                                                                     | 15m    |
| zxqdx          | Updated tests & Added tests for tricky abstract factory pattern packages.                                             | 15m    |
| all            | Fixed assoc arrow issue. Added new tests for use & assoc arrows. Added new tests for tricky abstract factory pattern. | 10m    |
| yangh1         | Updated manually created UML diagram for project.                                                                     | 10m    |
| zxqdx          | Regenerate all UML diagrams.                                                                                          | 5m     |
| Sasha          | Node class & Edge class.                                                                                              | 10m    |
| yangh1         | Added logic for GraphMethodVisitor. Removed Edge class.                                                               | 10m    |
| zxqdx          | Added utility methods for method signature.                                                                           | 10m    |
| Sasha          | Implemented the queue for BFS algorithm.                                                                              | 10m    |
| yangh1         | Implemented main logic in GraphMethodVisitor.                                                                         | 15m    |
| zxqdx          | Added DFS helper for CallGraph structure. Fixed several issues along the way.                                         | 15m    |
| Sasha          | Implemented ITraverser logic for Node class. Refactored some code along the way.                                      | 15m    |
| zxqdx          | Fixed issues related to SDEdit syntax. Added sdedit.jar.                                                              | 10m    |
| yangh1         | Ignored the static cases. Updated the instructions.                                                                   | 10m    |
| zxqdx          | Updated README.md. Added generated SD diagram for Collections.shuffle and Ragdoll.generateSD.                         | 10m    |
| Sasha          | Added an automated tricky edge case test for SD tool.                                                                 | 15m    |
| yangh1         | Manually created SD diagrams for both project and java.util.Collections.shuffle(List)                                 | 20m    |
| zxqdx          | Updated the design evolution description.                                                                             | 10m    |

## Usage / Instructions
### Before we start
Please make sure that you have installed [Java](https://www.java.com/en/download/) and [Eclipse](https://www.eclipse.org/downloads/).

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

### Build the project
Import the project into your Eclipse. Then, single click on the project (Ragdoll), and choose `"Project" -> "Clean.."`. This will let Eclipse build the `bin/` folder.

### Run the code
To run the code using `java`, type:
```
java -classpath ./bin:./lib/asm-all-5.0.4.jar ragdoll.app.Ragdoll <diagram_type> [<arguments>, ...]
```
For `<diagram_type>`, you can replace it with the following:
* **UML**: Generate the UML Diagram. Here, the arguments are `[<package_name>, ...]`.
* **SD**: Generate the Sequance Diagram. Here, the argument is `<fully_qualified_method_signature> [<max_depth>]`

For `[<package_name>, ...]`, you can replace it with one or more package names, and our program will traverse your entire package and get all classes in it except classes for testing purposes.

For `<fully_qualified_method_signature>`, you must use this format: `<package_name>.<class_name>.<method_name>([<param_type>, ...])`

For `<max_depth>`, you can specify the maximum depth that the program will traverse, or you can not specify it and it will use the default value `5`.

For example, by typing:
```
java -classpath ./bin:./lib/asm-all-5.0.4.jar UML ragdoll.app.Ragdoll ragdoll
```
This will output the Ragdoll's generated UML textual representation in GraphicViz image format to the console.

And by typing:
```
java -classpath ./bin:./lib/asm-all-5.0.4.jar SD ragdoll.app.Ragdoll ragdoll.asm.sd.test.sample.ClassA.methodA(int)
```
This will output the `ragdoll.asm.sd.test.sample.ClassA.methodA`'s generated Sequence Diagram in SDEdit format to the console.

### Generate the image
#### Generate UML image
To generate the UML image, type:
```
<ragdoll_command> | dot -Tpng -o <output_png_path>
```
For `<ragdoll_command>`, replace it with the command described above.

For `<output_png_path>`, replace it with the path of the output png file.

For example, by typing:
```
java -classpath ./bin:./lib/asm-all-5.0.4.jar ragdoll.app.Ragdoll UML ragdoll | dot -Tpng -o outputUML.png
```
This will generate a png image file `outputUML.png` (the UML diagram) in your current directory.

#### Generate SD image
To generate the SD image, type:
```
<ragdoll_command> > <temp_sd_file_store_path>; java -jar lib/sdedit-4.01.jar -o demo/test.png -t png <temp_sd_file_store_path>
```
You can specify the `<temp_sd_file_store_path>` whereever and whatever you want.

For example, by typing:
```
java -classpath ./bin:./lib/asm-all-5.0.4.jar ragdoll.app.Ragdoll SD "java.util.Collections.shuffle(java.util.List)" 2 > demo/test.sd; java -jar lib/sdedit-4.01.jar -o demo/test.png -t png demo/test.sd
```
This will generate a png image file `test.png` (the SD diagram) in your `demo/` directory.

## Pictures comparisons
### Lab1-3
#### Manually created UML diagram V2
![UML Diagram](/img/Lab1-3UMLManually.png "")

#### Generated UML diagram V2
![UML Diagram](/img/Lab1-3UMLGenerated.png "")

### Project
#### Manually created UML diagram V3
![UML Diagram](/img/ProjectUMLManually.png "")

#### Generated UML diagram V3
![UML Diagram](/img/ProjectUMLGenerated.png "")

#### Manually created SD diagram V1
![SD Diagram](/img/ProjectSDManually.png "")

#### Generated SD diagram V1
![SD Diagram](/img/ProjectSDGenerated.png "")

### Textbook Pizza Factory
#### Generated UML diagram V2
![UML Diagram](/img/PizzaFactoryUMLGenerated.png "")

### Collections.shuffle(List)
#### Manually created SD diagram V1
![SD Diagram](/img/CollectionsShuffleManually.png "")

#### Generated SD diagram
![SD Diagram](/img/CollectionsShuffleGenerated.png "")
