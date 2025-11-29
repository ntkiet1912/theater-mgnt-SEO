# 🔧 Backend Option B: Local Backend + Ngrok

**Tình huống:** Frontend đã deploy lên Vercel, backend chạy local

**Frontend URL:** https://theater-mgnt-seo.vercel.app/
**Backend Local:** http://localhost:8080
**Vấn đề:** Vercel KHÔNG thể connect tới localhost
**Giải pháp:** Ngrok expose backend ra public URL

---

## 📋 Yêu Cầu:

- [x] Backend Spring Boot running
- [x] Frontend đã deploy lên Vercel
- [ ] Ngrok installed
- [ ] CORS configured

---

## STEP 1: Cài Đặt Ngrok

### Option A: Download Executable (Recommended)

```bash
# 1. Download ngrok
# Vào: https://ngrok.com/download
# Chọn Windows > Download ZIP

# 2. Giải nén vào thư mục (ví dụ):
C:\ngrok\ngrok.exe

# 3. Add vào PATH (Optional):
# - Right-click "This PC" > Properties
# - Advanced > Environment Variables
# - System variables > Path > Edit > New
# - Add: C:\ngrok
# - OK all

# 4. Verify
ngrok version
```

### Option B: Install via npm

```bash
npm install -g ngrok
```

### Sign Up & Authenticate (FREE)

```bash
# 1. Sign up: https://dashboard.ngrok.com/signup
# 2. Copy authtoken từ dashboard
# 3. Authenticate:

ngrok config add-authtoken YOUR_AUTH_TOKEN_HERE
```

---

## STEP 2: Update Backend CORS Configuration

### File: `backend/theatermgnt/src/main/java/com/theatermgnt/theatermgnt/configuration/SecurityConfig.java`

Tìm method `corsConfigurationSource()` (line 63-74) và **UPDATE:**

```java
@Bean
public UrlBasedCorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();

    // Existing local origins
    corsConfiguration.addAllowedOrigin("http://localhost:5173");
    corsConfiguration.addAllowedOrigin("http://localhost:3000");

    // ADD THESE TWO LINES:
    corsConfiguration.addAllowedOrigin("https://theater-mgnt-seo.vercel.app");
    corsConfiguration.addAllowedOriginPattern("https://*.ngrok-free.app");

    corsConfiguration.addAllowedMethod("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
}
```

**Giải thích:**
- `theater-mgnt-seo.vercel.app` - Allow frontend Vercel
- `*.ngrok-free.app` - Allow mọi ngrok URLs (vì ngrok URL thay đổi mỗi lần restart)

### Save file và RESTART backend:

```bash
# Stop backend (Ctrl+C trong terminal đang chạy backend)

# Restart backend
cd C:\Users\pupgk\theater-mgnt\backend\theatermgnt
./mvnw spring-boot:run

# Hoặc Windows:
mvnw.cmd spring-boot:run
```

---

## STEP 3: Start Backend (Nếu Chưa Running)

### Check backend đang chạy:

```bash
curl http://localhost:8080/api/movies
```

Nếu error, start backend:

```bash
cd C:\Users\pupgk\theater-mgnt\backend\theatermgnt

# Option 1: Maven wrapper
./mvnw spring-boot:run

# Option 2: Windows
mvnw.cmd spring-boot:run

# Option 3: Nếu đã build jar
java -jar target/theatermgnt-0.0.1-SNAPSHOT.jar
```

**Wait for:**
```
Started TheatermgntApplication in X.XXX seconds
```

**Verify:**
```bash
# Test API
curl http://localhost:8080/api/movies

# Hoặc mở browser:
http://localhost:8080/api/movies
```

---

## STEP 4: Start Ngrok

### Mở Terminal/CMD MỚI (QUAN TRỌNG: Giữ backend terminal đang chạy!)

```bash
ngrok http 8080
```

**Output:**

```
ngrok

Session Status                online
Account                       Your Name (Plan: Free)
Version                       3.5.0
Region                        United States (us)
Latency                       45ms
Web Interface                 http://127.0.0.1:4040
Forwarding                    https://abc123def456.ngrok-free.app -> http://localhost:8080

Connections                   ttl     opn     rt1     rt5     p50     p90
                              0       0       0.00    0.00    0.00    0.00
```

### ⭐ COPY Forwarding URL:

```
https://abc123def456.ngrok-free.app
```

**LƯU Ý:**
- URL này **THAY ĐỔI** mỗi lần restart ngrok (Free plan)
- **KHÔNG TẮT** terminal này
- Giữ cả 2 terminals đang chạy:
  - Terminal 1: Backend (Spring Boot)
  - Terminal 2: Ngrok

---

## STEP 5: Test Ngrok URL

### Test trong Browser:

```
https://abc123def456.ngrok-free.app/api/movies
```

**Ngrok Free Plan Warning:**
- Lần đầu truy cập sẽ hiện warning page
- Click "**Visit Site**"
- Bạn sẽ thấy JSON response từ backend

