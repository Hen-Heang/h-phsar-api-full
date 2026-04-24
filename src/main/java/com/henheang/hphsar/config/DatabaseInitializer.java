package com.henheang.hphsar.config;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * DatabaseInitializer — Auto Schema Setup on Startup
 * <p>
 * Runs SQL statements once when the application starts to ensure the
 * database schema is ready before any request is handled.
 * <p>
 * Why use this instead of Flyway/Liquibase?
 *   - Simple approach for small, safe schema additions using IF NOT EXISTS
 *   - Safe to run multiple times (no side effects)
 *   - Good for adding columns or tables that may be missing in older deployments
 * <p>
 * When does it run?
 *   @PostConstruct → runs automatically after Spring injects all dependencies,
 *   but before the app starts handling HTTP requests.
 * <p>
 * What it sets up:
 *   - tb_distributor_otp: stores OTP codes sent to distributors (for password reset / verification)
 *   - tb_retailer_otp: stores OTP codes sent to retailers
 *   - tb_store.is_active: adds a soft-delete column if missing
 *   - tb_store.phone: adds phone column if missing
 */
@Component
public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initializeDatabaseSchema() {

        // OTP table for distributors — used to verify email or reset password
        // Links to tb_distributor_account and deletes OTP when account is deleted
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS tb_distributor_otp (
                    id                     SERIAL PRIMARY KEY,
                    distributor_account_id INTEGER NOT NULL REFERENCES tb_distributor_account (id) ON DELETE CASCADE,
                    otp_code               INTEGER NOT NULL,
                    distributor_email      VARCHAR(255) NOT NULL,
                    created_date           TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                );
                """);

        // OTP table for retailers — same purpose as distributor OTP
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS tb_retailer_otp (
                    id                   SERIAL PRIMARY KEY,
                    retailer_account_id  INTEGER NOT NULL REFERENCES tb_retailer_account (id) ON DELETE CASCADE,
                    otp_code             INTEGER NOT NULL,
                    retailer_email       VARCHAR(255) NOT NULL,
                    created_date         TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                );
                """);

        // Add is_active column to tb_store (soft delete — FALSE means store is deactivated, not deleted)
        jdbcTemplate.execute("""
                ALTER TABLE tb_store
                ADD COLUMN IF NOT EXISTS is_active BOOLEAN DEFAULT TRUE;
                """);

        // Add phone column to tb_store for store contact information
        jdbcTemplate.execute("""
                ALTER TABLE tb_store
                ADD COLUMN IF NOT EXISTS phone VARCHAR(50);
                """);

        // Ensure notification tables exist with the correct schema.
        // Creates them fresh if missing; renames FK columns if they were created
        // with old names (retailer_account_id / distributor_account_id).
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS tb_notification_type (
                    id       SERIAL PRIMARY KEY,
                    name     VARCHAR(100) NOT NULL UNIQUE,
                    template TEXT
                );
                """);

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS tb_distributor_notification (
                    id             SERIAL PRIMARY KEY,
                    distributor_id INTEGER NOT NULL REFERENCES tb_distributor_account(id) ON DELETE CASCADE,
                    type_id        INTEGER NOT NULL REFERENCES tb_notification_type(id),
                    content        TEXT,
                    is_read        BOOLEAN DEFAULT FALSE,
                    created_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                );
                """);

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS tb_retailer_notification (
                    id          SERIAL PRIMARY KEY,
                    retailer_id INTEGER NOT NULL REFERENCES tb_retailer_account(id) ON DELETE CASCADE,
                    type_id     INTEGER NOT NULL REFERENCES tb_notification_type(id),
                    content     TEXT,
                    is_read     BOOLEAN DEFAULT FALSE,
                    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                );
                """);

        // Seed order status lookup table (idempotent)
        jdbcTemplate.execute("""
                INSERT INTO tb_status (id, name) VALUES
                    (1, 'PENDING'),
                    (2, 'PROCESSING'),
                    (3, 'CONFIRMED'),
                    (4, 'SHIPPING'),
                    (5, 'DELIVERED'),
                    (6, 'COMPLETED'),
                    (7, 'CANCELLED'),
                    (8, 'REJECTED'),
                    (9, 'DRAFT')
                ON CONFLICT (id) DO NOTHING;
                """);

        // Seed notification types used throughout the order flow (idempotent via ON CONFLICT DO NOTHING)
        jdbcTemplate.execute("""
                INSERT INTO tb_notification_type (id, name, template) VALUES
                    (1,  'Order Received',    NULL),
                    (2,  'Out of Stock',      NULL),
                    (3,  'New Order',         NULL),
                    (4,  'Order Accepted',    NULL),
                    (5,  'Order Declined',    NULL),
                    (6,  'Order Preparing',   NULL),
                    (7,  'Order Dispatching', NULL),
                    (8,  'Order Arrived',     NULL),
                    (9,  'Order Complete',    NULL)
                ON CONFLICT (id) DO NOTHING;
                """);

        // Add order_id FK to notification tables so notifications can link to specific orders
        jdbcTemplate.execute("""
                ALTER TABLE tb_distributor_notification
                ADD COLUMN IF NOT EXISTS order_id INTEGER;
                """);

        jdbcTemplate.execute("""
                ALTER TABLE tb_retailer_notification
                ADD COLUMN IF NOT EXISTS order_id INTEGER;
                """);

        // Rename old FK columns to match the mapper if tables were created with legacy names
        jdbcTemplate.execute("""
                DO $$
                BEGIN
                    IF EXISTS (
                        SELECT 1 FROM information_schema.columns
                        WHERE table_name = 'tb_distributor_notification'
                          AND column_name = 'distributor_account_id'
                    ) THEN
                        ALTER TABLE tb_distributor_notification
                            RENAME COLUMN distributor_account_id TO distributor_id;
                    END IF;

                    IF EXISTS (
                        SELECT 1 FROM information_schema.columns
                        WHERE table_name = 'tb_retailer_notification'
                          AND column_name = 'retailer_account_id'
                    ) THEN
                        ALTER TABLE tb_retailer_notification
                            RENAME COLUMN retailer_account_id TO retailer_id;
                    END IF;

                    IF EXISTS (
                        SELECT 1 FROM information_schema.columns
                        WHERE table_name = 'tb_distributor_notification'
                          AND column_name = 'notification_type_id'
                    ) THEN
                        ALTER TABLE tb_distributor_notification
                            RENAME COLUMN notification_type_id TO type_id;
                    END IF;

                    IF EXISTS (
                        SELECT 1 FROM information_schema.columns
                        WHERE table_name = 'tb_retailer_notification'
                          AND column_name = 'notification_type_id'
                    ) THEN
                        ALTER TABLE tb_retailer_notification
                            RENAME COLUMN notification_type_id TO type_id;
                    END IF;
                END $$;
                """);
    }
}
