@echo off
setlocal EnableDelayedExpansion

:: PostgreSQL configuration
set "BASE_DIR=D:\learning\executable\postgres"
set "PG_BIN=%BASE_DIR%\bin"
set "PGDATA=%BASE_DIR%\data"
set "PGLOGS=%BASE_DIR%\logs"
set "PG_CTL=%PG_BIN%\pg_ctl.exe"
set "PSQL=%PG_BIN%\psql.exe"

echo PostgreSQL Server Shutdown
echo ========================

:: Check if executables exist
if not exist "%PG_CTL%" (
    echo ERROR: pg_ctl executable not found at: %PG_CTL%
    exit /b 1
)

:: Check if server is running
echo Checking server status...
"%PG_CTL%" status -D "%PGDATA%" >nul 2>&1
if !errorlevel! neq 0 (
    echo PostgreSQL server is not running
    exit /b 0
)

:: Check active connections before shutdown
echo.
echo Checking active connections...
for /f "tokens=1" %%a in ('"%PSQL%" -U postgres -t -c "SELECT count(*) FROM pg_stat_activity WHERE pid ^<^> pg_backend_pid();"') do set "active_connections=%%a"

if !active_connections! gtr 0 (
    echo WARNING: There are !active_connections! active connections
    choice /c YN /m "Do you want to proceed with shutdown"
    if !errorlevel! equ 2 (
        echo Shutdown aborted by user
        exit /b 0
    )
)

:: Attempt graceful shutdown first
echo.
echo Attempting graceful shutdown...
"%PG_CTL%" stop -D "%PGDATA%" -m smart -w -t 60
if !errorlevel! equ 0 (
    echo PostgreSQL server stopped successfully
    exit /b 0
)

:: If graceful shutdown fails, try fast shutdown
echo Graceful shutdown failed. Attempting fast shutdown...
"%PG_CTL%" stop -D "%PGDATA%" -m fast -w -t 30
if !errorlevel! equ 0 (
    echo PostgreSQL server stopped successfully with fast shutdown
    exit /b 0
)

:: If fast shutdown fails, try immediate shutdown as last resort
echo Fast shutdown failed. Attempting immediate shutdown...
"%PG_CTL%" stop -D "%PGDATA%" -m immediate
if !errorlevel! equ 0 (
    echo PostgreSQL server stopped successfully with immediate shutdown
    exit /b 0
) else (
    echo ERROR: Failed to stop PostgreSQL server
    echo Please check the log file at: %PGLOGS%\postgresql.log
    exit /b 1
)
