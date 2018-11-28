package com.jenkinspip.utils

jnlpImage="jenkins/jnlp-slave:3.27-1"

dockerImage='docker:latest'
mavenImage='maven:latest'
gradleImage='gradle:latest'
javaImage='java:latest'

public void dockerTemplate(label='docker', body) {
  podTemplate(label: label,
    containers: [
      containerTemplate(name: 'docker', image: dockerImage, command: 'cat', ttyEnabled: true),
      containerTemplate(name: 'jnlp', image: jnlpImage, args: '${computer.jnlpmac} ${computer.name}')
      ],
    volumes: [
      hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')
      ]
  ) {
      body()
    }
}

public void mavenTemplate(label='maven', body) {
  podTemplate(label: label,
    containers: [
      containerTemplate(name: 'maven', image: mavenImage, command: 'cat', ttyEnabled: true),
      containerTemplate(name: 'jnlp', image: jnlpImage, args: '${computer.jnlpmac} ${computer.name}')
      ]
    ) {
      body()
    }
}

public void gradleTemplate(label='gradle', body) {
  podTemplate(label: label,
    containers: [
      containerTemplate(name: 'gradle', image: gradleImage, command: 'cat', ttyEnabled: true),
      containerTemplate(name: 'jnlp', image: jnlpImage, args: '${computer.jnlpmac} ${computer.name}')
      ]
    ) {
      body()
    }
}


/*
    Template with the yaml syntax
*/

public void mdockerYamlTemplate(label='mdocker', body) {
  podTemplate(label: label, yaml: """
apiVersion: v1
kind: Pod
spec:
  securityContext:
    runAsUser: 0
  containers:
  - name: jnlp
    image: ${jnlpImage}
    args: ['\$(JENKINS_SECRET)', '\$(JENKINS_NAME)']
    volumeMounts:
    - name: dockersock
      mountPath: /var/run/docker.sock
    - name: dockerbin
      mountPath: /usr/bin/docker
    - name: dockerlib
      mountPath: /usr/lib/libltdl.so.7
    - name: dockeretc
      mountPath: /etc/docker
  volumes:
  - name: dockersock
    hostPath:
      path: /var/run/docker.sock
      type: Socket
  - name: dockerbin
    hostPath:
      path: /usr/bin/docker
  - name: dockerlib
    hostPath:
      path: /usr/lib64/libltdl.so.7.3.0
  - name: dockeretc
    hostPath:
      path: /etc/docker
"""
  ) {
      body()
    }
}


public void dockerYamlTemplate(label='docker', body) {
  podTemplate(label: label, yaml: """
apiVersion: v1
kind: Pod
spec:
  securityContext:
    runAsUser: 0
  containers:
  - name: docker
    image: ${dockerImage}
    command: ['cat']
    tty: true
    volumeMounts:
    - name: dockersock
      mountPath: /var/run/docker.sock
  - name: jnlp
    image: ${jnlpImage}
    args: ['\$(JENKINS_SECRET)', '\$(JENKINS_NAME)']
  volumes:
  - name: dockersock
    hostPath:
      path: /var/run/docker.sock
      type: Socket
"""
  ) {
      body()
    }
}


public void javadockerYamlTemplate(label='javadocker', body) {
  podTemplate(label: label, yaml: """
apiVersion: v1
kind: Pod
spec:
  securityContext:
    runAsUser: 0
  containers:
  - name: java
    image: ${javaImage}
    command: ['cat']
    tty: true
  - name: docker
    image: ${dockerImage}
    command: ['cat']
    tty: true
    volumeMounts:
    - name: dockersock
      mountPath: /var/run/docker.sock
  - name: jnlp
    image: ${jnlpImage}
    args: ['\$(JENKINS_SECRET)', '\$(JENKINS_NAME)']
  volumes:
  - name: dockersock
    hostPath:
      path: /var/run/docker.sock
      type: Socket
"""
  ) {
      body()
    }
}


public void mavenYamlTemplate(label='maven', body) {
  podTemplate(label: label, yaml: """
apiVersion: v1
kind: Pod
spec:
  securityContext:
    runAsUser: 0
  containers:
  - name: maven
    image: ${mavenImage}
    command: ['cat']
    tty: true
  - name: jnlp
    image: ${jnlpImage}
    args: ['\$(JENKINS_SECRET)', '\$(JENKINS_NAME)']
"""
    ) {
      body()
    }
}

public void gradleYamlTemplate(label='gradle', body) {
  podTemplate(label: label, yaml: """
apiVersion: v1
kind: Pod
spec:
  securityContext:
    runAsUser: 0
  containers:
  - name: gradle
    image: ${gradleImage}
    command: ['cat']
    tty: true
  - name: jnlp
    image: ${jnlpImage}
    args: ['\$(JENKINS_SECRET)', '\$(JENKINS_NAME)']
"""
    ) {
      body()
    }
}

public void mavendockerYamlTemplate(label='docker', body) {
  podTemplate(label: label, yaml: """
apiVersion: v1
kind: Pod
spec:
  securityContext:
    runAsUser: 0
  containers:
  - name: maven
    image: ${mavenImage}
    command: ['cat']
    tty: true
  - name: docker
    image: ${dockerImage}
    command: ['cat']
    tty: true
    volumeMounts:
    - name: dockersock
      mountPath: /var/run/docker.sock
  - name: jnlp
    image: ${jnlpImage}
    args: ['\$(JENKINS_SECRET)', '\$(JENKINS_NAME)']
  volumes:
  - name: dockersock
    hostPath:
      path: /var/run/docker.sock
      type: Socket
"""
  ) {
      body()
    }
}

public void gradledockerYamlTemplate(label='docker', body) {
  podTemplate(label: label, yaml: """
apiVersion: v1
kind: Pod
spec:
  securityContext:
    runAsUser: 0
  containers:
  - name: gradle
    image: ${gradleImage}
    command: ['cat']
    tty: true
  - name: docker
    image: ${dockerImage}
    command: ['cat']
    tty: true
    volumeMounts:
    - mountPath: /var/run/docker.sock
      name: dockersock
  - name: jnlp
    image: ${jnlpImage}
    args: ['\$(JENKINS_SECRET)', '\$(JENKINS_NAME)']
  volumes:
  - name: dockersock
    hostPath:
      path: /var/run/docker.sock
      type: Socket
"""
  ) {
      body()
    }
}


return this
