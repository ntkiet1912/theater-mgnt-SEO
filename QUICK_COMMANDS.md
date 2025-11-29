# ⚡ Quick Commands Reference

## 🎯 Cho Setup: https://theater-mgnt-seo.vercel.app/

---

## 📋 TÓM TẮT NHANH

1. ✅ Backend CORS đã update → Support Vercel + Ngrok
2. ✅ Restart backend
3. ✅ Start ngrok
4. ✅ Copy ngrok URL
5. ✅ Update Vercel env var
6. ✅ Test website

**Total time:** ~10 phút

---

## STEP 1: Restart Backend (2 phút)

### Terminal 1 - Backend:

```bash
# Di chuyển vào thư mục backend
cd C:\Users\pupgk\theater-mgnt\backend\theatermgnt

# Start Spring Boot
./mvnw spring-boot:run

# Windows alternative:
mvnw.cmd spring-boot:run

# Hoặc nếu đã build:
java -jar target/theatermgnt-0.0.1-SNAPSHOT.jar
```

**Wait for:**
```
Started TheatermgntApplication in X.XXX seconds
```

**Verify:**
```bash
# Test trong browser hoặc curl:
http://localhost:8080/api/movies
```

---

## STEP 2: Start Ngrok (1 phút)

### Nếu chưa cài ngrok:

```bash
# Download: https://ngrok.com/download
# Hoặc install via npm:
npm install -g ngrok

# Authenticate (one-time):
ngrok config add-authtoken YOUR_AUTH_TOKEN
```

### Terminal 2 - Ngrok:

```bash
ngrok http 8080
```

**Output:**
```
Forwarding    https://abc123def456.ngrok-free.app -> http://localhost:8080
              ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
              COPY THIS URL!
```

**⭐ COPY ngrok URL:** `https://abc123def456.ngrok-free.app`

---

## STEP 3: Update Vercel Environment Variable (3 phút)

### Option A: Via Vercel Dashboard (GUI)

1. Vào: https://vercel.com/dashboard
2. Select project: **theater-mgnt-seo**
3. **Settings** > **Environment Variables**
4. Find `NEXT_PUBLIC_API_URL` hoặc Add New:
   - Key: `NEXT_PUBLIC_API_URL`
   - Value: `https://abc123def456.ngrok-free.app` (paste ngrok URL)
   - Environments: **Production** (check)
5. **Save**
6. Vào **Deployments** tab
7. Click **"..."** > **"Redeploy"**

### Option B: Via Vercel CLI (Command Line)

```bash
# Install Vercel CLI (nếu chưa có)
npm install -g vercel

# Login
vercel login

# Link project (one-time)
cd C:\Users\pupgk\theater-mgnt\frontend\client
vercel link

# Update env var
vercel env add NEXT_PUBLIC_API_URL production
# Paste ngrok URL when prompted

# Redeploy
vercel --prod
```

---

## STEP 4: Test Website (2 phút)

### Đợi Vercel redeploy xong (~2-3 mins)

### Test frontend:

```
https://theater-mgnt-seo.vercel.app/
```

**Check:**
- [ ] Homepage loads
- [ ] Movies list hiển thị (data từ backend via ngrok)
- [ ] Click movie → Detail page works
- [ ] F12 Console → No CORS errors

### Test ngrok connection:

```
https://abc123def456.ngrok-free.app/api/movies
```

**First time:** Click "Visit Site" button (ngrok warning)

**Expected:** JSON response with movies

### Monitor traffic:

```
http://127.0.0.1:4040
```

Browser ngrok web interface để xem all requests real-time.

---

## 🎯 Full Command Flow (Copy-Paste Ready)

### Setup One-Time:

```bash
# Install ngrok
npm install -g ngrok

# Authenticate ngrok
ngrok config add-authtoken YOUR_AUTH_TOKEN_FROM_DASHBOARD
```

### Daily Workflow:

```bash
# ===== Terminal 1: Backend =====
cd C:\Users\pupgk\theater-mgnt\backend\theatermgnt
./mvnw spring-boot:run
# Wait for: "Started TheatermgntApplication"

# ===== Terminal 2: Ngrok (New terminal!) =====
ngrok http 8080
# Copy ngrok URL: https://xxxxx.ngrok-free.app

# ===== Update Vercel =====
# Go to: https://vercel.com/dashboard
# Settings > Environment Variables
# Update NEXT_PUBLIC_API_URL = https://xxxxx.ngrok-free.app
# Deployments > Redeploy

# ===== Test =====
# https://theater-mgnt-seo.vercel.app/
```

---

## 📊 Current Configuration

### Backend CORS (Already Updated ✅):

```java
// SecurityConfig.java - Line 63-82
corsConfiguration.addAllowedOrigin("http://localhost:5173");     // Local dev
corsConfiguration.addAllowedOrigin("http://localhost:3000");     // Next.js dev
corsConfiguration.addAllowedOrigin("https://theater-mgnt-seo.vercel.app"); // Production
corsConfiguration.addAllowedOriginPattern("https://*.ngrok-free.app");     // Ngrok
```

