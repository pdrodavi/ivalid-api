pipeline {
  agent any
  stages {
    stage('Checkout') {
      parallel {
        stage('Checkout') {
          steps {
            git 'https://github.com/pdrodavi/ivalid-api.git'
          }
        }

        stage('') {
          steps {
            sh 'sh \'echo hello\''
          }
        }

      }
    }

  }
}