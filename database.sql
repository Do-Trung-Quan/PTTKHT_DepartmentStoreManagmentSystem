CREATE DATABASE IF NOT EXISTS OnlineDepartmentStore;
USE OnlineDepartmentStore;

CREATE TABLE tblUser (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullName VARCHAR(255) NOT NULL,
    dateOfBirth DATE NOT NULL,
    gender VARCHAR(50) NOT NULL,
    telephone VARCHAR(50) UNIQUE,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE tblStaff (
    tblUserid INT PRIMARY KEY,
    role VARCHAR(50),
    FOREIGN KEY (tblUserid) REFERENCES tblUser(id)
);

CREATE TABLE tblManagementStaff (
    tblUserid INT PRIMARY KEY,
    FOREIGN KEY (tblUserid) REFERENCES tblStaff(tblUserid)
);

CREATE TABLE tblSaleStaff (
    tblUserid INT PRIMARY KEY,
    FOREIGN KEY (tblUserid) REFERENCES tblStaff(tblUserid)
);

CREATE TABLE tblCustomer (
    tblUserid INT PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    FOREIGN KEY (tblUserid) REFERENCES tblUser(id)
);

-- 1. Bảng danh mục sản phẩm
CREATE TABLE tblCategory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL unique,
    description VARCHAR(255)
);

-- 2. Bảng nhà cung cấp
CREATE TABLE tblSupplier (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL unique,
    telephone VARCHAR(50) NOT NULL unique,
    email VARCHAR(50) NOT NULL unique,
    address VARCHAR(255) NOT NULL
);

-- 3. Bảng sản phẩm
CREATE TABLE tblProduct (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL unique,
    quantity INT DEFAULT 0 NOT NULL,
    unitPrice FLOAT NOT NULL,
    description VARCHAR(255),
    tblCategoryid INT NOT NULL,
    FOREIGN KEY (tblCategoryid) REFERENCES tblCategory(id)
);

-- 4. Bảng nhân viên giao hàng
CREATE TABLE tblDeliveryStaff (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    dateOfBirth DATE NOT NULL,
    telephone VARCHAR(50) NOT NULL unique
);

-- 5. Bảng hóa đơn xuất hàng (bán hàng)
CREATE TABLE tblInvoice (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orderDate DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    note VARCHAR(255),
    tblDeliveryStaffid INT NOT NULL,
    tblCustomerid INT NOT NULL,
    tblSaleStaffid INT NOT NULL,
    FOREIGN KEY (tblDeliveryStaffid) REFERENCES tblDeliveryStaff(id),
    FOREIGN KEY (tblCustomerid) REFERENCES tblCustomer(tblUserid),
    FOREIGN KEY (tblSaleStaffid) REFERENCES tblSaleStaff(tblUserid)
);

-- 6. Bảng chi tiết sản phẩm trong hóa đơn (xuất hàng)
CREATE TABLE tblExportProduct (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    note VARCHAR(255),
    tblProductid INT NOT NULL,
    tblInvoiceid INT NOT NULL,
    FOREIGN KEY (tblProductid) REFERENCES tblProduct(id),
    FOREIGN KEY (tblInvoiceid) REFERENCES tblInvoice(id)
);

-- 7. Bảng hóa đơn nhập hàng
CREATE TABLE tblImportInvoice (
    id INT AUTO_INCREMENT PRIMARY KEY,
    importDate DATE NOT NULL,
    note VARCHAR(255),
    tblSupplierid INT NOT NULL,
    tblManagementStaffid INT NOT NULL,
    FOREIGN KEY (tblSupplierid) REFERENCES tblSupplier(id),
    FOREIGN KEY (tblManagementStaffid) REFERENCES tblManagementStaff(tblUserid)
);

-- 8. Bảng chi tiết sản phẩm trong hóa đơn nhập
CREATE TABLE tblImportProduct (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    importUnitPrice FLOAT NOT NULL,
    note VARCHAR(255),
    tblProductid INT NOT NULL,
    tblImportInvoiceid INT NOT NULL,
    FOREIGN KEY (tblProductid) REFERENCES tblProduct(id),
    FOREIGN KEY (tblImportInvoiceid) REFERENCES tblImportInvoice(id)
);

INSERT INTO tblUser (fullName, dateOfBirth, gender, telephone, email, password)
VALUES
('Nguyen Van A', '1990-05-10', 'Male', '0905123456', 'a@company.com', '123456'),
('Tran Thi B', '1995-03-22', 'Female', '0906234567', 'b@company.com', '123456'),
('Le Van C', '1988-12-01', 'Male', '0907345678', 'c@company.com', '123456'),
('Pham Thi D', '1999-09-09', 'Female', '0908456789', 'd@company.com', '123456'),
('Hoang Van E', '1992-02-15', 'Male', '0909567890', 'e@company.com', '123456'),
('Nguyen Thi F', '2000-07-11', 'Female', '0910678901', 'f@company.com', '123456');

