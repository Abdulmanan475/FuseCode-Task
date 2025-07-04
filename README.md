# 📦 Ktor Microservice Project – Full Setup & API Usage Guide

This guide helps you run and test the Ktor-based microservice project — including Docker setup, IntelliJ configuration, and Postman API testing.

---

## ✅ Step 1: Clone the Project

1. Open **IntelliJ IDEA**.
2. Click on **File > New > Project from Version Control**.
3. Paste the repository URL and clone the project.

---

## ⚙️ Step 2: Install Required Tools

- ✅ **Install Docker Desktop** on your PC  
  [Download Docker](https://www.docker.com/products/docker-desktop/)

- ✅ **Install Docker Plugin in IntelliJ**  
  - Go to **File > Settings > Plugins**
  - Search for `"Docker"` and install it
  - Restart IntelliJ IDEA

---

## 🐳 Step 3: Start Kafka & Zookeeper via Docker Compose

1. Open the project in IntelliJ.
2. Locate the file named `docker-compose.yaml`.
3. Right-click on it and click the green **Run** button (`Run 'docker-compose.yaml'`).
4. Wait for the containers (`kafka` and `zookeeper`) to fully start.

If you get a name conflict error, run:

```bash
docker stop kafka
docker rm kafka
```

Then try running Docker Compose again.

---

## ▶️ Step 4: Run the Ktor Application

1. Open the main file (`Application.kt`) in your project.
2. Run the application by clicking the green **Run** button or using shortcut `Shift + F10`.
3. The server should start at:

```
http://localhost:4000
```

---

## 🔐 Step 5: Test Login API in Postman

> This API returns a JWT token which you'll use to access protected routes.

### 🔸 URL:
```
POST http://localhost:4000/login
```

### 🔸 In Postman:

1. Open Postman
2. Select **POST** method
3. Paste the URL: `http://localhost:4000/login`
4. Go to the **Body** tab
5. Select **raw** and choose **JSON** format
6. Paste this JSON:

```json
{
  "username": "admin",
  "password": "password"
}
```

7. Click **Send**
8. You will get a response like:

```json
{
  "token": "your-jwt-token-here"
}
```

✅ Copy this token for the next step.

---

## 📦 Step 6: Test Order API in Postman

> This API places an order and pushes the event to Kafka.

### 🔸 URL:
```
POST http://localhost:4000/order
```

### 🔸 In Postman:

1. Create a new request and select **POST** method.
2. Paste the URL: `http://localhost:4000/order`
3. Go to the **Authorization** tab:
   - Type: **Bearer Token**
   - Paste the token you got from the login API.
4. Go to the **Body** tab
5. Select **raw** and choose **JSON** format
6. Paste the following JSON:

```json
{
  "customerId": "cust123",
  "items": [
    { "sku": "item001", "quantity": 2 },
    { "sku": "item002", "quantity": 1 }
  ]
}
```

7. Click **Send**

✅ You will receive a response like:

```json
{
  "status": "Order placed successfully",
  "orderId": "some-order-id"
}
```

---

### 📄 What Happens in the Background?

- When you hit the **Order API**, the order is published to a Kafka topic.
- In IntelliJ's **Run Console**, you will see Kafka event logs like:

```
Consumed order event: {
  "customerId": "cust123",
  "items": [...]
}
```

This confirms the order event is being sent and processed via Kafka.

---

## 🧼 To Stop Docker Services

To stop Kafka and Zookeeper:

```bash
docker compose -f docker-compose.yaml -p ktor down
```

---

## ❓ Help

If anything breaks:
- Check Docker is running
- Ensure you ran the app *after* Docker
- Make sure you're using the correct token
