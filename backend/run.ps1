# Load .env (Gmail SMTP giong todo-backend) roi chay backend
$port = 8080
Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction SilentlyContinue |
    ForEach-Object {
        $procId = $_.OwningProcess
        Write-Host "Stopping process on port $port (PID $procId)..."
        Stop-Process -Id $procId -Force -ErrorAction SilentlyContinue
    }
Start-Sleep -Seconds 1

Set-Location $PSScriptRoot

$envFile = Join-Path $PSScriptRoot ".env"
if (Test-Path $envFile) {
    Write-Host "Loading $envFile"
    Get-Content $envFile | ForEach-Object {
        $line = $_.Trim()
        if ($line -and -not $line.StartsWith("#") -and $line -match "^([^=]+)=(.*)$") {
            $name = $matches[1].Trim()
            $value = $matches[2].Trim().Trim('"')
            Set-Item -Path "env:$name" -Value $value
        }
    }
}

if ($env:MAIL_USERNAME -and $env:MAIL_PASSWORD) {
    Write-Host "Gmail SMTP (env): $($env:MAIL_USERNAME)" -ForegroundColor Green
} else {
    Write-Host "Chua co MAIL_USERNAME trong terminal — neu da dien trong application-local.yml thi van OK." -ForegroundColor Yellow
    Write-Host "Neu chua: chay .\setup-mail.ps1 hoac sua application-local.yml" -ForegroundColor Yellow
}

mvn spring-boot:run
