--http://www.cse.wustl.edu/~dshook/cse530/studios/studio1.html
--https://dev.mysql.com/doc/refman/5.7/en/group-by-functions.html SQL Aggregate (GROUP BY) Function Descriptions
--https://dev.mysql.com/doc/refman/5.7/en/comparison-operators.html Comparison Functions and Operators
--
--/Applications/MAMP/Library/bin/mysql --port=8889 --user=root --password=root

source employees.sql

SELECT * FROM employees

SELECT * FROM employees WHERE hire_date > '1990-01-01'

SELECT first_name, last_name, hire_date FROM employees.employees

SELECT * FROM employees WHERE birth_date > '1961-01-01' and birth_date < '1962-01-01'

SELECT COUNT(first_name) FROM employees

SELECT salary FROM salaries ORDER BY salary DESC

SELECT salary FROM salaries ORDER BY salary

SELECT AVG(salary) FROM salaries

SELECT dept_no, COUNT(dept_no) FROM dept_emp GROUP BY dept_no

SELECT departments.dept_name,COUNT(dept_emp.dept_no) FROM departments RIGHT JOIN dept_emp
ON departments.dept_no=dept_emp.dept_no GROUP BY departments.dept_name

SELECT gender,COUNT(emp_no) FROM employees GROUP BY gender
