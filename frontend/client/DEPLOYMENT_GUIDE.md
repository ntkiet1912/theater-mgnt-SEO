# 🚀 Hướng Dẫn Deploy Theater Management Frontend

## ✅ Đã Hoàn Thành (Pre-deployment)

- [x] SEO optimization với metadata, structured data, sitemap
- [x] Environment variable configuration
- [x] Build test thành công
- [x] ShowtimeSchema structured data
- [x] MetadataBase configuration

---

## 📋 Deployment Options

Bạn có 3 options để deploy:

1. **Vercel** (Recommended - Easiest for Next.js)
2. **Netlify** (Alternative)
3. **VPS/Server** (Advanced)

---

# Option 1: Deploy lên Vercel (RECOMMENDED)

## Tại Sao Chọn Vercel?

- ✅ Tích hợp sẵn với Next.js (Vercel là công ty tạo ra Next.js)
- ✅ Free tier rất generous (100GB bandwidth/month)
- ✅ Auto SSL certificate (HTTPS)
- ✅ Serverless deployment
- ✅ Automatic CI/CD từ GitHub
- ✅ Edge network (CDN) toàn cầu
- ✅ Preview deployments cho mỗi commit

---

## 🎯 STEP-BY-STEP: Deploy lên Vercel

### STEP 1: Chuẩn Bị

#### 1.1. Đảm bảo code đã commit

```bash
# Di chuyển vào thư mục root
cd C:\Users\pupgk\theater-mgnt

# Check git status
git status

# Add tất cả changes
git add .

# Commit với message rõ ràng
git commit -m "feat: Add SEO optimization - metadata, structured data, sitemap"

# Push lên GitHub
git push origin feature/SEO
```

#### 1.2. Merge vào main branch (nếu cần)

```bash
# Option A: Merge trực tiếp (nếu bạn là owner)
git checkout main
git merge feature/SEO
git push origin main

# Option B: Tạo Pull Request trên GitHub UI (recommended)
# - Vào GitHub repository
# - Click "Pull requests" > "New pull request"
# - Base: main, Compare: feature/SEO
# - Create pull request > Merge
```

---

### STEP 2: Setup Vercel Account

#### 2.1. Tạo tài khoản Vercel

1. Truy cập: https://vercel.com/signup
2. Chọn "Continue with GitHub"
3. Authorize Vercel truy cập GitHub repos

#### 2.2. Import Project

1. Vào Vercel Dashboard: https://vercel.com/dashboard
2. Click "Add New..." > "Project"
3. Import GitHub repository: `theater-mgnt`
4. Vercel sẽ tự detect Next.js

---

### STEP 3: Configure Project

#### 3.1. Framework Preset

```
Framework Preset: Next.js
```

#### 3.2. Root Directory

**QUAN TRỌNG:** Vì project của bạn là monorepo, phải set root directory:

```
Root Directory: frontend/client
```

Click "Edit" bên cạnh Root Directory và chọn `frontend/client`

#### 3.3. Build & Output Settings

Vercel tự động detect, nhưng verify:

```
Build Command: npm run build
Output Directory: .next
Install Command: npm install
```

#### 3.4. Environment Variables

Click "Environment Variables" và thêm:

```bash
# Key: NEXT_PUBLIC_SITE_URL
# Value: https://your-project-name.vercel.app
# (Vercel sẽ cung cấp URL này sau khi deploy, bạn update sau)

# Key: NEXT_PUBLIC_API_URL
# Value: http://localhost:8080
# (Hoặc URL backend thực nếu đã deploy backend)
```

**NOTE:** Bạn có thể skip NEXT_PUBLIC_SITE_URL lúc đầu, update sau khi có domain.

---

### STEP 4: Deploy

1. Click "Deploy"
2. Đợi 2-3 phút (Vercel sẽ build và deploy)
3. Xem build logs real-time

#### Build Process:

```
✓ Installing dependencies
✓ Building application
✓ Uploading build output
✓ Deployment ready
```

---

### STEP 5: Update Environment Variables

Sau khi deploy xong, Vercel sẽ cung cấp URL:

```
https://theater-mgnt-abc123.vercel.app
```

#### 5.1. Update NEXT_PUBLIC_SITE_URL

1. Vào Project Settings > Environment Variables
2. Edit `NEXT_PUBLIC_SITE_URL`:
   ```
   https://theater-mgnt-abc123.vercel.app
   ```
3. Click "Save"