### Test với curl:

```bash
curl https://abc123def456.ngrok-free.app/api/movies
```

**Expected response:**
```json
[
  {
    "id": 1,
    "title": "Movie Title",
    "genre": ["Action", "Adventure"],
    ...
  }
]
```

✅ **Nếu thấy data → Ngrok hoạt động!**

---

## STEP 6: Update Vercel Environment Variable

### 6.1. Vào Vercel Dashboard:

1. https://vercel.com/dashboard
2. Select project: **theater-mgnt-seo**
3. Settings > Environment Variables

### 6.2. Update NEXT_PUBLIC_API_URL:

**Nếu đã có variable:**
- Click "Edit" bên cạnh `NEXT_PUBLIC_API_URL`
- Update value:
  ```
  https://abc123def456.ngrok-free.app
  ```
  (Thay bằng ngrok URL thực của bạn)

**Nếu chưa có:**
- Click "Add New"
- Key: `NEXT_PUBLIC_API_URL`
- Value: `https://abc123def456.ngrok-free.app`
- Environments: Production, Preview, Development (check all)
- Click "Save"

### 6.3. Redeploy Frontend:

**Option A: Redeploy từ Vercel UI:**
1. Vào tab "Deployments"
2. Click "..." bên cạnh latest deployment
3. Click "Redeploy"
4. Confirm

**Option B: Trigger redeploy với git:**
```bash
cd C:\Users\pupgk\theater-mgnt

git commit --allow-empty -m "chore: Update backend URL to ngrok"
git push origin feature/SEO
```

**Đợi deployment finish (~2-3 mins)**

---

## STEP 7: Verify Full Stack Connection

### 7.1. Kiểm tra Ngrok vẫn đang chạy:

Terminal ngrok phải vẫn hiển thị:
```
Session Status                online
Forwarding                    https://abc123def456.ngrok-free.app -> http://localhost:8080
```

### 7.2. Test Frontend trên Vercel:

```
https://theater-mgnt-seo.vercel.app/
```

**Check:**
- [ ] Homepage loads
- [ ] Movies list hiển thị (data từ backend)
- [ ] Click vào movie → Movie detail page
- [ ] No CORS errors trong Console (F12)

### 7.3. Monitor Ngrok Traffic:

**Ngrok Web Interface:**
```
http://127.0.0.1:4040
```

Mở trong browser, bạn sẽ thấy:
- All requests từ Vercel frontend
- Request/Response details
- Status codes
- Timing

---

## STEP 8: Monitor & Debug

### Check Backend Logs:

Terminal đang chạy backend sẽ show:
```
GET /api/movies
GET /api/movies/1
POST /auth/token
...
```

### Check Ngrok Logs:

Terminal ngrok hoặc web interface:
```
GET /api/movies           200 OK
GET /api/movies/1         200 OK
```

### Frontend Console:

```
F12 > Console
```

**Không có errors là OK!**

**Nếu có CORS error:**
- Check CORS config trong SecurityConfig.java
- Verify đã restart backend sau khi update CORS
- Verify ngrok URL đúng trong Vercel env vars

---

## 🎯 Architecture Overview

```
User Browser
    ↓
    ↓ HTTPS
    ↓
Vercel Frontend (https://theater-mgnt-seo.vercel.app)
    ↓
    ↓ API Calls (HTTPS)
    ↓
Ngrok Public URL (https://abc123.ngrok-free.app)
    ↓
    ↓ HTTP Tunnel
    ↓
Localhost:8080 (Your Backend - Spring Boot)
    ↓
    ↓
Database (Local or Remote)
```

---

## 📊 Current Status Checklist

