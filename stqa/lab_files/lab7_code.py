import selenium
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import time


chrome_driver_path = "E:\setups\chromedriver_win32\chromedriver.exe"


# Create a new instance of the Chrome driver
driver = webdriver.Chrome(chrome_driver_path)


driver.get("http://20.204.138.86/login")

time.sleep(5)

loginForm = driver.find_element(By.CSS_SELECTOR, '.login-form')
uf = loginForm.find_element(By.XPATH, '//input[1]')
pf = loginForm.find_element(By.XPATH, '//input[2]')
loginButton = loginForm.find_element(By.XPATH, '//button')


uf.send_keys("hrishikesh214")
pf.send_keys("hrishi")


loginButton.click()

time.sleep(2)

if driver.current_url == "http://20.204.138.86/":
    print("Test Passed")
else:
    print("Test Failed")


driver.close()
