# Tao file .env — cung cach todo-backend (PowerShell env)
$envPath = Join-Path $PSScriptRoot ".env"

Write-Host "Cau hinh Gmail SMTP (giong TodoWeb)" -ForegroundColor Cyan
Write-Host "App Password: https://myaccount.google.com/apppasswords`n"

$username = Read-Host "Gmail (MAIL_USERNAME)"
$secure = Read-Host "App Password (MAIL_PASSWORD)" -AsSecureString
$plain = [Runtime.InteropServices.Marshal]::PtrToStringAuto(
    [Runtime.InteropServices.Marshal]::SecureStringToBSTR($secure))

@"
MAIL_USERNAME=$username
MAIL_PASSWORD=$plain
MAIL_MOCK=false
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
DB_USER=root
DB_PASSWORD=123456
"@ | Set-Content -Path $envPath -Encoding UTF8

Write-Host "`nDa tao $envPath — chay .\run.ps1 de khoi dong backend." -ForegroundColor Green
