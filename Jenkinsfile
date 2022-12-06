pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        git(url: 'https://github.com/pdrodavi/ivalid-api.git', branch: 'jfrog')
      }
    }

    stage('Build') {
      steps {
        sh 'sh \'mvn -B -DskipTests clean package\''
      }
    }

  }
  environment {
    maven = 'M3'
  }
}