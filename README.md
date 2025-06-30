# PipraPay Android SDK

A simple Android SDK for integrating PipraPay in Java Android apps.

## Features

- Create charge (payment link)
- Verify payment
- Supports sandbox & live URLs
- Lightweight and easy to use

---

## How to Use

1. Add `PipraPay.java` to your project (`com.yourapp.sdk`).
2. Use it in your activity:

```java
PipraPay pipraPay = new PipraPay("YOUR_API_KEY", "https://sandbox.piprapay.com", "BDT");

Map<String, Object> data = new HashMap<>();
data.put("full_name", "John Doe");
data.put("email_mobile", "john@example.com");
data.put("amount", "10");
data.put("redirect_url", "https://yourapp.com/success");
data.put("cancel_url", "https://yourapp.com/cancel");
data.put("webhook_url", "https://yourapp.com/webhook");

pipraPay.createCharge(data, new PipraPay.Callback() {
    @Override
    public void onSuccess(Map<String, Object> response) {
        String url = (String) response.get("pp_url");
        // Open in WebView or Browser
    }

    @Override
    public void onFailure(String error) {
        // Handle failure
    }
});
```

---

## License

MIT
