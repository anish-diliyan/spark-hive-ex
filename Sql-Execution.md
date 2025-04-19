# SQL Execution Flow (Including JOIN and Window Functions)

---

## Sample Tables

**`products`**:

| **product_id** | **category** | **sales_amount** |
|----------------|--------------|------------------|
| 1              | Electronics  | 15               |
| 2              | Electronics  | 25               |
| 3              | Electronics  | 5                |
| 4              | Appliances   | 10               |
| 5              | Appliances   | 20               |
| 6              | Furniture    | 30               |

**`regions`**:

| **product_id** | **region_name** |
|----------------|-----------------|
| 1              | North           |
| 2              | South           |
| 4              | East            |
| 6              | West            |

---

## Query Used

```sql
SELECT
    p.product_id,
    p.sales_amount,
    p.category,
    r.region_name,
    SUM(p.sales_amount) OVER (PARTITION BY p.category) AS total_category_sales,
    ROW_NUMBER() OVER (PARTITION BY p.category ORDER BY p.sales_amount DESC) AS sales_rank
FROM
    products AS p
JOIN
    regions AS r
ON
    p.product_id = r.product_id
WHERE
    p.sales_amount > 10
GROUP BY
    p.product_id, p.category, p.sales_amount, r.region_name
HAVING
    COUNT(*) > 0
ORDER BY
    p.sales_amount DESC
LIMIT 3;
```

---

## Step-by-Step Execution Flow

---

### 1. **FROM AND JOIN Clause**: Retrieve data from `products` and `regions` tables.

- INNER JOIN `products` and `regions`

### 2. **ON Clause**:

- ON `p.product_id = r.product_id`
- Combine rows from both tables where `product_id` matches in both `products` and `regions`.

**Result after JOIN**:

| **product_id** | **category**   | **sales_amount** | **region_name** |
|----------------|----------------|------------------|-----------------|
| 1              | Electronics    | 15               | North           |
| 2              | Electronics    | 25               | South           |
| 4              | Appliances     | 10               | East            |
| 6              | Furniture      | 30               | West            |

---

### 3. **WHERE Clause** (`sales_amount > 10`):

- Remove rows where `sales_amount <= 10`.

**Filtered Data**:

| **product_id** | **category**   | **sales_amount** | **region_name** |
|----------------|----------------|------------------|-----------------|
| 1              | Electronics    | 15               | North           |
| 2              | Electronics    | 25               | South           |
| 6              | Furniture      | 30               | West            |

---

### 4. **GROUP BY Clause** (`p.product_id`, `p.category`, `p.sales_amount`, `r.region_name`):

- **Explanation**:
    - The groups are formed based on the columns `product_id`, `category`, `sales_amount`, and `region_name`.
    - Each unique combination of these columns forms its own group since the combinations are already unique.

**Visualization of Groups**:

| **Group No.** | **product_id** | **category**   | **sales_amount** | **region_name** |
|---------------|----------------|----------------|------------------|-----------------|
| Group 1       | 1              | Electronics    | 15               | North           |
| Group 2       | 2              | Electronics    | 25               | South           |
| Group 3       | 6              | Furniture      | 30               | West            |

---

### 5. **HAVING Clause** (`COUNT(*) > 0`):

- No groups are filtered out since each group has at least one row.

**Data after HAVING**:

| **product_id** | **category**   | **sales_amount** | **region_name** |
|----------------|----------------|------------------|-----------------|
| 1              | Electronics    | 15               | North           |
| 2              | Electronics    | 25               | South           |
| 6              | Furniture      | 30               | West            |

---

### 6. **Window Functions**:
- Add computations for:
    1. `SUM(p.sales_amount) OVER (PARTITION BY p.category)`: Calculates the total sales for each category.
    2. `ROW_NUMBER() OVER (PARTITION BY p.category ORDER BY p.sales_amount DESC)`: Assigns a rank to products within each category based on their sales (highest first).

**Output with Window Functions**:

| **product_id** | **category**   | **sales_amount** | **region_name** | **total_category_sales** | **sales_rank** |
|----------------|----------------|------------------|-----------------|--------------------------|----------------|
| 2              | Electronics    | 25               | South           | 40                       | 1              |
| 1              | Electronics    | 15               | North           | 40                       | 2              |
| 6              | Furniture      | 30               | West            | 30                       | 1              |

---

### 7. **SELECT Clause**:

- Retrieve the columns specified in the query.

**Output after SELECT**:

| **product_id** | **sales_amount** | **category**   | **region_name** | **total_category_sales** | **sales_rank** |
|----------------|------------------|----------------|-----------------|--------------------------|----------------|
| 2              | 25               | Electronics    | South           | 40                       | 1              |
| 1              | 15               | Electronics    | North           | 40                       | 2              |
| 6              | 30               | Furniture      | West            | 30                       | 1              |

---

### 8. **ORDER BY Clause** (`p.sales_amount DESC`):

- Sort rows by `sales_amount` in descending order.

**Sorted Output**:

| **product_id** | **sales_amount** | **category**   | **region_name** | **total_category_sales** | **sales_rank** |
|----------------|------------------|----------------|-----------------|--------------------------|----------------|
| 6              | 30               | Furniture      | West            | 30                       | 1              |
| 2              | 25               | Electronics    | South           | 40                       | 1              |
| 1              | 15               | Electronics    | North           | 40                       | 2              |

---

### 9. **LIMIT Clause** (`LIMIT 3`):

- Limit the output to the first 3 rows.

**Final Output**:

| **product_id** | **sales_amount** | **category**   | **region_name** | **total_category_sales** | **sales_rank** |
|----------------|------------------|----------------|-----------------|--------------------------|----------------|
| 6              | 30               | Furniture      | West            | 30                       | 1              |
| 2              | 25               | Electronics    | South           | 40                       | 1              |
| 1              | 15               | Electronics    | North           | 40                       | 2              |

---