# EventSync

EventSync is a backend application that helps teams organize events and automatically analyzes participant feedback using AI sentiment analysis.

## Overview

EventSync is a platform designed to streamline event management for teams organizing workshops, team buildings, or small conferences. The application allows event organizers to create and manage events, while participants can submit feedback after attending. What sets EventSync apart is its integration with AI sentiment analysis, which automatically classifies feedback as positive, neutral, or negative, providing organizers with valuable insights about event reception.

## System Architecture

```
┌─────────────┐     ┌─────────────┐     ┌─────────────────────┐
│             │     │             │     │                     │
│  REST API   │────▶│  Services   │────▶│  Sentiment Analysis │
│  Endpoints  │     │             │     │  API (Hugging Face) │
│             │     │             │     │                     │
└─────────────┘     └──────┬──────┘     └─────────────────────┘
                           │
                           ▼
                    ┌─────────────┐
                    │             │
                    │  H2 Database│
                    │             │
                    └─────────────┘
```

## Features

- Create and view events with title, description, date, and location
- Submit feedback for specific events
- Automatic sentiment analysis of feedback using Hugging Face API
- View feedback count and sentiment summary per event
- RESTful API with Swagger documentation
![Screenshot 2025-06-16 at 01.15.27.png](../../../../var/folders/v_/tyrwxp093zs449kyjgp169mh0000gn/T/TemporaryItems/NSIRD_screencaptureui_HrmRsa/Screenshot%202025-06-16%20at%2001.15.27.png)
## Installation for Users

UI ....LOADING
### Setup

--coming up

## Developer Guide

### Technology Stack

- **Framework**: Spring Boot 3.5.0
- **Language**: Java 17
- **Database**: H2 (in-memory)
- **Build Tool**: Maven
- **API Documentation**: SpringDoc OpenAPI
- **External API**: Hugging Face Sentiment Analysis
- **React**: Front-End

### Project Structure

- `controllers`: REST API endpoints
- `model`: Data models (Event, Feedback, SentimentAnalysis)
- `service`: Business logic and external API integration
- `data`: Repository interfaces for database access

### API Endpoints

- `POST /api/events`: Create a new event
- `GET /api/events`: List all events
- `POST /api/events/{eventId}/feedback`: Submit feedback for an event
- `GET /api/events/{eventId}/summary`: Get sentiment breakdown for an event
- `GET /api/events/feedback`: Get all feedback
- `GET /api/events/summary`: Get sentiment summary for all events

### Development Setup

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Hugging Face API token


1. Clone the repository:
   ```
   git clone https://github.com/yourusername/EventSync.git
   cd EventSync
   ```

2. Import the project into your IDE (IntelliJ IDEA, Eclipse, etc.)

3. Set up the environment variable for the Hugging Face API token:
   ```
   export TOKEN=your_hugging_face_api_token
   ```

4. Run the application from your IDE or using Maven:
   ```
   mvn spring-boot:run
   ```

5. Access the application:
   - UI: http://localhost:8080
   - API: http://localhost:8080/api/events
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:testdb)

6. Run tests:
   ```
   mvn test
   ```


## Contact

[ephraim.gibson@vvk.lt]
