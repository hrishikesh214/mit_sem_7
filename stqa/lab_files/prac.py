import selenium
from selenium import webdriver
from selenium.webdriver.common.by import By

chrome_driver_path = "E:\setups\chromedriver_win32\chromedriver.exe"

driver = webdriver.Chrome(chrome_driver_path)

driver.get("http://google.co.in")

searchBar = driver.find_element(
    By.XPATH, "/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input")
searchBtn = driver.find_element(
    By.XPATH, '/html/body/div[1]/div[3]/form/div[1]/div[1]/div[4]/center/input[1]')


searchBar.send_keys("Hello World")
searchBtn.click()
