import pytest
from selenium import webdriver
from selenium.webdriver.common.by import By
from webdriver_manager.chrome import ChromeDriverManager

@pytest.fixture(scope="module")
def driver():
    driver = webdriver.Chrome(ChromeDriverManager().install())
    yield driver
    driver.quit()

def test_google_search(driver):
    driver.get("https://www.google.com")
    search_box = driver.find_element(By.NAME, "q")
    search_box.send_keys("Selenium Python")
    search_box.submit()
    assert "Selenium" in driver.title
