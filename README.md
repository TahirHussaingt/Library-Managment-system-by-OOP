# 📚 Library Management System (Java, OOP)

A desktop library management application built in Java using core object-oriented programming principles, with a graphical interface and database-backed persistence.

## 📋 Overview

This project simulates a real library system — managing books, members, and borrowing/returning workflows — while demonstrating core OOP concepts through a clean, structured codebase.

## ✨ Features

- 📖 Add, search, and manage books (including both printed and digital formats)
- 👤 Register and manage library members
- 🔄 Issue and return books with availability tracking
- ⚠️ Custom exception handling for invalid operations (e.g. issuing an unavailable book)
- 🖥️ Graphical user interface built with Java Swing
- 🗄️ Persistent storage via database integration

## 🧱 OOP Concepts Demonstrated

| Concept | Where it's used |
|---|---|
| **Inheritance** | `Book` is the base class, extended by `EBook` and `PrintedBook` for format-specific behavior |
| **Encapsulation** | Model classes (`Book`, `Member`, `Library`) expose controlled access to internal state |
| **Custom Exceptions** | `BookNotAvailableException` handles invalid book-issuing attempts gracefully |
| **Separation of Concerns** | Code is organized into distinct packages: `model`, `gui`, `exception`, `app` |

## 🛠️ Tech Stack

- **Language:** Java
- **IDE:** Apache NetBeans
- **GUI:** Java Swing
- **Persistence:** Database integration via `DatabaseHelper`
- **Build tool:** Apache Ant (`build.xml`)

## 📁 Project Structure
