
CREATE TABLE IF NOT EXISTS rider (
  id BIGINT AUTO_INCREMENT NOT NULL,
   created_at datetime NOT NULL,
   updated_at datetime NOT NULL,
   name VARCHAR(255) NULL,
   CONSTRAINT pk_rider PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS rider_review (
  rider_review_id BIGINT NOT NULL,
   rider_review_comment VARCHAR(255) NULL,
   rider_rating VARCHAR(255) NULL,
   CONSTRAINT pk_riderreview PRIMARY KEY (rider_review_id)
);
CREATE TABLE IF NOT EXISTS driver_review (
  driver_review_id BIGINT NOT NULL,
   driver_review_comment VARCHAR(255) NULL,
   CONSTRAINT pk_driverreview PRIMARY KEY (driver_review_id)
);
CREATE TABLE IF NOT EXISTS driver (
  id BIGINT AUTO_INCREMENT NOT NULL,
   created_at datetime NOT NULL,
   updated_at datetime NOT NULL,
   name VARCHAR(255) NULL,
   license_number VARCHAR(255) NULL,
   CONSTRAINT pk_driver PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS booking_review (
  id BIGINT AUTO_INCREMENT NOT NULL,
   created_at datetime NOT NULL,
   updated_at datetime NOT NULL,
   content VARCHAR(255) NOT NULL,
   rating DOUBLE NULL,
   booking_id BIGINT NOT NULL,
   CONSTRAINT pk_bookingreview PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS booking (
  id BIGINT AUTO_INCREMENT NOT NULL,
   created_at datetime NOT NULL,
   updated_at datetime NOT NULL,
   booking_status ENUM('SCHEDULED','CANCELLED','CAB_ARRIVED','ASSIGNING_DRIVER','IN_RIDE','COMPLETED') NULL,
   start_time datetime NULL,
   end_time datetime NULL,
   total_distance BIGINT NULL,
   driver_id BIGINT NULL,
   rider_id BIGINT NULL,
   CONSTRAINT pk_booking PRIMARY KEY (id)
);

ALTER TABLE booking ADD CONSTRAINT FK_BOOKING_ON_DRIVER FOREIGN KEY (driver_id) REFERENCES driver (id);

ALTER TABLE booking ADD CONSTRAINT FK_BOOKING_ON_RIDER FOREIGN KEY (rider_id) REFERENCES rider (id);
ALTER TABLE booking_review ADD CONSTRAINT uc_bookingreview_booking UNIQUE (booking_id);

ALTER TABLE booking_review ADD CONSTRAINT FK_BOOKINGREVIEW_ON_BOOKING FOREIGN KEY (booking_id) REFERENCES booking (id);
ALTER TABLE driver ADD CONSTRAINT uc_driver_license_number UNIQUE (license_number);
ALTER TABLE driver_review ADD CONSTRAINT FK_DRIVERREVIEW_ON_DRIVERREVIEWID FOREIGN KEY (driver_review_id) REFERENCES booking_review (id);
ALTER TABLE rider_review ADD CONSTRAINT FK_RIDERREVIEW_ON_RIDERREVIEWID FOREIGN KEY (rider_review_id) REFERENCES booking_review (id);