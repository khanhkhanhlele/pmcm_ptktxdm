--Chuyển db thành unsafe để delete dữ liệu table
SET SQL_SAFE_UPDATES = 0;
--Chuyển dn thành safe
SET SQL_SAFE_UPDATES = 1;
--Xóa hết hàng trong bảng
TRUNCATE TABLE pmchamcong.attendancerecords;
--Inset dữ liệu vào bảng (không chạy lệnh insert attendancerecords trong sql của t)
--Dat ten schema la pmchamcong
CREATE TABLE pmchamcong.departments (
    department_id CHAR(10) PRIMARY KEY,
    department_name VARCHAR(255)
);
CREATE TABLE pmchamcong.employees (
    employee_id CHAR(30) PRIMARY KEY,
    name VARCHAR(255),
    department_id CHAR(10),
    employee_type VARCHAR(50),
    FOREIGN KEY (department_id) REFERENCES departments(department_id)
);

CREATE TABLE pmchamcong.attendancerecords (
    record_id CHAR(10) PRIMARY KEY,
    employee_id CHAR(10),
    fingerscanner_id CHAR(10),
    date VARCHAR(10),
    time VARCHAR(8),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);
--Trước khi import chuẩn bị db có sẵn ntn
INSERT INTO pmchamcong.attendancerecords (record_id, employee_id, fingerscanner_id, date, time) VALUES
('RECORD001', 'EMP00001', 'SCAN0001', '16-12-2023', '08:30:00'),
('RECORD002', 'EMP00002', 'SCAN0002', '16-12-2023', '08:35:00'),
('RECORD003', 'EMP00003', 'SCAN0003', '16-12-2023', '08:40:00'),
('RECORD004', 'EMP00004', 'SCAN0004', '16-12-2023', '08:45:00'),
('RECORD005', 'EMP00005', 'SCAN0005', '16-12-2023', '08:50:00'),
('RECORD006', 'EMP00006', 'SCAN0006', '16-12-2023', '08:55:00'),
('RECORD007', 'EMP00007', 'SCAN0007', '16-12-2023', '09:00:00'),
('RECORD008', 'EMP00008', 'SCAN0008', '16-12-2023', '09:05:00'),
('RECORD009', 'EMP00009', 'SCAN0009', '16-12-2023', '09:10:00'),
('RECORD010', 'EMP00010', 'SCAN0010', '16-12-2023', '09:15:00');
--Sau khi import c load csv vào ấn refesh nó sẽ thêm 100 dòng cho c