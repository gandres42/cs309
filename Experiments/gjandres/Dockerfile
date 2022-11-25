FROM ubuntu:latest
RUN apt-get update
RUN apt-get install -y apt-utils
RUN apt-get install -y openjdk-11-jdk-headless gradle android-sdk wget unzip
RUN wget https://dl.google.com/android/repository/commandlinetools-linux-6609375_latest.zip
RUN unzip commandlinetools-linux-6609375_latest.zip -d cmdline-tools
RUN mv cmdline-tools /usr/lib/android-sdk/
RUN export PATH="/usr/lib/android-sdk/cmdline-tools/tools/bin:$PATH"
RUN yes | /usr/lib/android-sdk/cmdline-tools/tools/bin/sdkmanager "platform-tools" "platforms;android-28"
ENV ANDROID_HOME="/usr/lib/android-sdk/"
