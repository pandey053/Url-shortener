# 🔗 Scalable URL Shortener

A production-ready URL Shortener built using **Spring Boot, MySQL, and Redis**, designed with scalability and performance in mind.  
The system leverages **caching, analytics, and rate limiting** to handle high traffic efficiently.

---

## 🚀 Features

### 🔹 URL Shortening
- Converts long URLs into short, unique codes
- Uses UUID-based generation for uniqueness

### 🔹 Fast Redirection
- Instant redirection to original URL using short code

### 🔹 ⚡ Redis Caching (Cache-Aside Pattern)
- Frequently accessed URLs are cached in Redis
- Reduces database load significantly
- Improves response time

### 🔹 📊 Click Analytics
- Tracks number of clicks per short URL
- Uses Redis atomic counters for real-time analytics

### 🔹 🚫 Rate Limiting
- Limits number of requests per user/code
- Prevents API abuse
- Implemented using Redis atomic counters + TTL

---

## 🏗️ System Design

- **Cache-Aside Pattern** used for efficient data retrieval
- Redis used for:
  - Caching
  - Analytics counters
  - Rate limiting
- MySQL used as persistent storage

---

## ⚙️ Tech Stack

| Layer        | Technology         |
|------------- |------------------  |
| Backend      | Spring Boot (Java) |
| Database     | MySQL              |
| Cache        | Redis              |
| Build Tool   | Maven              |
| Container    | Docker (Redis)     |

---

## 📈 Performance & Impact

- 🚀 Reduced database hits using Redis caching
- ⚡ Improved response time (~20% faster)
- 🔥 Handled 1000+ requests with stable performance
- 🛡️ Prevented abuse using rate limiting

---

## 🧪 API Endpoints

### 🔹 Create Short URL 
POST /longUrl

### 🔹 Redirect  
GET /{code}

### 🔹 Analytics  
GET /analytics/{code}

---

## 🧠 Key Learnings

- Implemented **Redis caching strategies**
- Solved **serialization issues in RedisTemplate**
- Handled **race conditions using atomic operations**
- Built **rate limiter using TTL-based counters**
- Debugged real-world backend issues

---

## 🚀 Future Improvements

- User-based rate limiting (IP/User ID)
- Expiring URLs
- Custom short codes
- Dashboard for analytics
- Distributed deployment

---

## 📌 Conclusion

This project demonstrates building a **scalable backend system** with real-world features like caching, analytics, and rate limiting, making it suitable for production-level applications.

---
