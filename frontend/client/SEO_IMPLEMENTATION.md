# SEO Implementation Guide

## ✅ Đã hoàn thành

### 1. Metadata Tối ưu hóa
- **Root Layout** (`app/layout.tsx`): Đã cập nhật metadata toàn cục với thông tin đầy đủ
  - Title template
  - Description
  - Keywords
  - Open Graph tags
  - Twitter Card tags
  - Robots directives
  - Google verification placeholder

### 2. Dynamic Metadata cho Movie Pages
- **Movie Layout** (`app/movies/[id]/layout.tsx`): Generate metadata động cho từng phim
- **Metadata Generator** (`app/movies/[id]/metadata-generator.tsx`): Fetch dữ liệu và tạo metadata
- Bao gồm: title, description, keywords, OG tags, Twitter cards, canonical URLs

### 3. Structured Data (Schema.org)
- **MovieSchema** (`components/movie-schema.tsx`): JSON-LD schema cho Movie
- **ShowtimeSchema** (`components/showtime-schema.tsx`): JSON-LD schema cho ScreeningEvent
- Đã tích hợp vào movie detail page

### 4. Sitemap
- **Sitemap.ts** (`app/sitemap.ts`): Tự động generate sitemap.xml
- Fetch tất cả movies từ API
- Include: homepage, booking, authenticate, movie pages
- Fallback nếu API fails

### 5. Robots.txt
- **Robots.ts** (`app/robots.ts`): Cấu hình crawler access
- Disallow: /api/, /admin/, /profile/, /my-tickets/
- Allow: tất cả trang công khai
- Link đến sitemap

### 6. Manifest (PWA)
- **Manifest.ts** (`app/manifest.ts`): Web app manifest
- Support cho Progressive Web App
- Icons, theme colors, display mode

### 7. Language & Viewport
- Lang attribute: `vi` (Vietnamese)
- Viewport: responsive, max-scale: 5

## 🔧 Cần làm thêm

### 1. **Tạo Open Graph Images**
Tạo các file ảnh sau trong thư mục `public/`:
```
/og-image.jpg (1200x630px)
/twitter-image.jpg (1200x630px)
/icon-192.png (192x192px)
/icon-512.png (512x512px)
```

### 2. **Google Search Console Setup**
- Xác thực website với Google Search Console
- Thay thế `your-google-site-verification-code` trong `layout.tsx` bằng code thực
- Submit sitemap: `https://your-domain.com/sitemap.xml`

### 3. **Update Domain URLs**
Thay đổi `https://theater-management.com` thành domain thực của bạn trong:
- `app/layout.tsx` (line 34)
- `app/sitemap.ts` (line 4)
- `app/robots.ts` (line 4)
- `app/movies/[id]/metadata-generator.tsx` (line 20)

### 4. **Image Optimization**
Thay thế tất cả `<img>` tags bằng Next.js `<Image>` component:

```tsx
import Image from 'next/image'

// Before:
<img src={movie.poster} alt={movie.title} />

// After:
<Image
  src={movie.poster}
  alt={movie.title}
  width={400}
  height={600}
  priority={isAboveFold} // cho images hiển thị ngay
  placeholder="blur"
  blurDataURL="/placeholder.svg"
/>
```

Files cần update:
- `app/movies/[id]/page.tsx` (multiple img tags)
- `components/hero-section.tsx`
- `components/now-showing-section.tsx`
- `components/coming-soon-section.tsx`
- Các components khác có image

### 5. **Semantic HTML**
Cải thiện structure HTML:

```tsx
// Đảm bảo mỗi page chỉ có 1 <h1>
<h1>Tên phim chính</h1>

// Sử dụng semantic tags
<article> // cho movie cards
<section> // cho các sections
<nav> // cho navigation
<aside> // cho sidebar content
```

### 6. **Internal Linking**
Thêm breadcrumbs navigation:

```tsx
// components/breadcrumbs.tsx
<nav aria-label="Breadcrumb">
  <ol>
    <li><a href="/">Home</a></li>
    <li><a href="/movies">Movies</a></li>
    <li aria-current="page">{movie.title}</li>
  </ol>
</nav>
```

### 7. **Performance Optimization**

#### a. Enable Next.js Image Optimization
Đã có trong `next.config.ts`, cần configure thêm:

