# Ragdoll
CSSE374 Term Project

大家都是萌萌哒~ (As Cute As Ragdoll~)

## Table of contents
1. [Design of the tool](#design-of-the-tool)
2. [Who-Did-What table](#who-did-what-table)
3. [Usage / Instructions](#usage--instructions)
4. [Pictures comparisons](#pictures-comparisons)

## Design of the tool
![UML Diagram](/img/ProjectUMLManually.png "UML Diagram")
Description WIP

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

## Usage / Instructions
WIP

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