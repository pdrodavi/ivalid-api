pipeline {
    
  agent {
    kubernetes {
      yamlFile 'docker-maven-jnlp-pod-template.yaml'
    }
  }
  
  tools {
    maven "M3"
  }
  
  stages {

    stage('Checkout') {
      steps {
        container('maven') {
          git branch: 'release/1.0.0', changelog: false, poll: false, url: 'https://github.com/pdrodavi/ivalid-api.git'
        }
      }
    }  

    stage('Package') {
      steps {
          script {
            println("Realizando construção do artefato")
            println("Artifact: " + readMavenPom().getArtifactId())
            println("Version: " + readMavenPom().getVersion())
            sh "mvn -Dmaven.test.skip=true -Dmaven.test.failure.ignore package"
          }
      }
    }

    stage('Build Image') {
      steps {
        container('docker') {
          println("Criando a imagem Docker")
          sh "docker build -t ${readMavenPom().getArtifactId()}:${readMavenPom().getVersion()} ."
        }
      }
    }
  }
}