```ts
const nextConfig: NextConfig = {
  images: {
    domains: ['your-image-cdn.com'], // nếu load images từ external
    formats: ['image/avif', 'image/webp'],
  },
  // ... existing config
}
```

#### b. Add Loading States
Đã có loading skeleton, tốt!

#### c. Code Splitting
Next.js tự động làm, nhưng có thể optimize thêm với dynamic imports:

```tsx
import dynamic from 'next/dynamic'

const HeavyComponent = dynamic(() => import('./heavy-component'), {
  loading: () => <LoadingSkeleton />,
  ssr: false // nếu không cần SSR
})
```

### 8. **Analytics & Monitoring**

#### Google Analytics
Thêm vào `app/layout.tsx`:

```tsx
import Script from 'next/script'

// Trong RootLayout
<Script
  src="https://www.googletagmanager.com/gtag/js?id=GA_MEASUREMENT_ID"
  strategy="afterInteractive"
/>
<Script id="google-analytics" strategy="afterInteractive">
  {`
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());
    gtag('config', 'GA_MEASUREMENT_ID');
  `}
</Script>
```

### 9. **Accessibility (a11y)**
- Thêm `aria-label` cho interactive elements
- Ensure keyboard navigation works
- Add focus styles
- Test với screen readers

### 10. **Local Business Schema** (Optional)
Nếu có địa chỉ rạp thực tế:

```tsx
// components/organization-schema.tsx
{
  "@context": "https://schema.org",
  "@type": "MovieTheater",
  "name": "Theater Management",
  "address": {
    "@type": "PostalAddress",
    "streetAddress": "123 Main St",
    "addressLocality": "Ho Chi Minh City",
    "addressCountry": "VN"
  },
  "telephone": "+84-xxx-xxx-xxx",
  "url": "https://theater-management.com"
}
```

## 📊 Testing & Validation

### Tools để test SEO:

1. **Google Search Console**
   - URL Inspection Tool
   - Coverage Report
   - Mobile Usability

2. **Google Rich Results Test**
   - Test structured data: https://search.google.com/test/rich-results
   - Paste URL của movie page

3. **PageSpeed Insights**
   - https://pagespeed.web.dev/
   - Check Core Web Vitals

4. **Lighthouse** (Chrome DevTools)
   ```bash
   # Hoặc chạy CLI
   npm install -g lighthouse
   lighthouse https://your-domain.com --view
   ```

5. **Schema Validator**
   - https://validator.schema.org/

6. **Meta Tags Checker**
   - https://metatags.io/
   - Debug Open Graph tags

7. **Mobile-Friendly Test**
   - https://search.google.com/test/mobile-friendly

## 🚀 Deployment Checklist

- [ ] Update tất cả domain URLs từ `theater-management.com` sang domain thực
- [ ] Tạo và upload OG images
- [ ] Setup Google Search Console
- [ ] Submit sitemap
- [ ] Add Google Analytics
- [ ] Test all meta tags với metatags.io
- [ ] Validate structured data
- [ ] Run Lighthouse audit
- [ ] Check mobile responsiveness
- [ ] Verify canonical URLs
- [ ] Test 404 pages có proper metadata
- [ ] Enable HTTPS (SSL certificate)
- [ ] Setup redirects (HTTP -> HTTPS, www -> non-www hoặc ngược lại)

## 📈 Expected Results

Sau khi implement đầy đủ:

1. **Search Rankings**
   - Movie pages sẽ xuất hiện trong search với rich snippets
   - Star ratings, price, showtimes hiển thị trong search results

2. **Social Sharing**
   - Rich previews khi share trên Facebook, Twitter, LinkedIn
   - Custom images và descriptions

3. **Performance**
   - Lighthouse score > 90 cho SEO
   - Core Web Vitals đạt "Good"

4. **Discoverability**
   - Google có thể crawl và index tất cả pages
   - Sitemap giúp phát hiện nội dung mới nhanh hơn

## 🔗 Resources

- [Next.js SEO Docs](https://nextjs.org/learn/seo/introduction-to-seo)
- [Google Search Central](https://developers.google.com/search)
- [Schema.org Movie](https://schema.org/Movie)
- [Web.dev SEO Guides](https://web.dev/learn/seo/)
