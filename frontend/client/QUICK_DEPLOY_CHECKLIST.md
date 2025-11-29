# ⚡ Quick Deploy Checklist

## Pre-Deploy (Đã Hoàn Thành ✅)

- [x] SEO optimization complete
- [x] Build test passed
- [x] Environment variables configured
- [x] ShowtimeSchema added
- [x] MetadataBase configured

---

## 🚀 Deploy to Vercel - Quick Steps

### 1. Push Code to GitHub (5 mins)

```bash
cd C:\Users\pupgk\theater-mgnt
git add .
git commit -m "feat: Add SEO optimization"
git push origin feature/SEO

# Optional: Merge to main
git checkout main
git merge feature/SEO
git push origin main
```

### 2. Setup Vercel (10 mins)

1. ✅ Go to: https://vercel.com/signup
2. ✅ Sign up with GitHub
3. ✅ Click "Add New Project"
4. ✅ Import `theater-mgnt` repo
5. ✅ **IMPORTANT:** Set Root Directory: `frontend/client`
6. ✅ Add Environment Variable (optional for now):
   ```
   NEXT_PUBLIC_API_URL=http://localhost:8080
   ```
7. ✅ Click "Deploy"

### 3. Wait for Deployment (2-3 mins)

```
✓ Installing dependencies
✓ Building
✓ Deploying
```

### 4. Update Site URL (5 mins)

After deploy, you'll get URL: `https://theater-mgnt-xxx.vercel.app`

1. ✅ Go to Project Settings > Environment Variables
2. ✅ Add:
   ```
   NEXT_PUBLIC_SITE_URL=https://theater-mgnt-xxx.vercel.app
   ```
3. ✅ Click Deployments > Redeploy

### 5. Test Deployment (5 mins)

Visit your site and check:

- [ ] Homepage: `https://your-site.vercel.app`
- [ ] Sitemap: `https://your-site.vercel.app/sitemap.xml`
- [ ] Robots: `https://your-site.vercel.app/robots.txt`
- [ ] Movie page works
- [ ] No console errors

### 6. Test SEO (5 mins)

1. **Open Graph Preview:**

   ```
   https://metatags.io/
   Paste your URL
   ```

2. **Rich Results Test:**
   ```
   https://search.google.com/test/rich-results
   Paste movie page URL
   ```

---

## ⚠️ Important Notes

### Backend API

Frontend cần backend để load movies. Options:

**Option A: Deploy Backend (Recommended)**

- Deploy Spring Boot backend lên Render/Railway
- Update `NEXT_PUBLIC_API_URL` với backend URL
- Redeploy frontend

**Option B: Local Backend + Ngrok (Testing)**

```bash
# Terminal 1: Start backend
cd backend
./mvnw spring-boot:run

# Terminal 2: Expose via ngrok
ngrok http 8080

# Update Vercel env var with ngrok URL
NEXT_PUBLIC_API_URL=https://xxx.ngrok.io
```

### Sitemap Warning

Khi build, bạn sẽ thấy:

```
Error generating sitemap: ECONNREFUSED
```

Đây là NORMAL. Lý do:

- Build time không có backend running
- Sitemap có fallback (chỉ homepage)
- Khi backend running, sitemap sẽ generate đầy đủ

---

## 📋 Post-Deploy TODO

### Immediate (Optional):

- [ ] Create OG images (`public/og-image.jpg`, etc.)
- [ ] Setup Google Search Console
- [ ] Submit sitemap
- [ ] Add Google Analytics

### Later:

- [ ] Custom domain
- [ ] SSL certificate (auto by Vercel)
- [ ] Performance optimization
- [ ] Replace `<img>` with `<Image>`

---

## 🎯 Success Criteria

Deploy successful khi:

1. ✅ Site accessible via HTTPS
2. ✅ Pages load without errors
3. ✅ Sitemap & robots.txt work
4. ✅ Meta tags hiển thị trong view source
5. ✅ Mobile responsive

---

## 🆘 Quick Troubleshooting

**Build Failed?**

```bash
cd frontend/client
npm install
git commit -am "fix: Update dependencies"
git push
```

**Env Vars Not Working?**

- Vercel loads env vars at build time
- After updating env vars, MUST redeploy
- Or push new commit to trigger auto deploy

**API Not Working?**

- Check backend is running
- Check CORS config in backend
- Check `NEXT_PUBLIC_API_URL` in Vercel env vars

---

## Total Time: ~30 minutes

**Ready to deploy?** Follow steps 1-5 above! 🚀

For detailed guide, see: `DEPLOYMENT_GUIDE.md`