### Vercel Env Vars (To Update):

```env
NEXT_PUBLIC_SITE_URL=https://theater-mgnt-seo.vercel.app
NEXT_PUBLIC_API_URL=https://xxxxx.ngrok-free.app  # ← UPDATE THIS
```

---

## ⚠️ Important Notes

### Ngrok URL Changes Every Restart

```bash
# Run 1:
Forwarding: https://abc123.ngrok-free.app

# Stop & restart ngrok:
Forwarding: https://xyz789.ngrok-free.app  # ← DIFFERENT URL!
```

**Solution:**
1. Copy new URL
2. Update Vercel env var
3. Redeploy

**Tired of this?**
- Upgrade ngrok ($8/month) → static subdomain
- Or deploy backend permanently (Render/Railway - FREE)

### Keep Both Terminals Running

```
Terminal 1: Backend (Spring Boot) → MUST stay open
Terminal 2: Ngrok                  → MUST stay open
```

If you close either → Frontend breaks.

### Ngrok Free Plan Limits

- ✅ Unlimited HTTP requests (rate limited)
- ✅ 1 concurrent tunnel
- ⚠️ URL changes on restart
- ⚠️ "Visit Site" warning page
- ⚠️ 2-hour session timeout (auto-reconnect)

---

## 🐛 Quick Troubleshooting

### Frontend shows "Network Error"

```bash
# Check 1: Backend running?
curl http://localhost:8080/api/movies

# Check 2: Ngrok running?
# Look at Terminal 2 → should show "Session Status: online"

# Check 3: Ngrok URL correct in Vercel?
# Settings > Environment Variables > NEXT_PUBLIC_API_URL
```

### CORS Error in Console

```bash
# Check 1: Did you restart backend after updating CORS?
# Ctrl+C to stop backend
./mvnw spring-boot:run

# Check 2: Verify CORS config includes Vercel URL
# backend/theatermgnt/src/main/java/.../SecurityConfig.java
# Should have: "https://theater-mgnt-seo.vercel.app"
```

### Ngrok "Tunnel Not Found"

```bash
# Check 1: Ngrok authenticated?
ngrok config add-authtoken YOUR_TOKEN

# Check 2: Correct port?
ngrok http 8080  # NOT 3000!

# Check 3: Backend running first?
# Start backend BEFORE ngrok
```

### "Visit Site" Button Every Time

**Reason:** Ngrok free plan security feature

**Solutions:**
- Accept it (testing only)
- Upgrade ngrok
- Deploy backend permanently

---

## 🔗 Useful URLs

### Local:

- Backend: http://localhost:8080
- Ngrok Dashboard: http://127.0.0.1:4040
- Frontend Dev: http://localhost:3000 (if running locally)

### Production:

- Frontend: https://theater-mgnt-seo.vercel.app/
- Backend: https://xxxxx.ngrok-free.app (changes each restart)
- Vercel Dashboard: https://vercel.com/dashboard
- Ngrok Account: https://dashboard.ngrok.com/

---

## 📱 Test Pages

After setup, test these URLs:

```
✅ Homepage:
https://theater-mgnt-seo.vercel.app/

✅ Movies (data from backend):
https://theater-mgnt-seo.vercel.app/

✅ Movie Detail:
https://theater-mgnt-seo.vercel.app/movies/1

✅ Backend API direct:
https://xxxxx.ngrok-free.app/api/movies

✅ Ngrok Web Interface:
http://127.0.0.1:4040/inspect/http
```

---

## 🎉 Success Indicators

When everything works:

- [x] 2 terminals running (backend + ngrok)
- [x] Ngrok shows "Session Status: online"
- [x] Website loads movies at https://theater-mgnt-seo.vercel.app/
- [x] No CORS errors in Console (F12)
- [x] Ngrok web interface shows API calls
- [x] Movie detail pages work
- [x] Can click through entire site

---

## 💡 Pro Tips

1. **Bookmark ngrok web UI:** http://127.0.0.1:4040
2. **Use tmux/screen:** Keep terminals alive
3. **Script it:** Create start.sh/start.bat
4. **Monitor logs:** Watch both backend + ngrok terminals
5. **Consider deployment:** If tired of ngrok URL changes

---

## 📝 Next Steps

### For Testing (Current Setup):

✅ You're ready! Just run commands above daily.

### For Production (Permanent):

Consider deploying backend:

1. **Render.com** (FREE, recommended)
2. **Railway.app** (FREE)
3. **Heroku** ($5/month)

Then update Vercel:
```
NEXT_PUBLIC_API_URL=https://your-backend.onrender.com
```

One-time setup, no more ngrok URL changes!

---

**Created:** 2025-11-29
**For:** https://theater-mgnt-seo.vercel.app/
**Status:** ✅ Ready to Use
**Time:** ~10 minutes setup

Good luck! 🚀
