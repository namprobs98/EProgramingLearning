# E-Programming Learning Platform

Website học lập trình trực tuyến đa ngôn ngữ — React + Spring Boot + MySQL, song ngữ Vi/En, xác thực email bắt buộc, JWT.

## Cấu trúc dự án

```
EProgramingLearning/
├── database/schema.sql     # MySQL DDL + seed languages
├── backend/                # Spring Boot 3.2 (Java 17)
└── frontend/               # React 18 + Vite
```

## Yêu cầu hệ thống

- Java 17+
- Maven 3.8+
- Node.js 18+
- MySQL 8+

## 1. Cơ sở dữ liệu

```bash
mysql -u root -p < database/schema.sql
```

### Lỗi `Access denied for user 'root'`

Bạn đang chạy profile **mysql** nhưng mật khẩu sai. Chọn một trong hai:

- Chạy **không cần MySQL**: `mvn spring-boot:run` (profile `dev` — mặc định)
- Hoặc dùng Docker: `docker compose up -d` + profile `docker`

## 2. Backend

### Cách nhanh — H2 (mặc định, không cần MySQL)

```powershell
cd backend
.\run.ps1
```

Hoặc `mvn spring-boot:run` — nếu báo **Port 8080 was already in use**, chạy `.\run.ps1` (tự tắt process cũ) hoặc đóng terminal đang chạy backend trước đó.

Profile `dev` dùng H2 in-memory, tự tạo bảng + dữ liệu mẫu. API: `http://localhost:8080`

### MySQL thật — Docker (mật khẩu cố định `eprog123`)

```powershell
docker compose up -d
cd backend
mvn spring-boot:run "-Dspring-boot.run.jvmArguments=-Dspring.profiles.active=docker"
```

### MySQL thật — MySQL cài trên máy

1. Chạy `database/schema.sql`
2. Sửa mật khẩu trong `backend/application-local.yml`
3. Chạy:

```powershell
mvn spring-boot:run "-Dspring-boot.run.jvmArguments=-Dspring.profiles.active=mysql"
```

### API chính

| Method | Endpoint | Mô tả |
|--------|----------|--------|
| POST | `/api/v1/auth/register` | Đăng ký (gửi email xác thực) |
| GET | `/api/v1/auth/verify?token=` | Kích hoạt tài khoản |
| POST | `/api/v1/auth/login` | Đăng nhập (JWT) |
| GET | `/api/v1/languages` | Danh sách ngôn ngữ lập trình |
| GET | `/api/v1/courses` | Khóa học (`Accept-Language: vi\|en`) |
| GET | `/api/v1/courses/{id}/lessons` | Bài học theo khóa |
| GET | `/api/v1/lessons/{id}` | Chi tiết bài học |

## 3. Frontend

```bash
cd frontend
npm install
npm run dev
```

Ứng dụng tại `http://localhost:3000`.

## Luồng đăng ký

1. Đăng ký → user `is_active = false`, token UUID 15 phút.
2. Email chứa link `http://localhost:3000/verify-email?token=...`
3. Frontend gọi `GET /api/v1/auth/verify?token=...`
4. Sau khi kích hoạt mới đăng nhập được.

## Đa ngôn ngữ

- **UI:** `react-i18next` — `frontend/src/locales/vi.json`, `en.json`
- **Dữ liệu DB:** header `Accept-Language` → backend trả `title_vi` / `title_en`, v.v.

## Cấu hình gửi email (giống todo-backend)

App todo của bạn gửi mail qua **Gmail SMTP** — người **nhận** có thể là `@fpt.edu.vn`, nhưng tài khoản **gửi** phải là Gmail + App Password.

```powershell
$env:MAIL_USERNAME="your@gmail.com"
$env:MAIL_PASSWORD="your-gmail-16-char-app-password"
$env:MAIL_MOCK="false"
cd backend
.\run.ps1
```

Hoặc ghi trong `backend/application-local.yml`:

```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: your@gmail.com
    password: "gmail-app-password"
app:
  mail:
    mock: false
```

Bật 2FA Gmail → tạo [App Password](https://myaccount.google.com/apppasswords).

`app.mail.mock=true` hoặc thiếu `MAIL_USERNAME`/`MAIL_PASSWORD` → chỉ in link ra log (CONSOLE), không gửi mail thật.

## Dữ liệu mẫu

Khi DB trống khóa học, backend tự tạo khóa **Java Basics** (2 bài học) qua `DataInitializer`.
