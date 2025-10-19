from selenium.webdriver.common.by import By

class GoogleHomePage:
    def __init__(self, driver):
        self.driver = driver
    def search(self, query):
        search_box = self.driver.find_element(By.NAME, "q")
        search_box.send_keys(query)
        search_box.submit()
