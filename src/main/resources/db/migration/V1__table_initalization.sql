CREATE
EXTENSION IF NOT EXISTS pgcrypto;
-- example
CREATE TABLE "order_items"
(
    order_item_id BIGSERIAL PRIMARY KEY NOT NULL UNIQUE,
    order_id      BIGSERIAL             NOT NULL REFERENCES "orders" ("order_id"),
    dish_id       BIGINT                NOT NULL REFERENCES "dishes" ("dish_id"),
    dish_quantity INT                   NOT NULL
);

-- Photos Rating
CREATE TABLE rating
(
    rating_id long PRIMARY KEY UNIQUE,
    user_id integer NOT NULL REFERENCES Users (user_id),
    dish_id integer NOT NULL REFERENCES DIshes (dish_id),
    rating_value decimal NOT NULL,
    comments varchar,
    created_time TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_time TIMESTAMP WITH TIME ZONE NOT NULL
);

-- zzy Bookings
CREATE TYPE booking_status AS ENUM ('PENDING', 'CONFIRMED', 'CANCELLED');

CREATE TABLE Bookings
(
    booking_id       SERIAL PRIMARY KEY,
    user_id          INTEGER                  NOT NULL REFERENCES Users (user_id),
    table_number     INTEGER                  NOT NULL,
    reservation_time TIMESTAMP WITH TIME ZONE NOT NULL,
    status           booking_status           NOT NULL,
    restaurant_id    INTEGER                  NOT NULL REFERENCES Restaurants (restaurant_id),
    created_time     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_time     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Steve Order

CREATE TYPE order_status AS ENUM ('PENDING', 'PREPARING', 'FINISHED', 'CANCELLED');
CREATE TYPE order_type AS ENUM ('DINE_IN', 'DELIVERY', 'PICKUP');

CREATE TABLE "orders"
(
    order_id     BIGSERIAL PRIMARY KEY    NOT NULL UNIQUE,
    user_id      BIGINT REFERENCES "users" ("user_id"),
    created_time TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_time TIMESTAMP WITH TIME ZONE NOT NULL,
    order_status order_status             NOT NULL,
    order_type   order_type               NOT NULL,
    table_number INT,
    pickup_time  TIMESTAMP,
    address      VARCHAR(255),
    total_price  DECIMAL(10, 2)           NOT NULL,
    discount     DECIMAL(10, 2)           NOT NULL,
    note         VARCHAR(255)
);

-- Simon Dishes
CREATE TABLE Dishes
(   
    "dish_id"        BIGSERIAL PRIMARY KEY,
    "dish_name"      VARCHAR(255)        NOT NULL,
    "description"    VARCHAR(255)        NOT NULL,
    "price"          DECIMAL(5,2)        NOT NULL,
    "image_url"      VARCHAR(255)        NOT NULL,
    "category"       VARCHAR(255)        NOT NULL,
    "rating"         DECIMAL(3,1)                ,
    "restaurant_id"  INTEGER             NOT NULL,
    "availability"   BOOLEAN             NOT NULL,
    "created_at" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updated_at" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL

);

-- Robin Restaurants
create TABLE "restaurants"
(
    "restaurant_id"  BIGSERIAL PRIMARY KEY,
    "name"           VARCHAR(255)        NOT NULL,
    "description"    TEXT                NOT NULL,
    "address"        VARCHAR(255)        NOT NULL,
    "contact_number" VARCHAR(255)        NOT NULL,
   "average_rating" DECIMAL(3,1),
    "created_time" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updated_time" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

create TYPE week as enum ('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday');

create TABLE "opening_hours"
(
    "id"  BIGSERIAL PRIMARY KEY,
    "restaurant_id" BIGINT REFERENCES "restaurants" ("restaurant_id"),
    "day_of_week" week NOT NULL,
    "opening_time" VARCHAR(255) NOT NULL,
    "closing_time" VARCHAR(255) NOT NULL,
    "created_time" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updated_time" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Charles Users
CREATE TYPE user_role AS ENUM ('CUSTOMER', 'MERCHANT', 'SYS_ADMIN');
CREATE TABLE users(
    user_id       BIGINT PRIMARY KEY NOT NULL UNIQUE,
    username      VARCHAR(255)       NOT NULL,
    password      VARCHAR(255)       NOT NULL,
    user_email    VARCHAR(255)       NOT NULL UNIQUE,
    avatar_url    VARCHAR(255)       NOT NULL,
    points        INT,
    address  VARCHAR(255),
    user_role     user_role          NOT NULL,
    created_time  TIMESTAMP          NOT NULL,
    updated_time  TIMESTAMP          NOT NULL
);

-- shanshan Ingredients & Items

-- Ingredients Table
CREATE TABLE Ingredients
(
    ingredient_id     SERIAL PRIMARY KEY,
    ingredient_name   VARCHAR(255)       NOT NULL,
    unit              VARCHAR(50)        NOT NULL,
    created_time     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_time     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- IngredientItems Table
CREATE TABLE IngredientItems
(
    ingredientitems_id   SERIAL PRIMARY KEY,
    dish_id              INTEGER           NOT NULL REFERENCES Dishes (dish_id),
    ingredient_id        INTEGER           NOT NULL REFERENCES Ingredients (ingredient_id),
    quantity_value       DECIMAL(10, 2)    NOT NULL,
    quantity_unit        VARCHAR(50)       NOT NULL,
    created_time     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_time     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);