#### 5.2. Redeploy

1. Vào "Deployments" tab
2. Click "..." bên cạnh latest deployment
3. Click "Redeploy"

Hoặc:

```bash
# Push bất kỳ commit nào để trigger auto redeploy
git commit --allow-empty -m "chore: Trigger redeploy"
git push
```

---

### STEP 6: Verify Deployment

#### 6.1. Test Website

Truy cập URL Vercel đã cung cấp:

```
https://your-project.vercel.app
```

Check:

- [x] Homepage loads
- [x] Movie details page works
- [x] Images hiển thị
- [x] Routing works

#### 6.2. Test SEO Features

1. **Sitemap:**

   ```
   https://your-project.vercel.app/sitemap.xml
   ```

2. **Robots.txt:**

   ```
   https://your-project.vercel.app/robots.txt
   ```

3. **Manifest:**

   ```
   https://your-project.vercel.app/manifest.webmanifest
   ```

4. **Open Graph Preview:**
   - Vào: https://metatags.io/
   - Paste URL: `https://your-project.vercel.app`
   - Check OG image, title, description

#### 6.3. Test Structured Data

1. Vào: https://search.google.com/test/rich-results
2. Paste URL của một movie page:
   ```
   https://your-project.vercel.app/movies/1
   ```
3. Verify Movie schema và ScreeningEvent schema hiển thị

---

### STEP 7: Setup Custom Domain (Optional)

#### 7.1. Nếu bạn có domain riêng:

1. Vào Project Settings > Domains
2. Click "Add Domain"
3. Nhập domain: `theater-management.com`
4. Follow hướng dẫn config DNS:

   ```
   Type: A
   Name: @
   Value: 76.76.21.21

   Type: CNAME
   Name: www
   Value: cname.vercel-dns.com
   ```

5. Đợi DNS propagate (5-30 phút)

#### 7.2. Update Environment Variable

```bash
NEXT_PUBLIC_SITE_URL=https://theater-management.com
```

Redeploy lại.

---

## 🔧 Deploy Backend (Important!)

Frontend cần backend API để hoạt động. Có 2 options:

### Option A: Deploy Backend lên Render/Railway

```bash
# Backend Spring Boot có thể deploy lên:
- Render.com (free tier)
- Railway.app (free tier)
- Heroku ($5/month)
```

Sau khi deploy backend, update:

```bash
NEXT_PUBLIC_API_URL=https://your-backend.render.com
```

### Option B: Keep Backend Local (Testing Only)

**NOTE:** Frontend trên Vercel KHÔNG thể connect tới localhost backend. Phải deploy backend hoặc expose qua ngrok:

```bash
# Install ngrok
npm install -g ngrok

# Start backend local
cd backend
./mvnw spring-boot:run

# Expose backend
ngrok http 8080

# Copy ngrok URL và update env var
NEXT_PUBLIC_API_URL=https://abc123.ngrok.io
```

---

## 📊 Post-Deployment: SEO Setup

### 1. Google Search Console

#### 1.1. Verify Ownership

1. Vào: https://search.google.com/search-console
2. Add property: `https://your-domain.vercel.app`
3. Chọn "HTML tag" method
4. Copy verification meta tag
5. Update `frontend/client/app/layout.tsx`:

   ```tsx
   verification: {
     google: "paste-verification-code-here",
   },
   ```

6. Commit, push, đợi redeploy
7. Click "Verify" trong Search Console

#### 1.2. Submit Sitemap

1. Vào Search Console > Sitemaps
2. Add sitemap URL:
   ```
   https://your-domain.vercel.app/sitemap.xml
   ```
3. Submit

### 2. Google Analytics (Optional)

#### 2.1. Create GA4 Property

1. Vào: https://analytics.google.com/
2. Create account > Property
3. Copy Measurement ID: `G-XXXXXXXXXX`

#### 2.2. Add to Frontend

Update `frontend/client/app/layout.tsx`, thêm trong `<body>`:

```tsx
import Script from 'next/script'

// Inside <body>
<Script
  src="https://www.googletagmanager.com/gtag/js?id=G-XXXXXXXXXX"
  strategy="afterInteractive"
/>
<Script id="google-analytics" strategy="afterInteractive">
  {`
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());
    gtag('config', 'G-XXXXXXXXXX');
  `}
</Script>
```

### 3. Create OG Images

Để social sharing đẹp, tạo images:

```bash
# Vào Canva hoặc Figma
# Tạo design 1200x630px
# Download và save vào public/

frontend/client/public/
  ├── og-image.jpg        (1200x630px)
  ├── twitter-image.jpg   (1200x630px)
  ├── icon-192.png        (192x192px)
  └── icon-512.png        (512x512px)
```

Commit và push:

```bash
git add public/
git commit -m "feat: Add OG images and PWA icons"
git push
```

---

## 🧪 Testing Checklist

Sau khi deploy, test:

### Functionality:

- [ ] Homepage loads
- [ ] Movies list hiển thị
- [ ] Movie detail page works
- [ ] Booking flow works
- [ ] Authentication works
- [ ] Dark/Light mode toggle

### SEO:

- [ ] Sitemap.xml accessible
- [ ] Robots.txt accessible
- [ ] Meta tags hiển thị đúng (check view source)
- [ ] Open Graph preview (metatags.io)
- [ ] Twitter Card preview
- [ ] Structured data valid (Google Rich Results Test)

### Performance:

- [ ] Lighthouse audit > 90 (SEO)
- [ ] Core Web Vitals "Good"
- [ ] Images load nhanh
- [ ] No console errors

### Mobile:

- [ ] Responsive design
- [ ] Mobile-friendly test (Google)
- [ ] PWA installable

---

## 🐛 Troubleshooting

### Issue 1: Build Failed

**Error:** `Module not found`

**Fix:**

```bash
cd frontend/client
npm install
git add package-lock.json
git commit -m "fix: Update dependencies"
git push
```

### Issue 2: API Connection Error

**Error:** `CORS error` hoặc `Network error`

**Fix:** Update backend CORS config:

```java
// backend/src/main/java/config/WebConfig.java
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("https://your-frontend.vercel.app")
        .allowedMethods("GET", "POST", "PUT", "DELETE");
}
```

### Issue 3: Environment Variables Not Working

**Fix:** Vercel chỉ load env vars khi deploy. Sau khi update:

- Redeploy hoặc
- Push commit mới

### Issue 4: Sitemap Empty

**Lý do:** API không running khi build

**Fix:** Đây là normal behavior. Sitemap sẽ có data khi:

- Backend API running
- User truy cập `/sitemap.xml` lần đầu (dynamic generation)

---

## 📈 Monitoring & Maintenance

### Vercel Dashboard

Check:

- Deployment history
- Build logs
- Error logs (Runtime Logs)
- Analytics (pageviews, top pages)

### Google Search Console

Weekly check:

- Coverage (indexed pages)
- Performance (clicks, impressions)
- Core Web Vitals
- Mobile Usability

### Updates

Mỗi lần update code:

```bash
git add .
git commit -m "feat: Your feature"
git push
```

Vercel tự động build và deploy.

---

## 🎉 Success Criteria

Deploy thành công khi:

1. ✅ Website accessible qua HTTPS
2. ✅ All pages load correctly
3. ✅ SEO metadata hiển thị
4. ✅ Sitemap & robots.txt accessible
5. ✅ Google Search Console verified
6. ✅ Lighthouse SEO score > 90
7. ✅ No console errors
8. ✅ Mobile responsive

---

## 📞 Next Steps After Deployment

1. **Share với team** - Test trên nhiều devices
2. **Submit to Google** - Đợi 1-2 tuần để indexed
3. **Monitor performance** - Google Analytics + Search Console
4. **Add more content** - Movies, showtimes
5. **Build backlinks** - Share trên social media
6. **Optimize images** - Convert to WebP, add blur placeholders

---

## 🔗 Useful Links

- **Vercel Docs:** https://vercel.com/docs
- **Next.js Deployment:** https://nextjs.org/docs/deployment
- **Google Search Console:** https://search.google.com/search-console
- **PageSpeed Insights:** https://pagespeed.web.dev/
- **Rich Results Test:** https://search.google.com/test/rich-results
- **Meta Tags Preview:** https://metatags.io/

---

## 💡 Tips

1. **Use Preview Deployments:** Mỗi branch tự động có preview URL
2. **Environment per Branch:** Dev, staging, production env vars
3. **Rollback:** Vercel cho phép rollback về deployment cũ
4. **Custom Domain:** Free SSL certificate tự động
5. **Analytics:** Enable Vercel Analytics cho real-time metrics

---

**Created:** 2025-11-29
**Status:** ✅ Ready to Deploy
**Estimated Time:** 30-45 minutes (first time)

Good luck! 🚀
