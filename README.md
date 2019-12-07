# MyModels

This mobile application project utilizes nywang16's repository of Pixel2Mesh to perform 3D reconstruction. This is done for research purposes and not for commercial distribution. To run this project it requires the user to be able to run Docker on a local machine and be familiar with its commands.

## Installation

#### System Requirements
Android Studio/Android Device <br/>
Docker Desktop <br/>
Flask <br/>

### Installation Guide
Inside of terminal run the following command
```shell
git clone https://github.com/dref11/MyModels.git
```
Once you have cloned the repository, the folder called FlaskServer will be used to setup the Docker container. Move this folder outside of the repository. From your terminal window cd into this folder. Make sure that you have Docker desktop running on your machine and then execute the following command
```shell
docker build -t mymodels:latest .
```
This make take a few minutes depending on your machine and internet connection. Once finished this will create an image to run Pixel2Mesh and a success message will be displayed. You can also verify this by using the following command
```shell
docker images --all
```
and you should see the newly created Docker image listed. Next, we need to run an instance of this image. To do so run the following command
```shell
docker run -d -p 5000:5000 mymodel:1.0
```
This will create a Docker container in detached mode and route the exposed port 5000 of the container to port 5000 of the local machine. To view all containers running use the following command
```shell
docker ps --all
```
If successful the Flask server should be live and can be verified by
```shell
curl http://0.0.0.0:5000/connect
```
A message will then be displayed notifying a successful connection to the Flask server from inside the Docker container. Next we will have to modify a few lines of code in order for the model to perform reconstruction. We will first run
```shell
docker exec -t -i <container id> /bin/bash
```
This will put the container into interactive mode and let us modify files inside the container from the terminal window.
```shell
cd p2m
```
and then open layers.py. We will have to modify the input shapes of x2 in  line 29 and y2 in line 31 to the following
```python
x2 = tf.minimum(tf.ceil(x), tf.cast(tf.shape(img_feat)[0], tf.float32) - 1)
y2 = tf.minimum(tf.ceil(y), tf.cast(tf.shape(img_feat)[1], tf.float32) - 1)
```
Next, open the Android studio folder and the application will be able to successfully connect to the running server. To stop the running container use
```shell
docker stop <container id>
```