INSERT INTO tblStaff (tblUserid, role)
VALUES (1, 'Manager'), (2, 'Sales'), (3, 'Manager'), (5, 'Sales');

INSERT INTO tblManagementStaff (tblUserid)
VALUES (1), (3);

INSERT INTO tblSaleStaff (tblUserid)
VALUES (2), (5);

INSERT INTO tblCustomer (tblUserid, address)
VALUES
(4, '123 Nguyen Trai, Thanh Xuan, Ha Noi'),
(6, '45 Le Loi, District 1, Ho Chi Minh City');

-- ============================================
-- 1. Dữ liệu bảng danh mục sản phẩm
-- ============================================
INSERT INTO tblCategory (title, description) VALUES
('Smartphone', 'Các dòng điện thoại thông minh'),
('Laptop', 'Máy tính xách tay cho học tập và làm việc'),
('Phụ kiện', 'Các thiết bị điện tử phụ kiện đi kèm');

-- ============================================
-- 2. Dữ liệu bảng nhà cung cấp
-- ============================================
INSERT INTO tblSupplier (name, telephone, email, address) VALUES
('Apple Vietnam', '0901234567', 'contact@apple.vn', 'Số 1, Đường Nguyễn Huệ, TP.HCM'),
('FPT Trading', '0907654321', 'sales@fpttrading.vn', 'Tòa nhà FPT, Cầu Giấy, Hà Nội'),
('Samsung VN', '0912345678', 'support@samsung.vn', 'Quận 7, TP.HCM'),
('Dell Vietnam', '0934567890', 'info@dellvn.com', 'Tầng 10, Tòa nhà Etown, Tân Bình, TP.HCM'),
('Asus Vietnam', '0978123456', 'contact@asus.vn', 'Số 12, Hoàng Văn Thụ, Quận Phú Nhuận, TP.HCM'),
('LG Electronics VN', '0923456789', 'support@lg.com.vn', 'Tòa nhà Saigon Centre, Quận 1, TP.HCM');

-- ============================================
-- 3. Dữ liệu bảng sản phẩm
-- ============================================
INSERT INTO tblProduct (name, quantity, unitPrice, description, tblCategoryid) VALUES
('iPhone 15 Pro', 10, 32000000, 'Điện thoại Apple cao cấp', 1),
('MacBook Air M2', 5, 28000000, 'Laptop hiệu năng cao của Apple', 2),
('AirPods Pro 2', 30, 6000000, 'Tai nghe không dây chống ồn', 3);

-- ============================================
-- 4. Dữ liệu bảng nhân viên giao hàng
-- ============================================
INSERT INTO tblDeliveryStaff (name, gender, dateOfBirth, telephone) VALUES
('Nguyễn Văn A', 'Nam', '1998-04-15', '0909111222'),
('Trần Thị B', 'Nữ', '2000-09-21', '0911222333'),
('Phạm Văn C', 'Nam', '1997-12-05', '0933444555');

-- ============================================
-- 7. Dữ liệu bảng hóa đơn nhập hàng (tblImportInvoice)
-- (Giả định tblManagementStaffid = 1,2,3 đã tồn tại)
-- ============================================
INSERT INTO tblImportInvoice (importDate, note, tblSupplierid, tblManagementStaffid) VALUES
('2025-10-10', 'Nhập hàng Apple', 1, 1),
('2025-10-12', 'Nhập hàng FPT', 2, 1),
('2025-10-15', 'Nhập hàng Samsung', 3, 3);

-- ============================================
-- 8. Dữ liệu chi tiết sản phẩm trong hóa đơn nhập (tblImportProduct)
-- ============================================
INSERT INTO tblImportProduct (quantity, importUnitPrice, note, tblProductid, tblImportInvoiceid) VALUES
(10, 25000000, 'Nhập lô iPhone 15 Pro', 1, 1),
(5, 22000000, 'Nhập MacBook Air M2', 2, 2),
(30, 4500000, 'Nhập AirPods Pro 2', 3, 3);

select * from tblUser;
select * from tblStaff;
select * from tblManagementStaff;
select * from tblSaleStaff;
select * from tblCustomer;
select * from tblProduct;
select * from tblImportProduct;
select * from tblImportInvoice;
select * from tblCategory;
select * from tblSupplier;

ALTER TABLE tblUser
ADD UNIQUE (email);

UPDATE tblProduct SET quantity = 15 WHERE name = 'iPhone 15 Pro';
UPDATE tblProduct SET quantity = 15 WHERE name = 'MacBook Air M2';
UPDATE tblProduct SET quantity = 30 WHERE name = 'AirPods Pro 2';

delete from tblCustomer where tblUserid = 27
delete from tblUser where id = 27
drop table tblImportProduct
drop table tblImportInvoiced
drop table tblCategory
drop table tblExportProduct
drop table tblProduct

