import credentials
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
#from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.common.exceptions import TimeoutException
import sqlite3
from datetime import datetime


timeout = 10
driver = webdriver.Chrome(ChromeDriverManager().install())
driver.get('https://sti24.tfi.bnpparibas.pl/client/login')

try:
    element = WebDriverWait(driver, timeout).until(EC.element_to_be_clickable((By.ID, "submitLogin")))
except TimeoutException:
    print("Loading took too much time")
username = driver.find_element_by_id("user-login")
password = driver.find_element_by_id("user-password")
username.send_keys(credentials.user_login)
password.send_keys(credentials.user_password)
driver.find_element_by_id("submitLogin").click()

try:
    element = WebDriverWait(driver, timeout).until(EC.element_to_be_clickable((By.CLASS_NAME, "register-detail")))
except TimeoutException:
    print("Loading took too much time")
driver.find_element_by_class_name("register-detail").click()

try:
    element = WebDriverWait(driver, timeout).until(EC.presence_of_element_located((By.CLASS_NAME, "ui-table-tbody")))
except TimeoutException:
    print("Loading took too much time")

table = driver.find_element(By.CLASS_NAME, "ui-table-tbody").text.split("\n")
first_obli = table[0][table[0].index("pracodawcy") + 11:].replace(" ", "")
second_obli = table[1][table[1].index("pracownika") + 11:].replace(" ", "")
first_equity = table[2][table[2].index("pracodawcy") + 11:].replace(" ", "")
second_equity = table[3][table[3].index("pracownika") + 11:].replace(" ", "")
first_obli_val = float(first_obli[:first_obli.index("P")].replace(",", "."))
first_obli_count = float(first_obli[first_obli.index("N") + 1:].replace(",", "."))
second_obli_val = float(second_obli[:second_obli.index("P")].replace(",", "."))
second_obli_count = float(second_obli[second_obli.index("N") + 1:].replace(",", "."))
first_equity_val = float(first_equity[:first_equity.index("P")].replace(",", "."))
first_equity_count = float(first_equity[first_equity.index("N") + 1:].replace(",", "."))
second_equity_val = float(second_equity[:second_equity.index("P")].replace(",", "."))
second_equity_count = float(second_equity[second_equity.index("N") + 1:].replace(",", "."))
obli_sum_val = first_obli_val + second_obli_val
obli_total_count = first_obli_count + second_obli_count
equity_sum_val = first_equity_val + second_equity_val
equity_total_count = first_equity_count + second_equity_count
total = obli_sum_val + equity_sum_val

print(f"{first_obli_val}\n{second_obli_val}\n{first_equity_val}\n{second_equity_val}")
print(f"{first_obli_count}\n{second_obli_count}\n{first_equity_count}\n{second_equity_count}")
try:
    conn = sqlite3.connect('investments.sqlite')
    cur = conn.cursor()
    cur.execute('CREATE TABLE investments (date timestamp, obligations REAL, obligations_units REAL, equity REAL, equity_units REAL, total REAL)')
    conn.commit()
    sqlite_insert_data = """INSERT INTO 'investments' ('date', 'obligations', 'obligations_units', 'equity', 'equity_units', 'total') VALUES (?, ?, ?, ?, ?, ?);"""
    data_tuple = (datetime.now(), obli_sum_val, obli_total_count, equity_sum_val, equity_total_count, total)
    cur.execute(sqlite_insert_data, data_tuple)
    conn.commit()
    cur.close()
except sqlite3.Error as error:
    print("Database error")
finally:
    if conn:
        conn.close()
