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
        sh 'mvn clean package'
      }
    }

  }
}