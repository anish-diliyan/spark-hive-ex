# Orders Database ERD and Schema

## **Tables in the Orders Database**

### 1. Customers
- Tracks details of customers who place orders.
- **Columns**:
  - `id` (Primary Key): Unique identifier for each customer.
  - `name`: Name of the customer.
  - `email`: Email address of the customer.
  - `phone`: Contact number of the customer.
  - `address`: Physical address of the customer.
- **Relationships**:
  - One-to-Many: with `Orders` (via customer_id).

---

### 2. Products
- Tracks details of available products.
- **Columns**:
  - `id` (Primary Key): Unique identifier for each product.
  - `name`: Product name.
  - `price`: Price per unit of the product.
  - `inventory`: Number of units available in inventory.
- **Relationships**:
  - Many-to-Many: with `Orders` (via `Order_Details`).

---

### 3. Orders
- Tracks customer orders.
- **Columns**:
  - `id` (Primary Key): Unique identifier for each order.
  - `customer_id` (Foreign Key): Links to `Customers.id`.
  - `order_date`: Date when the order was placed.
  - `shipping_date`: Date when the order was shipped.
  - `status`: Order status (e.g., `Processing`, `Shipped`, `Delivered`).
- **Relationships**:
  - One-to-Many: with `Order_Details`.

---

### 4. Order_Details
- Tracks which products were ordered, their quantities, and price details.
- **Columns**:
  - `id` (Primary Key): Unique identifier for each record.
  - `order_id` (Foreign Key): Links to `Orders.id`.
  - `product_id` (Foreign Key): Links to `Products.id`.
  - `quantity`: Quantity of the product ordered.
  - `price`: Price per unit at the time of the order.
  - `total`: Calculated total = `price * quantity`.
- **Relationships**:
  - Many-to-One: with `Orders`.
  - Many-to-One: with `Products`.

---

## Relationships Overview
- **Customers → Orders**:
  - Many-to-One: Each order belongs to one customer, but a customer can place multiple orders.
- **Orders → Order_Details**:
  - One-to-Many: Each order can have multiple items.
- **Order_Details → Products**:
  - Many-to-One: Each ordered item links to a product.