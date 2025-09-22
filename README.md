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
2. Install dependencies
   ```bash
   mvn clean install
   ```
3. Compile the project
   ```bash
   mvn clean compile
   ```
4. Run the data processing (enrich jokes and store into SQLite)
   ```bash
   mvn exec:java -Dexec.mainClass="com.jokefactory.App"
   ```
5. Output file:
   ```bash
   src/main/resources/enriched_jokes.jsonl
   ```
6. Database:
   ```bash
   jokes.db
   ```
7. Start the Web application
   ```bash
   mvn spring-boot:run
   ```
8. Open in your browser:
   ```bash
   API: http://localhost:8080/jokes
   Web UI: http://localhost:8080/
   ```
## ⚡ Performance & Concurrency
- Jokes are processed in **parallel** using a thread pool (leveraging all CPU cores).
- Enriched jokes are passed via a **BlockingQueue** to a single-threaded DB writer (safe for SQLite).
- Output JSONL is written with synchronization to avoid race conditions.

