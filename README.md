# IDDE laborvizsgara

---

## Profilozas

### Spring: application.properties

### nem spring: "PROFILE" env variable

---

### Nem auto_inc id-ju order tabla
CREATE TABLE `orders_noautoincid` (
`id` bigint NOT NULL,
`orderDate` date NOT NULL,
`address` varchar(255) NOT NULL,
`customerName` varchar(255) NOT NULL,
`amountPaid` bigint NOT NULL,
`status` varchar(50) NOT NULL,
PRIMARY KEY (`id`)
);

### Auto_inc idju orders tabla
CREATE TABLE orders (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
orderDate DATE NOT NULL,
address VARCHAR(255) NOT NULL,
customerName VARCHAR(255) NOT NULL,
amountPaid BIGINT NOT NULL,
status VARCHAR(50) NOT NULL
);


### Auto_inc idju products tabla
CREATE TABLE `products` (
`id` bigint NOT NULL AUTO_INCREMENT,
`name` varchar(255) NOT NULL,
`price` bigint NOT NULL,
`stock` bit(1) NOT NULL,
`order_id` bigint NOT NULL,
PRIMARY KEY (`id`),
KEY `FK_order_id` (`order_id`),
CONSTRAINT `FK_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE
);


