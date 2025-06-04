# ♻️ Recycling Management System

**A Java application that educates users about recycling through interactive tracking and mini-games, built with OOP principles.**

## 🚀 Features

- **User Profiles**: Track recycling history, points, and games played
- **Smart Recycling Centers**: Generate reports with material-specific disposal tips
- **Mini-Game**: "Trash Collection" challenge with recyclable items
- **Data Persistence**: Save users/centers to CSV files
- **Educational Facts**: Random upcycling tips loaded from text files

## 🛠️ Technical Implementation

- **OOP Pillars**:
  - _Encapsulation_: Protected class attributes with getters/setters
  - _Polymorphism_: `RecyclableItem` subtypes with unique point calculations
  - _Abstraction_: Simplified interfaces like `generateReport()`
- **File I/O**: CSV data storage for users and centers
- **UML Design**: [View system architecture](/uml.png)

## 📦 How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/KimTaeman/Recycling-Management-System.git
   ```
2. Compile and run:

````javac App.java
java App  ```

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.
````
