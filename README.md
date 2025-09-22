# Setup & Run

##1. Clean and install mevan
   ```bash
   mvn clean install
   ```
##2. Compile
   ```bash
   Maven clean compile
   ```
##3. Run app
   ```bash
   mvn exec:java -Dexec.mainClass="com.jokefactory.App"
   And you will get the output in resources enriched_jokes.jsonl
   ```
# Claude's Joke Factory - Data Processing & Integration

This project implements a **programming jokes data enrichment and integration system**.  
It includes:
- Data enrichment (Sentiment, Keywords, Readability, Length Classification)
- Data storage (SQLite)
- Web API (Spring Boot REST endpoints)
- Simple Web UI (HTML + JS to display jokes)

---

## ✨ Features

- **Data Enrichment**
  - Joke length classification (short / medium / long)
  - Keyword extraction (based on Stanford CoreNLP POS tagging)
  - Sentiment analysis (Stanford CoreNLP sentiment model)
  - Readability scoring (Flesch-Kincaid Reading Ease)

- **Integration**
  - Enriched jokes stored in SQLite (`jokes.db`)
  - REST API endpoints to query jokes (`/jokes`, `/jokes/{id}`)
  - Simple Web UI (HTML + JS) for visualization

---

## 🛠 Tech Stack

- **Java 17**
- **Maven**
- **Jackson** (JSON processing)
- **SQLite JDBC** (database)
- **Stanford CoreNLP** (NLP enrichment)
- **Spring Boot** (Web API)

---

## 🚀 Setup & Run

1. Clone the repository and navigate into it
   ```bash
   git clone https://github.com/yourname/joke-factory.git
   cd joke-factory

