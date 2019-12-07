import flask
import werkzeug
import os
import subprocess
from zipfile import ZipFile
from flask_session import Session

app = flask.Flask(__name__)
SESSION_TYPE = 'filesystem'
Session(app)

#Route to handle POST request to URL server
@app.route('/', methods=["POST"])
def handle_request():

    #Get the image file & store it to the current working directory
    imageFile = flask.request.files['image']
    filename = werkzeug.utils.secure_filename(imageFile.filename)
    print('\n Recieved image File name:',imageFile.filename)
    imageFile.save(os.path.join("Data/examples",filename))

    #Convert the jpg image into an obj object
    tempPath = 'Data/examples/'+filename
    resultCode = subprocess.call(['python','demo.py','--image',tempPath])

    if(resultCode ==0):
        objPath = tempPath.replace('.png','.obj')
        txtPath = objPath.replace('.obj','_obj.txt')
        txtName = filename.replace('.png','_obj.txt')
        os.rename(objPath, txtPath)

        return 'Successful 3D reconstruction'

    else:
        return 'Unable to perform 3D reconstruction'

@app.route('/txtfile')
def handle_file():
    return flask.send_file('Data/examples/test_obj.txt')

#Route to handle GET request to URL server
@app.route('/connect',methods=["GET","POST"])
def handle_get():
    return "Successful Flask server connection"

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0',port=5000)
