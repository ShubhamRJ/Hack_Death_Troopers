import numpy
import cv2
import matplotlib

import io
import os
from firebase_admin import db
os.environ["GOOGLE_APPLICATION_CREDENTIALS"]="C://Users/Swapnil Modani/Downloads/Traffic Control-4c2a6d2e4e45.json"
# Imports the Google Cloud client library
from google.cloud import vision
from google.cloud.vision import types


cam = cv2.VideoCapture(0)
s, im = cam.read() # captures image
cv2.imshow("Test Picture", im) # displays captured image
cv2.imwrite("test.bmp",im) # writes image test.bmp to disk

cv2.destroyAllWindows()

trafficstatus = 0

# Instantiates a client
client = vision.ImageAnnotatorClient()

# The name of the image file to annotate
file_name = 'C://Users/Swapnil Modani/Desktop/Hack_Death_Troopers/tra.jpg'

# Loads the image into memory
with io.open(file_name, 'rb') as image_file:
    content = image_file.read()

image = types.Image(content=content)

# Performs label detection on the image file

resp = client.label_detection(image=image)
labels = resp.label_annotations



response = client.web_detection(image=image)
#labels = response.label_annotations
#print(response)
#print(labels)

print('Labels:')
for label in labels:
    #print(label.description)
    if label.description == 'traffic':
        if label.score >= 0.5:
            print(label.description)
            print(label.score)
            trafficstatus = 1

print(trafficstatus)

#firebase_admin.initialize_app(options = {
#        'databaseURL':'https://hackabit-9f6b2.firebaseio.com'
#    })
#os.environ['GOOGLE_APPLICATION_CREDENTIALS']='C://Users/Swapnil Modani/Downloads/creds.json'
#a = db.reference('/traffic')
#a.push(json.dumps({'trafficstatus':trafficstatus}))

