#Install Ubuntu and update packages
FROM "ubuntu:trusty"
FROM python:2.7.9
RUN apt-get update && yes | apt-get upgrade

#Create directory of the docker image
RUN mkdir -p /app

#Install zip & unzip
RUN apt-get install unzip

#Install pip
RUN apt-get install -y git python-pip
RUN pip install --upgrade pip

#Clone deep learning model (Pixel2Mesh) repo
RUN git clone https://github.com/nywang16/Pixel2Mesh.git /app

WORKDIR /app
COPY . /app

#Install TensorFlow 1.3.0
RUN pip install --ignore-installed six -U pre tensorflow==1.3.0

#Install TFlearn
RUN pip install git+https://github.com/tflearn/tflearn.git

#Install more dependencies
RUN pip install -r requirements.txt

#Download zip of trained model to correct directory
RUN wget --load-cookies /tmp/cookies.txt "https://docs.google.com/uc?export=download&confirm=$(wget --quiet --save-cookies /tmp/cookies.txt \
    --keep-session-cookies --no-check-certificate 'https://docs.google.com/uc?export=download&id=1gD-dk-XrAa5mfrgdZSunjaS6pUUWsZgU' \
    -O- | sed -rn 's/.*confirm=([0-9A-Za-z_]+).*/\1\n/p')&id=1gD-dk-XrAa5mfrgdZSunjaS6pUUWsZgU" -O /app/Data/checkpoint.zip && rm -rf /tmp/cookies.txt

RUN unzip /app/Data/checkpoint.zip -d /app/Data

#Once container starts, start up the flask server
ENTRYPOINT ["python"]

CMD ["app.py", "flask", "run", "--host", "0.0.0.0"]
