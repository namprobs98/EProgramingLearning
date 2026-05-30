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

Chỉnh `backend/src/main/resources/application.yml` nếu user/password MySQL khác `root` / `root`.

## 2. Backend

```bash
cd backend
# Tùy chọn: set biến môi trường mail & JWT
set MAIL_USERNAME=your@gmail.com
set MAIL_PASSWORD=your-app-password
set JWT_SECRET=YourSecretKeyAtLeast32Chars!!!!!!!!

mvn spring-boot:run
```

API chạy tại `http://localhost:8080`.

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

## Gmail SMTP

1. Bật xác minh 2 bước trên Google Account.
2. Tạo [App Password](https://myaccount.google.com/apppasswords).
3. Gán `MAIL_USERNAME` và `MAIL_PASSWORD` khi chạy backend.

## Dữ liệu mẫu

Khi DB trống khóa học, backend tự tạo khóa **Java Basics** (2 bài học) qua `DataInitializer`.