- [ ] Backend running on http://localhost:8080
- [ ] Ngrok tunnel active (https://xxx.ngrok-free.app)
- [ ] CORS updated with Vercel URL
- [ ] Vercel env var updated with ngrok URL
- [ ] Frontend redeployed
- [ ] Website loads movies successfully
- [ ] No CORS errors

---

## ⚠️ LIMITATIONS của Ngrok Free Plan

### 1. **URL Thay Đổi Mỗi Lần Restart**

Mỗi lần bạn restart ngrok:
```bash
# Old URL (hết hiệu lực)
https://abc123.ngrok-free.app

# New URL (mới)
https://xyz789.ngrok-free.app
```

**Solution:**
- Copy new URL
- Update Vercel env var
- Redeploy frontend

**Mệt mỏi?** → Upgrade ngrok ($8/month) cho static domain

### 2. **Warning Page**

Lần đầu user visit, ngrok hiện warning page.

**Solution:**
- Click "Visit Site" (one-time per browser)
- Hoặc upgrade ngrok plan

### 3. **Connection Limits**

Free plan: Limited connections/minute

**For testing:** OK
**For production:** KHÔNG phù hợp

### 4. **Ngrok Phải Luôn Chạy**

Nếu tắt ngrok → Frontend lỗi.

**Solution:** Giữ ngrok running 24/7 hoặc deploy backend.

---

## 🚀 Alternative: Deploy Backend (Recommended for Production)

Ngrok chỉ tốt cho **TESTING**. Cho production, deploy backend:

### Option A: Render.com (FREE)

1. Vào: https://render.com/
2. Sign up with GitHub
3. New > Web Service
4. Connect repository: `theater-mgnt`
5. Root Directory: `backend/theatermgnt`
6. Build Command: `./mvnw clean package -DskipTests`
7. Start Command: `java -jar target/theatermgnt-0.0.1-SNAPSHOT.jar`
8. Deploy

**Result:** `https://theater-backend.onrender.com`

### Option B: Railway.app (FREE)

1. Vào: https://railway.app/
2. Similar setup
3. Auto-detect Spring Boot

### Update Vercel:

```
NEXT_PUBLIC_API_URL=https://theater-backend.onrender.com
```

**Redeploy frontend → DONE!**

---

## 🐛 Troubleshooting

### Issue 1: CORS Error

**Error in Console:**
```
Access to fetch at 'https://xxx.ngrok-free.app/api/movies'
from origin 'https://theater-mgnt-seo.vercel.app'
has been blocked by CORS policy
```

**Fix:**

1. Check SecurityConfig.java:
   ```java
   corsConfiguration.addAllowedOrigin("https://theater-mgnt-seo.vercel.app");
   corsConfiguration.addAllowedOriginPattern("https://*.ngrok-free.app");
   ```

2. Restart backend:
   ```bash
   # Ctrl+C to stop
   ./mvnw spring-boot:run
   ```

### Issue 2: Ngrok Tunnel Not Found

**Error:**
```
ERR_NGROK_3200
```

**Fix:**
- Verify ngrok đang chạy
- Check ngrok terminal cho forwarding URL
- Copy đúng URL (include https://)

### Issue 3: Backend Not Responding

**Error:** 502 Bad Gateway

**Fix:**

1. Check backend running:
   ```bash
   curl http://localhost:8080/api/movies
   ```

2. Check ngrok forwarding đúng port:
   ```bash
   ngrok http 8080  # NOT 3000 or other port
   ```

### Issue 4: Ngrok URL Changed

**Symptom:** Frontend suddenly can't load data

**Reason:** Ngrok restarted → new URL

**Fix:**

1. Check ngrok terminal for new URL
2. Update Vercel env var
3. Redeploy

### Issue 5: "Visit Site" Button Every Time

**Reason:** Ngrok free plan warning

**Fix:**
- Accept this for testing
- Or upgrade ngrok ($8/month)

---

## 📝 Daily Workflow

### Khi Bắt Đầu Làm Việc:

```bash
# Terminal 1: Start Backend
cd C:\Users\pupgk\theater-mgnt\backend\theatermgnt
./mvnw spring-boot:run

# Terminal 2: Start Ngrok
ngrok http 8080

# Copy ngrok URL
# Nếu URL thay đổi → Update Vercel env var + Redeploy
```

### Khi Kết Thúc:

```bash
# Stop Ngrok: Ctrl+C trong terminal 2
# Stop Backend: Ctrl+C trong terminal 1
```

### Ngày Hôm Sau:

```bash
# Start lại backend + ngrok
# URL ngrok SẼ KHÁC
# Phải update Vercel env var lại
```

**Mệt?** → Consider deploying backend permanently.

---

## 🎉 Success Checklist

Khi setup thành công, bạn sẽ thấy:

- [x] 2 terminals đang chạy (backend + ngrok)
- [x] Ngrok shows: "Session Status: online"
- [x] Backend API accessible via ngrok URL
- [x] Vercel frontend loads movies
- [x] No CORS errors
- [x] Ngrok web interface (http://127.0.0.1:4040) shows traffic
- [x] Can click movies → detail page works
- [x] Booking flow works

---

## 💡 Tips

1. **Bookmark ngrok web interface:** http://127.0.0.1:4040
2. **Use fixed domain:** Upgrade ngrok → static subdomain
3. **Monitor traffic:** Check ngrok interface for all API calls
4. **Keep terminals open:** Don't close backend or ngrok
5. **Consider deployment:** For long-term, deploy backend properly

---

## 🔗 Useful Links

- **Ngrok Docs:** https://ngrok.com/docs
- **Ngrok Dashboard:** https://dashboard.ngrok.com/
- **Vercel Env Vars:** https://vercel.com/docs/environment-variables
- **Spring Boot CORS:** https://spring.io/guides/gs/rest-service-cors/

---

**Status:** ✅ Ready to Use
**Complexity:** ⭐⭐ Medium
**Time to Setup:** 15-20 mins
**Best For:** Testing, Development
**Production Ready:** ❌ No (use actual backend deployment)

---

Need help? Check troubleshooting section above! 🚀
