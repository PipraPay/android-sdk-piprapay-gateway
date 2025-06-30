# PipraPay Android SDK

A simple Android SDK for integrating **PipraPay** payment gateway into your Java-based Android apps.

---

## ✅ Features

- 🔗 Create charge (get payment link)
- 🔍 Verify payment by `pp_id`
- 🛡️ Supports Sandbox & Live PipraPay environments
- 💡 Callback-based async networking
- ⚙️ Lightweight and no third-party dependencies

---

## 📦 Setup

1. Copy `PipraPay.java` into your app (e.g., under `com.yourapp.sdk`)
2. Use it inside your activity or service

---

## 💻 Usage Example

### 1. Initialize the SDK

```java
PipraPay pipraPay = new PipraPay(
    "YOUR_API_KEY",
    "https://sandbox.piprapay.com", // Or your live base URL
    "BDT" // Currency (e.g., BDT, USD)
);
```

### 2. Create Charge (Payment Link)

```java
Map<String, Object> data = new HashMap<>();
data.put("full_name", "John Doe");
data.put("email_mobile", "john@example.com");
data.put("amount", "10");

Map<String, String> metadata = new HashMap<>();
metadata.put("invoiceid", "INV-001");
data.put("metadata", metadata);

data.put("redirect_url", "https://yourapp.com/success");
data.put("cancel_url", "https://yourapp.com/cancel");
data.put("webhook_url", "https://yourapp.com/ipn");

pipraPay.createCharge(data, new PipraPay.Callback() {
    @Override
    public void onSuccess(Map<String, Object> response) {
        String paymentUrl = (String) response.get("pp_url");
        // 👉 Open this URL in a WebView or external browser
    }

    @Override
    public void onFailure(String error) {
        // ❌ Handle error
    }
});
```

### 3. Verify Payment

```java
pipraPay.verifyPayment("181055228", new PipraPay.Callback() {
    @Override
    public void onSuccess(Map<String, Object> response) {
        // ✅ Handle verification success
    }

    @Override
    public void onFailure(String error) {
        // ❌ Handle error
    }
});
```

---

## 📌 Notes

- IPN/Webhook must be handled on your server (`ipn.php` or Laravel endpoint)
- The SDK uses `HttpURLConnection` — no extra libraries required
- Network calls are on background thread, callback runs on UI thread

---

## 📁 File Structure

```
com/
└── yourapp/
    ├── sdk/
    │   └── PipraPay.java        ← Main SDK
    └── example/
        └── MainActivity.java    ← Usage example
```

---

## 🪪 License

MIT License
