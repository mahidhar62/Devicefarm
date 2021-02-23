import boto3, pytest
import os
import unittest
from appium import webdriver
import selenium
from time import sleep

# in your tests:
# Set up the Device Farm client, get a driver URL:
class DeviceFarmAppiumWebTests(unittest.TestCase):
  def setup_method(self, method): 
    devicefarm_client = boto3.client("devicefarm",aws_access_key_id=AKIAXKIRXAQZU447TK4K, aws_secret_access_key=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,region_name="us-west-2")
    testgrid_url_response = devicefarm_client.create_test_grid_url(
      projectArn="arn:aws:devicefarm:us-west-2:503085204531:testgrid-project:9e6db8fd-737b-4a63-819b-028e112a0747",
      expiresInSeconds=300)
    desired_caps = {}
    desired_caps['browserName'] = 'firefox'
    self.driver = webdriver.Remote(testgrid_url_response["url"], desired_caps)


  def test_devicefarm(self):
    self.driver.get(
            'http://docs.aws.amazon.com/devicefarm/latest/developerguide/welcome.html')
    sleep(5)
    screenshot_folder = os.getenv('SCREENSHOT_PATH', '/tmp')
    self.driver.save_screenshot(screenshot_folder + '/devicefarm.png')

  # later, make sure to end your WebDriver session:
  def teardown_method(self, method):
    self.driver.quit()

# Start of script
if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(DeviceFarmAppiumWebTests)
    unittest.TextTestRunner(verbosity=2).run(suite)
