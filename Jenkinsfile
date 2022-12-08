pipeline {
  agent any
  stages {
    stage('Input') {
      steps {
        input(message: 'Input Test', id: 'test')
      }
    }

  }
  environment {
    maven = 'M3'
  }
}